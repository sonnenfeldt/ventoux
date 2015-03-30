package de.sonnenfeldt.lavisgrafix.controller;

import de.sonnenfeldt.lavisgrafix.dao.AssetRepository;
import de.sonnenfeldt.lavisgrafix.model.Asset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

@Controller
public class AssetController {
	
	static Logger log = Logger.getLogger(AssetController.class.getName());

	private static String MAIN_VIEW = "main";
		
	private AssetRepository repo;
	
	@Autowired
	public AssetController(AssetRepository repo) {
		this.repo = repo;
	}

	
	//show all assets
	@RequestMapping(value = "/")
	public String getAllAssets(Model model) {
		model.addAttribute("assets", repo.getAll());
		String view = MAIN_VIEW;
		log.debug("AssetController::getAllAssets() returns view: " + view);
		return view;
	}

	
	// REST end-points
	// curl http://localhost:8080/LAVisGrafiX/assets/
	@RequestMapping(value = {"/assets","/assets/"}, method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Asset> getAllAssets() {
		List<Asset> assetList = repo.getAll();
		log.debug("AssetController::getAllAssets() returns: " + assetList);
		return assetList;
	}

	// curl http://localhost:8080/LAVisGrafiX/assets/{id}
	@RequestMapping(value = "/assets/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Asset getAsset(@PathVariable String id) {
		Asset asset = repo.findById(id);
		log.debug("AssetController::getAsset() returns: " + asset.toJsonString());		
		return asset;
	}

	
	@RequestMapping(value = "/assets/", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	public void createAsset(@RequestBody Asset asset, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("AssetController::createAsset() received asset: " + asset.toJsonString());
		repo.insert(asset);
		String location = getLocationForAssetResource(asset, request);
		response.addHeader("Location", location);
		log.debug("AssetController::createAsset() Location: " + location);
	}
	
	
	@RequestMapping(value = "{/assets/{id}}", method = RequestMethod.PUT, consumes = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateAsset(@PathVariable String id, @RequestBody Asset asset) {
		Asset assetToUpdate = repo.findById(id);
		asset.setUuid(assetToUpdate.getUuid());
		log.debug("AssetController:updateAsset() received asset: " + asset.toJsonString());
		repo.update(asset);
	}	
	
	/*
	 * curl -v -H "Content-Type: application/json" -X DELETE localhost:8080/LAVisGrafiX/assets/938bcfce-b39b-4a37-b048-0d9d543a7029
	 */
	
	@RequestMapping(value = "/assets/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAsset(@PathVariable String id) {
		log.debug("AssetController::deleteAsset delete asset: " + id);			
		repo.delete(id);
	}	
	
	// Helper methods
	private String getLocationForAssetResource(Asset asset,
			HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		UriTemplate template = new UriTemplate(url.append("/{Id}").toString());
		return template.expand(asset.getUuid(), template).toASCIIString();
	}	
	
	// Exception handler for findById if "Asset" item does not exist in repo
	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void notFound() {
		log.debug("AssetController::notFound(): No asset found.");				
	}

}

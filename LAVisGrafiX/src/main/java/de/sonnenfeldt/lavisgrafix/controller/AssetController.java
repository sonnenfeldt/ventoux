package de.sonnenfeldt.lavisgrafix.controller;

import de.sonnenfeldt.lavisgrafix.service.AssetService;
import de.sonnenfeldt.lavisgrafix.service.CategoryService;
import de.sonnenfeldt.lavisgrafix.service.UserService;
import de.sonnenfeldt.lavisgrafix.model.Asset;
import de.sonnenfeldt.lavisgrafix.model.Category;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	public static String MAIN_VIEW = "main";
	public static String SINGLE_ASSET_VIEW = "asset";
		
	private AssetService assetService;
	private CategoryService categoryService;
	private UserService userService;
	
	@Autowired
	public AssetController(AssetService assetService, CategoryService categoryService, UserService userService) {
		this.assetService = assetService;
		this.categoryService = categoryService;
		this.userService = userService;
	}
	
	//show all assets
	@RequestMapping("/")
	public String getAllAssets(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		if (authentication != null) {
			username = authentication.getName();	
		}
		model.addAttribute("assets", assetService.getAll());
		model.addAttribute("user", userService.getUser(username));
		String view = MAIN_VIEW;
		log.debug("AssetController::getAllAssets() returns view: " + view);
		return view;
	}

    @RequestMapping({"/assets","/assets/"})
    String favicon() {
    	return "redirect:/";
    }
	
	
	//show single assets
	@RequestMapping("/assets/{id}")
	public String getSingleAsset(Model model, @PathVariable String id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		if (authentication != null) {
			username = authentication.getName();	
		}
		Asset asset = assetService.getAsset(id,username);
		List<Category> categories = categoryService.getAll();
		model.addAttribute("asset", asset);
		model.addAttribute("categories", categories);
		model.addAttribute("user", userService.getUser(username));	
		String view = SINGLE_ASSET_VIEW;
		log.debug("AssetController::getSingleAsset() returns view: " + view);
		return view;
	}	
	
	
	@RequestMapping(value = "/assets/update/{id}", method = RequestMethod.POST)
	public String updateAsset(@RequestParam String description, @RequestParam String category, @RequestParam String keywords, @RequestParam String rating, @PathVariable String id) {
		assetService.updateAsset(id, description, category, keywords, rating);
		log.debug("AssetController::updateAsset() parameters: id=" + id + " description=" + description + " category=" + category + " keywords=" + keywords + " rating=" + rating);	
		return "redirect:/assets/" + id;
	}	
	
	// REST end-points
	// curl http://localhost:8080/LAVisGrafiX/assets/
	@RequestMapping(value = {"/assets","/assets/"}, method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Asset> getAllAssets() {
		List<Asset> assetList = assetService.getAll();
		log.debug("AssetController::getAllAssets() returns: " + assetList);
		return assetList;
	}

	// curl http://localhost:8080/LAVisGrafiX/assets/{id}
	@RequestMapping(value = "/assets/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Asset getAsset(@PathVariable String id) {
		log.debug("AssetController::getAsset() with uuid: " + id);	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		if (authentication != null) {
			username = authentication.getName();	
		}
		Asset asset = assetService.getAsset(id,username);
		if (asset != null) {
			log.debug("AssetController::getAsset() returns: " + asset.toJsonString());	
		} else {
			log.debug("AssetController::getAsset() returns null.");		
		}
		
		return asset;
	}

	
	@RequestMapping(value = "/assets/", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	public void createAsset(@RequestBody Asset asset, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("AssetController::createAsset() received asset: " + asset.toJsonString());
		assetService.insert(asset);
		String location = getLocationForAssetResource(asset, request);
		if (location != null) {
			response.addHeader("Location", location);
		}
		log.debug("AssetController::createAsset() Location: " + location);
	}
	
	
	@RequestMapping(value = "{/assets/{id}}", method = RequestMethod.PUT, consumes = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateAsset(@PathVariable String id, @RequestBody Asset asset) {
		log.debug("AssetController::updateAsset() with uuid: " + id);		
		Asset assetToUpdate = assetService.findById(id);
		if (assetToUpdate != null) {
			asset.setUuid(assetToUpdate.getUuid());
			log.debug("AssetController:updateAsset() updating asset: " + asset.toJsonString());
			assetService.update(asset);
		} else {
			log.debug("AssetController::updateAsset() asset not found.");		
		}
	}	
	
	/*
	 * curl -v -H "Content-Type: application/json" -X DELETE localhost:8080/LAVisGrafiX/assets/938bcfce-b39b-4a37-b048-0d9d543a7029
	 */
	
	@RequestMapping(value = "/assets/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAsset(@PathVariable String id) {
		log.debug("AssetController::deleteAsset delete asset: " + id);			
		assetService.delete(id);
	}	
	
	// Helper methods
	private String getLocationForAssetResource(Asset asset,
			HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		
		String result = null;
		if (url != null) {
			UriTemplate template = new UriTemplate(url.append("/{Id}").toString());
			result = template.expand(asset.getUuid(), template).toASCIIString();		
		}
		
		return result;
	}	
	
	// Exception handler for findById if "Asset" item does not exist in assetService
	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void notFound() {
		log.debug("AssetController::notFound(): No asset found.");				
	}

}

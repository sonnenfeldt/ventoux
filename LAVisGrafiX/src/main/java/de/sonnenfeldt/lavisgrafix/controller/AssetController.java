package de.sonnenfeldt.lavisgrafix.controller;

import de.sonnenfeldt.lavisgrafix.dao.AssetRepository;
import de.sonnenfeldt.lavisgrafix.model.Asset;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	private AssetRepository repro;
	
	@Autowired
	public AssetController(AssetRepository repro) {
		this.repro = repro;
	}

	
	//show all assets
	@RequestMapping(value = "/assets/list")
	public String getAllAssets(Model model) {
		model.addAttribute("assets", repro.getAll());
		return "asset";
	}

	
	@RequestMapping(value = "/assets/add", method = RequestMethod.POST)
	public String create(Asset asset) {
		repro.insert(asset);
		return "redirect:all";
	}

	@RequestMapping(value = "/assets/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable String id) {
		repro.delete(id);
		return "redirect:all";
	}


	// REST end-points
	// curl http://localhost:8080/LAVisGrafiX/assets/
	@RequestMapping(value = { "all", "/assets/" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Asset> getAllAssets() {
		return repro.getAll();
	}

	// curl http://localhost:8080/LAVisGrafiX/assets/{id}
	@RequestMapping(value = "/assets/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Asset getAsset(@PathVariable String id) {
		return repro.findById(id);
	}

	@RequestMapping(value = "/assets/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAsset(@PathVariable String id) {
		repro.delete(id);
	}

	@RequestMapping(value = "/assets/", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Asset asset, HttpServletRequest request,
			HttpServletResponse response) {
		repro.insert(asset);
		String location = getLocationForTodoResource(asset, request);
		response.addHeader("Location", location);
	}

	// curl -v -H "Content-Type: application/json" -X PUT <Location> -d
	// '{"text":"New Todo Updateâ€�}â€™
	@RequestMapping(value = "/assets/{id}", method = RequestMethod.PUT, consumes = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable String id, @RequestBody Asset asset) {
		Asset assetStored = (Asset) repro.findById(id);
		asset.setUuid(assetStored.getUuid());
		repro.update(asset);
	}

	// Helper methods
	private String getLocationForTodoResource(Asset asset,
			HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		UriTemplate template = new UriTemplate(url.append("/{childId}")
				.toString());
		return template.expand(asset.getUuid(), template).toASCIIString();
	}

	// Exception handler for findById if "Todo" item does not exist in repo
	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void notFound() {
	}	
	
}

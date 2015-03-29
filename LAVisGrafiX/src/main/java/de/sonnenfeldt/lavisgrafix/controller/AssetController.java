package de.sonnenfeldt.lavisgrafix.controller;

import de.sonnenfeldt.lavisgrafix.dao.AssetRepository;
import de.sonnenfeldt.lavisgrafix.model.Asset;

import java.lang.annotation.Annotation;
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
	@RequestMapping(value = "/")
	public String getAllAssets(Model model) {
		model.addAttribute("assets", repro.getAll());
		return "main";
	}

	
	// REST end-points
	// curl http://localhost:8080/LAVisGrafiX/assets/
	@RequestMapping(value = "/assets/", method = RequestMethod.GET, produces = "application/json")
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

	// Exception handler for findById if "Asset" item does not exist in repo
	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void notFound() {
	}


	
}

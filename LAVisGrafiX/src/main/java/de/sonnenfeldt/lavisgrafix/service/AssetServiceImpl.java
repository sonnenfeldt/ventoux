package de.sonnenfeldt.lavisgrafix.service;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.sonnenfeldt.lavisgrafix.model.Asset;
import de.sonnenfeldt.lavisgrafix.model.Category;
import de.sonnenfeldt.lavisgrafix.model.Keyword;
import de.sonnenfeldt.lavisgrafix.model.UserRating;
import de.sonnenfeldt.lavisgrafix.dao.AssetRepository;



@Component
@Transactional
public class AssetServiceImpl implements AssetService {
	
	static Logger log = Logger.getLogger(AssetServiceImpl.class.getName());	
	
	private AssetRepository assetRepo;
	private CategoryService categoryService;
	private KeywordService keywordService;
	
	@Autowired	
	public AssetServiceImpl(AssetRepository assetRepo, CategoryService categoryService, KeywordService keywordService) {
		this.assetRepo = assetRepo;
		this.categoryService = categoryService;
		this.keywordService = keywordService;
	}

	public void insert(Asset asset) {
		assetRepo.insert(asset);
	}
	
	public void insert(Asset asset, List<Category> categories,
			List<Keyword> keywords, UserRating rating) {
		assetRepo.insert(asset);
		
		Iterator<Category> categoryIterator = categories.iterator();
		while (categoryIterator.hasNext()) {
			assetRepo.addCategory(asset, categoryIterator.next());
		}
		
		Iterator<Keyword> keywordIterator = keywords.iterator();
		while (keywordIterator.hasNext()) {
			assetRepo.addKeyword(asset, keywordIterator.next());
		}

		assetRepo.addUserRating(rating);
		
	}
	
	public void delete(String uuid) {
		assetRepo.delete(uuid);
	}

	public void update(Asset asset) {
		assetRepo.update(asset);
	}
	
	public void update(Asset asset, List<Category> categories,
			List<Keyword> keywords, UserRating rating) {
		assetRepo.update(asset);
		
		Iterator<Category> categoryIterator = categories.iterator();
		while (categoryIterator.hasNext()) {
			assetRepo.addCategory(asset, categoryIterator.next());
		}
		
		Iterator<Keyword> keywordIterator = keywords.iterator();
		while (keywordIterator.hasNext()) {
			assetRepo.addKeyword(asset, keywordIterator.next());
		}

		assetRepo.addUserRating(rating);
		
	}

	public List<Asset> getAll() {
		List<Asset> assets = assetRepo.getAll();
		
		Collections.reverse(assets);
		
		return assets; 
	}

	public Asset findById(String uuid) {
		Asset asset = assetRepo.findById(uuid);
		return asset;
	}

	public Asset getAsset(String uuid, String username) {
		log.debug("AssetService::getAsset() parameters:" + uuid + ", " + "username: " + username);	
		Asset asset = null;
		asset = assetRepo.findById(uuid);
		if (asset != null) {
			asset.setCategories(getCategories(asset));
			asset.setCategoryNames(getCategoryNames(asset));
			asset.setKeywordNames(getKeywordNames(asset));
			asset.setKeywordNamesAsCSV(getKeywordsNamesAsCSV(asset));
			asset.setUser(username);
			UserRating rating = getUserRating(asset,username);
			if (rating != null) {
				asset.setRating(rating.getRating());				
			}
		}
		log.debug("AssetService::getAsset() for asset_uuid:" + uuid + ", " + "username: " + username + "returned: " + asset.toJsonString());	
		return asset;
	}
	
	public List<Category> getCategories(Asset asset) {
		return assetRepo.getCategories(asset);
	}

	private String[] getCategoryNames(Asset asset) {
		String[] array = null;
		List<Category> categories = assetRepo.getCategories(asset);
		if (categories != null) {
			array = new String [categories.size()];
			Iterator<Category> categoryIterator = categories.iterator();
			int counter = 0;
			while (categoryIterator.hasNext()) {
				array[counter] = categoryIterator.next().getName();
				counter++;
			}			
		}
		return array;
	}
	
	private String getKeywordsNamesAsCSV(Asset asset) {
		String keywordNamesAsCSV = new String();
		List<Keyword> keywords = assetRepo.getKeywords(asset);
		if (keywords != null) {
			Iterator<Keyword> keywordsIterator = keywords.iterator();
			while (keywordsIterator.hasNext()) {
				keywordNamesAsCSV = keywordNamesAsCSV.concat(keywordsIterator.next().getName());
				if (keywordsIterator.hasNext()) {
					keywordNamesAsCSV = keywordNamesAsCSV.concat(", ");
				}
				
			}			
		}
		return keywordNamesAsCSV;
	}
		
	private String[] getKeywordNames(Asset asset) {
		String[] array = null;
		List<Keyword> keywords = assetRepo.getKeywords(asset);
		if (keywords != null) {
			array = new String [keywords.size()];
			Iterator<Keyword> keywordIterator = keywords.iterator();
			int counter = 0;
			while (keywordIterator.hasNext()) {
				array[counter] = keywordIterator.next().getName();
				counter++;
			}			
		}
		return array;
	}	
	
	public List<Keyword> getKeywords(Asset asset) {
		return assetRepo.getKeywords(asset);
	}

	public UserRating getUserRating(Asset asset, String username) {
		return assetRepo.getUserRating(asset, username);	
	}

	public void updateAsset(String asset_uuid, String description,
			String category, String keywords, String rating) {
		Asset asset = findById(asset_uuid);
		if (asset != null) {
			asset.setDescription(description);
			assetRepo.update(asset);
			categoryService.addCategory(category, asset_uuid);
			keywordService.addKeywords(keywords, asset_uuid);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();	
			
			assetRepo.deleteUserRating(asset,username);
			
			UserRating userRating = new UserRating();
			userRating.setAsset_uuid(asset_uuid);
			userRating.setUsername(username);
			userRating.setRating(rating);
			
			assetRepo.addUserRating(userRating);
						
		}
	} 

}

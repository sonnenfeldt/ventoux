package de.sonnenfeldt.lavisgrafix.service;

import java.util.List;

import de.sonnenfeldt.lavisgrafix.model.Asset;
import de.sonnenfeldt.lavisgrafix.model.Keyword;
import de.sonnenfeldt.lavisgrafix.model.Category;
import de.sonnenfeldt.lavisgrafix.model.Property;
import de.sonnenfeldt.lavisgrafix.model.UserRating;
import de.sonnenfeldt.lavisgrafix.model.User;

public interface AssetService {

	void insert(Asset asset);
	public void insert(Asset asset, List<Category> categories, List<Keyword> keywords, UserRating rating);	
	void delete(String uuid);
	public void update(Asset asset);
	public void updateAsset(String asset_id, String description, String category, String keywords, String rating);
	void update(Asset asset, List<Category> categories, List<Keyword> keywords, UserRating rating);
	List<Asset> getAll();
	Asset findById(String uuid);
	public Asset getAsset(String uuid, String username);
	List<Category> getCategories(Asset asset);
	List<Keyword> getKeywords(Asset asset);
	UserRating getUserRating(Asset asset, String username);
	

}

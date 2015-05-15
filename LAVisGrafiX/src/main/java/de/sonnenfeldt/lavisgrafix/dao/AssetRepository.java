package de.sonnenfeldt.lavisgrafix.dao;
import java.util.List;

import de.sonnenfeldt.lavisgrafix.model.Asset;
import de.sonnenfeldt.lavisgrafix.model.Category;
import de.sonnenfeldt.lavisgrafix.model.Keyword;
import de.sonnenfeldt.lavisgrafix.model.UserRating;


public interface AssetRepository {
	void insert(Asset asset);
	void delete(String uuid);
	void update(Asset asset);
	List<Asset> getAll();
	Asset findById(String uuid);	
	
	List<Category> getCategories(Asset asset);
	List<Category> getAllCategories();
	List<Keyword> getKeywords(Asset asset);
	UserRating getUserRating(Asset asset, String username);
	
	void addCategory(Asset asset, Category category);
	void addKeyword(Asset asset, Keyword keyword);
	void addUserRating(UserRating userRating);
	void deleteUserRating(Asset asset, String username);
}

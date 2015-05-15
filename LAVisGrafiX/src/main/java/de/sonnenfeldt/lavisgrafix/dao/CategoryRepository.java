package de.sonnenfeldt.lavisgrafix.dao;

import java.util.List;

import de.sonnenfeldt.lavisgrafix.model.Category;
import de.sonnenfeldt.lavisgrafix.model.Keyword;

public interface CategoryRepository {
	
	void insert(Category category);
	void delete(String uuid);
	List<Category> getAll();
	Category findById(String uuid);	
	Category findByName(String name);

	List<Category> getCategories(String asset_uuid);
	void addCategory(Category category, String asset_uuid);
	void deleteCategories(String asset_uuid);

}

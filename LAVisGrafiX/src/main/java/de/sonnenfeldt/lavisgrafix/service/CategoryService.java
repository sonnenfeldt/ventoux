package de.sonnenfeldt.lavisgrafix.service;

import java.util.List;

import de.sonnenfeldt.lavisgrafix.model.Category;

public interface CategoryService {
	List<Category> getAll();
	public void addCategory(String categoryName, String asset_uuid);
}

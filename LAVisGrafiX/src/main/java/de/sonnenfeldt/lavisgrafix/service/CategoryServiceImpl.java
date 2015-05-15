package de.sonnenfeldt.lavisgrafix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.sonnenfeldt.lavisgrafix.model.Category;
import de.sonnenfeldt.lavisgrafix.dao.CategoryRepository;

@Component
@Transactional
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepo;
	
	@Autowired	
	public CategoryServiceImpl(CategoryRepository categoryRepo) {
		this.categoryRepo = categoryRepo;
	}

	public List<Category> getAll() {
		return categoryRepo.getAll();
	}

	public void addCategory(String categoryName, String asset_uuid) {
		Category category = categoryRepo.findByName(categoryName);
		categoryRepo.deleteCategories(asset_uuid);
		categoryRepo.addCategory(category, asset_uuid);
	}

}

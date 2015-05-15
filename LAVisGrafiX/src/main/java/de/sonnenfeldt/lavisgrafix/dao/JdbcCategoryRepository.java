package de.sonnenfeldt.lavisgrafix.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import de.sonnenfeldt.lavisgrafix.model.Category;

@Component
public class JdbcCategoryRepository implements CategoryRepository {

	static Logger log = Logger.getLogger(JdbcCategoryRepository.class.getName());
	
	private JdbcTemplate jdbcTemplate;
		
	@Autowired
	public JdbcCategoryRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcCategoryRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public void insert(Category category) {
		try {
			jdbcTemplate.update("insert into lvg_category (id, name, description) values(?,?,?)",
					category.getUuid(), category.getName(), category.getDescription());		
			log.debug("category insert success: " + category.toJsonString());
		} catch (Exception e) {
			log.debug("category insert failed: " + category.toJsonString());
			log.debug("category insert failed: " + e.toString());
		}
		
	}

	public void delete(String uuid) {
		try {
			jdbcTemplate.update("delete from lvg_category where id=?", uuid);
			log.debug("category delete success: " + uuid);
		} catch (Exception e) {
			log.debug("category delete failed: " + uuid);
			log.debug("category delete failed: " + e.toString());		}	
		
	}

	public List<Category> getAll() {
		return jdbcTemplate.query("select id, name, description from lvg_category",
				new CategoryRowMapper());
	}

	public Category findById(String uuid) {
		Category category = null;
		
		try {
			category = jdbcTemplate.queryForObject(
					"select id, name, description from lvg_category where id=?",
					new CategoryRowMapper(), uuid);	
			log.debug("category findById success: " + category.toJsonString());
		} catch (EmptyResultDataAccessException e) {
			log.debug("category findById failed: " + uuid);	
			log.debug("category findById failed: " + e.toString());
		} catch (Exception e) {
			log.debug("category findById failed: " + uuid);	
			log.debug("category findById failed: " + e.toString());
		}

		return category;
	}

	public Category findByName(String name) {
		Category category = null;
		
		try {
			category = jdbcTemplate.queryForObject(
					"select id, name, description from lvg_category where name=?",
					new CategoryRowMapper(), name);	
			log.debug("category findById success: " + category.toJsonString());
		} catch (EmptyResultDataAccessException e) {
			log.debug("category findById failed: " + name);	
			log.debug("category findById failed: " + e.toString());
		} catch (Exception e) {
			log.debug("category findById failed: " + name);	
			log.debug("category findById failed: " + e.toString());
		}

		return category;
	}		
	
	class CategoryRowMapper implements RowMapper<Category> {

		public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
			String id = rs.getString("id");
			String name = rs.getString("name");
			String description = rs.getString("description");
			Category category = new Category();
			category.setUuid(id);
			category.setName(name);
			category.setDescription(description);
			return category;
		}	
	
	
	}

	public List<Category> getCategories(String asset_uuid) {
		List<Category> categories = null;
		try {	
			categories = jdbcTemplate.query("select id, name, description from lvg_category where id in (select category_id from lvg_category_classifying_asset where asset_id=?)", new CategoryRowMapper(), asset_uuid);
		} catch (Exception e) {
			log.debug("getCategory failed for asset_uuid: " + asset_uuid);	
			log.debug("getCategory failed: " + e.toString());
		}		
		return categories;
	}

	public void addCategory(Category category, String asset_uuid) {
		
		Category existingCategory = findById(category.getUuid());
		if (existingCategory == null) {
			log.debug("addCategory adding new category: " + category.toJsonString());			
			insert(category);
		} 

		try {
			
			jdbcTemplate.update("insert into lvg_category_classifying_asset (category_id, asset_id) values(?,?)",
					category.getUuid(), asset_uuid);		
			log.debug("addCategory insert category_classfiying_asset success: " + category.toJsonString() + " " + asset_uuid);
		} catch (Exception e) {
			log.debug("addCategory insert category_classfiying_asset failed: " + category.toJsonString());
			log.debug("addCategory insert category_classfiying_asset failed: " + e.toString());
		}
				
	}

	public void deleteCategories(String asset_uuid) {
		try {
			jdbcTemplate.update("delete from lvg_category_classifying_asset where asset_id=?",asset_uuid);
		} catch (Exception e) {
			log.debug("deleteCategories failed: " + e.toString());
		}
		
	}


	
}

package de.sonnenfeldt.lavisgrafix.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import de.sonnenfeldt.lavisgrafix.model.Asset;
import de.sonnenfeldt.lavisgrafix.model.Category;
import de.sonnenfeldt.lavisgrafix.model.Keyword;
import de.sonnenfeldt.lavisgrafix.model.UserRating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import de.sonnenfeldt.lavisgrafix.dao.CategoryRepository;
import de.sonnenfeldt.lavisgrafix.dao.JdbcCategoryRepository;
import de.sonnenfeldt.lavisgrafix.dao.KeywordRepository;
import de.sonnenfeldt.lavisgrafix.dao.JdbcKeywordRepository;
import de.sonnenfeldt.lavisgrafix.dao.UserRatingRepository;
import de.sonnenfeldt.lavisgrafix.dao.JdbcUserRatingRepository;

@Component
public class JdbcAssetRepository  implements AssetRepository {

	static Logger log = Logger.getLogger(JdbcAssetRepository.class.getName());
	
	private JdbcTemplate jdbcTemplate;
	
	private CategoryRepository categoryRepo;
	private KeywordRepository keywordRepo;
	private UserRatingRepository userRatingRepo;
	
		
	@Autowired
	public JdbcAssetRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		categoryRepo = new JdbcCategoryRepository(jdbcTemplate);
		keywordRepo = new JdbcKeywordRepository(jdbcTemplate);
		userRatingRepo = new JdbcUserRatingRepository(jdbcTemplate);
	}

	public void insert(Asset asset) {
		try {
			jdbcTemplate.update("insert into lvg_asset (id, name, description, location,thumbnail_location) values(?,?,?,?,?)",
					asset.getUuid(), asset.getName(), asset.getDescription(), asset.getLocation(), asset.getThumbnailLocation());		
			log.debug("asset insert success: " + asset.toJsonString());
		} catch (Exception e) {
			log.debug("asset insert failed: " + asset.toJsonString());
			log.debug("asset insert failed: " + e.toString());
		}
		
	}

	public void delete(String uuid) {
		try {
			jdbcTemplate.update("delete from lvg_asset where id=?", uuid);
			log.debug("asset delete success: " + uuid);
		} catch (Exception e) {
			log.debug("asset delete failed: " + uuid);
			log.debug("asset delete failed: " + e.toString());		}			
	}

	public void update(Asset asset) {
		try {
			jdbcTemplate.update("update lvg_asset set name=?, description=?, location=?, thumbnail_location=? where id=?",
					asset.getName(), asset.getDescription(), asset.getLocation(), asset.getThumbnailLocation(), asset.getUuid());
			log.debug("asset updated success: " + asset.toJsonString());
		} catch (Exception e) {
			log.debug("asset update failed: " + asset.toJsonString());
			log.debug("asset update failed: " + e.toString());		}
		
	}

	public List<Asset> getAll() {
		return jdbcTemplate.query("select id, name, description, location, thumbnail_location from lvg_asset",
				new AssetRowMapper());
	}

	public Asset findById(String uuid) {
		Asset asset = null;
	
		try {
			asset = jdbcTemplate.queryForObject(
					"select id, name, description, location, thumbnail_location from lvg_asset where id=?",
					new AssetRowMapper(), uuid);	
			log.debug("asset findById success: " + asset.toJsonString());
		} catch (EmptyResultDataAccessException e) {
			log.debug("asset findById failed: " + uuid);	
			log.debug("asset findById failed: " + e.toString());
		} catch (Exception e) {
			log.debug("asset findById failed: " + uuid);	
			log.debug("asset findById failed: " + e.toString());
		}

		return asset;
	}

	class AssetRowMapper implements RowMapper<Asset> {

		public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
			String id = rs.getString("id");
			String name = rs.getString("name");
			String description = rs.getString("description");
			String location = rs.getString("location");
			String thumbnailLocation = rs.getString("thumbnail_location");
			Asset asset = new Asset();
			asset.setUuid(id);
			asset.setName(name);
			asset.setDescription(description);
			asset.setLocation(location);
			asset.setThumbnailLocation(thumbnailLocation);
			return asset;
		}	
	
	
	}

	public List<Category> getCategories(Asset asset) {
		return categoryRepo.getCategories(asset.getUuid());
	}

	public List<Category> getAllCategories() {
		return categoryRepo.getAll();
	}

	public List<Keyword> getKeywords(Asset asset) {
		return keywordRepo.getKeywords(asset.getUuid());
	}

	public UserRating getUserRating(Asset asset, String username) {
		return userRatingRepo.getUserRating(asset.getUuid(), username);
	}

	public void addCategory(Asset asset, Category category) {
		categoryRepo.addCategory(category,asset.getUuid());
	}

	public void addKeyword(Asset asset, Keyword keyword) {
		keywordRepo.addKeyword(keyword, asset.getUuid());
	}

	public void addUserRating(UserRating userRating) {
		userRatingRepo.insert(userRating);
	}

	public void deleteUserRating(Asset asset, String username) {
		userRatingRepo.deleteUserRating(asset.getUser(), username);
	}
	

}
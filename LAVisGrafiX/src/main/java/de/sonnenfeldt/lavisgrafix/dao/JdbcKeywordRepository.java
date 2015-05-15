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

import de.sonnenfeldt.lavisgrafix.dao.JdbcCategoryRepository.CategoryRowMapper;
import de.sonnenfeldt.lavisgrafix.model.Category;
import de.sonnenfeldt.lavisgrafix.model.Keyword;

@Component
public class JdbcKeywordRepository implements KeywordRepository {

	static Logger log = Logger.getLogger(JdbcKeywordRepository.class.getName());
	
	private JdbcTemplate jdbcTemplate;
	

	@Autowired
	public JdbcKeywordRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcKeywordRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}	
	
	public void insert(Keyword keyword) {
		try {
			jdbcTemplate.update("insert into lvg_keyword (id, name, description) values(?,?,?)",
					keyword.getUuid(), keyword.getName(), keyword.getDescription());		
			log.debug("keyword insert success: " + keyword.toJsonString());
		} catch (Exception e) {
			log.debug("keyword insert failed: " + keyword.toJsonString());
			log.debug("keyword insert failed: " + e.toString());
		}
		
	}

	public void delete(String uuid) {
		try {
			jdbcTemplate.update("delete from lvg_keyword where id=?", uuid);
			log.debug("keyword delete success: " + uuid);
		} catch (Exception e) {
			log.debug("keyword delete failed: " + uuid);
			log.debug("keyword delete failed: " + e.toString());		}	
		
	}

	public List<Keyword> getAll() {
		return jdbcTemplate.query("select id, name, description from lvg_catgeory",
				new KeywordRowMapper());
	}

	public Keyword findById(String uuid) {
		Keyword keyword = null;
		
		try {
			keyword = jdbcTemplate.queryForObject(
					"select id, name, description from lvg_keyword where id=?",
					new KeywordRowMapper(),uuid);	
			log.debug("keyword findById success: " + keyword.toJsonString());
		} catch (EmptyResultDataAccessException e) {
			log.debug("keyword findById failed: " + uuid);	
			log.debug("keyword findById failed: " + e.toString());
		} catch (Exception e) {
			log.debug("keyword findById failed: " + uuid);	
			log.debug("keyword findById failed: " + e.toString());
		}

		return keyword;
	}

	public Keyword findByName(String name) {
	Keyword keyword = null;
		
		try {
			keyword = jdbcTemplate.queryForObject(
					"select id, name, description from lvg_keyword where name=?",
					new KeywordRowMapper(),name);	
			log.debug("keyword findById success: " + keyword.toJsonString());
		} catch (EmptyResultDataAccessException e) {
			log.debug("keyword findByName failed: " + name);	
			log.debug("keyword findByName failed: " + e.toString());
		} catch (Exception e) {
			log.debug("keyword findByName failed: " + name);	
			log.debug("keyword findByName failed: " + e.toString());
		}

		return keyword;
	}		
	
	class KeywordRowMapper implements RowMapper<Keyword> {

		public Keyword mapRow(ResultSet rs, int rowNum) throws SQLException {
			String id = rs.getString("id");
			String name = rs.getString("name");
			String description = rs.getString("description");
			Keyword keyword = new Keyword();
			keyword.setUuid(id);
			keyword.setName(name);
			keyword.setDescription(description);
			return keyword;
		}	
	
	
	}

	public List<Keyword> getKeywords(String asset_uuid) {
		List<Keyword> keywordList = null;
		try {	
			keywordList = jdbcTemplate.query("select id, name, description from lvg_keyword where id in (select keyword_id from lvg_keyword_describing_asset where asset_id=?)", new KeywordRowMapper(), asset_uuid);
			log.debug("KeywordController::getKeywords(asset) returns: " + keywordList.toString());
		} catch (Exception e) {
			log.debug("getKeyword failed for asset_uuid: " + asset_uuid);	
			log.debug("getKeyword failed: " + e.toString());
		}		
		return keywordList;

	}

	public void addKeyword(Keyword keyword, String asset_uuid) {

		Keyword existingKeyword = findById(keyword.getUuid());
		if (existingKeyword == null) {
			log.debug("addKeyword adding new keyword: " + keyword.toJsonString());			
			insert(keyword);
		} 

		try {
						jdbcTemplate.update("insert into lvg_keyword_describing_asset (keyword_id, asset_id) values(?,?)",
					keyword.getUuid(), asset_uuid);		
			log.debug("addKeyword insert keyword_classfiying_asset success: " + keyword.toJsonString() + " " + asset_uuid);
		} catch (Exception e) {
			log.debug("addKeyword insert keyword_classfiying_asset failed: " + keyword.toJsonString());
			log.debug("addKeyword insert keyword_classfiying_asset failed: " + e.toString());
		}		
		
	}

	public void deleteKeywords(String asset_id) {
		try {
			jdbcTemplate.update("delete from lvg_keyword_describing_asset where asset_id=?",asset_id);
		} catch (Exception e) {
			log.debug("deleteKeywordss failed: " + e.toString());
		}	
	}


		
}

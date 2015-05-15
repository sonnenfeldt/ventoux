package de.sonnenfeldt.lavisgrafix.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.postgresql.util.PGobject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import de.sonnenfeldt.lavisgrafix.dao.JdbcKeywordRepository.KeywordRowMapper;
import de.sonnenfeldt.lavisgrafix.model.Keyword;
import de.sonnenfeldt.lavisgrafix.model.UserRating;

@Component
public class JdbcUserRatingRepository implements UserRatingRepository {

	static Logger log = Logger.getLogger(JdbcUserRatingRepository.class.getName());
	
	private JdbcTemplate jdbcTemplate;		
		
	@Autowired
	public JdbcUserRatingRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcUserRatingRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}	
	
	public void insert(UserRating userRating) {
		try {	
			jdbcTemplate.update("insert into lvg_user_rated_asset (username, asset_id, rating) values(?,?,?)",
					userRating.getUsername(), userRating.getAsset_uuid(),  userRating.getRating());		
			log.debug("userRating insert success: " + userRating.toJsonString());
		} catch (Exception e) {
			log.debug("userRating insert failed: " + userRating.toJsonString());
			log.debug("userRating insert failed: " + e.toString());
		}
			}

	public UserRating findById(String asset_uuid) {
		UserRating userRating = null;
		
		try {
			userRating = jdbcTemplate.queryForObject(
					"select username, asset_id, rating from lvg_user_rated_asset where id=?",
					new UserRatingRowMapper(), asset_uuid);	
			log.debug("userRating findById success: " + userRating.toJsonString());
		} catch (EmptyResultDataAccessException e) {
			log.debug("userRating findById failed: " + asset_uuid);	
			log.debug("userRating findById failed: " + e.toString());
		} catch (Exception e) {
			log.debug("userRating findById failed: " + asset_uuid);	
			log.debug("userRating findById failed: " + e.toString());
		}

		return userRating;
	}

	class UserRatingRowMapper implements RowMapper<UserRating> {

		public UserRating mapRow(ResultSet rs, int rowNum) throws SQLException {
			String username = rs.getString("username");
			String asset_id = rs.getString("asset_id");
			String rating = rs.getString("rating");
			UserRating userRating = new UserRating();
			userRating.setUsername(username);;
			userRating.setAsset_uuid(asset_id);
			userRating.setRating(rating);
			return userRating;
		}	
	
	
	}

	public UserRating getUserRating(String asset_uuid, String username) {
		log.debug("getUserRating parameters: asset_uuid: " + asset_uuid + " username: " + username);
		UserRating userRating = null;
		try {	
			userRating = jdbcTemplate.queryForObject("select username, asset_id, rating from lvg_user_rated_asset where username=? and asset_id=?", new UserRatingRowMapper(), username, asset_uuid);
		} catch (Exception e) {
			log.debug("getUserRating failed for asset_uuid: " + asset_uuid);	
			log.debug("getUserRating failed: " + e.toString());
		}		
		return userRating;
	}

	public void deleteUserRating(String asset_uuid, String username) {
		try {
			jdbcTemplate.update("delete from lvg_user_rated_asset where username=? and asset_id=?",username, asset_uuid);
		} catch (Exception e) {
			log.debug(" deleteUserRating failed: " + e.toString());
		}	
	}		

}

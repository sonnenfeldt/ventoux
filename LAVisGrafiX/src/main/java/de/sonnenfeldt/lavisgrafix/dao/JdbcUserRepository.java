package de.sonnenfeldt.lavisgrafix.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import de.sonnenfeldt.lavisgrafix.model.Authority;


import de.sonnenfeldt.lavisgrafix.model.User;


@Component
public class JdbcUserRepository implements UserRepository{
	
	static Logger log = Logger.getLogger(JdbcUserRepository.class.getName());
	
	private JdbcTemplate jdbcTemplate;	

	@Autowired
	public JdbcUserRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public User getUser(String username) {
		User user = null;
		List<Authority> authorities = null;
		
		try {
			user = jdbcTemplate.queryForObject(
					"select username, password, enabled, family_name, first_name from users where username=?",
					new UserRowMapper(), username);	
			log.debug("user getUser success: " + user.toJsonString());
			
			authorities = jdbcTemplate.query(
					"select username, authority from authorities where username=?",
					new AuthorityRowMapper(), username);
			
			if (authorities != null) {
				user.setAuthorities(authorities);
			}
			log.debug("user getUser success: " + user.toJsonString());
			
		} catch (EmptyResultDataAccessException e) {
			log.debug("user getUser failed: " + username);	
			log.debug("user getUser failed: " + e.toString());
		}

		return user;
		}

	class UserRowMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			String username = rs.getString("username");
			String password = rs.getString("password");
			Boolean isEnabled = rs.getBoolean("enabled");
			String familyName = rs.getString("family_name");
			String firstName = rs.getString("first_name");
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setEnabled(isEnabled);
			user.setFamilyName(familyName);
			user.setFirstName(firstName);
			return user;
		}	
	
	
	}	
	
	class AuthorityRowMapper implements RowMapper<Authority> {

		public Authority mapRow(ResultSet rs, int rowNum) throws SQLException {
			String username = rs.getString("username");
			String role = rs.getString("authority");
			Authority authority = new Authority();
			authority.setUsername(username);
			authority.setRole(role);
			return authority;
		}	
	}		
	
}

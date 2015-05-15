package de.sonnenfeldt.lavisgrafix.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


import de.sonnenfeldt.lavisgrafix.model.User;


@Component
public class JdbcUserRepository implements UserRepository{
	
	static Logger log = Logger.getLogger(JdbcUserRepository.class.getName());
	
	private JdbcTemplate jdbcTemplate;	

	@Autowired
	public JdbcUserRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public User findById(String username) {
		User user = null;
		
		try {
			user = jdbcTemplate.queryForObject(
					"select username, password, enabled, family_name, first_name from user where username=?",
					new UserRowMapper(), username);	
			log.debug("user findById success: " + user.toJsonString());
		} catch (EmptyResultDataAccessException e) {
			log.debug("user findById failed: " + username);	
			log.debug("user findById failed: " + e.toString());
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
	
}

package de.sonnenfeldt.lavisgrafix.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.postgresql.util.PGobject;

import de.sonnenfeldt.lavisgrafix.model.Asset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JdbcAssetRepository  implements AssetRepository {

	static Logger log = Logger.getLogger(JdbcAssetRepository.class.getName());
	
	private JdbcTemplate jdbcTemplate;
	
	private PGobject pgUuid = new PGobject();
	
	@Autowired
	public JdbcAssetRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		pgUuid.setType("uuid");
	}

	@Override
	public void insert(Asset asset) {
		try {
			pgUuid.setValue(asset.getUuid());
			jdbcTemplate.update("insert into lvg_asset (id, name, description, location,thumbnail_location) values(?,?,?,?,?)",
					pgUuid, asset.getName(), asset.getDescription(), asset.getLocation(), asset.getThumbnailLocation());		
			log.debug("asset insert success: " + asset.toJsonString());
		} catch (SQLException e) {
			log.debug("asset insert failed: " + asset.toJsonString());
			log.debug("asset insert failed: " + e.toString());
		}
		
	}

	@Override
	public void delete(String uuid) {
		try {
			pgUuid.setValue(uuid);
			jdbcTemplate.update("delete from lvg_asset where id=?", pgUuid);
			log.debug("asset delete success: " + uuid);
		} catch (SQLException e) {
			log.debug("asset delete failed: " + uuid);
			log.debug("asset delete failed: " + e.toString());		}			
	}

	@Override
	public void update(Asset asset) {
		try {
			pgUuid.setValue(asset.getUuid());
			jdbcTemplate.update("update lvg_asset set name=?, description=?, location=?, thumbnail_location=? where id=?",
					asset.getName(), asset.getDescription(), asset.getLocation(), asset.getThumbnailLocation(), pgUuid);
			log.debug("asset updated success: " + asset.toJsonString());
		} catch (SQLException e) {
			log.debug("asset update failed: " + asset.toJsonString());
			log.debug("asset update failed: " + e.toString());		}
		
	}

	@Override
	public List<Asset> getAll() {
		return jdbcTemplate.query("select id, name, description, location, thumbnail_location from lvg_asset",
				new AssetRowMapper());
	}

	@Override
	public Asset findById(String uuid) {
		Asset asset = null;
	
		try {
			pgUuid.setValue(uuid);
			asset = jdbcTemplate.queryForObject(
					"select id, name, description, location, thumbnail_location from lvg_asset where id=?",
					new AssetRowMapper(), pgUuid);	
			log.debug("asset findById success: " + asset.toJsonString());
		} catch (EmptyResultDataAccessException e) {
			log.debug("asset findById failed: " + uuid);	
			log.debug("asset findById failed: " + e.toString());
		} catch (SQLException e) {
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

}
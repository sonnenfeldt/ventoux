package de.sonnenfeldt.lavisgrafix.dao;
import java.util.List;

import de.sonnenfeldt.lavisgrafix.model.Asset;


public interface AssetRepository {
	void insert(Asset asset);
	void delete(String uuid);
	void update(Asset asset);
	List<Asset> getAll();
	Asset findById(String uuid);
}

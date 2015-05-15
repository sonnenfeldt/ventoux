package de.sonnenfeldt.lavisgrafix.dao;

import java.util.List;

import de.sonnenfeldt.lavisgrafix.model.Category;
import de.sonnenfeldt.lavisgrafix.model.Keyword;

public interface KeywordRepository {
	
	void insert(Keyword keyword);
	void delete(String uuid);
	List<Keyword> getAll();
	Keyword findById(String uuid);	
	Keyword findByName(String name);	
	List<Keyword> getKeywords(String asset_uuid);
	void addKeyword(Keyword keyword, String asset_uuid);	
	void deleteKeywords(String asset_id);
}

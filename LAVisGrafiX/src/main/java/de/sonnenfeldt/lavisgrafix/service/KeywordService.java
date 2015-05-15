package de.sonnenfeldt.lavisgrafix.service;

import java.util.List;

import de.sonnenfeldt.lavisgrafix.model.Keyword;

public interface KeywordService {

	List<Keyword> getAll();
	void addKeywords(String keywords, String asset_id);
	
}

package de.sonnenfeldt.lavisgrafix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.sonnenfeldt.lavisgrafix.model.Keyword;
import de.sonnenfeldt.lavisgrafix.dao.KeywordRepository;

@Component
@Transactional
public class KeywordServiceImpl implements KeywordService {

	private KeywordRepository keywordRepo;
	
	@Autowired
	public KeywordServiceImpl(KeywordRepository keywordRepo) {
		this.keywordRepo = keywordRepo;
	}

	public List<Keyword> getAll() {
		return keywordRepo.getAll();
	}

	public void addKeywords(String keywords, String asset_uuid) {
		
		CharSequence comma = ",";
		keywordRepo.deleteKeywords(asset_uuid);
		
		if (keywords != null) {
			if (keywords.contains(comma)) {
				String[] parts = keywords.split(comma.toString());
				String keywordName;
				for (int i=0; i < parts.length;i++) {
					keywordName = parts[i].trim();
					Keyword keyword = keywordRepo.findByName(keywordName);
					if (keyword == null) {
						keyword = new Keyword();
						keyword.setName(keywordName);
						keyword.setDescription(keywordName + " Description");
						keywordRepo.insert(keyword);
					}
					keywordRepo.addKeyword(keyword, asset_uuid);
				}
				
			}
		}
		
	}
	
}

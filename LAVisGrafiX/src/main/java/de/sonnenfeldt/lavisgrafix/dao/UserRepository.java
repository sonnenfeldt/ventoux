package de.sonnenfeldt.lavisgrafix.dao;

import de.sonnenfeldt.lavisgrafix.model.User;

public interface UserRepository {
	User findById(String username);		
}

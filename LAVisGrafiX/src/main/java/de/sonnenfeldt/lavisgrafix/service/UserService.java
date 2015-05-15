package de.sonnenfeldt.lavisgrafix.service;

import de.sonnenfeldt.lavisgrafix.model.User;

public interface UserService {
	User findById(String username);		
}

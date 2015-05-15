package de.sonnenfeldt.lavisgrafix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.sonnenfeldt.lavisgrafix.model.User;
import de.sonnenfeldt.lavisgrafix.dao.UserRepository;

@Component
@Transactional
public class UserServiceImpl implements UserService{

	private UserRepository userRepo;
	
	@Autowired	
	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public User getUser(String username) {
		return userRepo.getUser(username);
	}

}

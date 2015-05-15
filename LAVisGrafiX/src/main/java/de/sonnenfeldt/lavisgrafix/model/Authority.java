package de.sonnenfeldt.lavisgrafix.model;

import de.sonnenfeldt.lavisgrafix.model.LibraryEntryBase;

public class Authority extends LibraryEntryBase{
	
	private String username;
	private String role;
	
	
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public Authority() {
		super();
	}

}

package de.sonnenfeldt.lavisgrafix.model;

public class UserRating extends LibraryEntryBase {

	private String username;
	private String asset_uuid;
	private String rating;
	
	public UserRating()  {

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

	/**
	 * @return the asset_uuid
	 */
	public String getAsset_uuid() {
		return asset_uuid;
	}

	/**
	 * @param asset_uuid the asset_uuid to set
	 */
	public void setAsset_uuid(String asset_uuid) {
		this.asset_uuid = asset_uuid;
	}

	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	
}

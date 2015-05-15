package de.sonnenfeldt.lavisgrafix.dao;


import de.sonnenfeldt.lavisgrafix.model.UserRating;

public interface UserRatingRepository {
	void insert(UserRating userRating);
	UserRating getUserRating(String asset_uuid, String username);
	void deleteUserRating(String asset_uuid, String username);
}

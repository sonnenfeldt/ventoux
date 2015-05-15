/**
 * 
 */
package de.sonnenfeldt.lavisgrafix.model;

import java.util.List;

import de.sonnenfeldt.lavisgrafix.model.Category;

/**
 * @author rudi
 *
 */
public class Asset extends LibraryEntry {


	private String location;
	private String thumbnailLocation;
	private List<Category> categories;
	private String [] categoryNames;
	private String [] keywordNames;
	private String keywordNamesAsCSV;
	private String rating;
	private String user;
	
	public Asset() {
		super();
	}


	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}


	/**
	 * @return the thumbnailLocation
	 */
	public String getThumbnailLocation() {
		return thumbnailLocation;
	}


	/**
	 * @param thumbnailLocation the thumbnailLocation to set
	 */
	public void setThumbnailLocation(String thumbnailLocation) {
		this.thumbnailLocation = thumbnailLocation;
	}


	/**
	 * @return the categoryNames
	 */
	public String[] getCategoryNames() {
		return categoryNames;
	}


	/**
	 * @param categoryNames the categoryNames to set
	 */
	public void setCategoryNames(String[] categoryNames) {
		this.categoryNames = categoryNames;
	}


	/**
	 * @return the keywordNames
	 */
	public String[] getKeywordNames() {
		return keywordNames;
	}
	
	/**
	 * @param keywordNames the keywordNames to set
	 */
	public void setKeywordNames(String[] keywordNames) {
		this.keywordNames = keywordNames;			
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
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


	/**
	 * @return the keywordNamesAsCSV
	 */
	public String getKeywordNamesAsCSV() {
		return keywordNamesAsCSV;
	}


	/**
	 * @param keywordNamesAsCSV the keywordNamesAsCSV to set
	 */
	public void setKeywordNamesAsCSV(String keywordNamesAsCSV) {
		this.keywordNamesAsCSV = keywordNamesAsCSV;
	}


	/**
	 * @return the categories
	 */
	public List<Category> getCategories() {
		return categories;
	}


	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}


	
}

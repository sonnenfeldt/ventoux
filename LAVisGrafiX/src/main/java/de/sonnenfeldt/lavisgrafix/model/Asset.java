/**
 * 
 */
package de.sonnenfeldt.lavisgrafix.model;

/**
 * @author rudi
 *
 */
public class Asset extends LibraryEntry {


	private String location;
	private String thumbnailLocation;
	
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
	
	//getAllKeywords
	//getAllCategories
	//getAllProperties
	//getUserRatings
	
	
}

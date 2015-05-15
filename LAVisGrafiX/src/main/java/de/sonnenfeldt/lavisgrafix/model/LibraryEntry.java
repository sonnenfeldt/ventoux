/**
 * 
 */
package de.sonnenfeldt.lavisgrafix.model;

import java.io.IOException;
import java.util.UUID;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import de.sonnenfeldt.lavisgrafix.model.LibraryEntryBase;

/**
 * @author rudi
 *
 */
public abstract class LibraryEntry extends LibraryEntryBase {

	private String uuid;
	private String name;
	private String description;	
	
	/**
	 * 
	 */
	public LibraryEntry() {
		super();
		this.uuid = UUID.randomUUID().toString();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}

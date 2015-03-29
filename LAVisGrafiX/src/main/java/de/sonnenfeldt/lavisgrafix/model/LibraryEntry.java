/**
 * 
 */
package de.sonnenfeldt.lavisgrafix.model;

import java.io.IOException;
import java.util.UUID;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author rudi
 *
 */
public abstract class LibraryEntry {

	private String uuid;
	private String name;
	private String description;	
	
	/**
	 * 
	 */
	public LibraryEntry() {
		// TODO Auto-generated constructor stub
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

	public String  toJsonString() {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(this);
		} catch (JsonGenerationException e) {
			jsonString = new String("Conversion to JSON failed: JsonGenerationException.");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			jsonString = new String("Conversion to JSON failed: JsonMappingException.");
			e.printStackTrace();
		} catch (IOException e) {
			jsonString = new String("Conversion to JSON failed: IOException.");
			e.printStackTrace();
		}
		return jsonString;
		
	}
	
}

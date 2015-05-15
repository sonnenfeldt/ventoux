package de.sonnenfeldt.lavisgrafix.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public abstract class LibraryEntryBase {

	public LibraryEntryBase() {
		
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

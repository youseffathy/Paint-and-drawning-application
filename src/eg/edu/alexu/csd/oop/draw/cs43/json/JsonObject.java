package eg.edu.alexu.csd.oop.draw.cs43.json;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class JsonObject {
	 class JsonElement {
			private String key;
			private String value;
			public JsonElement(String key,String value) {
				this.key = key;
				this.value = value;
			}
			public String getValue() {
				return value;
			}
			public String getKey() {
				return key;
			}
		}

	private LinkedList<JsonElement> elements = new LinkedList<>();

	public JsonObject() {

	}

	public void put(String key, String value) {
		elements.add(new JsonElement(key, value));

	}

	public String get(String key) {
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).getKey().equals(key)) {
				return elements.get(i).getValue();
			}
		}
		return null;
	}

	public void remove(String key) {
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).getKey().equals(key)) {
				elements.remove(elements.get(i));
				return;
			}
		}
	}

	public int size() {
		return elements.size();
	}

	public void toJsonFile(String path) {
		
		File jsonFile  = new File(path);
		try {
			
			FileWriter fileWriter = new FileWriter(jsonFile);
			if (!jsonFile.exists()) {
				jsonFile.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(fileWriter);
			writer.write(toString());
			writer.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		for (int i = 0; i < elements.size(); i++) {
			
			builder.append("\"");
			builder.append(elements.get(i).getKey());
			builder.append("\" : \"");
			builder.append(elements.get(i).getValue());
			builder.append("\"");
			if (i == elements.size() - 1) {
				
			} else {
				builder.append(",\n");
			}
		}
		builder.append("}");
		return builder.toString();
		
	}
	
}

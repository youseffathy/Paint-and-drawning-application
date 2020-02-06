package eg.edu.alexu.csd.oop.draw.cs43.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class JsonArray {

	private LinkedList<JsonObject> elements = new LinkedList<>();

	public JsonArray() {

	}

	public void putJsonObject(JsonObject jsonObject) {
		elements.add(jsonObject);
	}

	public JsonObject get(int index) {
		return elements.get(index);
	}

	public void remove(int index) {
		elements.remove(index);
	}

	public int size() {
		return elements.size();
	}

	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (int i = 0; i < elements.size(); i++) {
			
			builder.append(elements.get(i).toString());
			if (i == elements.size() - 1) {
				builder.append("]");
			} else {
				builder.append(",\n");
			}
		}
		
		return builder.toString();
	
	}

}

package eg.edu.alexu.csd.oop.draw.cs43.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class JsonParser {

	public JsonParser() {

	}	
	public JsonArray parse(String path) {
		File file = new File(path);
		StringBuilder stringBuilder = new StringBuilder();
		try {
			String inputLine;
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			while ((inputLine = bufferedReader.readLine()) != null) {
				stringBuilder.append(inputLine);
			}
			JsonArray jsonArray = new JsonArray();
	
			String string = stringBuilder.toString();	
			String[] strings = string.split("\\},\\{");
			strings[0] = strings[0].substring(2, strings[0].length());
			strings[strings.length - 1] = strings[strings.length - 1].substring(0,
					strings[strings.length - 1].length() - 2);

			for (int i = 0; i < strings.length; i++) {

				String element = strings[i];
				String[] attributes = element.split("\",\"");
				attributes[0] = attributes[0].substring(1, attributes[0].length());
				attributes[attributes.length - 1] = attributes[attributes.length - 1].substring(0,
						attributes[attributes.length - 1].length() - 1);

				JsonObject jsonObject = new JsonObject();
				for (int j = 0; j < attributes.length; j++) {
					String[] KeyAndValue = attributes[j].split("\" : \"");
					jsonObject.put(KeyAndValue[0], KeyAndValue[1]);
				}
				jsonArray.putJsonObject(jsonObject);
			}
			
			
			return jsonArray;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}

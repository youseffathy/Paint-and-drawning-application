package eg.edu.alexu.csd.oop.draw.cs43.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFile {
	public JsonFile(JsonArray jsonArray, String path) {
		tojsonfile(jsonArray, path);
	}

	private void tojsonfile(JsonArray array, String path) {
		File jsonFile = new File(path);
		try {

			FileWriter fileWriter = new FileWriter(jsonFile);
			if (!jsonFile.exists()) {
				jsonFile.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(fileWriter);
			writer.write(array.toString());
			writer.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

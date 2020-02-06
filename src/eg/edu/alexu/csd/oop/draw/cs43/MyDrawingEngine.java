package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs43.json.JsonArray;
import eg.edu.alexu.csd.oop.draw.cs43.json.JsonFile;
import eg.edu.alexu.csd.oop.draw.cs43.json.JsonObject;
import eg.edu.alexu.csd.oop.draw.cs43.json.JsonParser;


public class MyDrawingEngine implements DrawingEngine {
	private List<Shape> list = new LinkedList<>();
	private List<Class<? extends Shape>> supportedShapes = new LinkedList<>();
	private List<ShapeOperationClass> undo = new LinkedList<ShapeOperationClass>();
	private List<ShapeOperationClass> redo = new LinkedList<ShapeOperationClass>();
	private int currentIndexUndo = 0;
	private int numberOfundos = 0;
	private String path;// = "C:\\Users\\SHIKO\\Desktop\\Draw\\RoundRectangle.jar";
	private ClassLoader classLoad;
	@Override
	public void refresh(Graphics canvas) {

		for (int i = 0; i < list.size(); i++) {
			list.get(i).draw(canvas);
		}
	}

	@Override
	public void addShape(Shape shape) {

		if (numberOfundos > 0) {
			numberOfundos--;
		}
		list.add(shape);
		System.out.println("add");
		ShapeOperationClass soc = new ShapeOperationClass(shape, null, "add");
		undo.add(soc);

		currentIndexUndo = undo.size();
	}

	@Override
	public void removeShape(Shape shape) {
		if (numberOfundos > 0) {
			numberOfundos--;
		}
		for (int i = 0; i < list.size(); i++) {
			if (shape.equals(list.get(i))) {
				list.remove(i);
				System.out.println("remove");
				break;
			}
		}

		ShapeOperationClass soc = new ShapeOperationClass(shape, null, "remove");
		undo.add(soc);
		currentIndexUndo = undo.size();

	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		if (numberOfundos > 0) {
			numberOfundos--;
		}
		int i;
		for (i = 0; i < list.size(); i++) {// gets the old shape from the list and remove it
			
			if (oldShape.equals(list.get(i))) {
				list.remove(i);
				System.out.println("Updateremove");
				break;
			}
		}
		try {
			list.add(newShape);
			System.out.println("Updateadd");
		} catch (Exception e) {
			
			e.printStackTrace();
		} // adds the new shape
		ShapeOperationClass soc = new ShapeOperationClass(oldShape, newShape, "update");
		undo.add(soc);
		currentIndexUndo = undo.size();

	}

	@Override
	public Shape[] getShapes() {
		Shape[] shapes = new Shape[list.size()];
		return list.toArray(shapes);
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		List<Class<? extends Shape>> classes = new LinkedList<>();
		try {
			File f = new File("");
			URL url;
			url = f.toURI().toURL();
			URL[] urlss = new URL[] { url };
			classLoad = new URLClassLoader(urlss);
			classes.add((Class<? extends Shape>) classLoad.loadClass("eg.edu.alexu.csd.oop.draw.cs43.Circle"));
			classes.add((Class<? extends Shape>) classLoad.loadClass("eg.edu.alexu.csd.oop.draw.cs43.Rectangle"));
			classes.add((Class<? extends Shape>) classLoad.loadClass("eg.edu.alexu.csd.oop.draw.cs43.Square"));
			classes.add((Class<? extends Shape>) classLoad.loadClass("eg.edu.alexu.csd.oop.draw.cs43.Ellipse"));
			classes.add((Class<? extends Shape>) classLoad.loadClass("eg.edu.alexu.csd.oop.draw.cs43.LineSegment"));
			classes.add((Class<? extends Shape>) classLoad.loadClass("eg.edu.alexu.csd.oop.draw.cs43.Triangle"));
		} catch (Exception e2) {

		}
		try {
			JarFile file = new JarFile(path);
			URL[] urls = { new URL("jar:file:" + path + "!/") };
			classLoad = new URLClassLoader(urls);
			Enumeration<JarEntry> enumeration = file.entries();
			while (enumeration.hasMoreElements()) {
				JarEntry jarEntry = enumeration.nextElement();
				if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
					continue;
				}
				String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
				className = className.replace('/', '.');
				Class c = classLoad.loadClass(className);
				if (c.newInstance() instanceof Shape && !classes.contains(c)) {
					classes.add(c);
				}
			}
		} catch (Exception e) {

		}
		return classes;
	}

	@Override
	public void undo() {
		if (numberOfundos >= 20) {
			return;
		}

		currentIndexUndo--;
		if (currentIndexUndo < 0) {
			currentIndexUndo++;
			return;
		}

		ShapeOperationClass soc = undo.get(currentIndexUndo);
		if (soc.getOperation().equals("add")) {
			numberOfundos++;
			System.out.println("undoadd");
			list.remove(list.size() - 1);
			redo.add(new ShapeOperationClass(soc.getShape(), null, "add"));
		} else if (soc.getOperation().equals("remove")) {
			numberOfundos++;
			System.out.println("undoremove");
			list.add(soc.getShape());
			redo.add(new ShapeOperationClass(soc.getShape(), null, "remove"));
		} else if (soc.getOperation().equals("update")) {
			System.out.println("undoupdate");
			numberOfundos++;
			list.remove(list.size() - 1);
			list.add(soc.getShape());
			redo.add(new ShapeOperationClass(soc.getShape(), soc.getShape2(), "update"));
		}

	}

	@Override
	public void redo() {
		
		try {
			ShapeOperationClass soc = undo.get(currentIndexUndo);
			currentIndexUndo++;
			if (soc.getOperation().equals("add")) {
				list.add(soc.getShape());
				System.out.println("REdoadd");
				numberOfundos--;
			} else if (soc.getOperation().equals("remove")) {
				list.remove(list.size() - 1);
				System.out.println("redoRemove");
				numberOfundos--;
			} else if (soc.getOperation().equals("update")) {
				list.remove(list.size() - 1);
				System.out.println("redoupdate");
				list.add(soc.getShape2());
				numberOfundos--;
			}
		} catch (Exception e) {
			
		}

	}

	private void saveXml(String path) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document d = db.newDocument();
			Element shapes = d.createElement("Shapes");
			d.appendChild(shapes);

			for (int i = 0; i < list.size(); ++i) {
				System.out.println(i);
				Element element = d.createElement(list.get(i).getClass().getName());
				shapes.appendChild(element);

				Map<String, Double> temp = new HashMap<String, Double>();
				temp = list.get(i).getProperties();

				try {
					for (Entry<String, Double> e : temp.entrySet()) {
						Element x1 = d.createElement(e.getKey());
						x1.appendChild(d.createTextNode(String.valueOf(list.get(i).getProperties().get(e.getKey()))));
						element.appendChild(x1);
					}
				} catch (Exception e) {

				}
				try {
					Element color = d.createElement("color");
					element.appendChild(color);
					color.appendChild(d.createTextNode(String.valueOf(list.get(i).getColor().getRed())));
					color.appendChild(d.createTextNode(","));
					color.appendChild(d.createTextNode(String.valueOf(list.get(i).getColor().getBlue())));
					color.appendChild(d.createTextNode(","));
					color.appendChild(d.createTextNode(String.valueOf(list.get(i).getColor().getGreen())));
					element.appendChild(color);
				} catch (Exception e1) {

				}
				try {
					Element fillcolor = d.createElement("fillcolor");
					element.appendChild(fillcolor);
					fillcolor.appendChild(d.createTextNode(String.valueOf(list.get(i).getFillColor().getRed())));
					fillcolor.appendChild(d.createTextNode(","));
					fillcolor.appendChild(d.createTextNode(String.valueOf(list.get(i).getFillColor().getBlue())));
					fillcolor.appendChild(d.createTextNode(","));
					fillcolor.appendChild(d.createTextNode(String.valueOf(list.get(i).getFillColor().getGreen())));
					element.appendChild(fillcolor);
				} catch (Exception e) {

				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(d);
			StreamResult streamResult = new StreamResult(new File(path));
			transformer.transform(source, streamResult);
		} catch (Exception e) {

		}
	}

	private void saveJson(String path) {
		JsonArray shapesArray = new JsonArray();
		for (int i = 0; i < list.size(); i++) {
			Shape shape = list.get(i);
			try {
				JsonObject jsonObject = shapeToJsonObject(shape);
				shapesArray.putJsonObject(jsonObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		JsonFile file = new JsonFile(shapesArray, path);
	}

	private JsonObject shapeToJsonObject(Shape shape) {
		JsonObject jsonObject = new JsonObject();
		try {
			jsonObject.put("classname", shape.getClass().getName());
			try {
				for (Entry<String, Double> e : shape.getProperties().entrySet()) {
					jsonObject.put(e.getKey(), String.valueOf(e.getValue()));
				}
			} catch (Exception e) {

			}

			StringBuilder builder;
			try {
				builder = new StringBuilder();
				builder.append(String.valueOf(shape.getColor().getRed()));
				builder.append(",");
				builder.append(String.valueOf(shape.getColor().getBlue()));
				builder.append(",");
				builder.append(String.valueOf(shape.getColor().getGreen()));
				jsonObject.put("color", builder.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				builder = new StringBuilder();
				builder.append(String.valueOf(shape.getFillColor().getRed()));
				builder.append(",");
				builder.append(String.valueOf(shape.getFillColor().getBlue()));
				builder.append(",");
				builder.append(String.valueOf(shape.getFillColor().getGreen()));
				jsonObject.put("fillcolor", builder.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			jsonObject.put("classname", "null");
		}
		return jsonObject;
	}

	@Override
	public void save(String path) {

		String[] strings = path.substring(path.lastIndexOf("\\") + 1, path.length()).split("\\.");

		if (strings[strings.length - 1].toLowerCase().equals("xml")) {
			saveXml(path);
		} else if (strings[strings.length - 1].toLowerCase().equals("json")) {
			saveJson(path);
		}
	}

	@Override
	public void load(String path) {
		String[] strings = path.substring(path.lastIndexOf("\\") + 1, path.length()).split("\\.");
		if (strings[strings.length - 1].toLowerCase().equals("xml")) {

			loadXml(path);

		} else if (strings[strings.length - 1].toLowerCase().equals("json")) {

			loadJson(path);

		}
	}



	private void loadJson(String path) {
		JsonParser jsonParser = new JsonParser();
		
		JsonArray jsonArray = jsonParser.parse(path);

		list = new LinkedList<>();
		undo = new LinkedList<>();
		redo = new LinkedList<>();
		currentIndexUndo = 0;
		numberOfundos = 0;
	
		for (int i = 0; i < jsonArray.size(); i++) {
			try {
				JsonObject jsonObject = jsonArray.get(i);
				String classname = jsonObject.get("classname");
				File f = new File("");
				URL url;
				url = f.toURI().toURL();
				URL[] urlss = new URL[] { url };
				classLoad = new URLClassLoader(urlss);
				System.out.println(classname);
				Class Shape = (Class<? extends Shape>) classLoad.loadClass(classname);
				Shape shape = (Shape) Shape.newInstance();
				Map<String, Double> map = shape.getProperties();
				try {
					for (Entry<String, Double> e : map.entrySet()) {
						map.put(e.getKey(), Double.valueOf(jsonObject.get(e.getKey())));
					}
					shape.setProperties(map);
					Point position = new Point(map.get("positionx").intValue(), map.get("positiony").intValue());
					shape.setPosition(position);

					try {
						String s = jsonObject.get("color");
						String[] colorRBG = s.split(",");
						Color color = new Color(Integer.valueOf(colorRBG[0]), Integer.valueOf(colorRBG[1]),
								Integer.valueOf(colorRBG[2]));
						shape.setColor(color);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						String fill = jsonObject.get("fillcolor");
						String[] fillcolorRBG = fill.split(",");
						Color fillcolor = new Color(Integer.valueOf(fillcolorRBG[0]), Integer.valueOf(fillcolorRBG[1]),
								Integer.valueOf(fillcolorRBG[2]));
						shape.setFillColor(fillcolor);
					} catch (Exception e) {
						e.printStackTrace();
					}

					list.add(shape);
					
					shape = null;
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

	}

	private void loadXml(String path) {
		try {
			File file = new File(path);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList nodes = document.getElementsByTagName("Shapes");

			list = new LinkedList<>();
			undo = new LinkedList<>();
			redo = new LinkedList<>();
			currentIndexUndo = 0;
			numberOfundos = 0;

			Node node = nodes.item(0);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				NodeList ll = element.getChildNodes();

				for (int i = 0; i < ll.getLength(); ++i) {

					String ss = ll.item(i).getNodeName();

					Element element2 = (Element) ll.item(i);

					File f = new File("");
					URL url;
					url = f.toURI().toURL();
					URL[] urlss = new URL[] { url };
					classLoad = new URLClassLoader(urlss);
					Class Shape = (Class<? extends Shape>) classLoad.loadClass(ss);
					Shape shape = (Shape) Shape.newInstance();
					Map<String, Double> map = shape.getProperties();
					try {
						for (Entry<String, Double> e : map.entrySet()) {
							map.put(e.getKey(),
									Double.valueOf(element2.getElementsByTagName(e.getKey()).item(0).getTextContent()));
						}
						shape.setProperties(map);
						Point position = new Point(map.get("positionx").intValue(), map.get("positiony").intValue());
						shape.setPosition(position);
					} catch (Exception e) {

					}

					String[] colorRBG;
					try {
						String s = element.getElementsByTagName("color").item(0).getTextContent();
						colorRBG = element.getElementsByTagName("color").item(0).getTextContent().split(",");
						Color color = new Color(Integer.valueOf(colorRBG[0]), Integer.valueOf(colorRBG[1]),
								Integer.valueOf(colorRBG[2]));
						shape.setColor(color);
					} catch (Exception e) {

					}

					try {
						String fill = element.getElementsByTagName("fillcolor").item(0).getTextContent();
						String[] fillcolorRBG = element.getElementsByTagName("fillcolor").item(0).getTextContent()
								.split(",");
						Color fillcolor = new Color(Integer.valueOf(fillcolorRBG[0]), Integer.valueOf(fillcolorRBG[1]),
								Integer.valueOf(fillcolorRBG[2]));
						shape.setFillColor(fillcolor);
					} catch (Exception e) {
						// TODO Auto-generated catch block

					}

					list.add(shape);
					shape = null;

				}

			}

		} catch (Exception e) {

		}

	}

	@Override
	public void installPluginShape(String jarPath) {
		path = jarPath;
	}

}

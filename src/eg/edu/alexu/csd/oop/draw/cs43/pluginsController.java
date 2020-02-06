package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import eg.edu.alexu.csd.oop.draw.Shape;

public class pluginsController implements ActionListener {
	MyDrawingEngine drawingEngine = new MyDrawingEngine();
	private JFileChooser chooser;
	private JFrame frame;
	private LinkedList<String> shapes = new LinkedList<>();
	public List<Class<? extends Shape>> classes = new LinkedList<>();
	private DrawingApp app;
	private JButton draw;
	private JButton addAttribute;
	private String selectedPlugin;
	private Shape shape;
	private int x;
	private int y;
	private String property;
	private Canvas canvas;
	private JButton redraw, updateAttribute, deletePlugin;
	private LinkedList<Shape> DrawedPlugins = new LinkedList<>();
	private String selectedDrawedPlugin;
	private Shape Oldinstance;
	private Shape newInstance;
	public void setButtons(JButton redraw, JButton updateAttribute, JButton deletePlugin) {
		this.redraw = redraw;
		this.updateAttribute = updateAttribute;
		this.deletePlugin = deletePlugin;
	}

	public void setSelectedAttribute(String proberty) {
		this.property = proberty;
	}

	public void setSelectedDrawedPlugin(String s) {
		selectedDrawedPlugin = s;
	}

	public pluginsController(MyDrawingEngine drawingEngine, JFrame frame, JComboBox jComboBox, DrawingApp app,
			JButton draw, JButton addAttribute, JComboBox attributes, JComboBox supportedShapes, Canvas canvas) {
		this.drawingEngine = drawingEngine;
		this.app = app;
		this.draw = draw;
		this.addAttribute = addAttribute;
		this.canvas = canvas;
		shapes.add("LineSegment");
		shapes.add("Triangle");
		shapes.add("Circle");
		shapes.add("Rectangle");
		shapes.add("Ellipse");
		shapes.add("Square");
		app.setpluginsController(this);

		addActionToButtons();
	}

	private void addActionToButtons() {
		ActionListener delete = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!selectedDrawedPlugin.equals("none")) {
					int index = Integer.valueOf(selectedDrawedPlugin.substring(selectedDrawedPlugin.length() - 1,
							selectedDrawedPlugin.length())) - 1;
					Shape shape = DrawedPlugins.get(index);
					DrawedPlugins.remove(index);
					app.removeDrawedPlugin();
					drawingEngine.removeShape(shape);
					canvas.paint(canvas.getGraphics());
					drawingEngine.refresh(canvas.getGraphics());
				}
			}
		};
		ActionListener drawing = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				List<Shape> allshapes = Arrays.asList(drawingEngine.getShapes());
				if (!allshapes.contains(shape)) {
					shape.setPosition(new Point(x, y));
					drawingEngine.addShape(shape);
					DrawedPlugins.add(shape);
					app.addDrawedPlugin("shape" + DrawedPlugins.size());
					canvas.paint(canvas.getGraphics());
					drawingEngine.refresh(canvas.getGraphics());
					NewInstance();

				}
			}
		};

		ActionListener Redrawing = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = Integer.valueOf(selectedDrawedPlugin.substring(selectedDrawedPlugin.length() - 1,
						selectedDrawedPlugin.length())) - 1;
				Shape shape = DrawedPlugins.get(index);
				DrawedPlugins.remove(index);
				DrawedPlugins.add(index, newInstance);
				newInstance.setPosition(new Point(x, y));
				drawingEngine.updateShape(Oldinstance, newInstance);			
				canvas.paint(canvas.getGraphics());
				drawingEngine.refresh(canvas.getGraphics());
			}
		};
		ActionListener UPattributeAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (property.equals("positionx")) {
					x = Integer.valueOf(app.getValue());
				} else if (property.equals("positiony")) {
					y = Integer.valueOf(app.getValue());
				} else {
					newInstance.getProperties().put(property, Double.valueOf(app.getValue()));
				}

			}
		};
		ActionListener attributeAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (property.equals("positionx")) {
					x = Integer.valueOf(app.getValue());
				} else if (property.equals("positiony")) {
					y = Integer.valueOf(app.getValue());
				} else {
					shape.getProperties().put(property, Double.valueOf(app.getValue()));
				}

			}
		};

		
		addAttribute.addActionListener(attributeAction);
		updateAttribute.addActionListener(UPattributeAction);
		redraw.addActionListener(Redrawing);
		draw.addActionListener(drawing);
		deletePlugin.addActionListener(delete);
	}

	public void NewInstance() {
		Oldinstance = shape;
		try {
			newInstance = (Shape) shape.clone();
		} catch (CloneNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < classes.size(); i++) {
			String classname = classes.get(i).getName();
			String[] strings = classname.split("\\.");
			if (strings[strings.length - 1].equals(selectedPlugin)) {
				try {

					shape = classes.get(i).newInstance();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setSelectedPlugin(String plugin) {
		selectedPlugin = plugin;
		for (int i = 0; i < classes.size(); i++) {
			String classname = classes.get(i).getName();
			String[] strings = classname.split("\\.");
			if (strings[strings.length - 1].equals(selectedPlugin)) {
				try {
					shape = classes.get(i).newInstance();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("select external jar");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		chooser.setAcceptAllFileFilterUsed(false);
		//
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			String s = chooser.getSelectedFile().getPath();
			String[] strings = s.substring(s.lastIndexOf("\\") + 1, s.length()).split("\\.");
			if (strings[strings.length - 1].equals("jar")) {
				drawingEngine.installPluginShape(s);
				classes = drawingEngine.getSupportedShapes();

				for (int i = 0; i < classes.size(); i++) {
					String[] strs = classes.get(i).getName().split("\\.");

					if (!shapes.contains(strs[strs.length - 1])) {
						shapes.add(strs[strs.length - 1]);
						app.addTextToPluginscombobox(strs[strs.length - 1]);
						Class<? extends Shape> plugin = classes.get(i);
						Map<String, Double> map = new HashMap<>();
						try {
							map = plugin.newInstance().getProperties();
						} catch (Exception e) {
							e.printStackTrace();
						}
						for (Entry<String, Double> e : map.entrySet()) {
							app.addattributes(e.getKey());
						}
						app.addattributes("positionx");
						app.addattributes("positiony");
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "file extension not supported", "Error", JOptionPane.ERROR_MESSAGE);
				actionPerformed(arg0);
			}

		} else {

		}

	}

}

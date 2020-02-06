package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Controller {
	private Canvas canvas;
	private JFrame frame;

	private JButton btnSave;
	private JButton btnLoad;
	private JButton btnColor2;
	private  JTextArea colorArea2;

	private Point p;
	private Map<String, Double> map;
	private MyDrawingEngine drawingEngine = new MyDrawingEngine();

	private saveController saveController;
	private loadController loadController;

	private ColorsController colorsController;

	private String selected = null;
	private MyShape shape1;
	private int sideLength;

	private pluginsController pluginController;

	private boolean drawmode = false;
	private SelectAndMoveController selectAndMoveController;
	private Color color = Color.black;


	public Controller(Canvas canvas, JButton btnundo, JButton btnredo, JButton btnSave, JButton btnLoad,
			JButton btnColors, JFrame frame, JButton plugin, JComboBox jComboBox, DrawingApp app, JButton draw,
			JButton addAttribute, JComboBox attributes, JComboBox supportedShapes, JTextArea colorArea, JButton btnColor2, JTextArea colorArea2,JButton delete) {

		this.canvas = canvas;

		this.btnSave = btnSave;
		this.btnLoad = btnLoad;
		this.btnColor2 = btnColor2;
		this.colorArea2 = colorArea2;
		colorArea2.setBackground(Color.black);

		this.frame = frame;
		saveController = new saveController(frame, btnSave, drawingEngine);
		loadController = new loadController(frame, btnLoad, drawingEngine, canvas);

		selectAndMoveController = new SelectAndMoveController(canvas, drawingEngine,delete);

		pluginController = new pluginsController(drawingEngine, frame, jComboBox, app, draw, addAttribute, attributes,
				supportedShapes, canvas);


		colorsController = new ColorsController(canvas, drawingEngine, btnColors, frame, colorArea,this.selectAndMoveController);


		drawingEngine.refresh(canvas.getGraphics());
		plugin.addActionListener(pluginController);
	}
	
	
	public void setColor() {
		color = JColorChooser.showDialog(frame, "Choose", Color.black);

		if(color !=null) {
			colorArea2.setBackground(color);
		} else {
			colorArea2.setBackground(Color.black);
			color = Color.BLACK;

		}
		
		
	}
	
	
	

	private void mousePressedAction(Point point) {
		canvas.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		shape1.setColor(color);
		shape1.setFillColor(Color.white);
		map = shape1.getProperties();
		shape1.setPosition(point);
	}

	public void triangle() {

		shape1 = new Triangle();
		selected = "Triangle";
		selectAndMoveController.setSelectmode(false);
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				if (selected.equals("Triangle")) {
					p = arg0.getPoint();
					mousePressedAction(p);

				}
			}
		});
		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (selected.equals("Triangle")) {
					canvas.paint(canvas.getGraphics());
					sideLength = (int) p.distance(e.getPoint());
					
					canvas.getGraphics().drawPolygon(new int[] { p.x, e.getX(), e.getX() - sideLength },
							new int[] { p.y, e.getY(), e.getY() }, 3);
					drawingEngine.refresh(canvas.getGraphics());
				}
			}
		});
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (selected.equals("Triangle")) {
					map = shape1.getProperties();

					map.put("x1", p.getX());
					map.put("y1", p.getY());
					map.put("x2", e.getPoint().getX());
					map.put("y2", e.getPoint().getY());
					map.put("x3", e.getPoint().getX() - sideLength);
					map.put("y3", e.getPoint().getY());
					shape1.setProperties(map);
					drawingEngine.addShape(shape1);
					drawingEngine.refresh(canvas.getGraphics());
					selectAndMoveController.setSelectmode(true);

					map = null;
					selected = "shape";

				}
			}
		});
	}

	public void circle() {
		shape1 = new Circle();
		selected = "circle";
		selectAndMoveController.setSelectmode(false);
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				if (selected.equals("circle")) {
					p = arg0.getPoint();
					mousePressedAction(p);
				}
			}
		});
		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (selected.equals("circle")) {
					canvas.paint(canvas.getGraphics());
					canvas.getGraphics().drawOval(p.x, p.y, (int) Math.abs(e.getX() - p.getX()),
							(int) Math.abs(e.getX() - p.getX()));
					drawingEngine.refresh(canvas.getGraphics());
				}
			}
		});
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (selected.equals("circle")) {
					map = shape1.getProperties();
					map.put("radius", Math.abs(e.getX() - p.getX()));
					shape1.setProperties(map);
					drawingEngine.addShape(shape1);
					drawingEngine.refresh(canvas.getGraphics());
					selectAndMoveController.setSelectmode(true);
					map = null;
					selected = "shape";
				}
			}
		});

	}

	public void square() {
		shape1 = new Square();
		selected = "square";
		selectAndMoveController.setSelectmode(false);
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				if (selected.equals("square")) {

					p = arg0.getPoint();
					mousePressedAction(p);
				}
			}
		});
		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (selected.equals("square")) {
					canvas.paint(canvas.getGraphics());
					canvas.getGraphics().drawRect(p.x, p.y, ((int) Math.abs(e.getX() - p.getX())),
							(int) Math.abs(e.getX() - p.getX()));
					drawingEngine.refresh(canvas.getGraphics());
				}
			}
		});
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (selected.equals("square")) {
					map = shape1.getProperties();
					map.put("length", Math.abs(e.getX() - p.getX()));
					shape1.setProperties(map);
					drawingEngine.addShape(shape1);
					drawingEngine.refresh(canvas.getGraphics());
					map = null;
					selectAndMoveController.setSelectmode(true);
					selected = "shape";
				}
			}
		});

	}

	public void rec() {
		shape1 = new Rectangle();
		selected = "rectangle";
		selectAndMoveController.setSelectmode(false);
		MouseAdapter adapter = new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				if (selected.equals("rectangle")) {
					
					p = arg0.getPoint();
					mousePressedAction(p);
				}
			}
		};
		canvas.addMouseListener(adapter);
		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (selected.equals("rectangle")) {
					canvas.paint(canvas.getGraphics());
					canvas.getGraphics().drawRect(p.x, p.y, ((int) Math.abs(e.getX() - p.getX())),
							(int) Math.abs(e.getY() - p.getY()));
					drawingEngine.refresh(canvas.getGraphics());
				}
			}
		});
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (selected.equals("rectangle")) {
					map = shape1.getProperties();

					map.put("width", Math.abs(e.getX() - p.getX()));
					map.put("length", Math.abs(e.getY() - p.getY()));
					shape1.setProperties(map);
					drawingEngine.addShape(shape1);
					drawingEngine.refresh(canvas.getGraphics());
					selectAndMoveController.setSelectmode(true);
					map = null;
					selected = "shape";
				}
			}
		});

	}

	public void ellipse() {
		shape1 = new Ellipse();
		selected = "ellipse";
		selectAndMoveController.setSelectmode(false);
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				if (selected.equals("ellipse")) {
					p = arg0.getPoint();
					mousePressedAction(p);
				}
			}
		});
		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (selected.equals("ellipse")) {
					canvas.paint(canvas.getGraphics());
					canvas.getGraphics().drawOval(p.x, p.y, (int) Math.abs(e.getX() - p.getX()),
							(int) Math.abs(e.getY() - p.getY()));
					drawingEngine.refresh(canvas.getGraphics());
				}
			}
		});
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (selected.equals("ellipse")) {
					map = shape1.getProperties();
					map.put("radiusx", Math.abs(e.getX() - p.getX()));
					map.put("radiusy", Math.abs(e.getY() - p.getY()));
					shape1.setProperties(map);
					drawingEngine.addShape(shape1);
					drawingEngine.refresh(canvas.getGraphics());
					selectAndMoveController.setSelectmode(true);
					map = null;
					selected = "shape";
				}
			}
		});
	}

	public void line() {
		shape1 = new LineSegment();
		selected = "LineSegment";
		selectAndMoveController.setSelectmode(false);
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				if (selected.equals("LineSegment")) {
					p = arg0.getPoint();
					mousePressedAction(p);
				}
			}
		});
		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (selected.equals("LineSegment")) {
					canvas.paint(canvas.getGraphics());
					canvas.getGraphics().drawLine(p.x, p.y, e.getX(), e.getY());
					drawingEngine.refresh(canvas.getGraphics());
				}
			}
		});
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (selected.equals("LineSegment")) {
					map = shape1.getProperties();
					map.put("x1", p.getX());
					map.put("y1", p.getY());
					map.put("x2", e.getPoint().getX());
					map.put("y2", e.getPoint().getY());
					shape1.setProperties(map);
					drawingEngine.addShape(shape1);
					drawingEngine.refresh(canvas.getGraphics());
					selectAndMoveController.setSelectmode(true);
					map = null;
					selected = "shape";
				}
			}
		});
	}

	public void undo() {
		drawingEngine.undo();
		canvas.paint(canvas.getGraphics());
		drawingEngine.refresh(canvas.getGraphics());
	}

	public void redo() {
		drawingEngine.redo();
		canvas.paint(canvas.getGraphics());
		drawingEngine.refresh(canvas.getGraphics());

	}

}

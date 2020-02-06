package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.Canvas;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;

import eg.edu.alexu.csd.oop.draw.Shape;

public class SelectAndMoveController implements MouseListener, MouseMotionListener {

	private MyDrawingEngine myDrawingEngine;
	private Canvas canvas;
	private Rectangle2D rectangle2d1 = new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);;
	private Rectangle2D rectangle2d2 = new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);;
	private Rectangle2D rectangle2d3 = new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);;
	private Rectangle2D rectangle2d4 = new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);;

	public SelectAndMoveController(Canvas canvas, MyDrawingEngine myDrawingEngine, JButton delete) {
		this.canvas = canvas;
		this.canvas.addMouseListener(this);
		this.canvas.addMouseMotionListener(this);
		this.myDrawingEngine = myDrawingEngine;

		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				myDrawingEngine.removeShape(s);
				canvas.paint(canvas.getGraphics());
				myDrawingEngine.refresh(canvas.getGraphics());
				s = null;
				rectangle2d1 = null;
				rectangle2d2 = null;
				rectangle2d3 = null;
				rectangle2d4 = null;
			}
		});

	}

	private int oldx;
	private int oldy;
	private Shape s;
	private final int ConstSelector = 30;
	private boolean selectmode = true;
	private int pressX;
	private int pressY;
	private double oldx1;
	private double oldy1;
	private double oldx2;
	private double oldy2;
	private double oldx3;
	private double oldy3;
	private double dx;
	private double dy;
	private double dx2;
	private double dy2;
	private Shape oldshape;
	private Shape newShape;
	private boolean shapeMoved = false;
	private String shape;
	private String shapeSelected = null;
	private boolean refresh = false;
	private boolean shapeDragged = false;
	private boolean shapeResized = false;
	private boolean instaneousDraging1 = false;
	private boolean instaneousDraging4 = false;
	private boolean instaneousDraging2 = false;
	private boolean instaneousDraging3 = false;
	private Point oldP;
	private Map<String, Double> oldMap;

	public void setSelectmode(boolean selectmode) {
		this.selectmode = selectmode;
	}

	@Override
	public void mousePressed(MouseEvent e) {

		Point p = e.getPoint();
		select(e);

		if (rectangle2d1.contains(p)||rectangle2d2.contains(p)||rectangle2d3.contains(p) || rectangle2d4.contains(p)) {
			oldshape = checkIfShapeSelected(e);
			oldMap = new HashMap<String, Double>();
			for (Entry<String, Double> en : oldshape.getProperties().entrySet()) {
				oldMap.put(en.getKey(), en.getValue());
			}

			oldP = new Point(oldshape.getPosition().x, oldshape.getPosition().y);
			instaneousDraging1 = false;
			instaneousDraging4 = false;
			instaneousDraging2 = false;
			canvas.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));

			shapeResized = true;
			return;

		} else if (checkIfShapeSelected(e) != null && selectmode) {
			canvas.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			shapeMoved = true;
			oldshape = checkIfShapeSelected(e);
			oldMap = new HashMap<String, Double>();
			for (Entry<String, Double> en : oldshape.getProperties().entrySet()) {
				oldMap.put(en.getKey(), en.getValue());
			}

			oldP = new Point(oldshape.getPosition().x, oldshape.getPosition().y);
			pressX = oldshape.getPosition().x;
			pressY = oldshape.getPosition().y;
			try {

				oldx1 = oldshape.getProperties().get("x1");
				oldy1 = oldshape.getProperties().get("y1");
				oldx2 = oldshape.getProperties().get("x2");
				oldy2 = oldshape.getProperties().get("y2");
				dx = oldshape.getProperties().get("x2") - oldshape.getProperties().get("x1");
				dy = oldshape.getProperties().get("y2") - oldshape.getProperties().get("y1");

				dx2 = oldshape.getProperties().get("x3") - oldshape.getProperties().get("x1");
				dy2 = oldshape.getProperties().get("y3") - oldshape.getProperties().get("y1");

				oldx3 = oldshape.getProperties().get("x3");
				oldy3 = oldshape.getProperties().get("y3");

			} catch (Exception e2) {
				// TODO: handle exception
			}

			// .out.println(checkIfShapeSelected(e).getClass().getName());
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		resizeDrag(e);
		if (!shapeResized && shapeMoved/* && canvas.contains(new Point(e.getX(), e.getY())) */ && selectmode) {
			shapeDragged = true;
			if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Rectangle") {

				oldshape.setPosition(new Point(e.getX(), e.getY()));
				canvas.paint(canvas.getGraphics());
				myDrawingEngine.refresh(canvas.getGraphics());

			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Circle") {

				oldshape.setPosition(new Point(e.getX(), e.getY()));
				canvas.paint(canvas.getGraphics());

				myDrawingEngine.refresh(canvas.getGraphics());
			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Ellipse") {

				oldshape.setPosition(new Point(e.getX(), e.getY()));
				canvas.paint(canvas.getGraphics());

				myDrawingEngine.refresh(canvas.getGraphics());
			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Square") {
				oldshape.setPosition(new Point(e.getX(), e.getY()));
				canvas.paint(canvas.getGraphics());

				myDrawingEngine.refresh(canvas.getGraphics());
			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.LineSegment") {

				oldshape.setPosition(new Point(e.getX(), e.getY()));

				oldshape.getProperties().put("x1", oldshape.getPosition().getX());
				oldshape.getProperties().put("y1", oldshape.getPosition().getY());
				oldshape.getProperties().put("x2", oldshape.getPosition().getX() + dx);
				oldshape.getProperties().put("y2", oldshape.getPosition().getY() + dy);

				canvas.paint(canvas.getGraphics());

				myDrawingEngine.refresh(canvas.getGraphics());
			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Triangle") {

				oldshape.setPosition(new Point(e.getX(), e.getY()));
				oldshape.getProperties().put("x1", oldshape.getPosition().getX());
				oldshape.getProperties().put("y1", oldshape.getPosition().getY());

				oldshape.getProperties().put("x2", oldshape.getPosition().getX() + dx);
				oldshape.getProperties().put("y2", oldshape.getPosition().getY() + dy);
				oldshape.getProperties().put("x3", oldshape.getPosition().getX() + dx2);
				oldshape.getProperties().put("y3", oldshape.getPosition().getY() + dy2);

				canvas.paint(canvas.getGraphics());

				myDrawingEngine.refresh(canvas.getGraphics());

			}

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if ((shapeMoved || shapeResized) && selectmode && shapeDragged) {
			canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			try {
				newShape = (Shape) oldshape.clone();
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			oldshape.setPosition(oldP);
			oldshape.setProperties(oldMap);
			myDrawingEngine.updateShape(oldshape, newShape);
			canvas.paint(canvas.getGraphics());
			myDrawingEngine.refresh(canvas.getGraphics());
			shape = "shape";
			shapeMoved = false;
			shapeDragged = false;
		}
		if (shapeResized) {
			shapeResized = false;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

		int x = e.getX();
		int y = e.getY();
		if (checkIfShapeSelected(e) != null && !shapeResized) {
			canvas.setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

	}

	private void select(MouseEvent e) {
		s = checkIfShapeSelected(e);
		if (s != null) {

			if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Rectangle") {

				Point point = s.getPosition();
				canvas.paint(canvas.getGraphics());
				myDrawingEngine.refresh(canvas.getGraphics());
				rectangle2d1 = new Rectangle2D.Double(point.x - ConstSelector / 2, point.y - ConstSelector / 2,
						ConstSelector, ConstSelector);
				rectangle2d2 = new Rectangle2D.Double(
						point.x + s.getProperties().get("width").intValue() - ConstSelector / 2,
						point.y - ConstSelector / 2, ConstSelector, ConstSelector);
				rectangle2d3 = new Rectangle2D.Double(point.x - ConstSelector / 2,
						point.y - ConstSelector / 2 + s.getProperties().get("length").intValue(), ConstSelector,
						ConstSelector);
				rectangle2d4 = new Rectangle2D.Double(
						point.x - ConstSelector / 2 + s.getProperties().get("width").intValue(),
						point.y + s.getProperties().get("length").intValue() - ConstSelector / 2, ConstSelector,
						ConstSelector);
				canvas.getGraphics().fillRect(point.x - ConstSelector / 2, point.y - ConstSelector / 2, ConstSelector,
						ConstSelector);
				canvas.getGraphics().fillRect(point.x + s.getProperties().get("width").intValue() - ConstSelector / 2,
						point.y - ConstSelector / 2, ConstSelector, ConstSelector);
				canvas.getGraphics().fillRect(point.x - ConstSelector / 2,
						point.y - ConstSelector / 2 + s.getProperties().get("length").intValue(), ConstSelector,
						ConstSelector);
				canvas.getGraphics().fillRect(point.x - ConstSelector / 2 + s.getProperties().get("width").intValue(),
						point.y + s.getProperties().get("length").intValue() - ConstSelector / 2, ConstSelector,
						ConstSelector);
				refresh = true;

			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Square") {

				Point point = s.getPosition();
				canvas.paint(canvas.getGraphics());
				myDrawingEngine.refresh(canvas.getGraphics());

				rectangle2d1 = new Rectangle2D.Double(point.x - ConstSelector / 2, point.y - ConstSelector / 2,
						ConstSelector, ConstSelector);
				rectangle2d2 = new Rectangle2D.Double(
						point.x + s.getProperties().get("length").intValue() - ConstSelector / 2,
						point.y - ConstSelector / 2, ConstSelector, ConstSelector);
				rectangle2d3 = new Rectangle2D.Double(point.x - ConstSelector / 2,
						point.y - ConstSelector / 2 + s.getProperties().get("length").intValue(), ConstSelector,
						ConstSelector);
				rectangle2d4 = new Rectangle2D.Double(
						point.x - ConstSelector / 2 + s.getProperties().get("length").intValue(),
						point.y + s.getProperties().get("length").intValue() - ConstSelector / 2, ConstSelector,
						ConstSelector);

				canvas.getGraphics().fillRect(point.x - ConstSelector / 2, point.y - ConstSelector / 2, ConstSelector,
						ConstSelector);
				canvas.getGraphics().fillRect(point.x + s.getProperties().get("length").intValue() - ConstSelector / 2,
						point.y - ConstSelector / 2, ConstSelector, ConstSelector);
				canvas.getGraphics().fillRect(point.x - ConstSelector / 2,
						point.y - ConstSelector / 2 + s.getProperties().get("length").intValue(), ConstSelector,
						ConstSelector);
				canvas.getGraphics().fillRect(point.x - ConstSelector / 2 + s.getProperties().get("length").intValue(),
						point.y + s.getProperties().get("length").intValue() - ConstSelector / 2, ConstSelector,
						ConstSelector);
				refresh = true;
			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Triangle") {

				Point point = s.getPosition();

				rectangle2d1 = new Rectangle2D.Double(point.x - ConstSelector / 2, point.y - ConstSelector / 2,
						ConstSelector, ConstSelector);

				rectangle2d2 = new Rectangle2D.Double(s.getProperties().get("x2").intValue() - ConstSelector / 2,
						s.getProperties().get("y2").intValue() - ConstSelector / 2, ConstSelector, ConstSelector);

				rectangle2d3 = new Rectangle2D.Double(s.getProperties().get("x3").intValue(),
						s.getProperties().get("y3").intValue() - ConstSelector / 2, ConstSelector, ConstSelector);

				canvas.paint(canvas.getGraphics());
				myDrawingEngine.refresh(canvas.getGraphics());
				canvas.getGraphics().fillRect(point.x - ConstSelector / 2, point.y - ConstSelector / 2, ConstSelector,
						ConstSelector);
				canvas.getGraphics().fillRect(s.getProperties().get("x2").intValue() - ConstSelector / 2,
						s.getProperties().get("y2").intValue() - ConstSelector / 2, ConstSelector, ConstSelector);
				canvas.getGraphics().fillRect(s.getProperties().get("x3").intValue(),
						s.getProperties().get("y3").intValue() - ConstSelector / 2, ConstSelector, ConstSelector);
				refresh = true;
			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Ellipse") {

				Point point = s.getPosition();
				canvas.paint(canvas.getGraphics());
				myDrawingEngine.refresh(canvas.getGraphics());

				rectangle2d1 = new Rectangle2D.Double(point.x - ConstSelector / 2, point.y - ConstSelector / 2,
						ConstSelector, ConstSelector);
				rectangle2d2 = new Rectangle2D.Double(
						point.x + s.getProperties().get("radiusx").intValue() - ConstSelector / 2,
						point.y - ConstSelector / 2, ConstSelector, ConstSelector);
				rectangle2d3 = new Rectangle2D.Double(point.x - ConstSelector / 2,
						point.y - ConstSelector / 2 + s.getProperties().get("radiusy").intValue(), ConstSelector,
						ConstSelector);
				rectangle2d4 = new Rectangle2D.Double(
						point.x - ConstSelector / 2 + s.getProperties().get("radiusx").intValue(),
						point.y + s.getProperties().get("radiusy").intValue() - ConstSelector / 2, ConstSelector,
						ConstSelector);

				canvas.getGraphics().fillRect(point.x - ConstSelector / 2, point.y - ConstSelector / 2, ConstSelector,
						ConstSelector);
				canvas.getGraphics().fillRect(point.x + s.getProperties().get("radiusx").intValue() - ConstSelector / 2,
						point.y - ConstSelector / 2, ConstSelector, ConstSelector);
				canvas.getGraphics().fillRect(point.x - ConstSelector / 2,
						point.y - ConstSelector / 2 + s.getProperties().get("radiusy").intValue(), ConstSelector,
						ConstSelector);
				canvas.getGraphics().fillRect(point.x - ConstSelector / 2 + s.getProperties().get("radiusx").intValue(),
						point.y + s.getProperties().get("radiusy").intValue() - ConstSelector / 2, ConstSelector,
						ConstSelector);
				refresh = true;
			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Circle") {

				Point point = s.getPosition();
				canvas.paint(canvas.getGraphics());
				myDrawingEngine.refresh(canvas.getGraphics());

				rectangle2d1 = new Rectangle2D.Double(point.x - ConstSelector / 2, point.y - ConstSelector / 2,
						ConstSelector, ConstSelector);
				rectangle2d2 = new Rectangle2D.Double(
						point.x + s.getProperties().get("radius").intValue() - ConstSelector / 2,
						point.y - ConstSelector / 2, ConstSelector, ConstSelector);
				rectangle2d3 = new Rectangle2D.Double(point.x - ConstSelector / 2,
						point.y - ConstSelector / 2 + s.getProperties().get("radius").intValue(), ConstSelector,
						ConstSelector);
				rectangle2d4 = new Rectangle2D.Double(
						point.x - ConstSelector / 2 + s.getProperties().get("radius").intValue(),
						point.y + s.getProperties().get("radius").intValue() - ConstSelector / 2, ConstSelector,
						ConstSelector);

				canvas.getGraphics().fillRect(point.x - ConstSelector / 2, point.y - ConstSelector / 2, ConstSelector,
						ConstSelector);
				canvas.getGraphics().fillRect(point.x + s.getProperties().get("radius").intValue() - ConstSelector / 2,
						point.y - ConstSelector / 2, ConstSelector, ConstSelector);
				canvas.getGraphics().fillRect(point.x - ConstSelector / 2,
						point.y - ConstSelector / 2 + s.getProperties().get("radius").intValue(), ConstSelector,
						ConstSelector);
				canvas.getGraphics().fillRect(point.x - ConstSelector / 2 + s.getProperties().get("radius").intValue(),
						point.y + s.getProperties().get("radius").intValue() - ConstSelector / 2, ConstSelector,
						ConstSelector);

				refresh = true;
			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.LineSegment") {

				Point point = s.getPosition();
				canvas.paint(canvas.getGraphics());
				myDrawingEngine.refresh(canvas.getGraphics());
				rectangle2d1 = new Rectangle2D.Double(point.x - ConstSelector / 2, point.y - ConstSelector / 2,
						ConstSelector, ConstSelector);
				rectangle2d2 = new Rectangle2D.Double(s.getProperties().get("x2").intValue() - ConstSelector / 2,
						s.getProperties().get("y2").intValue() - ConstSelector / 2, ConstSelector, ConstSelector);
				canvas.getGraphics().fillRect(point.x - ConstSelector / 2, point.y - ConstSelector / 2, ConstSelector,
						ConstSelector);
				canvas.getGraphics().fillRect(s.getProperties().get("x2").intValue() - ConstSelector / 2,
						s.getProperties().get("y2").intValue() - ConstSelector / 2, ConstSelector, ConstSelector);
				refresh = true;
			}

		} else {
			rectangle2d1 = new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);
			rectangle2d2 = new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);
			rectangle2d3 = new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);
			rectangle2d4 = new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);
			s = null;

			refresh = false;
			canvas.paint(canvas.getGraphics());
			myDrawingEngine.refresh(canvas.getGraphics());
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private Shape checkIfShapeSelected(MouseEvent e) {
		Shape[] list;

		list = myDrawingEngine.getShapes();

		for (int i = 0; i < list.length; ++i) {
			if (list[i].getClass().getName() == "eg.edu.alexu.csd.oop.draw.cs43.Rectangle") {
				Rectangle2D rec = new Rectangle2D.Double(list[i].getPosition().getX(), list[i].getPosition().getY(),
						list[i].getProperties().get("width"), list[i].getProperties().get("length"));
				if (rec.contains(new Point(e.getX(), e.getY()))) {
					shape = "eg.edu.alexu.csd.oop.draw.cs43.Rectangle";
					return list[i];
				}

			} else if (list[i].getClass().getName() == "eg.edu.alexu.csd.oop.draw.cs43.Circle") {

				Rectangle2D circle = new Rectangle2D.Double(list[i].getPosition().x, list[i].getPosition().y,
						list[i].getProperties().get("radius"), list[i].getProperties().get("radius"));

				if (circle.contains(new Point(e.getX(), e.getY()))) {
					shape = "eg.edu.alexu.csd.oop.draw.cs43.Circle";
					return list[i];
				}

			} else if (list[i].getClass().getName() == "eg.edu.alexu.csd.oop.draw.cs43.Ellipse") {
				Rectangle2D ellipse = new Rectangle2D.Double(list[i].getPosition().x, list[i].getPosition().y,
						list[i].getProperties().get("radiusx"), list[i].getProperties().get("radiusy"));

				if (ellipse.contains(new Point(e.getX(), e.getY()))) {
					shape = "eg.edu.alexu.csd.oop.draw.cs43.Ellipse";

					return list[i];
				}
			} else if (list[i].getClass().getName() == "eg.edu.alexu.csd.oop.draw.cs43.LineSegment") {

				int x = list[i].getPosition().x;
				int y = list[i].getPosition().y;
				int width = list[i].getProperties().get("x2").intValue() - list[i].getPosition().x;
				int length = list[i].getProperties().get("y2").intValue() - list[i].getPosition().y;

				Rectangle2D lineBound = new Rectangle2D.Double(x, y, width, length);
				Line2D lineSegment = new Line2D.Double(new Point(x, y), new Point(
						list[i].getProperties().get("x2").intValue(), list[i].getProperties().get("y2").intValue()));

				if (lineSegment.getBounds2D().contains(e.getPoint().getX(), e.getPoint().getY())) {

					shape = "eg.edu.alexu.csd.oop.draw.cs43.LineSegment";
					return list[i];

				}

			} else if (list[i].getClass().getName() == "eg.edu.alexu.csd.oop.draw.cs43.Square") {

				Rectangle2D square = new Rectangle2D.Double(list[i].getPosition().getX(), list[i].getPosition().getY(),
						list[i].getProperties().get("length"), list[i].getProperties().get("length"));
				if (square.contains(new Point(e.getX(), e.getY()))) {
					shape = "eg.edu.alexu.csd.oop.draw.cs43.Square";

					return list[i];
				}

			} else if (list[i].getClass().getName() == "eg.edu.alexu.csd.oop.draw.cs43.Triangle") {

				int posx = list[i].getPosition().x;
				int posy = list[i].getPosition().y;
				Double x1 = list[i].getProperties().get("x1");

				Double y1 = list[i].getProperties().get("y1");
				Double x2 = list[i].getProperties().get("x2");

				Double y2 = list[i].getProperties().get("y2");
				Double x3 = list[i].getProperties().get("x3");
				Double y3 = list[i].getProperties().get("y3");

				int arrX[] = { x1.intValue(), x2.intValue(), x3.intValue() };
				int arrY[] = { y1.intValue(), y2.intValue(), y3.intValue() };
				Polygon p = new Polygon(arrX, arrY, 3);

				if (p.contains(new Point(e.getX(), e.getY()))) {
					shape = "eg.edu.alexu.csd.oop.draw.cs43.Triangle";
					return list[i];
				}

			}
		}
		return null;
	}

	private void resizeDrag(MouseEvent e) {
		
	//	System.out.println(instaneousDraging2);
		if (shapeResized && selectmode) {
			shapeDragged = true;
			if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Rectangle") {

				if (rectangle2d1.contains(e.getPoint()) || instaneousDraging1) {
					instaneousDraging1 = true;
					instaneousDraging4 = false;
					Point p = oldshape.getPosition();
					oldshape.setPosition(new Point(e.getX(), e.getY()));
					Map<String, Double> map = oldshape.getProperties();
					map.put("length", map.get("length") + p.getY() - e.getY());
					map.put("width", map.get("width") + p.getX() - e.getX());
					rectangle2d1 = new Rectangle2D.Double(oldshape.getPosition().x - ConstSelector / 2,
							oldshape.getPosition().y - ConstSelector / 2, ConstSelector, ConstSelector);
					oldshape.setProperties(map);
					canvas.paint(canvas.getGraphics());
					myDrawingEngine.refresh(canvas.getGraphics());

				} else if (rectangle2d4.contains(e.getPoint()) || instaneousDraging4) {
					instaneousDraging4 = true;
					instaneousDraging1 = false;

					Map<String, Double> map = oldshape.getProperties();
					Point p = oldshape.getPosition();
					map.put("length", Math.abs(p.getY() - e.getY()));
					map.put("width", Math.abs(p.getX() - e.getX()));

					rectangle2d4 = new Rectangle2D.Double(e.getX() - ConstSelector / 2, e.getY() - ConstSelector / 2,
							ConstSelector, ConstSelector);
					oldshape.setProperties(map);
					canvas.paint(canvas.getGraphics());
					myDrawingEngine.refresh(canvas.getGraphics());
				}

			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Circle") {
				if (rectangle2d1.contains(e.getPoint()) || instaneousDraging1) {
					instaneousDraging1 = true;
					instaneousDraging4 = false;
					Point p = oldshape.getPosition();
					oldshape.setPosition(new Point(e.getX(), e.getY()));
					Map<String, Double> map = oldshape.getProperties();
					map.put("radius", map.get("radius") + p.getY() - e.getY());

					rectangle2d1 = new Rectangle2D.Double(oldshape.getPosition().x - ConstSelector / 2,
							oldshape.getPosition().y - ConstSelector / 2, ConstSelector, ConstSelector);
					oldshape.setProperties(map);
					canvas.paint(canvas.getGraphics());
					myDrawingEngine.refresh(canvas.getGraphics());

				} else if (rectangle2d4.contains(e.getPoint()) || instaneousDraging4) {
					instaneousDraging4 = true;
					instaneousDraging1 = false;

					Map<String, Double> map = oldshape.getProperties();
					Point p = oldshape.getPosition();
					map.put("radius", Math.abs(p.getY() - e.getY()));

					rectangle2d4 = new Rectangle2D.Double(e.getX() - ConstSelector / 2, e.getY() - ConstSelector / 2,
							ConstSelector, ConstSelector);
					oldshape.setProperties(map);
					canvas.paint(canvas.getGraphics());
					myDrawingEngine.refresh(canvas.getGraphics());

				}

			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Ellipse") {
				if (rectangle2d1.contains(e.getPoint()) || instaneousDraging1) {
					instaneousDraging1 = true;
					instaneousDraging4 = false;

					Point p = oldshape.getPosition();
					oldshape.setPosition(new Point(e.getX(), e.getY()));
					Map<String, Double> map = oldshape.getProperties();
					map.put("radiusy", map.get("radiusy") + p.getY() - e.getY());
					map.put("radiusx", map.get("radiusx") + p.getX() - e.getX());
					rectangle2d1 = new Rectangle2D.Double(oldshape.getPosition().x - ConstSelector / 2,
							oldshape.getPosition().y - ConstSelector / 2, ConstSelector, ConstSelector);
					oldshape.setProperties(map);
					canvas.paint(canvas.getGraphics());
					myDrawingEngine.refresh(canvas.getGraphics());

				} else if (rectangle2d4.contains(e.getPoint()) || instaneousDraging4) {
					instaneousDraging4 = true;
					instaneousDraging1 = false;

					Map<String, Double> map = oldshape.getProperties();
					Point p = oldshape.getPosition();
					map.put("radiusx", Math.abs(p.getX() - e.getX()));
					map.put("radiusy", Math.abs(p.getY() - e.getY()));

					rectangle2d4 = new Rectangle2D.Double(e.getX() - ConstSelector / 2, e.getY() - ConstSelector / 2,
							ConstSelector, ConstSelector);
					oldshape.setProperties(map);
					canvas.paint(canvas.getGraphics());
					myDrawingEngine.refresh(canvas.getGraphics());

				}
				shapeSelected = null;
			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.LineSegment") {

				if (rectangle2d1.contains(e.getPoint()) || instaneousDraging1) {
					instaneousDraging1 = true;
					instaneousDraging2 = false;
					oldshape.setPosition(new Point(e.getX(), e.getY()));
					Map<String, Double> map = oldshape.getProperties();
					map.put("x1", e.getPoint().getX());
					map.put("y1", e.getPoint().getY());
					rectangle2d1 = new Rectangle2D.Double(oldshape.getPosition().x - ConstSelector / 2,
							oldshape.getPosition().y - ConstSelector / 2, ConstSelector, ConstSelector);
					oldshape.setProperties(map);
					canvas.paint(canvas.getGraphics());
					myDrawingEngine.refresh(canvas.getGraphics());

				} else if (rectangle2d2.contains(e.getPoint()) || instaneousDraging2) {
					instaneousDraging2 = true;
					instaneousDraging1 = false;
					
					Map<String, Double> map = oldshape.getProperties();
					
					map.put("x2", e.getPoint().getX());
					map.put("y2", e.getPoint().getY());
					rectangle2d2 = new Rectangle2D.Double(e.getX() - ConstSelector / 2, e.getY() - ConstSelector / 2,
							ConstSelector, ConstSelector);
					oldshape.setProperties(map);
					canvas.paint(canvas.getGraphics());
					myDrawingEngine.refresh(canvas.getGraphics());
				}
			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Square") {

				if (rectangle2d1.contains(e.getPoint()) || instaneousDraging1) {
					instaneousDraging1 = true;
					instaneousDraging4 = false;
					Point p = oldshape.getPosition();
					oldshape.setPosition(new Point(e.getX(), e.getY()));
					Map<String, Double> map = oldshape.getProperties();
					map.put("length", map.get("length") + p.getY() - e.getY());

					rectangle2d1 = new Rectangle2D.Double(oldshape.getPosition().x - ConstSelector / 2,
							oldshape.getPosition().y - ConstSelector / 2, ConstSelector, ConstSelector);
					oldshape.setProperties(map);
					canvas.paint(canvas.getGraphics());
					myDrawingEngine.refresh(canvas.getGraphics());

				} else if (rectangle2d4.contains(e.getPoint()) || instaneousDraging4) {
					instaneousDraging4 = true;
					instaneousDraging1 = false;

					Map<String, Double> map = oldshape.getProperties();
					Point p = oldshape.getPosition();
					map.put("length", Math.abs(p.getY() - e.getY()));

					rectangle2d4 = new Rectangle2D.Double(e.getX() - ConstSelector / 2, e.getY() - ConstSelector / 2,
							ConstSelector, ConstSelector);
					oldshape.setProperties(map);
					canvas.paint(canvas.getGraphics());
					myDrawingEngine.refresh(canvas.getGraphics());
				}
			} else if (shape == "eg.edu.alexu.csd.oop.draw.cs43.Triangle") {
				if (rectangle2d1.contains(e.getPoint()) || instaneousDraging1) {
					instaneousDraging1 = true;
					instaneousDraging2 = false;
					instaneousDraging3 = false;
					oldshape.setPosition(new Point(e.getX(), e.getY()));
					Map<String, Double> map = oldshape.getProperties();
					map.put("x1", e.getPoint().getX());
					map.put("y1", e.getPoint().getY());
					rectangle2d1 = new Rectangle2D.Double(oldshape.getPosition().x - ConstSelector / 2,
							oldshape.getPosition().y - ConstSelector / 2, ConstSelector, ConstSelector);
					oldshape.setProperties(map);
					canvas.paint(canvas.getGraphics());
					myDrawingEngine.refresh(canvas.getGraphics());

				} else if (rectangle2d2.contains(e.getPoint()) || instaneousDraging2) {
					instaneousDraging2 = true;
					instaneousDraging1 = false;
					instaneousDraging3 = false;
					Map<String, Double> map = oldshape.getProperties();
					
					map.put("x2", e.getPoint().getX());
					map.put("y2", e.getPoint().getY());
					rectangle2d2 = new Rectangle2D.Double(e.getX() - ConstSelector / 2, e.getY() - ConstSelector / 2,
							ConstSelector, ConstSelector);
					oldshape.setProperties(map);
					canvas.paint(canvas.getGraphics());
					myDrawingEngine.refresh(canvas.getGraphics());
				}else if (rectangle2d3.contains(e.getPoint()) || instaneousDraging3) {
					instaneousDraging3 = true;
					instaneousDraging1 = false;
					instaneousDraging2 = false;
					Map<String, Double> map = oldshape.getProperties();
					
					map.put("x3", e.getPoint().getX());
					map.put("y3", e.getPoint().getY());
					rectangle2d3 = new Rectangle2D.Double(e.getX() - ConstSelector / 2, e.getY() - ConstSelector / 2,
							ConstSelector, ConstSelector);
					oldshape.setProperties(map);
					canvas.paint(canvas.getGraphics());
					myDrawingEngine.refresh(canvas.getGraphics());
				}
			}
		}
	}
}

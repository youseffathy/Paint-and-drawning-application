package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import eg.edu.alexu.csd.oop.draw.Shape;

public class ColorsController implements ActionListener, MouseListener {
	private String shape;
	private Color color;
	private Canvas canvas;
	private JFrame frame;
	private MyDrawingEngine myDrawingEngine;
	private JButton btnColors;
	boolean colorIsSelected = false;
	private JTextArea colorArea;
	private SelectAndMoveController selectAndMoveController;

	public ColorsController(Canvas canvas, MyDrawingEngine myDrawingEngine, JButton btnColors, JFrame frame,
			JTextArea colorArea, SelectAndMoveController selectAndMoveController) {
		this.canvas = canvas;
		this.frame = frame;
		this.myDrawingEngine = myDrawingEngine;
		this.selectAndMoveController = selectAndMoveController;
		btnColors.addActionListener(this);
		this.canvas.addMouseListener(this);
		this.colorArea = colorArea;
		this.selectAndMoveController = selectAndMoveController;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		color = new Color(0, 0, 0);
		color = JColorChooser.showDialog(frame, "Choose", Color.white);

		if(color != null) {
			selectAndMoveController.setSelectmode(false);
			colorIsSelected = true;
			colorArea.setBackground(color);


		}else {
			colorArea.setBackground(Color.WHITE);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		Shape selectedShape = checkIfShapeSelected(e);
		Shape newShape = new MyShape();
		if (selectedShape != null && colorIsSelected) {

			
			colorIsSelected = false;
			
			try {
				newShape = (Shape) selectedShape.clone();
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			newShape.setFillColor(color);
			myDrawingEngine.updateShape(selectedShape, newShape);
			myDrawingEngine.refresh(canvas.getGraphics());
			colorArea.setBackground(Color.white);
			selectAndMoveController.setSelectmode(true);

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {

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

				Ellipse2D circle = new Ellipse2D.Double(list[i].getPosition().x, list[i].getPosition().y,
						list[i].getProperties().get("radius"), list[i].getProperties().get("radius"));

				if (circle.contains(new Point(e.getX(), e.getY()))) {
					shape = "eg.edu.alexu.csd.oop.draw.cs43.Circle";
					return list[i];
				}

			} else if (list[i].getClass().getName() == "eg.edu.alexu.csd.oop.draw.cs43.Ellipse") {
				Ellipse2D ellipse = new Ellipse2D.Double(list[i].getPosition().x, list[i].getPosition().y,
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

}

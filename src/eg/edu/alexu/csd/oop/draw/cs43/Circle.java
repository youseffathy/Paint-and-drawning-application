package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;
import java.awt.geom.Ellipse2D;

public class Circle extends Ellipse {
	public Circle() {
		Map<String, Double> map = getProperties();
		map.put("radius", 0.0);
		setProperties(map);

	}

	@Override
	public void draw(Graphics canvas) {

		if (getFillColor().equals(Color.white)) {
			if(getColor().equals(Color.white)) {
				canvas.setColor(Color.black);
			}else {
				canvas.setColor(getColor());
			}
			canvas.drawOval(getPosition().x, getPosition().y, getProperties().get("radius").intValue(),
					getProperties().get("radius").intValue());
			return;
		}
		canvas.setColor(getFillColor());
		canvas.fillOval(getPosition().x, getPosition().y, getProperties().get("radius").intValue(),
				getProperties().get("radius").intValue());
		
	}

	@Override
	public boolean contains(Point p) {
		Map<String, Double> map = getProperties();
		double raduis = map.get("radius");
		Point point = getPosition();

		Ellipse2D ellipse2d = new Ellipse2D.Double(point.getX() - raduis, point.getY() - raduis, 2.0 * raduis,
				2.0 * raduis);
		if (ellipse2d.contains(p)) {
			return true;
		}
		return false;
	}

}

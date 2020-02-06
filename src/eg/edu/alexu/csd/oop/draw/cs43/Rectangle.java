package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.GenericArrayType;
import java.util.HashMap;
import java.util.Map;

public class Rectangle extends MyShape {
	public Rectangle() {
		Map<String, Double> map = getProperties();
		map.put("width", 0.0);
		map.put("length", 0.0);
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
			
			canvas.drawRect(getPosition().x, getPosition().y, getProperties().get("width").intValue(),
					getProperties().get("length").intValue());

			return;

		}
		canvas.setColor(getFillColor());
		canvas.fillRect(getPosition().x, getPosition().y, getProperties().get("width").intValue(),
				getProperties().get("length").intValue());
	
		

	}

	public boolean contains(Point p) {
		Map<String, Double> map = getProperties();
		Rectangle2D rec = new Rectangle2D.Double(getPosition().getX(), getPosition().getY(),
				getProperties().get("width"), getProperties().get("lenth"));
		if (rec.contains(p)) {
			return true;
		}

		return false;

	}

}

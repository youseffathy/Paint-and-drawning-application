package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Square extends Rectangle {
	public Square() {
		Map<String, Double> map = getProperties();
		map.put("length", 0.0);
		setProperties(map);
	}
	@Override
	public void draw(Graphics canvas) {
		if (getFillColor().equals(Color.white)) {

			canvas.drawRect(getPosition().x, getPosition().y, getProperties().get("length").intValue(),
					getProperties().get("length").intValue());

			return;

		}
		canvas.setColor(getFillColor());
		canvas.fillRect(getPosition().x, getPosition().y, getProperties().get("length").intValue(),
				getProperties().get("length").intValue());
		// to avoid set the new shape with same color
		canvas.setColor(Color.black);
	}
/*	public boolean contains(Point p) {
		if(p.x = <)
	}
*/
}

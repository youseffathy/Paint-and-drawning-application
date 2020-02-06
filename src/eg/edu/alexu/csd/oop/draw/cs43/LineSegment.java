package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

public class LineSegment extends MyShape {
	public LineSegment() {
		Map<String, Double> map = getProperties();
		map.put("x1", 0.0);
		map.put("y1", 0.0);
		map.put("x2", 0.0);
		map.put("y2", 0.0);
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

			canvas.drawLine(getPosition().x, getPosition().y, getProperties().get("x2").intValue(),
					getProperties().get("y2").intValue());

			return;

		}
		canvas.setColor(getFillColor());
		canvas.drawLine(getPosition().x, getPosition().y, getProperties().get("x2").intValue(),
				getProperties().get("y2").intValue());



	}

}

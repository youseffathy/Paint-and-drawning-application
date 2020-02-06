package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

public class Ellipse extends MyShape {
	public Ellipse() {
		Map<String, Double> map = getProperties();
		map.put("radiusx", 0.0);
		map.put("radiusy", 0.0);
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

			canvas.drawOval(getPosition().x, getPosition().y, getProperties().get("radiusx").intValue(),
					getProperties().get("radiusy").intValue());
			return;

		}
		canvas.setColor(getFillColor());
		canvas.fillOval(getPosition().x, getPosition().y, getProperties().get("radiusx").intValue(),
				getProperties().get("radiusy").intValue());
		
	}

}

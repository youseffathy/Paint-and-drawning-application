package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import eg.edu.alexu.csd.oop.draw.Shape;

public class MyShape implements Shape ,ShapeFoundPoint,Cloneable{
	private Point point = null;
	private Map<String, Double> properties = new HashMap<>();
	
	private Color color = new Color(0, 0, 0);
	private Color fillcolor = new Color(0, 0, 0);
	public MyShape() {
		//hnktb el properities el common w fe kol class lel shapes hnktb el ba2y
		properties.put("positionx", 0.0);
		properties.put("positiony", 0.0);
	}
	@Override
	public void setPosition(Point position) {
		point = position;
		
		properties.put("positionx", position.getX());
		properties.put("positiony", position.getY());

	}

	@Override
	public Point getPosition() {
		
		return point;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		this.properties = properties;
	}

	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		return properties;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;

	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public void setFillColor(Color color) {
		// TODO Auto-generated method stub
		fillcolor = color;
	}

	@Override
	public Color getFillColor() {
		// TODO Auto-generated method stub
		return fillcolor;
	}

	@Override
	public void draw(Graphics canvas) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		//deep cloning
		Shape myShape;
		try {
			myShape = this.getClass().newInstance();
			
			myShape.setColor(new Color(getColor().getRed(),getColor().getGreen(),getColor().getBlue()));
			myShape.setFillColor(new Color(getFillColor().getRed(),getFillColor().getGreen(),getFillColor().getBlue()));
			myShape.setPosition(new Point(getPosition().x,getPosition().y));
			Map<String, Double> newMap = new HashMap<String, Double>();
			for(Entry<String, Double> e : getProperties().entrySet()) {
				newMap.put(e.getKey(), e.getValue());
			}
			myShape.setProperties(newMap);
			return myShape;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	public boolean contains(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

}

package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public abstract class Test {

	public static void main(String[] args) {
		Shape shape1 = new Circle();
		shape1.setPosition(new Point(3, 4));
		try {
			Shape shape2 = (Shape) shape1.clone();
			shape2.setPosition(new Point(3, 5));
			System.out.println();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

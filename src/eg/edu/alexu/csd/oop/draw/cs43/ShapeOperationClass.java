package eg.edu.alexu.csd.oop.draw.cs43;


import eg.edu.alexu.csd.oop.draw.Shape;

public class ShapeOperationClass {
	private Shape shape ;
	private String operation;
	private Shape shape2;
	 public ShapeOperationClass(Shape shape,Shape shape2,String operation) {
		 this.shape = shape;
		 this.operation = operation;
		 this.shape2 = shape2;
		 
	}
	 public String  getOperation() {
		 return operation;
	 }
	 public Shape  getShape() {
		 return shape;
	 }
	 public Shape  getShape2() {
		 return shape2;
	 }
}

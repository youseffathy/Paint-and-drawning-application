package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

public class ResizeController implements MouseMotionListener, MouseListener {
	SelectAndMoveController selectAndMoveController;
	private Rectangle2D rectangle2d1;
	private Rectangle2D rectangle2d2;
	private Rectangle2D rectangle2d3;
	private Rectangle2D rectangle2d4;
	public ResizeController(SelectAndMoveController selectAndMoveController) {
		this.selectAndMoveController = selectAndMoveController;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}

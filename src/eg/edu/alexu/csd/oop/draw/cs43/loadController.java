package eg.edu.alexu.csd.oop.draw.cs43;

import static org.junit.Assert.assertNull;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class loadController implements ActionListener {

	private MyDrawingEngine myDrawingEngine;

	private JFrame frame;

	private JButton btnLoad;

	String choosertitle;
	JFileChooser chooser;
	Canvas canvas;

	public loadController(JFrame frame, JButton btnLoad, MyDrawingEngine myDrawingEngine, Canvas canvas) {

		this.frame = frame;
		this.btnLoad = btnLoad;
		this.canvas = canvas;

		this.btnLoad.addActionListener(this);
		this.myDrawingEngine = myDrawingEngine;

	}

	public void load(String path) {

		myDrawingEngine.load(path);
		canvas.addNotify();
		canvas.paint(canvas.getGraphics());
		myDrawingEngine.refresh(canvas.getGraphics());
	}

	public void actionPerformed(ActionEvent e) {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		//
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			String s = chooser.getSelectedFile().getPath();
			String[] strings =s.substring(s.lastIndexOf("\\") + 1, s.length()).split("\\.");
			if (strings[strings.length-1].equals("xml")
					||strings[strings.length-1].equals("json")) {
				load(s);
			} else {
				JOptionPane.showMessageDialog(null, "can't load this file extension", "loading Error",
						JOptionPane.ERROR_MESSAGE);
				actionPerformed(e);
			}

		} else {

		}
	}



}

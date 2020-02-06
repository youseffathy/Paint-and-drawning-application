package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class saveController implements ActionListener {

	private MyDrawingEngine myDrawingEngine;

	private JFrame frame;

	private JButton btnSave;

	private String choosertitle;
	private JFileChooser chooser;

	public saveController(JFrame frame, JButton btnSave, MyDrawingEngine myDrawingEngine) {

		this.frame = frame;

		this.btnSave = btnSave;

		this.btnSave.addActionListener(this);
		this.myDrawingEngine = myDrawingEngine;

	}

	public void save(String path) {
		myDrawingEngine.save(path);
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
				save(s);
			} else {
				JOptionPane.showMessageDialog(null, "file extension not supported", "saving Error",
						JOptionPane.ERROR_MESSAGE);
				actionPerformed(e);
			}

		} else {
			System.out.println("No Selection");
		}
	}

	

}

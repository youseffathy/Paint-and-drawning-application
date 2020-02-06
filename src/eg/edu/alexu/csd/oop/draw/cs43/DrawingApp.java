package eg.edu.alexu.csd.oop.draw.cs43;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import java.awt.Label;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class DrawingApp {

	private JFrame frame;
	private JComboBox supportedShapes;
	private JTextArea colorArea = new JTextArea();
	private final JComboBox properties = new JComboBox(new Object[] {});
	private JButton btnSave = new JButton("");
	private JButton btnLoad = new JButton("");
	private JButton addAttribute = new JButton("add attribute");

	private JButton btnColors = new JButton("");
	private JButton btnColor2 = new JButton("color");
	private JButton btnUndo = new JButton("");
	private JButton btnRedo = new JButton("");
	private Canvas canvas = new Canvas();
	private JButton plugin = new JButton("");
	private final JButton Draw = new JButton("Draw");

	private final JButton circle = new JButton("");
	private final JButton square = new JButton("");
	private final JButton ellipse = new JButton("");
	private final JButton triangle = new JButton("");
	private final JButton line = new JButton("");
	private final JButton rectangle = new JButton("");
	private final JLabel lblPlugins = new JLabel("plugins");

	private final JTextField value = new JTextField();
	private final JLabel property = new JLabel("attribute");
	private pluginsController pluginsController;
	private final JTextArea colorArea2 = new JTextArea();
	private final JButton delete = new JButton("");
	private final JLabel lblSelectedPlugins = new JLabel("selected plugin");
	private final JButton DeletePlugin = new JButton("delete");
	private final JButton btnUpdateAttribute = new JButton("update attribute");
	private final JButton redraw = new JButton("Redraw");

	private Controller controller = new Controller(canvas, btnUndo, btnRedo, btnSave, btnLoad, btnColors, frame, plugin,
			supportedShapes, this, Draw, addAttribute, properties, supportedShapes, colorArea, btnColor2, colorArea2,
			delete);
	private final JComboBox selectedplugin = new JComboBox(new Object[] {});

	public void setpluginsController(pluginsController controller) {
		this.pluginsController = controller;
		pluginsController.setButtons(redraw, btnUpdateAttribute, DeletePlugin);
	}

	public void addTextToPluginscombobox(String s) {
		supportedShapes.addItem(s);
	}

	public void addattributes(String s) {
		properties.addItem(s);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrawingApp window = new DrawingApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DrawingApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		value.setBounds(226, 80, 110, 20);
		value.setColumns(10);

		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.inactiveCaption);
		frame.setBounds(100, 100, 929, 631);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		canvas.setBackground(Color.WHITE);
		canvas.setBounds(0, 119, 913, 473);
		frame.getContentPane().add(canvas);

		btnUndo.setIcon(new ImageIcon(DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/icons8-undo-52.png")));

		btnUndo.setBounds(813, 6, 40, 40);

		frame.getContentPane().add(btnUndo);

		btnRedo.setIcon(new ImageIcon(DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/icons8-redo-52.png")));

		btnRedo.setBounds(858, 6, 40, 40);

		frame.getContentPane().add(btnRedo);
		btnSave.setIcon(new ImageIcon(DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/save.png")));

		btnSave.setBounds(813, 66, 40, 40);

		frame.getContentPane().add(btnSave);
		btnLoad.setIcon(new ImageIcon(DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/img_510381 (2).png")));

		btnLoad.setBounds(858, 66, 40, 40);
		frame.getContentPane().add(btnLoad);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		circle.setIcon(new ImageIcon(
				DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/circle-png-circle-png-hd-1600.png")));

		circle.setBounds(60, 21, 40, 40);
		frame.getContentPane().add(circle);

		square.setIcon(new ImageIcon(
				DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/ui-element-square-border-frame-512.png")));

		square.setBounds(110, 71, 40, 40);

		frame.getContentPane().add(square);

		ellipse.setIcon(new ImageIcon(DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/ellipse.png")));

		ellipse.setBounds(60, 71, 40, 40);

		frame.getContentPane().add(ellipse);

		triangle.setIcon(new ImageIcon(DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/tri.png")));

		triangle.setBounds(10, 71, 40, 40);

		frame.getContentPane().add(triangle);
		line.setIcon(new ImageIcon(DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/line.png")));

		line.setBounds(110, 21, 40, 40);
		frame.getContentPane().add(line);

		rectangle.setIcon(
				new ImageIcon(DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/rectangle-transparent-5.png")));
		rectangle.setBounds(10, 21, 40, 40);

		frame.getContentPane().add(rectangle);

		Label label = new Label("Supported shapes");
		label.setFont(new Font("Calibri", Font.BOLD, 12));
		label.setAlignment(Label.CENTER);
		label.setBounds(25, 0, 112, 20);
		frame.getContentPane().add(label);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(160, 5, 2, 108);
		frame.getContentPane().add(separator);

		plugin.setIcon(new ImageIcon(DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/add-512.png")));
		plugin.setBackground(SystemColor.inactiveCaption);
		plugin.setFont(new Font("Calibri", Font.BOLD, 25));
		plugin.setBounds(172, 19, 37, 37);
		plugin.setBorderPainted(false);

		frame.getContentPane().add(plugin);
		lblPlugins.setFont(new Font("Calibri", Font.BOLD, 12));
		lblPlugins.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlugins.setBounds(195, 1, 60, 20);

		frame.getContentPane().add(lblPlugins);

		String[] shapes = {};
		supportedShapes = new JComboBox(shapes);
		supportedShapes.setToolTipText("plugins");
		supportedShapes.setBounds(226, 19, 110, 20);
		frame.getContentPane().add(supportedShapes);
		supportedShapes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pluginsController.setSelectedPlugin((String) supportedShapes.getSelectedItem());
			}
		});

		properties.setToolTipText("attributes");
		properties.setBounds(226, 49, 110, 20);
		properties.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pluginsController.setSelectedAttribute((String) properties.getSelectedItem());

			}
		});
		frame.getContentPane().add(properties);

		frame.getContentPane().add(value);
		property.setFont(new Font("Calibri", Font.BOLD, 12));
		property.setBounds(172, 84, 46, 14);

		frame.getContentPane().add(property);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(643, 5, 2, 108);
		frame.getContentPane().add(separator_1);
		addAttribute.setFont(new Font("Calibri", Font.BOLD, 11));

		addAttribute.setBounds(360, 49, 110, 20);
		frame.getContentPane().add(addAttribute);
		btnColors.setIcon(
				new ImageIcon(DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/icons8-fill-color-26.png")));

		btnColors.setBounds(655, 6, 40, 40);
		frame.getContentPane().add(btnColors);
		Draw.setFont(new Font("Calibri", Font.BOLD, 11));
		Draw.setBounds(360, 19, 110, 20);

		frame.getContentPane().add(Draw);

		colorArea.setBounds(704, 6, 40, 40);
		frame.getContentPane().add(colorArea);

		JButton btnColor2 = new JButton("");
		btnColor2.setIcon(
				new ImageIcon(DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/icons8-paint-palette-26.png")));
		btnColor2.setBounds(655, 66, 40, 40);
		frame.getContentPane().add(btnColor2);
		colorArea2.setEnabled(false);
		colorArea2.setEditable(false);
		colorArea2.setBounds(704, 66, 40, 40);

		frame.getContentPane().add(colorArea2);
		delete.setIcon(new ImageIcon(DrawingApp.class.getResource("/eg/edu/alexu/csd/oop/icons/icons8-trash-26.png")));
		delete.setBounds(752, 32, 45, 40);

		frame.getContentPane().add(delete);
		selectedplugin.setToolTipText("selected plugin");
		selectedplugin.setBounds(494, 19, 110, 20);
		selectedplugin.addItem("none");
		
		selectedplugin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				pluginsController.setSelectedDrawedPlugin(selectedplugin.getSelectedItem().toString());
				if(!selectedplugin.getSelectedItem().equals("none")) {
					System.out.println("ss");
					addAttribute.setEnabled(false);
					Draw.setEnabled(false);
				}else {
					System.out.println("en");
					addAttribute.setEnabled(true);
					Draw.setEnabled(true);
				}
			}
		});

		frame.getContentPane().add(selectedplugin);
		lblSelectedPlugins.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectedPlugins.setFont(new Font("Calibri", Font.BOLD, 12));
		lblSelectedPlugins.setBounds(500, 0, 85, 20);

		frame.getContentPane().add(lblSelectedPlugins);
		DeletePlugin.setFont(new Font("Calibri", Font.BOLD, 11));
		DeletePlugin.setBounds(494, 80, 110, 20);

		frame.getContentPane().add(DeletePlugin);
		btnUpdateAttribute.setFont(new Font("Calibri", Font.BOLD, 11));
		btnUpdateAttribute.setBounds(360, 80, 110, 20);

		frame.getContentPane().add(btnUpdateAttribute);
		redraw.setFont(new Font("Calibri", Font.BOLD, 11));
		redraw.setBounds(494, 49, 110, 20);

		frame.getContentPane().add(redraw);

		rectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.rec();
			}
		});
		ellipse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.ellipse();
			}
		});

		square.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.square();
			}
		});

		line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.line();
			}
		});

		triangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.triangle();
			}
		});

		circle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.circle();
			}
		});

		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.undo();
			}
		});

		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.redo();
			}
		});
		btnColor2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.setColor();
			}
		});

	}

	public void addDrawedPlugin(String string) {
		selectedplugin.addItem(string);
	}

	public void removeDrawedPlugin() {
		selectedplugin.removeItem(selectedplugin.getSelectedItem());
	}

	public String getValue() {
		return value.getText();
	}
}

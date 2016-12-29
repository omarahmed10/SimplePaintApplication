package paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static boolean flag;
	public static void main(String[] args) {
		// Create a File object on the root of the directory containing the class file  
		File file = new File("/home/omar/myClasses");
		
		try {
			// Convert File to a URL
			@SuppressWarnings("deprecation")
			URL url = file.toURL();          // file:/c:/myclasses/
			URL[] urls = new URL[]{url};
			
			// Create a new class loader with the directory
			ClassLoader cl = new URLClassLoader(urls);
			
			// Load in the class; MyClass.class should be located in
			// the directory file:/c:/myclasses/com/mycompany
			Class cls = cl.loadClass("paint.Square");
			flag = true;
			System.out.println("done");
		} catch (MalformedURLException e) {
		} catch (ClassNotFoundException e) {
			flag =false;
		}
		new MainFrame();
		
	}

	private Color color = Color.BLACK;

	public MainFrame() {
		super("Paint");
		try {
			for (LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(
							info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Can not set look and feel");
		}
		setLayout(new BorderLayout());
		JFileChooser fileChooser = new JFileChooser();
		DrawArea drawArea = new DrawArea();
		EditEngine editEngine = new EditEngine(drawArea);
		Resizer resizer = new Resizer(drawArea, editEngine);
		drawArea.addMouseListener(resizer);
		drawArea.addMouseMotionListener(resizer);
		MenuBar menu = new MenuBar(fileChooser, drawArea);
		setJMenuBar(menu);
		drawArea.setBackground(Color.GRAY.brighter());
		drawArea.setDrawColor(color);
		add(drawArea, BorderLayout.CENTER);
		add(new Toolbar(drawArea, editEngine,resizer,flag), BorderLayout.WEST);
		JColorChooser jcc = new JColorChooser();
		jcc.setPreviewPanel(new JPanel());
		jcc.setPreferredSize(new Dimension(50, 150));
		AbstractColorChooserPanel[] ccPanels = jcc
				.getChooserPanels();
		for (AbstractColorChooserPanel ccPanel : ccPanels) {
			String name = ccPanel.getClass()
					.getSimpleName();
			if (!name.equals("DefaultSwatchChooserPanel"))
				jcc.removeChooserPanel(ccPanel);
		}
		jcc.getSelectionModel()
				.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(
							ChangeEvent e) {
						color = jcc.getColor();
						if (drawArea.enablePaint) {
							drawArea.setFillColor(color);
						} else {
							drawArea.setDrawColor(color);
						}
					}
				});
		add(jcc, BorderLayout.AFTER_LAST_LINE);

		setSize(700, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
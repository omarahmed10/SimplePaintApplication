package paint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

public class Toolbar extends JToolBar {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Choose which Shape to be drawn.
	 */
	private JButton shapeButton;
	/**
	 * erase part of the Shape and replace it with the background color.
	 */
	private JButton moveButton;
	/**
	 * draw a free form line
	 */
	private JButton penButton;
	/**
	 *
	 */
	private JButton undoButton;
	/**
	 *
	 */
	private JButton redoButton;
	private JButton circleButton, ellipseButton, lineButton,
			rectangleButton, squareButton, triangleButton,
			fillButton, selectButton, deleteButton;

	private boolean editState;

	public Toolbar(DrawArea drawArea, EditEngine editEngine,
			Resizer resizer, boolean flag) {
		penButton = createButtons("/images/pen.png", true,
				"pen");
		penButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawArea.determineShape(null);
				drawArea.enablePainting(false);
				drawArea.enableMoving(false);
				resizer.enableResizer(false);
				drawArea.enableDeleting(false);
			}
		});
		rectangleButton = createButtons(
				"/images/rectangle.png", true, "rectangle");
		rectangleButton
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(
							ActionEvent e) {
						drawArea.determineShape(
								new Rectangle());
						drawArea.enableMoving(false);
						drawArea.enablePainting(false);
						resizer.enableResizer(false);
						drawArea.enableDeleting(false);
					}
				});
		squareButton = createButtons("/images/square.png",
				true, "square");
		squareButton
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(
							ActionEvent e) {
						try {
							drawArea.determineShape(
									(Shape) Class
											.forName(
													"paint.Square")
											.newInstance());
							drawArea.enableMoving(false);
							drawArea.enablePainting(false);
							resizer.enableResizer(false);
							drawArea.enableDeleting(false);
							if (flag)
								squareButton
										.setVisible(true);
							else
								squareButton
										.setVisible(false);
						} catch (Exception exception) {
							exception.printStackTrace();
						}
					}
				});
		ellipseButton = createButtons("/images/ellipse.png",
				true, "ellipse");
		ellipseButton
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(
							ActionEvent e) {
						drawArea.enableMoving(false);
						drawArea.enablePainting(false);
						resizer.enableResizer(false);
						drawArea.determineShape(
								new Ellipse());
						drawArea.enableDeleting(false);
					}
				});
		circleButton = createButtons("/images/circle.png",
				true, "circle");
		circleButton
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(
							ActionEvent e) {
						drawArea.determineShape(
								new Circle());
						drawArea.enableMoving(false);
						drawArea.enablePainting(false);
						resizer.enableResizer(false);
						drawArea.enableDeleting(false);
					}
				});
		lineButton = createButtons("/images/line.png", true,
				"line");
		lineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawArea.enableMoving(false);
				drawArea.determineShape(new Line());
				drawArea.enablePainting(false);
				resizer.enableResizer(false);
				drawArea.enableDeleting(false);
			}
		});
		triangleButton = createButtons(
				"/images/triangle.png", true, "triangle");
		triangleButton
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(
							ActionEvent e) {
						drawArea.enableMoving(false);
						drawArea.determineShape(
								new Triangle());
						drawArea.enablePainting(false);
						resizer.enableResizer(false);
						drawArea.enableDeleting(false);
					}
				});
		undoButton = createButtons("/images/undo.png", true,
				"undo");
		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawArea.undoAction();
			}
		});
		redoButton = createButtons("/images/redo.png", true,
				"redo");
		redoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawArea.redoAction();
			}
		});
		fillButton = createButtons("/images/fill.png", true,
				"fill");
		fillButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawArea.determineShape(null);
				drawArea.enablePainting(true);
				drawArea.enableMoving(false);
				resizer.enableResizer(false);
				drawArea.enableDeleting(false);
			}
		});
		selectButton = createButtons("/images/select.png",
				true, "resize");
		selectButton
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(
							ActionEvent e) {
						drawArea.determineShape(null);
						drawArea.enablePainting(false);
						drawArea.enableMoving(false);
						resizer.enableResizer(true);
						drawArea.enableDeleting(false);
					}
				});
		moveButton = createButtons("/images/move.png", true,
				"move");
		moveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawArea.enablePainting(false);
				drawArea.enableMoving(true);
				resizer.enableResizer(false);
				drawArea.enableDeleting(false);
			}
		});
		deleteButton = createButtons("/images/del.png",
				true, "delete");
		deleteButton
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(
							ActionEvent e) {
						drawArea.determineShape(null);
						drawArea.enablePainting(false);
						drawArea.enableMoving(false);
						resizer.enableResizer(false);
						drawArea.enableDeleting(true);
					}
				});
		setBackground(Color.BLACK);
		setOrientation(SwingConstants.VERTICAL);
	}

	private JButton createButtons(String imagePath,
			boolean state, String text) {
		JButton b = new JButton();
		b.setIcon(createIcon(imagePath));
		b.setVisible(state);
		b.setToolTipText(text);
		add(b);
		add(Box.createRigidArea(new Dimension(0, 6)));
		return b;
	}

	private ImageIcon createIcon(String Path) {
		URL url = getClass().getResource(Path);
		if (url == null) {
			throw new RuntimeException();
		}
		ImageIcon icon = new ImageIcon(url);

		return icon;
	}

}

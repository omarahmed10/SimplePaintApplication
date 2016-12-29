package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * this class create an area to draw in it.
 *
 * @author omar
 *
 */

public class DrawArea extends JPanel
		implements MouseListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the coordinates of mouse.
	 */
	private int currentX, currentY, oldX, oldY, Height,
			width, actualHeight, actualWidht, actualX,
			actualY, freeHandLineX, freeHandLineY;
	/**
	 * indicating whether user draw a new shape or he's just dragging the mouse.
	 * while user is dragging the mouse the flag is true and the shape'll be
	 * drawn.
	 */
	private boolean newShape, freeHand;
	/**
	 * points of freeHandLine.
	 */
	private ArrayList<Point> points = new ArrayList<Point>();
	/**
	 * this list save all drawn shape with their starting coordinates.
	 */
	private LinkedList<Shape> drawnShapeList = new LinkedList<Shape>();
	/**
	 * list save all drawn freeHandLine.
	 */
	private LinkedList<FreeHandLine> freeHandLineList = new LinkedList<FreeHandLine>();
	/**
	 * 
	 */
	private FreeHandLine requiredFreeHandLine = new FreeHandLine();
	/**
	 * 
	 */
	private Shape requiredDrawnShape, selectedShape,
			requiredToMove, requiredToResized,requiredToDelete;
	/**
	 * to save the undo move of shapes.
	 */
	private Stack<Object> undoStack = new Stack<Object>();
	/**
	 * to save the redo move of shapes.
	 */
	private Stack<Object> redoStack = new Stack<Object>();
	/**
	 * to save the undo move of freeHandLine.
	 */
	private Stack<FreeHandLine> freeUndoStack = new Stack<FreeHandLine>();
	/**
	 * to save the redo move of freeHandLine.
	 */
	private Stack<FreeHandLine> freeRedoStack = new Stack<FreeHandLine>();
	private Color prevBackground;
	/**
	 *
	 */
	private Color drawColor, fillColor;
	private EditEngine editEngine;
	private Resizer resizer;
	private int mouseClickX, mouseClickY;
	public boolean enablePaint, enableMove, startMoving,
			enableDelete;
	private Graphics2D g2;

	/**
	 * 
	 */
	public DrawArea() {
		addMouseListener(this);
		addMouseMotionListener(this);
		requiredDrawnShape = null;
		freeHand = true;
		prevBackground = this.getBackground();
	}

	/**
	 * setting a editEngine to perform editing on shapes.
	 * 
	 * @param engine
	 */
	public void setEditEngine(EditEngine engine) {
		this.editEngine = engine;
	}

	public void setResizer(Resizer r) {
		this.resizer = r;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		float thickness = 2;
		g2.setStroke(new BasicStroke(thickness));
		editEngine.drawEditShape(g2);
		if (resizer.isEnabled() && selectedShape != null) {
			Graphics2D g2d = (Graphics2D) g.create();
			Stroke dashed = new BasicStroke(3,
					BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_BEVEL, 0,
					new float[] { 9 }, 0);
			g2d.setStroke(dashed);
			g2d.setColor(Color.GREEN);
			g2d.draw(resizer.selectionRec);
			g2d.dispose();
			if (selectedShape.isFilled()) {
				requiredToResized.fill(g2);
			} else {
				requiredToResized.draw(g2);
			}
		} else if (startMoving
				&& !selectedShape.isFilled()) {
			requiredToMove
					.move(new Point(currentX, currentY));
			requiredToMove.draw(g2);
		} else if (startMoving
				&& selectedShape.isFilled()) {
			requiredToMove
					.move(new Point(currentX, currentY));
			requiredToMove.fill(g2);
		} else if (requiredDrawnShape != null && newShape) {
			getShapeType(requiredDrawnShape);
			requiredDrawnShape.setCol(getDrawColor());
			requiredDrawnShape.draw(g2);
		} else if (freeHand) {
			requiredFreeHandLine.setPoints(points);
			requiredFreeHandLine.setCol(getDrawColor());
			requiredFreeHandLine.draw(g2);
		}
		for (Shape shape : drawnShapeList) {
			if (shape.isDrawable()) {
				shape.draw(g2);
			}
		}
		for (FreeHandLine line : freeHandLineList) {
			line.draw(g2);
		}
	}

	/**
	 * @return the drawColor.
	 */
	public Color getDrawColor() {
		return drawColor;
	}

	/**
	 * @param C
	 *            the drawColor to set.
	 */
	public void setDrawColor(Color C) {
		this.drawColor = C;
	}

	/**
	 * @return the FillColor.
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * @param C
	 *            the fillColor to set.
	 */
	public void setFillColor(Color C) {
		this.fillColor = C;
	}

	/**
	 * it receives the type of shape from buttons of the side pane.
	 * 
	 * @param s
	 */
	public void determineShape(Shape s) {
		this.requiredDrawnShape = s;
		if (s == null && !resizer.isEnabled()) {
			freeHand = true;
		} else {
			freeHand = false;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		currentX = oldX = e.getX();
		currentY = oldY = e.getY();
		this.oldX = e.getX();
		this.oldY = e.getY();
		if (enableMove) {
			selectedShape = editEngine.foundInEditHistory(
					e.getPoint(), editEngine.getEditList());
			if (selectedShape != null) {
				requiredToMove = createShape(selectedShape);
			} else {
				selectedShape = editEngine
						.foundOriginalShape(e.getPoint(),
								drawnShapeList);
				if (selectedShape != null) {
					requiredToMove = createShape(
							selectedShape);
					System.out.println(requiredToMove.getArea());
				}
			}
		} else if (resizer.isEnabled()) {
			selectedShape = editEngine.foundInEditHistory(
					e.getPoint(), editEngine.getEditList());
			if (selectedShape != null) {
				requiredToResized = createShape(
						selectedShape);
			} else {
				selectedShape = editEngine
						.foundOriginalShape(e.getPoint(),
								drawnShapeList);
				if (selectedShape != null) {
					requiredToResized = createShape(
							selectedShape);
				}
			}
			resizer.resizeShape(requiredToResized);
		} else {
			startMoving = false;
		}
		repaint();

	}

	public Shape createShape(Shape s) {
		Shape s1 = null;
		try {
			s1 = (Shape) Class
					.forName(s.getClass().getName())
					.newInstance();
			s1.moved(enableMove || s.isMoved());
			s1.edit(true);
			s1.resized(
					resizer.isEnabled() || s.isResized());
			s1.fill(s.isFilled() || enablePaint);
			editEngine.getCopy(s1, s);
			startMoving = enableMove;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s1;
	}

	public LinkedList<Shape> getShapes() {
		return drawnShapeList;
	}

	public void addInUndoStack(Object item) {
		undoStack.push(item);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// you shouldn't save the shape until it done
		// you also shouldn't draw a shape in case of freeHand state
		if (enableMove && selectedShape != null) {
			editEngine.moveShape(new Point(oldX, oldY),
					e.getPoint(), selectedShape,
					requiredToMove);
			startMoving = false;
			repaint();
		} else if (resizer.isEnabled()
				&& selectedShape != null) {
			editEngine.resizeShape(selectedShape,
					requiredToResized);
		} else if (requiredDrawnShape != null && newShape) {
			getShapeType(requiredDrawnShape);
			this.drawnShapeList.add(requiredDrawnShape);
			this.undoStack.add(requiredDrawnShape);
			clearRedoStack();
			newShape = false;
			// create a new Shape of the same type of the previous one
			// to bew drawn in the next time.
			try {
				requiredDrawnShape = (Shape) Class
						.forName(requiredDrawnShape
								.getClass().getName())
						.newInstance();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (freeHand && newShape) {
			newShape = false;
			this.freeHandLineList.add(requiredFreeHandLine);
			this.undoStack.add(requiredFreeHandLine);
			clearRedoStack();
			requiredFreeHandLine = new FreeHandLine();
			points = new ArrayList<Point>();
		}
		oldX = oldY = currentX = currentY = 0;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.currentX = e.getX();
		this.currentY = e.getY();
		if (enableMove) {
			// do nothing.
		} else if (requiredDrawnShape != null) {
			newShape = true;
		} else if (freeHand && !resizer.isEnabled()) {
			points.add(e.getPoint());
			newShape = true;
		}
		repaint();

	}

	private void getShapeType(Shape shape) {
		int width = this.oldX - this.currentX;
		int height = this.oldY - this.currentY;
		this.actualWidht = Math.abs(width);
		this.actualHeight = Math.abs(height);
		if (shape.getClass().getName().equals("Square")) {
			getSquare(width, height);
		} else if (shape instanceof Circle) {
			getCircle();
		} else if (shape instanceof Rectangle) {
			getRectangle(width, height, shape);
		} else if (shape instanceof Line) {
			getLine(shape);
		} else if (shape instanceof Ellipse) {
			getEllipse(shape);
		} else if (shape instanceof Triangle) {
			getTriangle(width, height);
		}

	}

	private void getRectangle(int width, int height,
			Shape shape) {
		this.actualX = width < 0 ? this.oldX
				: this.currentX;
		this.actualY = height < 0 ? this.oldY
				: this.currentY;
		shape.setBound(new Rectangle(actualX, actualY,
				actualWidht, actualHeight));
	}

	/**
	 * in case of line, simply we send the old and new positions of mouse.
	 */
	private void getLine(Shape shape) {
		shape.setBound(new Rectangle(oldX, oldY, currentX,
				currentY));
	}

	private void getEllipse(Shape shape) {
		this.actualX = this.oldX - this.actualWidht;
		this.actualY = this.oldY - this.actualHeight;
		shape.setBound(new Rectangle(actualX, actualY,
				actualWidht * 2, actualHeight * 2));
	}

	private void getSquare(int width, int height) {
		if (actualHeight >= actualWidht) {
			this.actualX = width < 0 ? this.oldX
					: this.oldX - actualHeight;
			this.actualY = height < 0 ? this.oldY
					: this.oldY - actualHeight;
			requiredDrawnShape.setBound(
					new Rectangle(actualX, actualY,
							actualHeight, actualHeight));
		} else {
			this.actualX = width < 0 ? this.oldX
					: this.oldX - actualWidht;
			this.actualY = height < 0 ? this.oldY
					: this.oldY - actualWidht;
			requiredDrawnShape.setBound(
					new Rectangle(actualX, actualY,
							actualWidht, actualWidht));
		}

	}

	private void getCircle() {
		if (this.actualHeight >= this.actualWidht) {
			this.actualX = this.oldX - this.actualHeight;
			this.actualY = this.oldY - this.actualHeight;
			requiredDrawnShape.setBound(
					new Rectangle(actualX, actualY,
							actualHeight, actualHeight));
		} else if (this.actualHeight < this.actualWidht) {
			this.actualX = this.oldX - this.actualWidht;
			this.actualY = this.oldY - this.actualWidht;
			requiredDrawnShape.setBound(
					new Rectangle(actualX, actualY,
							actualWidht, actualWidht));
		}
	}

	private void getTriangle(int w, int h) {
		requiredDrawnShape
				.setBound(new Rectangle(oldX, oldY, w, h));
	}

	public void undoAction() {
		if (undoStack.isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Can't Undo", "Paint",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		} else {
			Object s = this.undoStack.pop();
			this.redoStack.push(s);
			if (s instanceof FreeHandLine) {
				this.freeHandLineList.removeLast();
			} else if (s instanceof Color) { // undo background color.
				this.setBackground(prevBackground);
			} else if (((Shape) s).isEdited()) {
				LinkedList<Shape> shapeHistory = editEngine
						.getEditHistory((Shape) s);
				shapeHistory.removeLast();
				if (shapeHistory.size() == 0) {
					// it isn't edited anymore.
					// so you can draw it normally.
					drawnShapeList
							.get(((Shape) s)
									.getEditedShapeIndex())
							.drawable(true);
				} else {
					shapeHistory.getLast().drawable(true);
				}
			} else {
				this.drawnShapeList.removeLast();
			}

		}
		repaint();
	}

	public void redoAction() {
		if (redoStack.isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Can't Redo", "Paint",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		} else {
			Object s = this.redoStack.pop();
			this.undoStack.push(s);
			if (s instanceof FreeHandLine) {
				FreeHandLine fHL = (FreeHandLine) s;
				this.freeHandLineList.add(fHL);
			} else if (s instanceof Color) {
				setBackground((Color) s);
			} else if (((Shape) s).isEdited()) {
				if (editEngine
						.addEditAction((Shape) s) == 1) {
					this.drawnShapeList
							.get(((Shape) s)
									.getEditedShapeIndex())
							.drawable(false);
				}
			} else {
				this.drawnShapeList.add((Shape) s);
			}

		}
		repaint();
	}

	/**
	 * clear the stack when an action performed.
	 */
	public void clearRedoStack() {
		this.redoStack.clear();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(
				Cursor.CROSSHAIR_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(
				Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseClickX = e.getX();
		mouseClickY = e.getY();
		if (enablePaint) {
			editEngine.fillShape(getMouseClickPos());
		}else if(enableDelete){
			selectedShape = editEngine.foundInEditHistory(
					e.getPoint(), editEngine.getEditList());
			if (selectedShape != null) {
				requiredToDelete = createShape(
						selectedShape);
			} else {
				selectedShape = editEngine
						.foundOriginalShape(e.getPoint(),
								drawnShapeList);
				if (selectedShape != null) {
					requiredToDelete = createShape(
							selectedShape);
				}
			}
			editEngine.deleteShape(selectedShape, requiredToDelete);
		}
	}

	/**
	 * get the mouse (x,y) when clicked.
	 * 
	 * @return
	 */
	public Point getMouseClickPos() {
		return new Point(mouseClickX, mouseClickY);
	}

	public void enablePainting(boolean state) {
		enablePaint = state;
	}

	public void enableMoving(boolean state) {
		enableMove = state;
	}

	public void enableDeleting(boolean state) {
		enableDelete = state;
	}

	public void saveToFile(File file) throws Exception {
        if (file.getName().endsWith(".xml")) {
            new XMLWriter(drawnShapeList,
                    freeHandLineList, editEngine, file);

        } else if (file.getName().endsWith(".json")) {
            new JsonWriter(drawnShapeList,
                    freeHandLineList, file);
        }
    }

    public void loadFromFile(File file) {
        drawnShapeList.clear();
        freeHandLineList.clear();
        undoStack.clear();
        if (file.getName().endsWith(".xml")) {
            try {
                new XMLReader(drawnShapeList, freeHandLineList,
                        file, editEngine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (file.getName().endsWith(".json")) {
            new JsonReader(drawnShapeList, freeHandLineList,
                    file);
        }
        repaint();
    }
}

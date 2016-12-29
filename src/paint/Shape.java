package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Ali-Metawea
 *
 */
public abstract class Shape implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Coordinates of the center of the shape.
	 */
	private Color col;

	/**
	 * Coordinates of the center of the shape.
	 */
	private Point startPoint = new Point();
	private Color drawColor, fillColor;
	private boolean edit, filled, drawable = true, moved,
			resized;
	private int editedShapeIndex;


	/**
	 * setting the shape bounds.
	 *
	 * @param bound
	 *            this parameter carry the coordinates and the size of the
	 *            shape.
	 */

	public abstract void setBound(Rectangle bound);

	public abstract boolean contains(double x, double y);

	/**
	 * @return the circle Center
	 */
	public final Point getStartPoint() {
		return startPoint;
	}

	public abstract int getWidth();
	public abstract int getHeight();

	/**
	 * @param d
	 *            Sets the Center x Coordinate
	 * @param e
	 *            Sets the Center y Coordinate
	 */
	public final void setStartPoint(final Point p) {
		this.startPoint = p;
	}

	/**
	 *
	 * @param newCenter
	 *            Coordinate of the new center
	 */
	public void move(final Point newStartPoint) {
		startPoint = newStartPoint;
	}

	/**
	 * draw the shape on the canvas.
	 *
	 * @param g
	 */
	public abstract void draw(Graphics2D g);

	public abstract double getArea();

	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public abstract boolean onBounds(double x, double y);

	/**
	 * @return the drawColor
	 */
	public Color getCol() {
		return drawColor;
	}

	/**
	 * @param drawColor
	 *            the col to set
	 */
	public void setCol(Color c) {
		this.drawColor = c;
	}

	/**
	 * @return the col
	 */
	public Color getFillCol() {
		return fillColor;
	}

	/**
	 * @param col
	 *            the col to set
	 */
	public void setFillCol(Color c) {
		this.fillColor = c;
	}

	public void edit(boolean state) {
		edit = state;
	}

	public boolean isEdited() {
		return edit;
	}

	/**
	 * draw and fill the shape on the canvas.
	 *
	 * @param g
	 */
	public void fill(Graphics g) {
		filled = true;
	}

	/**
	 * change the state of the shape to "edited".
	 * 
	 * @param state
	 */
	public void fill(boolean state) {
		filled = state;
	}

	/**
	 * if the shape has been edited.
	 * 
	 * @return
	 */
	public boolean isFilled() {
		return filled;
	}

	/**
	 * 
	 * @param state
	 * @param index
	 *            index of the same element in edited shape list.
	 */
	public void drawable(boolean state) {
		drawable = state;
	}

	public boolean isDrawable() {
		return drawable;
	}

	public void moved(boolean state) {
		moved = state;
	}

	public boolean isMoved() {
		return moved;
	}

	public void resized(boolean state) {
		resized = state;
	}

	public boolean isResized() {
		return resized;
	}

	/**
	 * @return index of the same element in edited shape list or the opposite.
	 */
	public int getEditedShapeIndex() {
		return editedShapeIndex;
	}

	/**
	 * @param i
	 *            index of editedShape or index of the drawn Shape.
	 */
	public void setEditedShapeIndex(int i) {
		editedShapeIndex = i;
	}

}

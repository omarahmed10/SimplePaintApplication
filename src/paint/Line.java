package paint;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Ali-Metawea
 *
 */
public class Line extends Shape {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The X coordinate of the end point of the line segment. The Y coordinate
	 * of the end point of the line segment. The X coordinate of the start point
	 * of the line segment. The Y coordinate of the start point of the line
	 * segment.
	 */
	private int startX, startY, endX, endY;

	/**
	 * Creates a new instance of Line.
	 *
	 * @param newStartX
	 *            the value of the property StartX
	 * @param newStartY
	 *            the value of the property StartY
	 * @param newEndX
	 *            the value of the property EndX
	 * @param newEndY
	 *            the value of the property EndY
	 */
	public Line(final int newStartX, final int newStartY,
			final int newEndX, final int newEndY) {
		this.setStartPoint(new Point(newStartX, newStartY));
		endX = newEndX;
		endY = newEndY;
	}

	public Line() {
		this.setStartPoint(new Point(0, 0));
		endX = 0;
		endY = 0;
	}

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}

	@Override
	public boolean onBounds(double x, double y) {
		double slope = (endY - getStartPoint().y)
				/ (endX - getStartPoint().x);
		if ((y - getStartPoint().y) == (slope
				* (x - getStartPoint().x))) {
			return true;
		}
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(getCol());
		g.drawLine(getStartPoint().x, getStartPoint().y,
				endX, endY);
	}
	@Override
	public void setBound(Rectangle bound) {
		// TODO Auto-generated method stub
		this.setStartPoint(
				new Point(bound.getStartPoint().x,
						bound.getStartPoint().y));
		endX = bound.getWidth();
		endY = bound.getHeight();
	}

	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean contains(double x, double y) {
		return onBounds(x, y);
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}

package paint;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Ali-Metawea
 *
 */
public class Ellipse extends Shape {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Defines the horizontal position of x-Coordinate of the ellipse in pixels.
	 * Defines the vertical position of y-Coordinate of the ellipse in pixels.
	 * Defines the width of the ellipse in pixels. Defines the height of the
	 * ellipse in pixels.
	 */
	private int startX = 0, startY = 0, height = 0,
			width = 0;

	/**
	 * Creates a new instance of Line.
	 *
	 * @param x
	 *            the value of the property StartX
	 * @param y
	 *            the value of the property StartY
	 * @param h
	 *            the value of the property height
	 * @param w
	 *            the value of the property width
	 */
	public Ellipse(final int x, final int y, final int w,
			final int h) {
		this.setStartPoint(new Point(x, y));
		this.height = h;
		this.width = w;
	}

	/**
	 * Creates a new instance of Ellipse.
	 */
	public Ellipse() {
		this.setStartPoint(new Point(0, 0));
		height = 0;
		width = 0;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public Point getCenter() {
		int centerX = (int) (getStartPoint().x
				+ 0.5 * width);
		int centerY = (int) (getStartPoint().y
				+ 0.5 * height);
		return new Point(centerX, centerY);
	}

	/**
	 * setting the shape bounds.
	 *
	 * @param bound
	 *            this parameter carry the coordinates and the size of the
	 *            shape.
	 */

	@Override
	public void setBound(Rectangle bound) {
		this.setStartPoint(
				new Point(bound.getStartPoint().x,
						bound.getStartPoint().y));
		this.height = bound.getHeight();
		this.width = bound.getWidth();
	}

	@Override
	public boolean onBounds(double x, double y) {
		double testX = Math.pow((x - this.getCenter().x), 2)
				/ Math.pow(this.width / 2, 2);
		double testY = Math.pow((y - this.getCenter().y), 2)
				/ Math.pow(this.height / 2, 2);
		if (testY + testX == 1) {
			return true;
		}
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(getCol());
		g.drawOval(getStartPoint().x, getStartPoint().y,
				this.width, this.height);
	}

	@Override
	public double getArea() {
		return Math.PI * width * height * 0.25;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getFillCol());
		g.fillOval(getStartPoint().x, getStartPoint().y,
				this.width, this.height);
	}

	@Override
	public boolean contains(double x, double y) {
		double testX = Math.pow((x - this.getCenter().x), 2)
				/ Math.pow(this.width / 2, 2);
		double testY = Math.pow((y - this.getCenter().y), 2)
				/ Math.pow(this.height / 2, 2);
		if (testY + testX <= 1) {
			return true;
		}
		return false;
	}

}

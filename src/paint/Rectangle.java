package paint;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Ali-Metawea
 *
 */
public class Rectangle extends Shape {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * the Rectangle Dimensions.
     */
	private int width, height, startX, startY, endX, endY;

	/**
	 * Creates a new instance of Rectangle with a specified Width & Height.
	 *
	 * @param w
	 *            Width of The Rectangle
	 * @param h
	 *            Height of The Rectangle
	 * @param x
	 *            Start X Coordinate
	 * @param y
	 *            Start Y Coordinate
	 * @param fX
	 *            End X Coordinate
	 * @param fY
	 *            End Y Coordinate
	 */
	public Rectangle(final int x, final int y, final int w,
			final int h) {
		this.setStartPoint(new Point(x, y));
		this.width = w;
		this.height = h;
		this.endX = x + w;
		this.endY = y + h;
	}

	@Override
	public final boolean onBounds(final double x,
			final double y) {
		if (x == this.startX && y >= this.startY
				&& y <= this.endY) {
			return true;
		} else if (x == this.endX && y >= this.startY
				&& y <= this.endY) {
			return true;
		} else if (y == this.startY && x >= this.startX
				&& x <= this.endX) {
			return true;
		} else if (y == this.endY && x >= this.startX
				&& x <= this.endX) {
			return true;
		}

		return false;
	}

	/**
	 * Creates a new instance of Rectangle.
	 */

	public Rectangle() {
		this.setStartPoint(new Point(0, 0));
		this.width = 0;
		this.height = 0;
		this.endX = 0;
		this.endY = 0;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
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
		this.endX = this.getStartPoint().x + width;
		this.endY = this.getStartPoint().y + height;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(getCol());
		g.drawRect(getStartPoint().x, getStartPoint().y,
				width, height);
	}

	@Override
	public double getArea() {
		return width * height;
	}

	private Point getRecPointA() {
		return new Point(getStartPoint().x,
				getStartPoint().y);
	}

	private Point getRecPointB() {
		return new Point(getStartPoint().x + width,
				getStartPoint().y);
	}

	private Point getRecPointC() {
		return new Point(getStartPoint().x,
				getStartPoint().y + height);
	}

	private Point getRecPointD() {
		return new Point(getStartPoint().x + width,
				getStartPoint().y + height);
	}

	@Override
	public boolean contains(double x, double y) {
		if (width == 0 || height == 0) {
			return false;
		}
		Point p = new Point((int) x, (int) y);
		Triangle tri1 = new Triangle(getRecPointA(),
				getRecPointB(), p);
		Triangle tri2 = new Triangle(getRecPointA(),
				getRecPointC(), p);
		Triangle tri3 = new Triangle(getRecPointC(),
				getRecPointD(), p);
		Triangle tri4 = new Triangle(getRecPointD(),
				getRecPointB(), p);
		return tri1.getArea() + tri2.getArea()
				+ tri3.getArea()
				+ tri4.getArea() == this.getArea();
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(getFillCol());
		g.fillRect(getStartPoint().x, getStartPoint().y,
				width, height);
	}

}

package paint;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Ali-Metawea
 *
 */
public class Circle extends Ellipse {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int startX, startY, rad;

	public int getRad() {
		return rad;
	}

	/**
	 *
	 * @param x
	 *            Start X Coordinate.
	 * @param y
	 *            Start Y Coordinate
	 * @param radius
	 *            The Radius of the Circle.
	 */
	public Circle(final int x, final int y,
			final int radius) {
		super(x, y, radius * 2, radius * 2);
		this.setStartPoint(new Point(x, y));
		this.rad = radius;
	}

	public Circle() {
		this.setStartPoint(new Point(0, 0));
		this.rad = 0;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(getCol());
		g.drawOval(this.getStartPoint().x,
				this.getStartPoint().y, this.rad * 2,
				this.rad * 2);
	}

	@Override
	public void setBound(Rectangle bound) {
		super.setBound(bound);
		this.rad = bound.getWidth();
		this.setStartPoint(bound.getStartPoint());
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getFillCol());
		g.fillOval(this.getStartPoint().x,
				this.getStartPoint().y, this.rad * 2,
				this.rad * 2);
	}

}

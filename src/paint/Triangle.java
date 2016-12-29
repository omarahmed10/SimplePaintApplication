package paint;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Ali-Metawea
 *
 */
public class Triangle extends Shape {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Defines the three Points of the Triangle.
	 */
	private Point pointX, pointY, pointZ;
	private int actualHeight, actualWidth;

	/**
	 * Creates a new instance of Triangle.
	 *
	 * @param x
	 *            the value of the property pointX
	 *
	 * @param y
	 *            the value of the property pointY
	 * @param z
	 *            the value of the property pointZ
	 */
	public Triangle(Point x, Point y, Point z) {
		pointX = x;
		pointY = y;
		pointZ = z;
	}

	public Triangle() {
		pointX = new Point(0, 0);
		pointY = new Point(0, 0);
		pointZ = new Point(0, 0);
	}

	@Override
	public void setBound(Rectangle bound) {
		actualHeight = Math.abs(bound.getHeight());
		actualWidth = Math.abs(bound.getWidth());
		if (actualHeight >= actualWidth) {
			getTriangleHori(bound, actualHeight);
		} else {
			getTriangleVer(bound, actualWidth);
		}
	}

	@Override
	public int getWidth() {
		return this.actualWidth;
	}

	@Override
	public int getHeight() {
		return this.actualHeight;
	}

	private void getTriangleHori(Rectangle rec, int x) {
		if (rec.getHeight() < 0 && rec.getWidth() < 0) {
			pointX.x = rec.getStartPoint().x + x;
			pointX.y = rec.getStartPoint().y;
			pointY.x = rec.getStartPoint().x - x;
			pointY.y = rec.getStartPoint().y;
			pointZ.x = rec.getStartPoint().x;
			pointZ.y = rec.getStartPoint().y - x;

		} else if (rec.getHeight() < 0
				&& rec.getWidth() > 0) {
			pointX.x = rec.getStartPoint().x + x;
			pointX.y = rec.getStartPoint().y;
			pointY.x = rec.getStartPoint().x - x;
			pointY.y = rec.getStartPoint().y;
			pointZ.x = rec.getStartPoint().x;
			pointZ.y = rec.getStartPoint().y - x;

		} else if (rec.getHeight() > 0
				&& rec.getWidth() < 0) {
			pointX.x = rec.getStartPoint().x + x;
			pointX.y = rec.getStartPoint().y;
			pointY.x = rec.getStartPoint().x - x;
			pointY.y = rec.getStartPoint().y;
			pointZ.x = rec.getStartPoint().x;
			pointZ.y = rec.getStartPoint().y + x;

		} else if (rec.getHeight() > 0
				&& rec.getWidth() > 0) {
			pointX.x = rec.getStartPoint().x + x;
			pointX.y = rec.getStartPoint().y;
			pointY.x = rec.getStartPoint().x - x;
			pointY.y = rec.getStartPoint().y;
			pointZ.x = rec.getStartPoint().x;
			pointZ.y = rec.getStartPoint().y + x;

		}
	}

	private void getTriangleVer(Rectangle rec, int x) {
		if (rec.getWidth() < 0 && rec.getHeight() < 0) {
			pointX.x = rec.getStartPoint().x;
			pointX.y = rec.getStartPoint().y - x;
			pointY.x = rec.getStartPoint().x;
			pointY.y = rec.getStartPoint().y + x;
			pointZ.x = rec.getStartPoint().x - x;
			pointZ.y = rec.getStartPoint().y;

		} else if (rec.getWidth() < 0
				&& rec.getHeight() > 0) {
			pointX.x = rec.getStartPoint().x;
			pointX.y = rec.getStartPoint().y - x;
			pointY.x = rec.getStartPoint().x;
			pointY.y = rec.getStartPoint().y + x;
			pointZ.x = rec.getStartPoint().x - x;
			pointZ.y = rec.getStartPoint().y;

		} else if (rec.getWidth() > 0
				&& rec.getHeight() < 0) {
			pointX.x = rec.getStartPoint().x;
			pointX.y = rec.getStartPoint().y - x;
			pointY.x = rec.getStartPoint().x;
			pointY.y = rec.getStartPoint().y + x;
			pointZ.x = rec.getStartPoint().x + x;
			pointZ.y = rec.getStartPoint().y;

		} else if (rec.getWidth() > 0
				&& rec.getHeight() > 0) {
			pointX.x = rec.getStartPoint().x;
			pointX.y = rec.getStartPoint().y - x;
			pointY.x = rec.getStartPoint().x;
			pointY.y = rec.getStartPoint().y + x;
			pointZ.x = rec.getStartPoint().x + x;
			pointZ.y = rec.getStartPoint().y;

		}
	}

	public Point getPointX() {
		return pointX;
	}

	public Point getPointY() {
		return pointY;
	}

	public Point getPointZ() {
		return pointZ;
	}

	public void setPointX(Point x) {
		pointX = x;
	}

	public void setPointY(Point y) {
		pointY = y;
	}

	public void setPointZ(Point z) {
		pointZ = z;
	}

	@Override
	public boolean contains(double x, double y) {
		double alpha = ((getPointY().y - getPointZ().y)
				* (x - getPointZ().x)
				+ (getPointZ().x - getPointY().x)
						* (y - getPointZ().y))
				/ ((getPointY().y - getPointZ().y)
						* (getPointX().x - getPointZ().x)
						+ (getPointZ().x - getPointY().x)
								* (getPointX().y
										- getPointZ().y));
		double beta = ((getPointZ().y - getPointX().y)
				* (x - getPointZ().x)
				+ (getPointX().x - getPointZ().x)
						* (y - getPointZ().y))
				/ ((getPointY().y - getPointZ().y)
						* (getPointX().x - getPointZ().x)
						+ (getPointZ().x - getPointY().x)
								* (getPointX().y
										- getPointZ().y));
		double gamma = 1.0f - alpha - beta;
		System.out.println(
				alpha > 0 && beta > 0 && gamma > 0);
		return alpha > 0 && beta > 0 && gamma > 0;
	}

	public double getArea() {
		try {
			return .5
					* Math.abs(pointX.getX()
							* (pointY.getY()
									- pointZ.getY())
							+ pointY.getX() * (pointZ.getY()
									- pointX.getY())
							+ pointZ.getX() * (pointX.getY()
									- pointY.getY()));
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}

	@Override
	public boolean onBounds(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		int[] xPoints = { (int) pointX.getX(),
				(int) pointY.getX(), (int) pointZ.getX() };
		int[] yPoints = { (int) pointX.getY(),
				(int) pointY.getY(), (int) pointZ.getY() };
		System.out.println(xPoints[0]+" "+yPoints[0]);
		g.setColor(getCol());
		g.drawPolygon(xPoints, yPoints, 3);
	}

	@Override
	public void fill(Graphics g) {
		int[] xPoints = { (int) pointX.getX(),
				(int) pointY.getX(), (int) pointZ.getX() };
		int[] yPoints = { (int) pointX.getY(),
				(int) pointY.getY(), (int) pointZ.getY() };
		g.setColor(this.getFillCol());
		g.fillPolygon(xPoints, yPoints, 3);
	}

	@Override
	public void move(final Point newStartPoint) {
		this.setBound(new Rectangle(newStartPoint.x,
				newStartPoint.y, this.getWidth(),
				this.getHeight()));
	}
}

package paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * draw the freeHandLine
 *
 * @author omar
 *
 */
public class FreeHandLine implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * point which create the freeHandLine.
	 */
	private ArrayList<Point> points;
	private Color col;

	/**
	 * initialize an empty object.
	 */
	public FreeHandLine() {

	}

	/**
	 * takes point from mouse and save it.
	 *
	 * @param points
	 */
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	/**
	 *
	 * @return array of drawn Points for Saving them.
	 */
	public Point[] getPointArray() {
		Point[] pts = new Point[points.size()];
		pts = points.toArray(pts);
		return pts;
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	/**
	 * draw lines between points of freeHandLine.
	 *
	 * @param g
	 */
	public void draw(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.BOLD, 40));
		for (int i = 0; i < points.size() - 2; i++) {
			Point p1 = points.get(i);
			Point p2 = points.get(i + 1);
			g.setColor(getCol());
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}

	/**
	 * @return the col
	 */
	public Color getCol() {
		return col;
	}

	/**
	 * @param col
	 *            the col to set
	 */
	public void setCol(Color C) {
		this.col = C;
	}
}
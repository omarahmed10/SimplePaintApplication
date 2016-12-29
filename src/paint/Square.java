package paint;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Ali-Metawea
 *
 */
public class Square extends Rectangle {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * square coordinates.
     */
    private int side;

    public int getSide() {
        return side;
    }

    /**
     *
     * @param x
     *            Start X Coordinate.
     * @param y
     *            Start Y Coordinate
     * @param w
     *            The Length Of the Square.
     */
    public Square(final int x, final int y, final int w) {
    	super(x,y,w,w);
        this.setStartPoint(new Point(x, y));
        this.side = w;
    }

    public Square() {
    	super();
    	this.setStartPoint(new Point(0,0));
        this.side = 0;
    }

    @Override
    public void setBound(Rectangle bound) {
    	super.setBound(bound);
        this.setStartPoint(
                new Point(bound.getStartPoint().x, bound.getStartPoint().y));
        this.side = bound.getHeight();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(getCol());
        g.drawRect(this.getStartPoint().x,
                this.getStartPoint().y, this.side,
                this.side);
    }

}
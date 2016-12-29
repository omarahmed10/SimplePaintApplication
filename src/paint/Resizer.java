package paint;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Resizer extends MouseAdapter {
	private DrawArea drawArea;
	private EditEngine editEngine;
	private boolean dragging, enableResizer,
			enableSelection;
	private Shape requiredToResize;
	// Give user some leeway for selections.
	private final int PROX_DIST = 3;
	public Rectangle selectionRec;

	public Resizer(DrawArea r, EditEngine e) {
		drawArea = r;
		drawArea.setResizer(this);
		editEngine = e;
	}

	public void enableResizer(boolean state) {
		enableResizer = state;
	}

	public boolean isEnabled() {
		return enableResizer;
	}

	public void resizeShape(Shape s) {
		requiredToResize = s;
		createSelectionRec();
	}

	public void enableSelection(boolean state) {
		enableSelection = true;
	}

	public boolean isSelectionEnable() {
		return enableSelection;
	}

	private void createSelectionRec() {
		if (requiredToResize instanceof Line) {
			selectionRec = getLine();
		} else if (requiredToResize instanceof paint.Rectangle) {
			selectionRec = getRec();
		} else if (requiredToResize instanceof Ellipse) {
			selectionRec = getEllipse();
		}
	}

	public void mousePressed(MouseEvent e) {
		if (selectionRec != null && enableResizer
				&& drawArea.getCursor() != Cursor
						.getDefaultCursor()) {
			// If cursor is set for resizing, allow dragging.
			dragging = true;
		} else if (enableSelection) {
			createSelectionRec();
		}
	}

	//
	public void mouseReleased(MouseEvent e) {
		dragging = false;
	}

	public void mouseDragged(MouseEvent e) {
		if (enableResizer && dragging) {
			Point p = e.getPoint();
			int type = drawArea.getCursor().getType();
			int dx = p.x - selectionRec.x;
			int dy = p.y - selectionRec.y;
			switch (type) {
			case Cursor.N_RESIZE_CURSOR:
				int height = selectionRec.height - dy;
				selectionRec.setRect(selectionRec.x,
						selectionRec.y + dy,
						selectionRec.width, height);
				requiredToResize.setBound(
						new paint.Rectangle(selectionRec.x,
								selectionRec.y,
								selectionRec.width,
								selectionRec.height));
				break;
			case Cursor.NW_RESIZE_CURSOR:
				int width = selectionRec.width - dx;
				height = selectionRec.height - dy;
				selectionRec.setRect(selectionRec.x + dx,
						selectionRec.y + dy, width, height);
				requiredToResize.setBound(
						new paint.Rectangle(selectionRec.x,
								selectionRec.y,
								selectionRec.width,
								selectionRec.height));
				break;
			case Cursor.W_RESIZE_CURSOR:
				width = selectionRec.width - dx;
				selectionRec.setRect(selectionRec.x + dx,
						selectionRec.y, width,
						selectionRec.height);
				requiredToResize.setBound(
						new paint.Rectangle(selectionRec.x,
								selectionRec.y,
								selectionRec.width,
								selectionRec.height));
				break;
			case Cursor.SW_RESIZE_CURSOR:
				width = selectionRec.width - dx;
				height = dy;
				selectionRec.setRect(selectionRec.x + dx,
						selectionRec.y, width, height);
				requiredToResize.setBound(
						new paint.Rectangle(selectionRec.x,
								selectionRec.y,
								selectionRec.width,
								selectionRec.height));
				break;
			case Cursor.S_RESIZE_CURSOR:
				height = dy;
				selectionRec.setRect(selectionRec.x,
						selectionRec.y, selectionRec.width,
						height);
				requiredToResize.setBound(
						new paint.Rectangle(selectionRec.x,
								selectionRec.y,
								selectionRec.width,
								selectionRec.height));
				break;
			case Cursor.SE_RESIZE_CURSOR:
				width = dx;
				height = dy;
				selectionRec.setRect(selectionRec.x,
						selectionRec.y, width, height);
				requiredToResize.setBound(
						new paint.Rectangle(selectionRec.x,
								selectionRec.y,
								selectionRec.width,
								selectionRec.height));
				break;
			case Cursor.E_RESIZE_CURSOR:
				width = dx;
				selectionRec.setRect(selectionRec.x,
						selectionRec.y, width,
						selectionRec.height);
				requiredToResize.setBound(
						new paint.Rectangle(selectionRec.x,
								selectionRec.y,
								selectionRec.width,
								selectionRec.height));
				break;
			case Cursor.NE_RESIZE_CURSOR:
				width = dx;
				height = selectionRec.height - dy;
				selectionRec.setRect(selectionRec.x,
						selectionRec.y + dy, width, height);
				requiredToResize.setBound(
						new paint.Rectangle(selectionRec.x,
								selectionRec.y,
								selectionRec.width,
								selectionRec.height));
				break;
			default:
				System.out.println(
						"unexpected type: " + type);
			}
			drawArea.repaint();
		}
	}

	public void mouseMoved(MouseEvent e) {
		Point p = e.getPoint();
		if (enableResizer && selectionRec != null) {
			if (!isOverRect(p)) {
				if (drawArea.getCursor() != Cursor
						.getDefaultCursor()) {
					// If cursor is not over rect reset it to the default.
					drawArea.setCursor(
							Cursor.getDefaultCursor());
				}
				return;
			} else {
				drawArea.setCursor(
						Cursor.getPredefinedCursor(
								Cursor.CROSSHAIR_CURSOR));
			}
			// Locate cursor relative to center of rect.
			int outcode = getOutcode(p);
			switch (outcode) {
			case Rectangle.OUT_TOP:
				if (Math.abs(
						p.y - selectionRec.y) < PROX_DIST) {
					drawArea.setCursor(
							Cursor.getPredefinedCursor(
									Cursor.N_RESIZE_CURSOR));
				}
				break;
			case Rectangle.OUT_TOP + Rectangle.OUT_LEFT:
				if (Math.abs(
						p.y - selectionRec.y) < PROX_DIST
						&& Math.abs(p.x
								- selectionRec.x) < PROX_DIST) {
					drawArea.setCursor(
							Cursor.getPredefinedCursor(
									Cursor.NW_RESIZE_CURSOR));
				}
				break;
			case Rectangle.OUT_LEFT:
				if (Math.abs(
						p.x - selectionRec.x) < PROX_DIST) {
					drawArea.setCursor(
							Cursor.getPredefinedCursor(
									Cursor.W_RESIZE_CURSOR));
				}
				break;
			case Rectangle.OUT_LEFT + Rectangle.OUT_BOTTOM:
				if (Math.abs(
						p.x - selectionRec.x) < PROX_DIST
						&& Math.abs(p.y - (selectionRec.y
								+ selectionRec.height)) < PROX_DIST) {
					drawArea.setCursor(
							Cursor.getPredefinedCursor(
									Cursor.SW_RESIZE_CURSOR));
				}
				break;
			case Rectangle.OUT_BOTTOM:
				if (Math.abs(p.y - (selectionRec.y
						+ selectionRec.height)) < PROX_DIST) {
					drawArea.setCursor(
							Cursor.getPredefinedCursor(
									Cursor.S_RESIZE_CURSOR));
				}
				break;
			case Rectangle.OUT_BOTTOM + Rectangle.OUT_RIGHT:
				if (Math.abs(p.x - (selectionRec.x
						+ selectionRec.width)) < PROX_DIST
						&& Math.abs(p.y - (selectionRec.y
								+ selectionRec.height)) < PROX_DIST) {
					drawArea.setCursor(
							Cursor.getPredefinedCursor(
									Cursor.SE_RESIZE_CURSOR));
				}
				break;
			case Rectangle.OUT_RIGHT:
				if (Math.abs(p.x - (selectionRec.x
						+ selectionRec.width)) < PROX_DIST) {
					drawArea.setCursor(
							Cursor.getPredefinedCursor(
									Cursor.E_RESIZE_CURSOR));
				}
				break;
			case Rectangle.OUT_RIGHT + Rectangle.OUT_TOP:
				if (Math.abs(p.x - (selectionRec.x
						+ selectionRec.width)) < PROX_DIST
						&& Math.abs(p.y
								- selectionRec.y) < PROX_DIST) {
					drawArea.setCursor(
							Cursor.getPredefinedCursor(
									Cursor.NE_RESIZE_CURSOR));
				}
				break;
			default: // center
				drawArea.setCursor(
						Cursor.getDefaultCursor());
			}
		}
	}

	/**
	 * Make a smaller Rectangle and use it to locate the cursor relative to the
	 * Rectangle center.
	 */
	private int getOutcode(Point p) {
		Rectangle r = (Rectangle) selectionRec.clone();
		r.grow(-PROX_DIST, -PROX_DIST);
		return r.outcode(p.x, p.y);
	}

	/**
	 * Make a larger Rectangle and check to see if the cursor is over it.
	 */
	private boolean isOverRect(Point p) {
		Rectangle r = (Rectangle) selectionRec.clone();
		r.grow(PROX_DIST, PROX_DIST);
		return r.contains(p);
	}

	private Rectangle getLine() {
		Line l = (Line) requiredToResize;
		int width = l.getStartPoint().x - l.getEndX();
		int height = l.getStartPoint().y - l.getEndY();
		int recX = width < 0 ? l.getStartPoint().x
				: l.getEndX();
		int recY = height < 0 ? l.getStartPoint().y
				: l.getEndY();
		return new Rectangle(recX, recY, width, height);
	}

	private Rectangle getRec() {
		paint.Rectangle r = (paint.Rectangle) requiredToResize;
		return new Rectangle(r.getStartPoint().x - 2,
				r.getStartPoint().y - 2, r.getWidth() + 4,
				r.getHeight() + 4);
	}

	private Rectangle getEllipse() {
		Ellipse e = (Ellipse) requiredToResize;
		return new Rectangle(e.getStartPoint().x,
				e.getStartPoint().y, e.getWidth(),
				e.getHeight());
	}
}
//package paint;
//
//import java.awt.Graphics;
//import java.awt.Point;
//import java.util.LinkedList;
//
//public class EditEngine {
//	/**
//	 * a reference to the drawArea Class to get shapes data.
//	 */
//	private DrawArea drawArea;
//	/**
//	 * list of all edited shaped (filled,moved,resized).
//	 */
//	private LinkedList<Shape> editedShapes = new LinkedList<Shape>();
//	/**
//	 * list of all shapes contains same point.
//	 */
//	private LinkedList<Shape> intersectedShapes = new LinkedList<Shape>();
//	/**
//	 * found at least one shape contains the given point.
//	 */
//	private boolean shapeFound;
//	/**
//	 * shape contain the given point and have the smallest Area.
//	 */
//	private Shape minAreaShape, requiredEditedShape,
//			prevEditedShape;
//
//	/**
//	 * 
//	 * @param drawArea
//	 *            an object from DrawArea Class.
//	 */
//	public EditEngine(DrawArea drawArea) {
//		this.drawArea = drawArea;
//		drawArea.setEditEngine(this);
//	}
//
//	/**
//	 * search for a required Shape to be edited and fill it with specific color.
//	 * if the shape was found then determine whether it was edited before or
//	 * not. if it was edited the change the previous editing with the new one.
//	 * else create a new object and perform the edit action on it then add it in
//	 * editShapes list. while creating the new object we get its index in the
//	 * editShapes list we also get index of its original shape in drawnShapes
//	 * list. finally we set the original shape to be undrawable (shouldn't be
//	 * drawn on the panel). else if the shape not found the user click on a
//	 * freeArea and you should change the background color.
//	 * 
//	 * @param p
//	 *            point of currently mouse location.
//	 */
//	public void fillShape(Point p) {
//		if (findShape(p, editedShapes)) {
//			try {
//				prevEditedShape = (Shape) Class.forName(
//						minAreaShape.getClass().getName())
//						.newInstance();
//				getCopy(prevEditedShape);
//				prevEditedShape.setFillCol(
//						this.drawArea.getFillColor());
//				minAreaShape.setFillCol(
//						this.drawArea.getFillColor());
//				this.drawArea
//						.addInUndoStack(prevEditedShape);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		} else if (findShape(p, drawArea.getShapes())) { //the first change
//			try {
//				requiredEditedShape = (Shape) Class.forName(
//						minAreaShape.getClass().getName())
//						.newInstance();
//				getCopy(requiredEditedShape);
//				requiredEditedShape.setFillCol(
//						this.drawArea.getFillColor());
//				requiredEditedShape.edited(true);
//				requiredEditedShape.setEditedShapeIndex(
//						this.drawArea.getShapes()
//								.indexOf(minAreaShape));
//				editedShapes.add(requiredEditedShape);
//				minAreaShape.drawable(false);
//				minAreaShape.setEditedShapeIndex(
//						editedShapes.size() - 1);
//				drawArea.addInUndoStack(
//						requiredEditedShape);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		} else {
//			// adding the color in the stack.
//			drawArea.addInUndoStack(
//					drawArea.getFillColor());
//			drawArea.setBackground(drawArea.getFillColor());
//		}
//		drawArea.repaint();
//	}
//
//	/**
//	 * take a new object of minAreaShape and put it in the new position delete
//	 * the old one. save the new shape. get the new point in every drag. be he
//	 * is trying to move an edited shape so he will not found it in the
//	 * drawnShape list.
//	 * 
//	 * @param p
//	 */
//	public void moveShape(Point p) {
//		if (findShape(p, drawArea.getShapes())) {
//			try {
//				requiredEditedShape = (Shape) Class.forName(
//						minAreaShape.getClass().getName())
//						.newInstance();
//				getCopy(requiredEditedShape);
//				System.out.println(p);
//				System.out.println(
//						requiredEditedShape.getStartPoint());
//				requiredEditedShape.move(p);
//				requiredEditedShape.edited(true);
//				editedShapes.add(requiredEditedShape);
//				// why we cann't leave it and set the drawable by false.
//				// simply because when you pressed on the pane in its old position
//				// it will give you that there was a shape but actually it is moved.
//				drawArea.getShapes().remove(minAreaShape);
//				drawArea.addInUndoStack(requiredEditedShape);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		} else if (findShape(p, editedShapes)) {
//			System.out.println(minAreaShape.isDrawable());
//			try {
//				requiredEditedShape = (Shape) Class.forName(
//						minAreaShape.getClass().getName())
//						.newInstance();
//				getCopy(requiredEditedShape);
//				requiredEditedShape.edited(true);
//				requiredEditedShape.move(p);
//				editedShapes.remove(minAreaShape);
//				editedShapes.add(requiredEditedShape);
//				drawArea.addInUndoStack(requiredEditedShape);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		}
//		drawArea.repaint();
//	}
//
//	/**
//	 * search for a specific shape in a specific list.
//	 * 
//	 * @param p
//	 *            a point from mouse location.
//	 * @param list
//	 *            a list to search for the shape.
//	 * @return
//	 */
//	private boolean findShape(Point p,
//			LinkedList<Shape> list) {
//		intersectedShapes.clear();
//		shapeFound = false;
//		for (Shape s : list) {
//			if (s.contain(p.getX(), p.getY())) {
//				intersectedShapes.add(s);
//				shapeFound = true;
//			}
//		}
//		if (shapeFound) {
//			// finding the minimum shape in area to fill it.
//			double minArea = Double.POSITIVE_INFINITY;
//			for (int i = 0; i < intersectedShapes
//					.size(); i++) {
//				double a = intersectedShapes.get(i)
//						.getArea();
//				if (a < minArea) {
//					minArea = a;
//					minAreaShape = intersectedShapes.get(i);
//				}
//			}
//		}
//		return shapeFound;
//	}
//
//	/**
//	 * printing all edited Shapes from the end.
//	 * 
//	 * @param g
//	 */
//	public void drawEditShape(Graphics g) {
//		if (editedShapes.size() > 0) {
//			for (int i = editedShapes.size()
//					- 1; i >= 0; i--) {
//				editedShapes.get(i).fill(g);
//			}
//
//		}
//	}
//
//	public void removeLastEdit() {
//		this.editedShapes.removeLast();
//	}
//
//	public void addEditAction(Shape s) {
//		this.editedShapes.add(s);
//	}
//
//	private void getCopy(Shape shape) {
//		if (requiredEditedShape instanceof Rectangle) {
//			getRectangle(shape);
//		} else if (requiredEditedShape instanceof Ellipse) {
//			getEllipse(shape);
//		}
//
//	}
//
//	private void getRectangle(Shape shape) {
//		Rectangle r = (Rectangle) minAreaShape;
//		shape.setBound(new Rectangle(r.getStartPoint().x,
//				r.getStartPoint().y, r.getWidth(),
//				r.getHeight()));
//	}
//
//	private void getEllipse(Shape shape) {
//		Ellipse e = (Ellipse) minAreaShape;
//		shape.setBound(new Rectangle(e.getStartPoint().x,
//				e.getStartPoint().y, e.getWidth(),
//				e.getHeight()));
//	}
//
//}

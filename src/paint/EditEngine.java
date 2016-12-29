package paint;

import java.awt.Graphics2D;
import java.awt.Point;
import java.lang.reflect.GenericArrayType;
import java.util.LinkedList;

public class EditEngine {
	/**
	 * a reference to the drawArea Class to get shapes data.
	 */
	private DrawArea drawArea;
	/**
	 * list of all edited shaped (filled,moved,resized). each element has all
	 * the edit history for each shape.
	 */
	private LinkedList<LinkedList<Shape>> editedShapes = new LinkedList<>();
	/**
	 * list of all shapes contains same point.
	 */
	private LinkedList<Shape> intersectedShapes = new LinkedList<Shape>();
	/**
	 * found at least one shape contains the given point.
	 */
	private boolean shapeFound;
	/**
	 * shape contain the given point and have the smallest Area.
	 */
	private Shape requiredEditedShape, newEditedShape;
	/**
	 * save all edit moves performed on the same shape.
	 */
	private LinkedList<Shape> editHistory;

	/**
	 * 
	 * @param drawArea
	 *            an object from DrawArea Class.
	 */
	public EditEngine(DrawArea drawArea) {
		this.drawArea = drawArea;
		drawArea.setEditEngine(this);
	}

	/**
	 * search for a required Shape to be edited and fill it with specific color.
	 * if the shape was found then determine whether it was edited before or
	 * not. if it was edited the change the previous editing with the new one.
	 * else create a new object and perform the edit action on it then add it in
	 * editShapes list. while creating the new object we get its index in the
	 * editShapes list we also get index of its original shape in drawnShapes
	 * list. finally we set the original shape to be undrawable (shouldn't be
	 * drawn on the panel). else if the shape not found the user click on a
	 * freeArea and you should change the background color.
	 * 
	 * @param p
	 *            point of currently mouse location.
	 */
	public void fillShape(Point p) {
		// check first whether the shape had previously been edited.
		Shape selectedShape = foundInEditHistory(p,
				editedShapes);
		if (selectedShape != null) {
			try {
				newEditedShape = (Shape) Class.forName(
						selectedShape.getClass().getName())
						.newInstance();
				editHistory = getEditHistory(selectedShape);
				if (editHistory != null) {
					getCopy(newEditedShape, selectedShape);
					newEditedShape.setFillCol(
							this.drawArea.getFillColor());
					newEditedShape.fill(true);
					newEditedShape.edit(true);
					newEditedShape.setEditedShapeIndex(
							selectedShape
									.getEditedShapeIndex());
					editHistory.add(newEditedShape);
					this.drawArea
							.addInUndoStack(newEditedShape);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			selectedShape = foundOriginalShape(p,
					drawArea.getShapes());
			if (selectedShape != null) {
				// the first change on the shape
				try {
					requiredEditedShape = (Shape) Class
							.forName(selectedShape
									.getClass().getName())
							.newInstance();
					getCopy(requiredEditedShape,
							selectedShape);
					requiredEditedShape.setFillCol(
							this.drawArea.getFillColor());
					requiredEditedShape.fill(true);
					requiredEditedShape.edit(true);
					requiredEditedShape.setEditedShapeIndex(
							this.drawArea.getShapes()
									.indexOf(
											selectedShape));
					editHistory = new LinkedList<>();
					editHistory.add(requiredEditedShape);
					editedShapes.add(editHistory);
					selectedShape.drawable(false);
					selectedShape.setEditedShapeIndex(
							editedShapes.size() - 1);
					drawArea.addInUndoStack(
							requiredEditedShape);
					requiredEditedShape.getArea();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			} else {
				// adding the color in the stack.
				drawArea.addInUndoStack(
						drawArea.getFillColor());
				drawArea.setBackground(
						drawArea.getFillColor());
			}
		}
		drawArea.clearRedoStack();
		drawArea.repaint();
	}

	/**
	 * take a new object of minAreaShape and put it in the new position delete
	 * the old one. save the new shape. get the new point in every drag. be he
	 * is trying to move an edited shape so he will not found it in the
	 * drawnShape list.
	 * 
	 * @param p
	 */
	public void moveShape(Point originalPoint,
			Point newPoint, Shape originalShape,
			Shape requiredToMove) {
		// check first whether the shape had previously been edited.
		if (originalShape.isEdited()) {
			editHistory = getEditHistory(originalShape);
			if (editHistory != null) {
				requiredToMove
						.setEditedShapeIndex(originalShape
								.getEditedShapeIndex());
				if (originalShape.isFilled()) {
					requiredToMove.setFillCol(
							originalShape.getFillCol());
				}
				editHistory.add(requiredToMove);
				originalShape.drawable(false);
				this.drawArea
						.addInUndoStack(requiredToMove);
			}
		} else {
			// the first change on the shape
			// putting reference to the original shape.
			requiredToMove.setEditedShapeIndex(this.drawArea
					.getShapes().indexOf(originalShape));
			editHistory = new LinkedList<>();
			editHistory.add(requiredToMove);
			editedShapes.add(editHistory);
			originalShape.drawable(false);
			originalShape.setEditedShapeIndex(
					editedShapes.size() - 1);
			// vanishing the old shape to prevent it from being
			// selected.
			// but don't remove it because you'll need it.
			drawArea.addInUndoStack(requiredToMove);
		}
		drawArea.clearRedoStack();
	}

	public void resizeShape(Shape originalShape,
			Shape requiredToResize) {
		// check first whether the shape had previously been edited.
		if (originalShape.isEdited()) {
			editHistory = getEditHistory(originalShape);
			if (editHistory != null) {
				requiredToResize
						.setEditedShapeIndex(originalShape
								.getEditedShapeIndex());
				if (originalShape.isFilled()) {
					requiredToResize.setFillCol(
							originalShape.getFillCol());
				}
				editHistory.add(requiredToResize);
				originalShape.drawable(false);
				this.drawArea
						.addInUndoStack(requiredToResize);
			}
		} else {
			// the first change on the shape
			// putting reference to the original shape.
			requiredToResize.setEditedShapeIndex(
					this.drawArea.getShapes()
							.indexOf(originalShape));
			editHistory = new LinkedList<>();
			editHistory.add(requiredToResize);
			editedShapes.add(editHistory);
			originalShape.drawable(false);
			originalShape.setEditedShapeIndex(
					editedShapes.size() - 1);
			// vanishing the old shape to prevent it from being
			// selected.
			// but don't remove it because you'll need it.
			drawArea.addInUndoStack(requiredToResize);

		}
		drawArea.clearRedoStack();
	}

	public void deleteShape(Shape originalShape,
			Shape requiredToDelete) {
		// check first whether the shape had previously been edited.
		if (originalShape.isEdited()) {
			editHistory = getEditHistory(originalShape);
			if (editHistory != null) {
				requiredToDelete.drawable(false);
				editHistory.add(requiredToDelete);
				originalShape.drawable(false);
				this.drawArea
						.addInUndoStack(requiredToDelete);
			}
		} else {
			// the first change on the shape
			requiredToDelete.setEditedShapeIndex(
					this.drawArea.getShapes()
							.indexOf(originalShape));
			editHistory = new LinkedList<>();
			editHistory.add(requiredToDelete);
			editedShapes.add(editHistory);
			originalShape.drawable(false);
			requiredToDelete.drawable(false);
			originalShape.setEditedShapeIndex(
					editedShapes.size() - 1);
			// vanishing the old shape to prevent it from being
			// selected.
			// but don't remove it because you'll need it.
			drawArea.addInUndoStack(requiredToDelete);

		}
		drawArea.clearRedoStack();
		drawArea.repaint();
	}

	public LinkedList<Shape> getEditHistory(Shape s) {
		for (LinkedList<Shape> l : editedShapes) {
			if (l != null && l.contains(s)) {
				return l;
			}
		}
		return null;
	}

	public LinkedList<LinkedList<Shape>> getEditList() {
		return editedShapes;
	}

	public Shape foundInEditHistory(Point p,
			LinkedList<LinkedList<Shape>> history) {
		LinkedList<Shape> list = new LinkedList<>();
		for (LinkedList<Shape> l : history) {
			if (l != null && l.getLast().isDrawable()) {
				list.add(l.getLast());
			}
		}
		return foundOriginalShape(p, list);
	}

	/**
	 * search for a specific shape in a specific list.
	 * 
	 * @param p
	 *            a point from mouse location.
	 * @param list
	 *            a list to search for the shape.
	 * @return
	 */
	public Shape foundOriginalShape(Point p,
			LinkedList<Shape> list) {
		Shape minAreaShape = null;
		intersectedShapes.clear();
		shapeFound = false;
		for (Shape s : list) {
			if (s.isDrawable()
					&& s.contains(p.getX(), p.getY())) {
				intersectedShapes.add(s);
				shapeFound = true;
			}
		}
		if (shapeFound) {
			// finding the minimum shape in area to fill it.
			double minArea = Double.POSITIVE_INFINITY;
			for (int i = 0; i < intersectedShapes
					.size(); i++) {
				double a = intersectedShapes.get(i)
						.getArea();
				if (a < minArea) {
					minArea = a;
					minAreaShape = intersectedShapes.get(i);
				}
			}
		}
		return minAreaShape;
	}

	/**
	 * printing all edited Shapes from the end.
	 * 
	 * @param g
	 */
	public void drawEditShape(Graphics2D g) {
		if (editedShapes.size() > 0) {
			for (int i = editedShapes.size()
					- 1; i >= 0; i--) {
				if (editedShapes.get(i) != null
						&& editedShapes.get(i).size() > 0) {
					Shape s = editedShapes.get(i).getLast();
					if (s.isFilled() && s.isDrawable()) {
						s.fill(g);
					} else if (s.isDrawable()) {
						s.draw(g);
					}
				} else {
					editedShapes.set(i, null);
				}
			}

		}
	}

	public void getCopy(Shape shape, Shape cShape) {
		if (cShape.getCol() != null) {
			shape.setCol(cShape.getCol());
		}
		if (cShape.getCol() != null) {
			shape.setFillCol(cShape.getFillCol());
		}
		if (shape instanceof Triangle) {
			System.out.println("asdgjkasg");
			getTriangle(shape, cShape);
		} else {
			getShape(shape, cShape);
		}
	}

	private void getShape(Shape shape, Shape cShape) {
		shape.setBound(new Rectangle(
				cShape.getStartPoint().x,
				cShape.getStartPoint().y, cShape.getWidth(),
				cShape.getHeight()));
	}

	private void getTriangle(Shape shape, Shape cShape) {
		Triangle t = (Triangle) cShape;
		Triangle t1 = (Triangle) shape;
		t1.setPointX(t.getPointX());
		t1.setPointY(t.getPointY());
		t1.setPointZ(t.getPointZ());
	}

	public int addEditAction(Shape s) {
		int index = s.getEditedShapeIndex();
		Shape originalShape = drawArea.getShapes()
				.get(index);
		if (editedShapes.get(originalShape
				.getEditedShapeIndex()) == null) {
			editHistory = new LinkedList<>();
			editHistory.add(s);
			editedShapes.add(editHistory);
			originalShape.setEditedShapeIndex(
					editedShapes.size() - 1);
			return 1;
		} else {
			editedShapes.get(
					originalShape.getEditedShapeIndex())
					.add(s);
			return editedShapes.get(
					originalShape.getEditedShapeIndex())
					.size();
		}
	}
	public void setEditedShapes(LinkedList<LinkedList<Shape>> editedShapes) {
        this.editedShapes = editedShapes;
    }
}

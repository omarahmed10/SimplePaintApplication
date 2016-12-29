package paint;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonWriter {
    public JsonWriter(LinkedList<Shape> s,
            LinkedList<FreeHandLine> fHL, File file)
            throws IOException {
        JSONObject writer = new JSONObject();
        FileWriter fileWriter = new FileWriter(file);
        for (Shape shape : s) {
            if (shape.getClass().getName()
                    .equals("paint.Rectangle")) {
                saveRec(writer, shape);
            } else if (shape.getClass().getName()
                    .equals("paint.Ellipse")) {
                saveElli(writer, shape);
            } else if (shape.getClass().getName()
                    .equals("paint.Line")) {
                saveLine(writer, shape);
            } else if (shape.getClass().getName()
                    .equals("paint.Square")) {
                saveSqr(writer, shape);
            } else if (shape.getClass().getName()
                    .equals("paint.Circle")) {
                saveCir(writer, shape);
            } else if (shape.getClass().getName()
                    .equals("paint.Triangle")) {
                saveTri(writer, shape);
            }
            fileWriter.write(writer.toJSONString());
            fileWriter.write(
                    System.getProperty("line.separator"));
        }
        for (FreeHandLine freeLine : fHL) {
            saveFH(writer, freeLine);
            fileWriter.write(writer.toJSONString());
            fileWriter.write(
                    System.getProperty("line.separator"));
        }
        fileWriter.flush();
        fileWriter.close();
    }

    @SuppressWarnings("unchecked")
    private void saveRec(JSONObject w, Shape shp) {
        Rectangle rec = (Rectangle) shp;
        w.clear();
        w.put("Type", "Rectangle");
        JSONArray listOfProp = new JSONArray();
        listOfProp.add(rec.getStartPoint().x);
        listOfProp.add(rec.getStartPoint().y);
        listOfProp.add(rec.getWidth());
        listOfProp.add(rec.getHeight());
        listOfProp.add(rec.getCol().getRGB());
        w.put("Prop", listOfProp);
    }

    @SuppressWarnings("unchecked")
    private void saveSqr(JSONObject w, Shape shp) {
//        Square rec = (Square) shp;
        w.clear();
        w.put("Type", "Square");
        JSONArray listOfProp = new JSONArray();
//        listOfProp.add(rec.getStartPoint().x);
//        listOfProp.add(rec.getStartPoint().y);
//        listOfProp.add(rec.getSide());
//        listOfProp.add(rec.getCol().getRGB());
        w.put("Prop", listOfProp);
    }

    @SuppressWarnings("unchecked")
    private void saveElli(JSONObject w, Shape shp) {
        Ellipse elli = (Ellipse) shp;
        w.clear();
        w.put("Type", "Ellipse");
        JSONArray listOfProp = new JSONArray();
        listOfProp.add(elli.getStartPoint().x);
        listOfProp.add(elli.getStartPoint().y);
        listOfProp.add(elli.getWidth());
        listOfProp.add(elli.getHeight());
        listOfProp.add(elli.getCol().getRGB());
        w.put("Prop", listOfProp);
    }

    @SuppressWarnings("unchecked")
    private void saveCir(JSONObject w, Shape shp) {
        Circle elli = (Circle) shp;
        w.clear();
        w.put("Type", "Circle");
        JSONArray listOfProp = new JSONArray();
        listOfProp.add(elli.getStartPoint().x);
        listOfProp.add(elli.getStartPoint().y);
        listOfProp.add(elli.getRad());
        listOfProp.add(elli.getCol().getRGB());
        w.put("Prop", listOfProp);
    }

    @SuppressWarnings("unchecked")
    private void saveLine(JSONObject w, Shape shp) {
        Line line = (Line) shp;
        w.clear();
        w.put("Type", "Line");
        JSONArray listOfProp = new JSONArray();
        listOfProp.add(line.getStartPoint().x);
        listOfProp.add(line.getStartPoint().y);
        listOfProp.add(line.getEndX());
        listOfProp.add(line.getEndY());
        listOfProp.add(line.getCol().getRGB());
        w.put("Prop", listOfProp);
    }

    @SuppressWarnings("unchecked")
    private void saveFH(JSONObject w, FreeHandLine fh) {
        w.clear();
        w.put("Type", "FreeHandLine");
        Point[] arr = new Point[fh.getPoints().size()];
        arr = fh.getPointArray();
        StringBuilder sB = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sB.append((int) arr[i].getX() + " "
                    + (int) arr[i].getY() + " ");
        }
        w.put("Point", sB.toString());
        w.put("Color", fh.getCol().getRGB());
    }

    @SuppressWarnings("unchecked")
    private void saveTri(JSONObject w, Shape shp) {
        Triangle tri = (Triangle) shp;
        w.clear();
        w.put("Type", "Triangle");
        JSONArray listOfProp = new JSONArray();
        listOfProp.add(tri.getPointX().x);
        listOfProp.add(tri.getPointX().y);
        listOfProp.add(tri.getPointY().x);
        listOfProp.add(tri.getPointY().y);
        listOfProp.add(tri.getPointZ().x);
        listOfProp.add(tri.getPointZ().y);
        listOfProp.add(tri.getCol().getRGB());
        w.put("Prop", listOfProp);
    }
}
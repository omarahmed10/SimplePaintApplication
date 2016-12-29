package paint;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonReader {

    private BufferedReader bufferedReader;

    public JsonReader(LinkedList<Shape> s, LinkedList<FreeHandLine> fHL,
            File file) {
        try {
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            JSONObject obj;
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                obj = (JSONObject) new JSONParser().parse(line);
                if (obj.get("Type").toString().equals("Rectangle")) {
                    JSONArray ReclistOfProp = new JSONArray();
                    ReclistOfProp = (JSONArray) obj.get("Prop");
                    loadRec(ReclistOfProp, s);
                } else if (obj.get("Type").toString().equals("Ellipse")) {
                    JSONArray elliListOfProp = new JSONArray();
                    elliListOfProp = (JSONArray) obj.get("Prop");
                    loadElli(elliListOfProp, s);
                } else if (obj.get("Type").toString().equals("Line")) {
                    JSONArray lineListOfProp = new JSONArray();
                    lineListOfProp = (JSONArray) obj.get("Prop");
                    loadLine(lineListOfProp, s);
                } else if (obj.get("Type").toString().equals("Square")) {
                    JSONArray SqrListOfProp = new JSONArray();
                    SqrListOfProp = (JSONArray) obj.get("Prop");
                    loadSqr(SqrListOfProp, s);
                } else if (obj.get("Type").toString().equals("Circle")) {
                    JSONArray CirListOfProp = new JSONArray();
                    CirListOfProp = (JSONArray) obj.get("Prop");
                    loadCir(CirListOfProp, s);
                } else if (obj.get("Type").toString().equals("Triangle")) {
                    JSONArray triListOfProp = new JSONArray();
                    triListOfProp = (JSONArray) obj.get("Prop");
                    loadTri(triListOfProp, s);
                } else if (obj.get("Type").toString().equals("FreeHandLine")) {
                    loadFreeHand(obj, fHL);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRec(JSONArray arr, LinkedList<Shape> sLL
            ) {
        int startX = Integer.parseInt(arr.get(0).toString());
        int startY = Integer.parseInt(arr.get(1).toString());
        int width = Integer.parseInt(arr.get(2).toString());
        int height = Integer.parseInt(arr.get(3).toString());
        int col = Integer.parseInt(arr.get(4).toString());
        Rectangle rec = new Rectangle(startX, startY, width, height);
        rec.setCol(new Color(col));
        sLL.add(rec);
    }

    private void loadSqr(JSONArray arr, LinkedList<Shape> sLL
            ) {
        int startX = Integer.parseInt(arr.get(0).toString());
        int startY = Integer.parseInt(arr.get(1).toString());
        int side = Integer.parseInt(arr.get(2).toString());
        int col = Integer.parseInt(arr.get(3).toString());
//        Square sqr = new Square(startX, startY, side);
//        sqr.setCol(new Color(col));
//        sLL.add(sqr);
    }

    private void loadElli(JSONArray arr, LinkedList<Shape> sLL
            ) {
        int startX = Integer.parseInt(arr.get(0).toString());
        int startY = Integer.parseInt(arr.get(1).toString());
        int width = Integer.parseInt(arr.get(2).toString());
        int height = Integer.parseInt(arr.get(3).toString());
        int col = Integer.parseInt(arr.get(4).toString());
        Ellipse elli = new Ellipse(startX, startY, width, height);
        elli.setCol(new Color(col));
        sLL.add(elli);
    }

    private void loadCir(JSONArray arr, LinkedList<Shape> sLL) {
        int startX = Integer.parseInt(arr.get(0).toString());
        int startY = Integer.parseInt(arr.get(1).toString());
        int rad = Integer.parseInt(arr.get(2).toString());
        int col = Integer.parseInt(arr.get(3).toString());
        Circle cir = new Circle(startX, startY, rad);
        cir.setCol(new Color(col));
        sLL.add(cir);
    }

    private void loadLine(JSONArray arr, LinkedList<Shape> sLL) {
        int startX = Integer.parseInt(arr.get(0).toString());
        int startY = Integer.parseInt(arr.get(1).toString());
        int endX = Integer.parseInt(arr.get(2).toString());
        int endY = Integer.parseInt(arr.get(3).toString());
        int col = Integer.parseInt(arr.get(4).toString());
        Line line = new Line(startX, startY, endX, endY);
        line.setCol(new Color(col));
        sLL.add(line);
    }

    private void loadFreeHand(JSONObject o, LinkedList<FreeHandLine> fHLine) {
        String temp = o.get("Point").toString();
        String[] parts = temp.split(" ");
        ArrayList<Point> aL = new ArrayList<Point>();
        for (int i = 0; i < parts.length; i = i + 2) {
            int p1 = Integer.parseInt(parts[i]);
            int p2 = Integer.parseInt(parts[i + 1]);
            Point pt = new Point(p1, p2);
            aL.add(pt);
        }
        int col = Integer.parseInt(o.get("Color").toString());
        FreeHandLine handL = new FreeHandLine();
        handL.setCol(new Color(col));
        handL.setPoints(aL);
        fHLine.add(handL);
    }

    private void loadTri(JSONArray arr, LinkedList<Shape> sLL) {
        int xOfX = Integer.parseInt(arr.get(0).toString());
        int yOfX = Integer.parseInt(arr.get(1).toString());
        int xOfY = Integer.parseInt(arr.get(2).toString());
        int yOfY = Integer.parseInt(arr.get(3).toString());
        int xOfZ = Integer.parseInt(arr.get(4).toString());
        int yOfZ = Integer.parseInt(arr.get(5).toString());
        int col = Integer.parseInt(arr.get(6).toString());
        Triangle tri = new Triangle(new Point(xOfX, yOfX),
                new Point(xOfY, yOfY), new Point(xOfZ, yOfZ));
        tri.setCol(new Color(col));
        sLL.add(tri);
    }

}
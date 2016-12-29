package paint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.LinkedList;

public class XMLReader {

    @SuppressWarnings({"resource", "unchecked" })
    public XMLReader(LinkedList<Shape> s, LinkedList<FreeHandLine> fHL,
            File file, EditEngine edit) throws IOException {
        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        try {
            Shape[] shapes = (Shape[]) in.readObject();
            s.addAll(Arrays.asList(shapes));
            FreeHandLine[] freeHL = (FreeHandLine[]) in.readObject();
            fHL.addAll(Arrays.asList(freeHL));
            LinkedList<LinkedList<Shape>> editShapes = (LinkedList<LinkedList<Shape>>) in
                    .readObject();
            edit.setEditedShapes(editShapes);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
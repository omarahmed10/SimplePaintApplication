package paint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class XMLWriter {

    public XMLWriter(LinkedList<Shape> s, LinkedList<FreeHandLine> fHL,
            EditEngine edit, File file)
            throws TransformerException, ParserConfigurationException,
            IOException {
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        Shape[] shapes = s.toArray(new Shape[s.size()]);
        FreeHandLine[] freeHL = fHL.toArray(new FreeHandLine[fHL.size()]);
        LinkedList<LinkedList<Shape>> llS = edit.getEditList();
        out.writeObject(shapes);
        out.writeObject(freeHL);
        out.writeObject(llS);
        out.close();

    }
}
package paint;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ShapeFileFilter extends FileFilter {
    private String ex;
    public ShapeFileFilter(String name) {
        ex = name;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String filename = f.getName();
        String extension = getFileExtension(filename);
        if (extension == null) {
            return false;
        } else if (extension.equals(ex)) {
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {

        return "(." + ex + ")";
    }

    public String getFileExtension(String name) {
        int pointIndex = name.lastIndexOf(".");

        if (pointIndex == -1) {
            return null;
        } else if (pointIndex == name.length() - 1) {
            return null;
        }
        return name.substring(pointIndex + 1, name.length());
    }
    
}

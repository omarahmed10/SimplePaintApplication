package paint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;
    File saveFile, loadFile;


    @SuppressWarnings("deprecation")
	public MenuBar(JFileChooser fileC, DrawArea area) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        add(fileMenu);

        saveItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileC.showSaveDialog(
                        fileC.getParent()) == JFileChooser.APPROVE_OPTION) {

                    saveFile = fileC.getSelectedFile();
                    if (fileC.getFileFilter().getDescription()
                            .equals("(.json)")
                            && !saveFile.toString().endsWith(".json")) {
                        saveFile = new File(saveFile + ".json");
                    }
                    if (fileC.getFileFilter().getDescription()
                            .equals("(.xml)")
                            && !saveFile.toString().endsWith(".xml")) {
                        saveFile = new File(saveFile + ".xml");
                    }
                    try {
                        area.saveToFile(saveFile);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        loadItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileC.showOpenDialog(
                        fileC.getParent()) == JFileChooser.APPROVE_OPTION) {
                    loadFile = fileC.getSelectedFile();
                    try {
                        area.loadFromFile(loadFile);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        saveItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        loadItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        exitItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        ShapeFileFilter filter = new ShapeFileFilter("json");
        ShapeFileFilter filter2 = new ShapeFileFilter("xml");
        fileC.setAcceptAllFileFilterUsed(false);
        fileC.addChoosableFileFilter(filter);
        fileC.addChoosableFileFilter(filter2);

    }
}

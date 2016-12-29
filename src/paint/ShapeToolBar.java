package paint;

import java.awt.Color;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class ShapeToolBar extends JToolBar {

    private JButton circleButton, ellipseButton, lineButton, rectangleButton,
            squareButton, triangleButton;

    public ShapeToolBar() {
        circleButton = new JButton();
        circleButton.setIcon(createIcon("/images/circle.png"));
        ellipseButton = new JButton();
        ellipseButton.setIcon(createIcon("/images/ellipse.png"));
        lineButton = new JButton();
        lineButton.setIcon(createIcon("/images/line.png"));
        rectangleButton = new JButton();
        rectangleButton.setIcon(createIcon("/images/rectangle.png"));
        squareButton = new JButton();
        squareButton.setIcon(createIcon("/images/square.png"));
        triangleButton = new JButton();
        triangleButton.setIcon(createIcon("/images/triangle.png"));
        add(circleButton);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(ellipseButton);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(lineButton);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(rectangleButton);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(squareButton);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(triangleButton);
        setBackground(Color.WHITE);
        setOrientation(SwingConstants.HORIZONTAL);
        setVisible(false);

    }

    private ImageIcon createIcon(String Path) {
        URL url = getClass().getResource(Path);
        if (url == null) {
            throw new RuntimeException();
        }
        ImageIcon icon = new ImageIcon(url);

        return icon;
    }

}

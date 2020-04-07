package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class TetroShape extends JPanel {
    private static final Point[][][] shapeTemplate =
            {
                    // - Shape
                    {
                            {new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0)},
                            {new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3)},
                            {new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0)},
                            {new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3)}
                    },
                    // Backward L Shape
                    {
                            {new Point(0, 0), new Point(1, 0), new Point(0, 1), new Point(0, 2)},
                            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)},
                            {new Point(0, 2), new Point(1, 2), new Point(1, 1), new Point(1, 0)},
                            {new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(2, 1)}
                    },
                    // L Shape
                    {
                            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2)},
                            {new Point(1, 1), new Point(2, 1), new Point(3, 1), new Point(3, 0)},
                            {new Point(2, 2), new Point(2, 1), new Point(2, 0), new Point(1, 0)},
                            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(2, 0)}
                    },
                    // S Shape
                    {
                            {new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
                            {new Point(0, 1), new Point(1, 1), new Point(1, 0), new Point(2, 0)},
                            {new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
                            {new Point(0, 1), new Point(1, 1), new Point(1, 0), new Point(2, 0)}
                    },
                    // Backward S Shape
                    {
                            {new Point(1, 0), new Point(1, 1), new Point(0, 1), new Point(0, 2)},
                            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
                            {new Point(1, 0), new Point(1, 1), new Point(0, 1), new Point(0, 2)},
                            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)}
                    },
                    // T Shape
                    {
                            {new Point(0, 1), new Point(1, 1), new Point(1, 0), new Point(2, 1)},
                            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 1)},
                            {new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(1, 1)},
                            {new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 1)}
                    },
                    // Box shape
                    {
                            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
                            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
                            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
                            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)}
                    }
            };

    private static final int SIZE = 30;
    private static final Color[] colors = {
            new Color(142, 175, 12),
            new Color(239, 47, 162),
            new Color(203, 231, 36),
            new Color(255, 214, 0),
            new Color(248, 92, 80),
            new Color(255, 107, 0),
            new Color(68, 0, 178)
    };

    public int getElementSize() {
        return SIZE;
    }

    public Element getRandomShape() {
        var random = new Random();
        int shapeFamily = random.nextInt(7);
        Element e = new Element(buildShape(shapeTemplate[shapeFamily][0]), shapeFamily);
        e.setElementColor(colors[shapeFamily]);
        return e;
    }

    public Element rotateShape(Element rectangles) {
        var family = rectangles.getShapeFamily();
        var rotate = rectangles.getRotateState();
        rectangles.setRotateState(rotate + 1);
        var newRotate = rectangles.getRotateState();
        int i = 0;
        double x = rectangles.getShape().get(0).getX();
        double y = rectangles.getShape().get(0).getY();
        for (Rectangle2D shape : rectangles.getShape()) {
            double newX = x + SIZE * shapeTemplate[family][newRotate][i].getX();
            double newY = y + SIZE * shapeTemplate[family][newRotate][i].getY();
            shape.setRect(newX, newY, SIZE, SIZE);
            i++;
        }
        return rectangles;
    }

    public ArrayList<Rectangle2D> buildShape(Point[] s) {
        ArrayList<Rectangle2D> rectangles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rectangles.add(new Rectangle2D.Double(s[i].getX() * SIZE + 120, s[i].getY() * SIZE, SIZE, SIZE));
        }
        return rectangles;
    }
}

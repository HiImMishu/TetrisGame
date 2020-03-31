package Tetris;

import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Element extends JComponent {
    private ArrayList<Rectangle2D> rectangles;
    private int rotateState;
    private int elementShape;

    Element(ArrayList<Rectangle2D> a, int elementShape)
    {
        this.rectangles = a;
        this.elementShape = elementShape;
        this.rotateState = 0;
    }

    public ArrayList<Rectangle2D> getShape()
    {
        return rectangles;
    }

    public int getRotateState()
    {
        return rotateState;
    }

    public int getShapeFamily()
    {
        return elementShape;
    }

    public void setRotateState(int rotateState)
    {
        this.rotateState = rotateState;
        this.rotateState %= 4;
    }
}
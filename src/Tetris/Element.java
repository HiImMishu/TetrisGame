package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Element extends JComponent implements Cloneable {
    private ArrayList<Rectangle2D> rectangles;
    private int rotateState;
    private int elementShape;
    private Color color;

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

    public Color getColor() { return color; }

    public void setRotateState(int rotateState)
    {
        this.rotateState = rotateState;
        this.rotateState %= 4;
    }

    public void setElementColor(Color color)
    {
        this.color = color;
    }

    public Element clone()
    {
        ArrayList<Rectangle2D> newRectangles = new ArrayList<>();
        for(Rectangle2D r: rectangles)
        {
            newRectangles.add(new Rectangle2D.Double(r.getMinX(), r.getY(), r.getWidth(), r.getHeight()));
        }
        Element e = new Element(newRectangles, elementShape);
        e.setElementColor(color);
        e.setRotateState(rotateState);

        return e;
    }
}
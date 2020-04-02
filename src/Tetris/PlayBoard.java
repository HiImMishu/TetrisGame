package Tetris;

import jdk.jfr.consumer.RecordedThreadGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class PlayBoard extends JComponent
{
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 560;
    private static final int SIZE = 20;
    private static final int MAX_ROW = DEFAULT_WIDTH / SIZE;

    private Element current;
    private Element next;
    private ArrayList<Rectangle2D> done;
    private TetroShape elementCreator;

    PlayBoard()
    {
        elementCreator = new TetroShape();
        current = elementCreator.getRandomShape();
        next = elementCreator.getRandomShape();
        done = new ArrayList<Rectangle2D>();

        var moveDown = new MoveAction("DOWN");
        var moveLeft = new MoveAction("LEFT");
        var moveRight = new MoveAction("RIGHT");
        var rotate = new MoveAction("ROTATE");
        InputMap imap = getInputMap();
        imap.put(KeyStroke.getKeyStroke("S"), "move.down");
        imap.put(KeyStroke.getKeyStroke("A"), "move.left");
        imap.put(KeyStroke.getKeyStroke("D"), "move.right");
        imap.put(KeyStroke.getKeyStroke("W"), "move.round");
        ActionMap amap = getActionMap();
        amap.put("move.down", moveDown);
        amap.put("move.right", moveRight);
        amap.put("move.left", moveLeft);
        amap.put("move.round", rotate);
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.CYAN);
        g2.fillRect(0,0,600,600);

        for(Rectangle2D shape: current.getShape())
        {
            g2.setColor(current.getColor());
            g2.fill(shape);
            g2.setColor(Color.DARK_GRAY);
            g2.draw(shape);
        }

        for(Rectangle2D painted: done)
        {
            g2.setColor(Color.RED);
            g2.fill(painted);
            g2.setColor(Color.DARK_GRAY);
            g2.draw(painted);
        }

    }

    public Dimension getPreferredSize()
    {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private class MoveAction extends AbstractAction
    {
        private String direction;

        MoveAction(String direction)
        {
            this.direction = direction;
        }

        public void actionPerformed(ActionEvent event)
        {
            if(direction.compareTo("DOWN") == 0)
                moveDown(current.getShape());
            else if(direction.compareTo("LEFT") == 0)
                moveLeft(current.getShape());
            else if(direction.compareTo("RIGHT") == 0)
                moveRight(current.getShape());
            else if(direction.compareTo("ROTATE") == 0)
                rotate();
        }
    }

    public void moveDown(ArrayList<Rectangle2D> r)
    {
        for(Rectangle2D shape: r)
        {
            double y = shape.getY()+SIZE;
            double x = shape.getX();
            shape.setRect(x, y, SIZE, SIZE);
        }
        repaint();
        colision(current);
        return;
    }

    public void moveLeft(ArrayList<Rectangle2D> r)
    {
        for(Rectangle2D shape: r)
        {
            if(shape.getMinX() <= 0)
                return;
        }

        for(Rectangle2D shape: r)
        {
            for (Rectangle2D d : done) {
                if (d.getMaxX() == shape.getMinX() && d.getMinY() == shape.getMinY()) {
                    colision(current);
                    return;
                }
            }
        }

        for(Rectangle2D shape: r)
        {
            double y = shape.getY();
            double x = shape.getX()-SIZE;
            shape.setRect(x, y, SIZE, SIZE);
        }
        repaint();
    }

    public void moveRight(ArrayList<Rectangle2D> r)
    {
        for(Rectangle2D shape: r)
        {
            if(shape.getMaxX() >= 300)
                return;
        }

        for(Rectangle2D shape: r) {
            for (Rectangle2D d : done) {
                if (d.getMinX() == shape.getMaxX() && d.getMinY() == shape.getMinY()) {
                    colision(current);
                    return;
                }
            }
        }

        for(Rectangle2D shape: r)
        {
            double y = shape.getY();
            double x = shape.getX()+SIZE;
            shape.setRect(x, y, SIZE, SIZE);
        }
        repaint();
    }

    public void rotate()
    {
        current = elementCreator.rotateShape(current);
        colision(current);
        repaint();
    }

    public void colision(Element c)
    {
        for(Rectangle2D shape: c.getShape())
        {
            if(shape.getMaxY() >= DEFAULT_HEIGHT)
            {
                done.addAll(c.getShape());
                current = next;
                next = elementCreator.getRandomShape();
                checkRow();
                return;
            }
        }

        for(Rectangle2D shape: c.getShape())
        {
            for(Rectangle2D flor: done)
            {
                if(flor.contains(shape.getX(), shape.getMaxY()))
                {
                    done.addAll(c.getShape());
                    current = next;
                    next = elementCreator.getRandomShape();
                    checkRow();
                    return;
                }
            }
        }
    }

    public void checkRow()
    {
        int[] elements = new int[(DEFAULT_HEIGHT/SIZE)];
        for(Rectangle2D d: done)
        {
            elements[(int)d.getMinY()/SIZE]++;
        }

        for(int i=0; i < DEFAULT_HEIGHT/SIZE; i++)
        {
            if(elements[i] == MAX_ROW)
            {
                removeRow(i);
                return;
            }
        }
    }

    private void removeRow(int lvl)
    {
        ArrayList<Rectangle2D> row = new ArrayList<>();
        for(Rectangle2D d: done)
        {
            if(d.getMinY()/SIZE == lvl)
                row.add(d);
        }
        done.removeAll(row);
        moveDoneDown(lvl);
        checkRow();
        return;
    }

    private void moveDoneDown(int lvl)
    {
        for(Rectangle2D d: done)
        {
            if(d.getMinY() < lvl * SIZE)
            {
                double x = d.getX();
                double y = d.getY() + SIZE;
                d.setRect(x, y, SIZE, SIZE);
            }
        }
        repaint();
        return;
    }

}

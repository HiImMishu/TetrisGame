package Tetris;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class PlayBoard extends JPanel
{
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 600;
    private static final int SIZE = 30;
    private static final int MAX_ROW = DEFAULT_WIDTH / SIZE;

    private Element current;
    private Element next;
    private ArrayList<Element> done;
    private TetroShape elementCreator;
    private ScoreSystem scoreSystem;
    private NextElementPanel nextElementPanel;

    PlayBoard(ScoreSystem scoreSystem)
    {
        elementCreator = new TetroShape();
        this.scoreSystem = scoreSystem;
        current = elementCreator.getRandomShape();
        next = elementCreator.getRandomShape();
        done = new ArrayList<Element>();
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

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
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Image background = new ImageIcon("C:\\Studia\\Sem4\\JiTP\\PROJEKT\\assets\\bg3.png").getImage();
        g2.drawImage(background, 0, 0, null);

        for(Rectangle2D shape: current.getShape())
        {
            g2.setColor(current.getColor());
            g2.fill(shape);
            g2.setColor(Color.DARK_GRAY);
            g2.draw(shape);
            g2.setColor(Color.WHITE);
        }

        for(Element e: done) {
            for (Rectangle2D painted : e.getShape()) {
                g2.setColor(e.getColor());
                g2.fill(painted);
                g2.setColor(Color.DARK_GRAY);
                g2.draw(painted);
            }
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
                moveDown();
            else if(direction.compareTo("LEFT") == 0)
                moveLeft(current.getShape());
            else if(direction.compareTo("RIGHT") == 0)
                moveRight(current.getShape());
            else if(direction.compareTo("ROTATE") == 0)
                rotate();
        }
    }

    public void moveDown()
    {
        if(collisionWithDone(current)){
            repaint();
            return;
        }

        for(Rectangle2D shape: current.getShape())
        {
            double y = shape.getY()+SIZE;
            double x = shape.getX();
            shape.setRect(x, y, SIZE, SIZE);
        }
        repaint();
        collision(current);
        return;
    }

    private void moveLeft(ArrayList<Rectangle2D> r)
    {
        for(Rectangle2D shape: r)
        {
            if(shape.getMinX() <= 0)
                return;
        }

        for(Rectangle2D shape: r) {
            for (Element e : done) {
                for (Rectangle2D d : e.getShape()) {
                    if (d.getMaxX() == shape.getMinX() && d.getMinY() == shape.getMinY()) {
                        collision(current);
                        return;
                    }
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

    private void moveRight(ArrayList<Rectangle2D> r)
    {
        for(Rectangle2D shape: r)
        {
            if(shape.getMaxX() >= DEFAULT_WIDTH)
                return;
        }

        for(Rectangle2D shape: r) {
            for(Element e: done) {
                for (Rectangle2D d : e.getShape()) {
                    if (d.getMinX() == shape.getMaxX() && d.getMinY() == shape.getMinY()) {
                        collision(current);
                        return;
                    }
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

    private void rotate()
    {
        boolean collide = true;
        Element rotated = elementCreator.rotateShape(current.clone());
        for(Rectangle2D r: rotated.getShape())
        {
            if(r.getMinX() < 0 || r.getMaxX() > DEFAULT_WIDTH || r.getMaxY() >= DEFAULT_HEIGHT)
                collide = false;

            for(Element e: done)
            {
                for(Rectangle2D d: e.getShape())
                {
                    if (d.contains(r.getX(), r.getMaxY())) {
                        collide = false;
                    }
                }
            }
        }
        if(collide)
            current = rotated;
        repaint();
    }

    private void collision(Element c)
    {
        for(Rectangle2D shape: c.getShape())
        {
            if(shape.getMaxY() >= DEFAULT_HEIGHT)
            {
                Element tmp = new Element(c.getShape(), c.getShapeFamily());
                done.add(tmp);
                tmp.setElementColor(c.getColor().darker());
                current = next.clone();
                next = elementCreator.getRandomShape();
                scoreSystem.setScore(10);
                nextElementPanel.repaint();
                checkRow();
                return;
            }
        }
        collisionWithDone(c);
    }

    private Boolean collisionWithDone(Element c)
    {
        for(Rectangle2D shape: c.getShape())
        {
            for(Element e:done) {
                for (Rectangle2D flor : e.getShape()) {
                    if (flor.contains(shape.getX(), shape.getMaxY()+1)) {
                        Element tmp = new Element(c.getShape(), c.getShapeFamily());
                        done.add(tmp);
                        tmp.setElementColor(c.getColor().darker());
                        current = next.clone();
                        next = elementCreator.getRandomShape();
                        checkRow();
                        scoreSystem.setScore(10);
                        nextElementPanel.repaint();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void checkRow()
    {
        int[] elements = new int[(DEFAULT_HEIGHT/SIZE)];
        for(Element e: done) {
            for (Rectangle2D d : e.getShape()) {
                elements[(int) d.getMinY() / SIZE]++;
            }
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
        for(Element e: done) {
            for (Rectangle2D d : e.getShape()) {
                if (d.getMinY() / SIZE == lvl)
                    row.add(d);
            }
            e.getShape().removeAll(row);
        }
        moveDoneDown(lvl);
        scoreSystem.setScore(100);
        checkRow();
        return;
    }

    private void moveDoneDown(int lvl)
    {
        for(Element e: done) {
            for (Rectangle2D d : e.getShape()) {
                if (d.getMinY() < lvl * SIZE) {
                    double x = d.getX();
                    double y = d.getY() + SIZE;
                    d.setRect(x, y, SIZE, SIZE);
                }
            }
        }
        repaint();
        return;
    }

    public Element getNext()
    {
        return this.next.clone();
    }

    public void setNextElementPanel(NextElementPanel nextElementPanel)
    {
        this.nextElementPanel = nextElementPanel;
    }

}

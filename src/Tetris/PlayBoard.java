package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Klasa umozliwiajaca poruszanie elementow, opadanie, obracanie.
 * Panel zawierajacy przestrzen do gry.
 */
public class PlayBoard extends JPanel {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 600;
    private static final int SIZE = 30;
    private static final int MAX_ROW = DEFAULT_WIDTH / SIZE;

    private boolean state;
    private Element current;
    private Element next;
    private ArrayList<Element> done;
    private TetroShape elementCreator;
    private ScoreSystem scoreSystem;
    private NextElementPanel nextElementPanel;
    private GameManager gameManager;

    /**
     * Konstruktor klasy.
     * Tworzy mape akcji oraz obiekt klasy InputMap pozwalajace na wywolywanie akcji za pomoca przyciskow klawiatury.
     * @param scoreSystem
     * @param gameManager
     */
    PlayBoard(ScoreSystem scoreSystem, GameManager gameManager) {
        this.gameManager = gameManager;
        state = false;
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
        InputMap imap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        imap.put(KeyStroke.getKeyStroke("DOWN"), "move.down");
        imap.put(KeyStroke.getKeyStroke("LEFT"), "move.left");
        imap.put(KeyStroke.getKeyStroke("RIGHT"), "move.right");
        imap.put(KeyStroke.getKeyStroke("UP"), "move.round");
        ActionMap amap = getActionMap();
        amap.put("move.down", moveDown);
        amap.put("move.right", moveRight);
        amap.put("move.left", moveLeft);
        amap.put("move.round", rotate);
    }

    /**
     * Metoda rysująca wszytkie elementy statyczne oraz element aktualny.
     * Ustawia obraz tla planszy.
     * @param g Obiekt klasy Graphics
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Image background = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("bg3.png"))).getImage();
        g2.drawImage(background, 0, 0, null);

        for (Rectangle2D shape : current.getShape()) {
            g2.setColor(current.getColor());
            g2.fill(shape);
            g2.setColor(Color.DARK_GRAY);
            g2.draw(shape);
            g2.setColor(Color.WHITE);
        }

        for (Element e : done) {
            for (Rectangle2D painted : e.getShape()) {
                g2.setColor(e.getColor());
                g2.fill(painted);
                g2.setColor(Color.DARK_GRAY);
                g2.draw(painted);
            }
        }

    }

    /**
     * Zwraca preferowany rozmiar panelu
     * @return Obiekt klasy Dimension
     */
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Klasa wewnetrzna umozliwiajaca wywolanie metod poruszajacych lu  obracajacych element
     */
    private class MoveAction extends AbstractAction {
        private String direction;

        /**
         * Kostruktor klasy
         * @param direction Parametr informujacy ktora metode nalezy wywolac.
         */
        MoveAction(String direction) {
            this.direction = direction;
        }

        /**
         * Wywolanie odpowiedniej metody w zaleznosci od parametru <code>direction</code>
         * @param event
         */
        public void actionPerformed(ActionEvent event) {
            if (direction.compareTo("DOWN") == 0)
                moveDown();
            else if (direction.compareTo("LEFT") == 0)
                moveLeft(current.getShape());
            else if (direction.compareTo("RIGHT") == 0)
                moveRight(current.getShape());
            else if (direction.compareTo("ROTATE") == 0)
                rotate();
        }
    }

    /**
     * Metoda umozliwiajaca poruszanie sie elementu do dolu.
     */
    public void moveDown() {
        if (!state) return;
        if (collisionWithDone(current)) {
            gameOver();
            repaint();
            return;
        }

        for (Rectangle2D shape : current.getShape()) {
            double y = shape.getY() + SIZE;
            double x = shape.getX();
            shape.setRect(x, y, SIZE, SIZE);
        }
        repaint();
        collision(current);
    }

    /**
     * Metoda przesuwajaca element w lewo.
     * @param r lista prymitywnych elementow ktore nalezy przesunac jako jeden element
     */
    private void moveLeft(ArrayList<Rectangle2D> r) {
        if (!state) return;
        for (Rectangle2D shape : r) {
            if (shape.getMinX() <= 0)
                return;
        }

        for (Rectangle2D shape : r) {
            for (Element e : done) {
                for (Rectangle2D d : e.getShape()) {
                    if (d.getMaxX() == shape.getMinX() && d.getMinY() == shape.getMinY()) {
                        collision(current);
                        return;
                    }
                }
            }
        }

        for (Rectangle2D shape : r) {
            double y = shape.getY();
            double x = shape.getX() - SIZE;
            shape.setRect(x, y, SIZE, SIZE);
        }
        repaint();
    }

    /**
     * Metoda umozliwiajaca przesuniecie elementu w prawo
     * @param r Lista prymitywow elementow ktore nalezy przesunac jako jeden element
     */
    private void moveRight(ArrayList<Rectangle2D> r) {
        if (!state) return;
        for (Rectangle2D shape : r) {
            if (shape.getMaxX() >= DEFAULT_WIDTH)
                return;
        }

        for (Rectangle2D shape : r) {
            for (Element e : done) {
                for (Rectangle2D d : e.getShape()) {
                    if (d.getMinX() == shape.getMaxX() && d.getMinY() == shape.getMinY()) {
                        collision(current);
                        return;
                    }
                }
            }
        }

        for (Rectangle2D shape : r) {
            double y = shape.getY();
            double x = shape.getX() + SIZE;
            shape.setRect(x, y, SIZE, SIZE);
        }
        repaint();
    }

    /**
     * Metoda umozliwiajaca obrot elementu jednokrotnie.
     */
    private void rotate() {
        if (!state) return;
        boolean collide = true;
        Element rotated = elementCreator.rotateShape(current.clone());
        for (Rectangle2D r : rotated.getShape()) {
            if (r.getMinX() < 0 || r.getMaxX() > DEFAULT_WIDTH || r.getMaxY() >= DEFAULT_HEIGHT)
                collide = false;

            for (Element e : done) {
                for (Rectangle2D d : e.getShape()) {
                    if (d.contains(r.getX(), r.getMaxY())) {
                        collide = false;
                    }
                }
            }
        }
        if (collide)
            current = rotated;
        repaint();
    }

    /**
     * Metoda sprawdzajaca czy element nie wchodzi w kolizje z granicami planszy.
     * @param c
     */
    private void collision(Element c) {
        for (Rectangle2D shape : c.getShape()) {
            if (shape.getMaxY() >= DEFAULT_HEIGHT) {
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

    /**
     * Metoda sprawdzajaca czy element nei wchodzi w kolizje z pozostalymi elementami.
     * @param c
     * @return
     */
    private Boolean collisionWithDone(Element c) {
        for (Rectangle2D shape : c.getShape()) {
            for (Element e : done) {
                for (Rectangle2D flor : e.getShape()) {
                    if (flor.contains(shape.getX(), shape.getMaxY() + 1)) {
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

    /**
     * Metoda sprawdzajaca czy istnieje rzad calkowicie zzapelniony elementami.
     */
    private void checkRow() {
        int[] elements = new int[(DEFAULT_HEIGHT / SIZE)];
        for (Element e : done) {
            for (Rectangle2D d : e.getShape()) {
                elements[(int) d.getMinY() / SIZE]++;
            }
        }

        for (int i = 0; i < DEFAULT_HEIGHT / SIZE; i++) {
            if (elements[i] == MAX_ROW) {
                removeRow(i);
                return;
            }
        }
    }

    /**
     * Metoda usuwajaca rzad zapelniony elementami.
     * @param lvl Parametr okreslajacy ktory rzad nalezy usuac.
     */
    private void removeRow(int lvl) {
        ArrayList<Rectangle2D> row = new ArrayList<>();
        for (Element e : done) {
            for (Rectangle2D d : e.getShape()) {
                if (d.getMinY() / SIZE == lvl)
                    row.add(d);
            }
            e.getShape().removeAll(row);
        }
        moveDoneDown(lvl);
        scoreSystem.setScore(100);
        checkRow();
    }

    /**
     * Metoda przesuwajaca wszystkie elementy ponad usunietym rzedem do dolu.
     * @param lvl wysokosc usunietego rzedu.
     */
    private void moveDoneDown(int lvl) {
        for (Element e : done) {
            for (Rectangle2D d : e.getShape()) {
                if (d.getMinY() < lvl * SIZE) {
                    double x = d.getX();
                    double y = d.getY() + SIZE;
                    d.setRect(x, y, SIZE, SIZE);
                }
            }
        }
        repaint();
    }

    /**
     * Metoda konczaca aktualna rozgrywke po przegranej.
     */
    private void gameOver() {
        for (Rectangle2D r : done.get(done.size() - 1).getShape())
            if (r.getMinY() <= 0) {
                gameManager.gameOver();
                pause();
            }
    }

    /**
     * Metoda rozpoczynajaca / wznawiajaca rozgrywke.
     */
    public void play() {
        state = true;
    }

    /**
     * Metoda konczaca rozgrywke.
     */
    public void pause() {
        state = false;
    }

    /**
     * Metoda resetujaca rozgrywke.
     */
    public void reset() {
        scoreSystem.reset();
        done.clear();
        current = next.clone();
        next = elementCreator.getRandomShape();
        nextElementPanel.repaint();
        repaint();
    }

    /**
     * Metoda zwracajaca nastepny element.
     * @return
     */
    public Element getNext() {
        return this.next.clone();
    }

    /**
     * Metoda umozliwiajaca ustawienie panelu z widokiem kolejnego elementu.
     * @param nextElementPanel Obiekt klasy NextElementPanel
     */
    public void setNextElementPanel(NextElementPanel nextElementPanel) {
        this.nextElementPanel = nextElementPanel;
    }

}

package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Klasa tworzaca panel z widokiem kolejnego elementu w grze.
 */
public class NextElementPanel extends JPanel {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 200;
    private PlayBoard playBoard;

    /**
     * Konstruktor klasy NextElementPanel.
     * @param playBoard Obiekt panelu gry.
     */
    NextElementPanel(PlayBoard playBoard) {
        this.playBoard = playBoard;
        setOpaque(true);
    }

    /**
     * Metoda rysujaca nastepny element gry.
     * @param gn Obiekt klasy Graphics.
     */
    public void paintComponent(Graphics gn) {
        super.paintComponent(gn);
        Graphics2D g = (Graphics2D) gn;
        setBackground(new Color(51, 51, 51));

        Element next = playBoard.getNext();
        for (Rectangle2D r : next.getShape()) {
            r.setRect(r.getMinX() - 50, r.getMinY() + 40, r.getWidth(), r.getHeight());
            g.setColor(next.getColor());
            g.fill(r);
            g.setColor(Color.BLACK);
            g.draw(r);
        }
    }

    /**
     * Metoda zwracajaca preferowany rozmiar panelu
     * @return Obiekt klasy Dimension.
     */
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}

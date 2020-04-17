package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * Klasa zarzadzajaca wyswietlaniem wynikow
 */
public class ScoreView extends JPanel {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 200;
    private ScoreSystem scoreSystem;

    /**
     * Kontruktor klasy ScoreView
     * @param scoreSystem Obiekt klasy ScoreSystem (systemu liczenia i zarzadzania wynikiem gracza)
     */
    ScoreView(ScoreSystem scoreSystem) {
        this.scoreSystem = scoreSystem;
    }

    /**
     * Metoda rysujaca informacje o aktualnym oraz najwyzszym wyniku gracza.
     * @param gn Obiekt klasy Graphics
     */
    public void paintComponent(Graphics gn) {
        super.paintComponent(gn);
        Graphics2D g = (Graphics2D) gn;
        setBackground(new Color(51, 51, 51));
        Font sasnBold26 = new Font("SansSerif", Font.BOLD, 26);
        Font sasnBold18 = new Font("SansSerif", Font.PLAIN, 18);
        Font sasnBold22 = new Font("SansSerif", Font.ITALIC + Font.BOLD, 22);

        String level = "LEVEL " + scoreSystem.getLvl(), score = "SCORE", scr = "" + scoreSystem.getScore();
        String highScore = "BEST SCORE", hsc = "" + scoreSystem.getHighScore();

        FontRenderContext context = g.getFontRenderContext();

        Rectangle2D bounds = sasnBold26.getStringBounds(level, context);
        g.setFont(sasnBold26);
        g.setColor(new Color(255, 255, 255));
        g.drawString(level, (int) (100 - bounds.getWidth() / 2), 40);

        bounds = sasnBold18.getStringBounds(score, context);
        g.setFont(sasnBold18);
        g.drawString(score, (int) (100 - bounds.getWidth() / 2), 70);

        bounds = sasnBold18.getStringBounds(scr, context);
        g.setFont(sasnBold18);
        g.drawString(scr, (int) (100 - bounds.getWidth() / 2), 100);

        bounds = sasnBold22.getStringBounds(highScore, context);
        g.setFont(sasnBold22);
        g.setColor(new Color(164, 0, 255));
        g.drawString(highScore, (int) (100 - bounds.getWidth() / 2), 140);

        bounds = sasnBold22.getStringBounds(hsc, context);
        g.setColor(new Color(194, 58, 148));
        g.drawString(hsc, (int) (100 - bounds.getWidth() / 2), 170);

    }

    /**
     * Metoda zwracajaca preferowany rozmiar.
     * @return Obiektk klasy Dimension.
     */
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

}

package Tetris;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Klasa <code>ControlsView</code> tworzy przyciski do sterowania opadaniem elementu (play, pause, restart).
 */
public class ControlsView extends JPanel {
    /**
     * Standardowa szerokosc ustawiona na 200
     */
    private static final int DEFAULT_WIDTH = 200;
    /**
     * Standardowa wysokosc ustawiona na 200
     */
    private static final int DEFAULT_HEIGHT = 200;
    private boolean active = true;

    /**
     * Konctruktor tworzacy trzy przyciski sterujace rozgrywka.
     * @param autoFall Obiekt Klasy <code>AutoFall</code>.
     * @param playBoard Obiekt Klasy <code>PlayBoard</code>.
     */
    ControlsView(AutoFall autoFall, PlayBoard playBoard) {
        ImageIcon playIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("play.png")));
        Image playImg = playIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JButton play = new JButton(new ImageIcon(playImg));
        play.setPreferredSize(new Dimension(55, 55));
        play.setMargin(new Insets(110, 110, 110, 110));
        play.addActionListener(event -> {
            if (active) {
                autoFall.play();
                playBoard.play();
            }
        });

        ImageIcon pauseIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("pause.png")));
        Image pauseImg = pauseIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JButton pause = new JButton(new ImageIcon(pauseImg));
        pause.setPreferredSize(new Dimension(55, 55));
        pause.addActionListener(event -> {
            if (active) {
                autoFall.pause();
                playBoard.pause();
            }
        });

        ImageIcon restartIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("restart.png")));
        Image restartImage = restartIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JButton restart = new JButton(new ImageIcon(restartImage));
        restart.setPreferredSize(new Dimension(55, 55));
        restart.addActionListener(event -> {
            if (active)
                playBoard.reset();
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.weightx = 10;
        constraints.weighty = 10;
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(pause, constraints);
        constraints.gridx = 3;
        constraints.gridy = 1;
        add(play, constraints);
        constraints.gridx = 4;
        constraints.gridy = 1;
        add(restart, constraints);
    }

    /**
     * Metoda ustawiajaca tlo panelu.
     * @param gn
     */
    public void paintComponent(Graphics gn) {
        super.paintComponent(gn);
        Graphics2D g = (Graphics2D) gn;
        setBackground(Color.DARK_GRAY);

        Image background = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("ar.png"))).getImage();
        g.drawImage(background, 0, 0, null);
    }

    /**
     * Metoda zwracająca preferowany rozmiar Panelu.
     * @return Obiekt klasy Dimension z preferowana wysokoscia i szerokoscia.
     */
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Metoda zmeiniająca stan przycisków. Aktywne / Nieaktywne.
     * @param active Wartosc logiczna true / false.
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}

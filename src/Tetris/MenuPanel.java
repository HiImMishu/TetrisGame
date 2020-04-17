package Tetris;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Klasa tworzaca Panel Main Menu
 */
public class MenuPanel extends JPanel {
    private int DEFAULT_WIDTH = 500;
    private int DEFAULT_HEIGHT = 600;
    private boolean menuVisibility = true;
    private AutoFall autoFall;
    private PlayBoard playBoard;
    private GameManager gameManager;

    /**
     * Konstruktor Klasy MenuPanel
     * Tworzy trzy przyciski pozwalajace na interakcje z menu gry.
     * @param playBoard plansza gry.
     * @param autoFall Obiekt klasy umozliwiajacy autoopadanie elemenu.
     * @param highScoreView Obiekt widoku rankingu wynikow.
     */
    MenuPanel(PlayBoard playBoard, AutoFall autoFall, HighScoreView highScoreView) {
        this.autoFall = autoFall;
        this.playBoard = playBoard;

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 20, 0);

        JButton play = new JButton("PLAY");
        play.setPreferredSize(new Dimension(180, 50));
        play.setFont(new Font("SansSerif", Font.ITALIC + Font.BOLD, 16));
        play.addActionListener(event -> {
            setMenuVisibility();
        });

        JButton highScores = new JButton("HIGH SCORES");
        highScores.setPreferredSize(new Dimension(180, 50));
        highScores.setFont(new Font("SansSerif", Font.ITALIC + Font.BOLD, 16));
        highScores.addActionListener(event -> {
            highScoreView.setVisible(true);
        });

        JButton quit = new JButton("EXIT");
        quit.setPreferredSize(new Dimension(180, 50));
        quit.setFont(new Font("SansSerif", Font.ITALIC + Font.BOLD, 16));
        quit.addActionListener(event -> {
            gameManager.closeGame();
        });

        add(play, gbc);
        add(highScores, gbc);
        add(quit, gbc);
    }

    /**
     * Wczytanie obrazu tla menu.
     * @param g Obiekt klasy Graphic
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Image background = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("menubg.png"))).getImage();
        g2.drawImage(background, 0, 0, null);
    }

    /**
     * Zwraca preferowany rozmiar.
     * @return Obiekt klasy Dimension.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Umozliwia wlaczenie / wylaczenie widocznosci menu.
     */
    public void setMenuVisibility() {
        menuVisibility = !menuVisibility;
        if (menuVisibility) {
            autoFall.pause();
            playBoard.pause();
        } else {
            autoFall.play();
            playBoard.play();
        }
        setVisible(menuVisibility);
    }

    /**
     * Ustawia GameManager.
     * @param gameManager obiekt klasy GameManager.
     */
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

}

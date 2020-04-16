package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

public class GameOverView extends JComponent {
    private ScoreSystem scoreSystem;

    GameOverView(GameManager gameManager, ScoreSystem scoreSystem) {
        this.scoreSystem = scoreSystem;

        setVisible(false);
        setBackground(new Color(51, 51, 51, 200));
        JButton menu = new JButton("MAIN MENU");
        menu.setPreferredSize(new Dimension(180, 50));
        menu.setFont(new Font("SansSerif", Font.ITALIC + Font.BOLD, 16));
        menu.addActionListener(event -> {
            gameManager.resume();
            setVisible(false);
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(100, 0, 0, 0);
        add(menu, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(51, 51, 51, 200));
        g2.fillRect(0, 0, 500, 600);
        Image gameOver = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("gameOver.png"))).getImage();
        g2.drawImage(gameOver, 0, 0, null);
        g2.setColor(Color.WHITE);
        Font sasnBold28 = new Font("SansSerif", Font.ITALIC + Font.BOLD, 28);
        FontRenderContext context = g2.getFontRenderContext();
        String score = "Your Ranking Position: " + DbConnection.getRankingPosition(scoreSystem.getScore());
        Rectangle2D bounds = sasnBold28.getStringBounds(score, context);
        g2.setFont(sasnBold28);
        g2.drawString(score, (int) (250 - bounds.getWidth() / 2), 250);
    }
}

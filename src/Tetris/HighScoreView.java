package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class HighScoreView extends JPanel {
    HighScoreView() {
        setBackground(new Color(51,51,51));
        setVisible(false);

        JButton back = new JButton("BACK TO MENU");
        back.setPreferredSize(new Dimension(180, 50));
        back.addActionListener(event -> {
            setVisible(false);
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(400, 0,0,0);

        add(back, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int[] top = DbConnection.getTopRanging();

        g2.setColor(Color.WHITE);
        Font sasnBold22 = new Font("SansSerif", Font.BOLD, 22);
        Font sasnBold30 = new Font("SansSerif", Font.ITALIC + Font.BOLD, 30);
        g2.setFont(sasnBold30);
        FontRenderContext context = g2.getFontRenderContext();
        String title = "TOP RANK";
        Rectangle2D bounds = sasnBold30.getStringBounds(title, context);
        g2.drawString(title, (int)(250-bounds.getWidth()/2), 70);
        g2.setFont(sasnBold22);

        for(int i=1; i<=10; i++) {
            g2.drawString(""+i+":", 170, 32*i+100);
            g2.drawString(""+top[i-1], 270, 32*i+100);
        }
    }
}

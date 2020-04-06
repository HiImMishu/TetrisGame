package Tetris;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR;

public class ControlsView extends JPanel {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 200;
    private JButton pause;
    private JButton play;
    private JButton restart;

    ControlsView()
    {
        ImageIcon playIcon;
        try{
            BufferedImage image = ImageIO.read(new File("C:\\Studia\\Sem4\\JiTP\\PROJEKT\\assets\\play.png"));
            image = getScaledInstance(image, 50, 50, VALUE_INTERPOLATION_BILINEAR, true);
            playIcon = new ImageIcon(image);
            this.pause = new JButton(playIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.play = new JButton("PLA");
        this.restart = new JButton("RES");
        //pause.setBackground(Color.BLACK);
        pause.setPreferredSize(new Dimension(50, 50));

        this.setLayout(new GridBagLayout());
        add(pause, new GridBagConstraints());
        add(play, new GridBagConstraints());
        add(restart, new GridBagConstraints());
    }

    public void paintComponent(Graphics gn)
    {
        super.paintComponent(gn);
        Graphics2D g = (Graphics2D) gn;
        setBackground(Color.PINK);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public BufferedImage getScaledInstance(BufferedImage img,
                                           int targetWidth,
                                           int targetHeight,
                                           Object hint,
                                           boolean higherQuality)
    {
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
                BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage)img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }


}

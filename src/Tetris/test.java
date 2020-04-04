package Tetris;

import javax.swing.*;
import java.awt.*;

public class test {
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
            new GameManager();
        });
    }
}

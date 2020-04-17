package Tetris;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 *Klasa<code>AutoFall</code> tworzona jest w osobnym watku, umozliwia ona autoopadanie klocka, zmiane tempa oraz zatrzymanie opadania.
 */

public class AutoFall extends Thread {
    private int interval;
    private boolean state;
    private Timer timer;
    private PlayBoard playBoard;
    private ScoreSystem scoreSystem;
    private int checkPoint = 5;

    /**
     * Kontruktor Klasy <code>AutoFall</code>
     * <code>interval</code> ustawiony jest na jedną sekundę.
     * <code>state</code> czyli stan opadania ustawiony jest na false, powoduje to wstrzymanie opadania od razu po utworzeniu obiektu.
     * @param playBoard Obiekt klasy pola na ktorym odbywa się rozgrywka.
     * @param scoreSystem Obiekt klasy systemu Punkto   w zarządzającego wynikiem.
     */
    AutoFall(PlayBoard playBoard, ScoreSystem scoreSystem) {
        this.playBoard = playBoard;
        this.scoreSystem = scoreSystem;
        this.interval = 1000;
        this.state = false;
    }

    /**
     * Obiekt Klasy <code>ActionListener</code> wyzwalany obiektem klasy <code>Timer</code>
     * Jesli zmienna <code>state</code> jest ustawiona na true, wyzwalana jest metoda <code>moveDown</code> obiektu <code>playBoard</code>
     * Jesli aktualny lvl osiagnał kolejny <code>checkPoint</code> czas opdania przyspiesza o 50 milisekund.
     */
    ActionListener listener = event -> {
        if (state) {
            if (scoreSystem.getLvl() >= checkPoint) {
                checkPoint += 5;
                if (interval > 100)
                    interval -= 50;
                timer.setDelay(interval);
            }
            playBoard.moveDown();
        }
    };

    /**
     * Nadpisana metoda <code>run</code> tworzaca nowy obiekt Klasy <code>Timer</code> z interwalem czasowym <code>interval</code> i obiektem Klasy <code>ActionListener</code>
     */
    @Override
    public void run() {
        this.timer = new Timer(interval, listener);
        timer.start();
    }

    /**
     * Publiczna metoda <code>play</code> zmieniajaca wartosc <code>state</code> na true, co powoduje automatyczne opadanie elementu.
     */
    public void play() {
        state = true;
    }

    /**
     * Publiczna metoda <code>pause</code> zmieniajaca wartosc <code>state</code> na false, co powoduje zatrzymanie automatycznego opadania elementu.
     */
    public void pause() {
        state = false;
    }

    /**
     * Publiczna metoda <code>setInterval</code> ustawiąjaca interwal czasowy pomiedzy opadaniem elementu.
     * @param interval Ilosc czasu w milisekundach ustawiana jako nowy interwał czasu.
     */
    public void setInterval(int interval) {
        this.interval = interval;
        timer.setDelay(interval);
    }
}

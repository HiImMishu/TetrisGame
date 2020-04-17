package Tetris;

/**
 * Klasa zarzadzajaca wynikami gry uzyskanymi przez gracza.
 */
public class ScoreSystem {
    private int highScore;
    private int score;
    private int lvl;
    private ScoreView scoreView;
    private int lvlCheckpoint = 100;

    /**
     * Konstruktor klasy.
     * Inicjuje wynik oraz najwyzszy wynik na wartosc 0
     * Inicjuje lvl na wartosc 1
     */
    ScoreSystem() {
        highScore = 0;
        score = 0;
        lvl = 1;
    }

    /**
     * Metoda zwracajaza najwyszy wynik uzyskany przez gracza.
     * @return Liczba calkowita - ajwyzszy wynik.
     */
    public int getHighScore() {
        return this.highScore;
    }

    /**
     * Ustawia najwyzszy wynik.
     * @param highScore zmienna ktora zostanie przypisana najwyzszemu wynikowi jesli poprzedni nie byl wiekszy.
     */
    public void setHighScore(int highScore) {
        if (this.highScore < highScore)
            this.highScore = highScore;
    }

    /**
     * Metoda zwracajaca aktualny wynik
     * @return Liczba calkowita - aktualny wynik
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Metoda ustawiajaca aktualny wynik osiagniety przez gracza
     * Jesli wynik osiagnal kolejny punkt kontrolny, gra osiaga kolejny poziom.
     * @param score wartosc dodawana do aktualnego wyniku.
     */
    public void setScore(int score) {
        this.score += score;
        if (this.score >= lvlCheckpoint) {
            setLvl(1);
            lvlCheckpoint += 100;
        }
        setHighScore(this.score);
        this.scoreView.repaint();
    }

    /**
     * Zwraca aktualny poziom gry
     * @return poziom gry
     */
    public int getLvl() {
        return lvl;
    }

    /**
     * Modyfikuje poziom gry
     * @param lvl wartosc dodawana do aktualnego poziomu.
     */
    public void setLvl(int lvl) {
        this.lvl += lvl;
    }

    /**
     * Resetowanie gry.
     * Przypisanie wartosci poczatkowych poziomowi oraz wynikowi.
     */
    public void reset() {
        this.lvl = 1;
        this.score = 0;
        this.scoreView.repaint();
    }

    /**
     * Pozwala na ustalenie panelu widoku wynikow.
     * @param scoreView Obikt klasy ScoreView
     */
    public void setScoreView(ScoreView scoreView) {
        this.scoreView = scoreView;
    }
}

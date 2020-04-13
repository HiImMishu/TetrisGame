package Tetris;

public class ScoreSystem {
    private int highScore;
    private int score;
    private int lvl;
    private ScoreView scoreView;
    private int lvlCheckpoint = 100;
    private boolean highScoreChanged = true;

    ScoreSystem() {
        highScore = 0;
        score = 0;
        lvl = 1;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public void setHighScore(int highScore) {
        if(this.highScore < highScore)
        this.highScore = highScore;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score += score;
        if (this.score >= lvlCheckpoint) {
            setLvl(1);
            lvlCheckpoint += 100;
        }
        setHighScore(this.score);
        this.scoreView.repaint();
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl += lvl;
    }

    public void reset() {
        this.lvl = 1;
        this.score = 0;
        this.scoreView.repaint();
    }

    public void setScoreView(ScoreView scoreView) {
        this.scoreView = scoreView;
    }
}

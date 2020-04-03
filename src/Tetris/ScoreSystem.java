package Tetris;

public class ScoreSystem
{
    private int highScore;
    private int score;

    ScoreSystem() {}

    public int getHighScore()
    {
        return this.highScore;
    }

    public void setHighScore(int highScore)
    {
        this.highScore = highScore;
    }

    public int getScore()
    {
        return this.score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
}

package bowling.domain;

public class Score {

  private int score;
  private int left;

  public Score(int score, int left) {
    this.score = score;
    this.left = left;
  }

  public int getScore() {
    return this.score;
  }

  public boolean canCalculateScore() {
    return left == 0;
  }

  public void bowl(int countOfPins) {
    this.score += countOfPins;
    this.left -= 1;
  }

  public static Score of(Frame frame) {
    ScoreSymbol symbol = frame.symbol();
    if (symbol == ScoreSymbol.STRIKE) {
      return new Score(10, 2);
    }
    if (symbol == ScoreSymbol.SPARE) {
      return new Score(10, 1);
    }
    return new Score(frame.pins.totalHitPin(), 0);
  }
}

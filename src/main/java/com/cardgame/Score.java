package com.cardgame;

public class Score {
  private final Integer playerId;
  private final Integer score;

  public Score(int playerId, int score) {
    this.playerId = playerId;
    this.score = score;
  }

  public int getScore() {
    return score;
  }

  public Integer getPlayerId() {
    return playerId;
  }
}

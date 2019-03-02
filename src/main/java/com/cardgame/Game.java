package com.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Game {
  private final int id;
  private Shoe shoe = new Shoe();
  private Map<Integer, Player> playersById = new ConcurrentHashMap();

  public Game(int id) {
    this.id = id;
  }

  public void addDeck(Deck deck) {
    shoe.addDeck(deck);
  }

  public void addPlayer(Player player) {
    playersById.put(player.getId(), player);
  }

  public Player getPlayer(int id) {
    return playersById.get(id);
  }

  public int dealCardsToPlayer(int playerId, int cardsAmount) {
    Player player = playersById.get(playerId);
    int dealtCardsAmount = 0;
    if (player != null) {
      List<Card> dealtCards = shoe.nextCards(cardsAmount);
      dealtCardsAmount = dealtCards.size();
      player.dealCards(dealtCards);
      return dealtCardsAmount;
    }
    throw new RuntimeException("player with id " + playerId + " doesn't belong to game " + id);
  }

  public Shoe getShoe() {
    return shoe;
  }

  public List<Score> scores() {

    List<Score> listScore = new ArrayList<Score>();
    for (Integer playerId : playersById.keySet()) {
      Player player = playersById.get(playerId);
      Score score = new Score(playerId, CardsUtil.totalScore(player.getCards()));
      listScore.add(score);
    }
    Collections.sort(listScore, (s1, s2) -> s2.getScore() - s1.getScore());
    return listScore;
  }

  public void shuffleShoe() {
    shoe.shuffle();
  }
}

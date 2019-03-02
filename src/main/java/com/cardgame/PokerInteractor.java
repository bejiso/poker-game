package com.cardgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PokerInteractor {
  // ids providers
  private final AtomicInteger gameIdProvider = new AtomicInteger();
  private final AtomicInteger deckIdProvider = new AtomicInteger();
  private final AtomicInteger playerIdProvider = new AtomicInteger();

  /*
     games store
  */
  private final Map<Integer, Game> gamesById = new ConcurrentHashMap<Integer, Game>();
  /*
     decks store
  */
  private final Map<Integer, Deck> decks = new ConcurrentHashMap<Integer, Deck>();

  public List<Score> gameScores(int gameId) {
    Game game = gamesById.get(gameId);
    if (game != null) {
      return game.scores();
    }
    throw new RuntimeException("game with id : " + gameId + " doesn't exist");
  }

  public int createGame() {
    int newGameId = gameIdProvider.getAndIncrement();
    gamesById.put(newGameId, new Game(newGameId));
    return newGameId;
  }

  public void deleteGame(int gameId) {
    gamesById.remove(gameId);
  }

  public int createDeck() {
    int newDeckId = deckIdProvider.getAndIncrement();
    decks.put(newDeckId, new Deck(newDeckId));
    return newDeckId;
  }

  public void addDeckToGame(int deckId, int gameId) {
    Game game = gamesById.get(gameId);

    if (game != null) {
      Deck deck = decks.get(deckId);
      if (deck != null) {
        game.addDeck(deck);
      } else {
        throw new RuntimeException("deck with id " + deckId + " doesn't exist");
      }
    } else {
      throw new RuntimeException("game with id " + gameId + " doesn't exist");
    }
  }

  public int addPlayerToGame(int gameId) {
    Game game = gamesById.get(gameId);
    if (game != null) {
      int newPlayerId = playerIdProvider.getAndIncrement();
      game.addPlayer(new Player(newPlayerId));
      return newPlayerId;
    }
    throw new RuntimeException("game with id " + gameId + " doesn't exist");
  }

  public int dealCardsToPlayer(int gameId, int playerId, int cardAmounts) {
    Game game = gamesById.get(gameId);
    if (game != null) {
      Player player = game.getPlayer(playerId);
      return game.dealCardsToPlayer(playerId, cardAmounts);
    } else {
      throw new RuntimeException("game with id " + gameId + " doesn't exist");
    }
  }

  public List<Card> playerCards(int gameId, int playerId) {
    Game game = gamesById.get(gameId);
    if (game != null) {
      Player player = game.getPlayer(playerId);
      return player.getCards();
    }
    throw new RuntimeException("player or game doesnt exist");
  }

  public List<SuitCount> undealtCardsBySuits(int gameId) {
    Game game = gamesById.get(gameId);
    if (game != null) {
      Shoe shoe = game.getShoe();
      return shoe.getUndealtCardBySuit();
    }
    throw new RuntimeException("game with id " + gameId + " doesn't exist");
  }

  public List<Card> undealtCards(int gameId) {
    Game game = gamesById.get(gameId);
    if (game != null) {
      Shoe shoe = game.getShoe();
      return shoe.getUndealtCard();
    }
    throw new RuntimeException("game with id " + gameId + " doesn't exist");
  }

  public void shuffle(int gameId) {
    Game game = gamesById.get(gameId);
    if (game != null) {
      game.shuffleShoe();
    } else {
      throw new RuntimeException("game with id " + gameId + " doesn't exist");
    }
  }
}

package com.cardgame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class GamePlayIntegrationTest {

  @Autowired private WebTestClient webClient;

  @Test
  public void gameCreationAndDeletionTest() throws Exception {
    createGame();
    String game2Id = createGame();
    deleteGameWithId(game2Id);
    accessDeletedGameScores(game2Id);
  }

  /** play test that involves no shuffle which will allow us to know the expected dealt cards */
  @Test
  public void gameDeterministicPlayTest() throws Exception {
    String gameId = createGame();
    assertFalse("the id must have some value", gameId.isEmpty());

    String deckId = createDeck();
    assertFalse("the id must have some value", deckId.isEmpty());

    addDeckToGame(gameId, deckId);

    String player1Id = addPlayerToGame(gameId);
    assertFalse("the id must have some value", player1Id.isEmpty());
    String player2Id = addPlayerToGame(gameId);
    assertFalse("the id must have some value", player2Id.isEmpty());

    assertTrue("ids of players should be different", player1Id != player2Id);

    dealCardsToPlayer(gameId, player1Id, 3);
    getCardsOfPlayer(
        gameId,
        player1Id,
        "[{\"suit\":\"HEARTS\",\"faceValue\":\"ACE\",\"value\":1},"
            + "{\"suit\":\"HEARTS\",\"faceValue\":\"TWO\",\"value\":2},"
            + "{\"suit\":\"HEARTS\",\"faceValue\":\"THREE\",\"value\":3}]");

    dealCardsToPlayer(gameId, player2Id, 3);
    getCardsOfPlayer(
        gameId,
        player2Id,
        "[{\"suit\":\"HEARTS\",\"faceValue\":\"FOUR\",\"value\":4},"
            + "{\"suit\":\"HEARTS\",\"faceValue\":\"FIVE\",\"value\":5},"
            + "{\"suit\":\"HEARTS\",\"faceValue\":\"SIX\",\"value\":6}]");

    getGameScores(gameId, "[{\"playerId\":1,\"score\":15}," + "{\"playerId\":0,\"score\":6}]");
  }

  private void getGameScores(String gameId, String expectedScores) {
    WebTestClient.ResponseSpec responseSpec =
        webClient.get().uri("/game/" + gameId + "/scores").exchange().expectStatus().isEqualTo(200);
    assertEquals(expectedScores, respToString(responseSpec));
  }

  private void getCardsOfPlayer(String gameId, String playerId, String expectedResponse) {
    WebTestClient.ResponseSpec responseSpec =
        webClient
            .get()
            .uri("/game/" + gameId + "/player/" + playerId + "/cards")
            .exchange()
            .expectStatus()
            .isEqualTo(200);
    assertEquals(expectedResponse, respToString(responseSpec));
  }

  private void dealCardsToPlayer(String gameId, String playerId, int cardsAmount) {
    webClient
        .put()
        .uri("/game/" + gameId + "/player/" + playerId + "/deal?cards-amount=" + cardsAmount)
        .exchange()
        .expectStatus()
        .isEqualTo(200);
  }

  private String addPlayerToGame(String gameId) {
    WebTestClient.ResponseSpec responseSpec =
        webClient.put().uri("/game/" + gameId + "/player").exchange().expectStatus().isEqualTo(200);
    return respToString(responseSpec);
  }

  private String respToString(WebTestClient.ResponseSpec responseSpec) {
    return new String(
        responseSpec.expectBody().returnResult().getResponseBody(), StandardCharsets.UTF_8);
  }

  private void addDeckToGame(String gameId, String deckId) {
    webClient
        .put()
        .uri("/game/" + gameId + "/deck/" + deckId)
        .exchange()
        .expectStatus()
        .isEqualTo(200);
  }

  private String createDeck() throws Exception {
    WebTestClient.ResponseSpec responseSpec =
        webClient.put().uri("/deck").exchange().expectStatus().isEqualTo(200);
    return respToString(responseSpec);
  }

  private void accessDeletedGameScores(String gameId) throws Exception {
    webClient
        .get()
        .uri("/game/" + gameId + "/scores")
        .exchange()
        .expectStatus()
        .isEqualTo(500)
        .expectBody()
        .jsonPath("message")
        .isEqualTo("game with id : " + gameId + " doesn't exist");
  }

  private void deleteGameWithId(String gameId) throws Exception {
    webClient.delete().uri("/game/" + gameId).exchange().expectStatus().isEqualTo(200);
  }

  private String createGame() throws Exception {
    WebTestClient.ResponseSpec responseSpec =
        webClient.put().uri("/game").exchange().expectStatus().isEqualTo(200);
    return respToString(responseSpec);
  }
}

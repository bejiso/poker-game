package com.cardgame;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class PokerService {

    private final PokerInteractor pokerInteractor=new PokerInteractor();

    @PutMapping("/game")
    public int createGame(){
        return pokerInteractor.createGame();
    }

    @DeleteMapping("/game/{gameId}")
    public void deleteGame(@PathVariable("gameId") int gameId){
        pokerInteractor.deleteGame(gameId);
    }

    @PutMapping("/deck")
    public int createDeck(){
        return pokerInteractor.createDeck();
    }


    @PutMapping("/game/{gameId}/deck/{deckId}")
    public void addDeckToGame(@PathVariable("deckId")int deckId,
                              @PathVariable("gameId") int gameId){
        pokerInteractor.addDeckToGame(deckId,gameId);
    }

    @PutMapping("/game/{gameId}/player")
    public int addPlayerToGame(@PathVariable("gameId") int gameId){
        return pokerInteractor.addPlayerToGame(gameId);
    }

     @PutMapping("/game/{gameId}/player/{playerId}/deal")
     public int dealCards(@PathVariable("gameId") int gameId,
                          @PathVariable("playerId")int playerId,
                          @RequestParam("cards-amount") int amountsCard ){
        return pokerInteractor.dealCardsToPlayer(gameId,playerId,amountsCard);
     }

    @GetMapping("game/{gameId}/playerId/{playerId}/cards")
    public List<Card> playerCards(@PathVariable("gameId")int gameId,
                                  @PathVariable("playerId") int playerId){
        return pokerInteractor.playerCards(gameId, playerId);
    }

    @GetMapping("/game/{gameId}/scores")
    public List<Score> gameScores(@PathVariable("gameId") int gameId){
        return pokerInteractor.gameScores(gameId);
    }

    @GetMapping("/game/{gameId}/undealtcardsbysuits")
    public Object undealtCardsBySuits(@PathVariable("gameId") int gameId){
        return null;  // TODO implement this method
    }

    @GetMapping("/game/{gameId}/undealtcards")
    public Object undealtCards(@PathVariable("gameId") int gameId){
        return null; //TODO implement this method
    }

    @GetMapping("/game/{gameId}/shuffle")
    public void shuffle(int gameId){
        pokerInteractor.shuffle(gameId);
    }
}

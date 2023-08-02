package ooga.model.games;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import ooga.Config;
import ooga.model.board.GameBoard;
import ooga.model.board.UnoGameBoard;
import ooga.model.cardcollection.CardCollection;
import ooga.model.cardcollection.DeckCardCollection;
import ooga.model.cardcollection.deck.DrawDeck;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;
import ooga.model.exceptions.InvalidNumberOfCardsToStartException;
import ooga.model.exceptions.InvalidNumberOfPlayersException;
import ooga.model.exceptions.NoCardDeckException;
import ooga.model.games.executors.Executor;
import ooga.model.players.GamePlayer;
import ooga.model.players.Player;

/**
 * Abstract class implementing the CardGame interface, allows players to draw cards and play cards
 * and make interactions with the game board that contains different card collections in the game
 *
 * @author Alicia Wu
 */
public abstract class Game implements CardGame {

  private List<Player> players;
  private int currPlayerId;
  private int nextPlayerId;
  private GameBoard board;
  private boolean clkWise;
  private ResourceBundle myResources;

  /**
   * creates a new instance of Game This is specifically used for creating a game that has not been
   * started yet, i.e. there are no players yet and the playDeck is empty
   *
   * @param numPlayers          number of players in this game
   * @param numCardsToBeginWith number of cards for each player to begin with in this game
   * @param allCardsInfo        information on all the cards in this game
   */
  public Game(int numPlayers, int numCardsToBeginWith,
      Map<CardColor, List<CardInfo[]>> allCardsInfo) {
    myResources = ResourceBundle.getBundle(Config.PROPERTIES_FILES_PATH + Config.ENGLISH_EXTENSION);
    setUpGameBoard(allCardsInfo);
    initPlayers(numPlayers);
    makeAllPlayersDrawCards(numCardsToBeginWith);
  }

  /**
   * Create a new Game, with the given drawDeck, playDeck, and players. This will be used to create
   * a Game based on a paused game that has been saved
   *
   * @param drawDeck   will be the draw deck on the board
   * @param playDeck   will be the play deck on the board
   * @param allPlayers is a list of all the players in the game
   */
  public Game(DeckCardCollection drawDeck, DeckCardCollection playDeck, List<Player> allPlayers) {
    board = new UnoGameBoard(drawDeck, playDeck);
    players = allPlayers;
  }

  private void setUpGameBoard(Map<CardColor, List<CardInfo[]>> allCardsInfo) {
    if (allCardsInfo == null || allCardsInfo.isEmpty()) {
      throw new NoCardDeckException(
          myResources.getString(NoCardDeckException.class.getSimpleName()));
    }
    board = new UnoGameBoard(new DrawDeck(allCardsInfo));
    board.shuffleDrawDeck();
  }

  private void initPlayers(int numPlayers) {
    if (numPlayers < Config.MIN_NUM_PLAYERS) {
      throw new InvalidNumberOfPlayersException(
          myResources.getString(InvalidNumberOfPlayersException.class.getSimpleName()));
    }
    players = new ArrayList<>();
    for (int i = 0; i < numPlayers; i++) {
      players.add(new GamePlayer(i));
    }
    clkWise = true;
    currPlayerId = 0; // new Random().nextInt(players.size());
    nextPlayerId = getNextPlayerId(currPlayerId);
  }

  private void makeAllPlayersDrawCards(int numCardsToBeginWith) {
    if (numCardsToBeginWith < 0) {
      throw new InvalidNumberOfCardsToStartException(
          myResources.getString(InvalidNumberOfCardsToStartException.class.getSimpleName()));
    }
    players.forEach(player -> {
      playerDrawCards(player.getPlayerId(), numCardsToBeginWith);
      player.getPlayerHand().reorganize();
    });
  }

  /**
   * finds the cards in the current player's hand that can be played
   *
   * @return collection of cards that can be played
   */
  public Collection<Card> findCurrPlayerValidCardsToPlay() {
    return getCurrPlayer().findValidCardsToPlay(board.peekPlayDeckTopCard());
  }

  /**
   * adds the given cards to the player's hand of cards
   *
   * @param playerId player's id number
   * @param cards    list of cards to be added to the player's hand
   */
  public void addCardsToPlayerHand(int playerId, List<Card> cards) {
    players.get(playerId).addAllToHand(cards);
  }

  /**
   * current player draws a card
   *
   * @return card that was drawn
   */
  public Card currPlayerDrawCard() {
    CardCollection playerHand = getCurrPlayer().getPlayerHand();
    Card cardTransferred = board.transferCardFromDrawDeckTo(playerHand);
    playerHand.reorganize();
    return cardTransferred;
  }

  /**
   * player of the given player id will draw a given number of cards
   *
   * @param playerId       player id of player to draw cards
   * @param numCardsToDraw number of cards the player should draw
   */
  public void playerDrawCards(int playerId, int numCardsToDraw) {
    for (int i = 0; i < numCardsToDraw; i++) {
      Card card = board.getFromDrawDeck();
      players.get(playerId).addToHand(card);
    }
  }

  /**
   * current player will play the given card
   *
   * @param cardToPlay card the current player will play
   */
  public void currPlayerPlayCard(Card cardToPlay) {
    getCurrPlayer().playCard(cardToPlay);
    board.addToPlayDeck(cardToPlay);
  }

  /**
   * ends the current player's turn
   */
  public void finishTurn() {
    currPlayerId = nextPlayerId;
    updateNextPlayerId();
  }

  protected void updateNextPlayerId() {
    nextPlayerId = getNextPlayerId(currPlayerId);
  }

  /**
   * sets the next player
   *
   * @param playerId player id of next player
   */
  public void setNextPlayer(int playerId) {
    nextPlayerId = playerId;
  }

  /**
   * gets the next player's id number given the previous player's id number
   *
   * @param currId current player's id number
   * @return next player's id number
   */
  public int getNextPlayerId(int currId) {
    int nextId = clkWise ? currId + 1 : currId - 1;
    if (nextId >= players.size()) {
      return 0;
    } else if (nextId < 0) {
      return players.size() - 1;
    }
    return nextId;
  }

  /**
   * get the next player's id number based on the current player
   *
   * @return next player's id number
   */
  public int getNextPlayerId() {
    return nextPlayerId;
  }

  /**
   * get the current player's id number
   *
   * @return current player's id number
   */
  public int getCurrPlayerId() {
    return currPlayerId;
  }

  /**
   * get the current player
   *
   * @return Player instance representing the current player
   */
  public Player getCurrPlayer() {
    return players.get(currPlayerId);
  }

  /**
   * gets the Player instance of a given player id number
   *
   * @param playerId player id of the player to return
   * @return Player instance with the given player id
   */
  public Player getPlayerById(int playerId) {
    return players.get(playerId);
  }

  /**
   * reverses the direction of gameplay
   */
  public void reverseDirection() {
    clkWise = !clkWise;
    updateNextPlayerId();
  }

  /**
   * gets a reference to the GameBoard instance in this game
   *
   * @return game board of this game
   */
  public GameBoard getGameBoard() {
    return board;
  }

  /**
   * gets all the players
   *
   * @return unmodifiable list of all players
   */
  public List<Player> getPlayers() {
    return Collections.unmodifiableList(players);
  }

  /**
   * gets the number of players
   *
   * @return number of players in this game
   */
  public int getNumPlayers() {
    return players.size();
  }

  /**
   * checks if the game is over
   *
   * @return true if a player in the game has no more cards
   */
  public boolean isGameOver() {
    for (Player player : players) {
      if (player.getAllCards().isEmpty()) {
        return true;
      }
    }
    return false;
  }

  /**
   * execute an action that will affect gameplay and/or other players
   *
   * @param executorParam int parameter for the action
   * @param executor      Executor to execute the action
   */
  public void executeAction(int executorParam, Executor executor) {
    executor.execute(this, executorParam);
  }
}

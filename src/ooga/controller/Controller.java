package ooga.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.stage.Stage;
import ooga.Config;
import ooga.model.cardcollection.CardCollection;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;
import ooga.model.exceptions.InvalidActionTypeException;
import ooga.model.exceptions.NoPossibleCardsToDrawException;
import ooga.model.games.CardGame;
import ooga.model.games.PirateGame;
import ooga.model.games.executors.Executor;
import ooga.view.View;
import ooga.view.display.Display;
import ooga.view.display.GameDisplay;
import ooga.view.resources.Resources;
import ooga.view.userAlert.UserAlert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is the Controller to deal with the flow of information to/from the frontend/backend
 *
 * @author Ryleigh Byrne, Alicia Wu
 */
public class Controller implements PropertyChangeListener {

  private CardGame myGame;
  private View myView;
  private Stage myStage;
  private int WIDTH;
  private int HEIGHT;
  private DataController dataController;
  private DisplayController displayController;
  private int numCardsToDrawNext;
  private PropertyChangeEvent event;
  private Logger logger = LogManager.getLogger(Controller.class);
  private ResourceBundle myResources;
  private GameDisplay gameDisplay;
  private String filePath;
  private String backOfCardFilePath;

  /**
   * creates a new instance of Controller, initializes the stage and game dimensions
   *
   * @param stage          JavaFX stage instance to display
   * @param gameDimensions game display dimensions
   */
  public Controller(Stage stage, int[] gameDimensions) {
    myResources = ResourceBundle.getBundle(Config.PROPERTIES_FILES_PATH + Config.ENGLISH_EXTENSION);
    myStage = stage;
    WIDTH = gameDimensions[1];
    HEIGHT = gameDimensions[0];
    dataController = new DataController();
  }

  /**
   * creates the initial game view
   */
  public void generateView() {
    myView = new View();
    myStage.setScene(myView.makeScene(WIDTH, HEIGHT));
    myStage.show();
    displayController = new DisplayController(this);
    addObservers();
  }

  /**
   * sets the given CardGame instance as the game to play
   *
   * @param game game instance to play
   */
  public void initializeNewGame(CardGame game) {
    myGame = game;
  }

  /**
   * parses all players' hands of cards in the model and communicates them to the view
   */
  public void initializePlayerHandsAtStartOfGame() {
    displayController.addGameDisplayObservers((Object[]) event.getNewValue());
    displayController.setPathToBackOfCard(backOfCardFilePath);
    gameDisplay.setBackOfCardFilePath(backOfCardFilePath);
    displayController.parseAllPlayersHandsForView(myGame.getPlayers());
    gameDisplay.createPlayerHands();
    updateValidCardsInView(myGame.findCurrPlayerValidCardsToPlay().stream().toList());
    displayController.updatePlayDeck(myGame.getGameBoard().peekPlayDeckTopCard());
    startFirstPlayerTurn();
  }

  private void startFirstPlayerTurn() {
    updateCurrentPlayer();
    if (myGame.getCurrPlayerId() != 0) {
      playOutTurnsByCPUPlayers();
    } else {
      findValidRealPlayerCards();
    }
  }

  private void updateCurrentPlayer() {
    displayController.updateCurrentPlayerId(myGame.getCurrPlayerId());
  }

  /**
   * end the current player's turn
   */
  public void endTurn() {
    if (myGame.isGameOver()) {
      myView.gameIsOver(myGame.getCurrPlayerId());
    }
    myGame.finishTurn(); // signal to game to finish turn
    updateCurrentPlayer();
  }

  private void playOutTurnsByCPUPlayers() {
    while (myGame.getCurrPlayerId() != 0
        && !myGame.isGameOver()) {
      if (checkIfValidCardsToPlay()) {
        playRandomValidCard(myGame.findCurrPlayerValidCardsToPlay().stream().toList());
      } else {
        myGame.currPlayerDrawCard();
        if (checkIfValidCardsToPlay()) {
          playRandomValidCard(myGame.findCurrPlayerValidCardsToPlay().stream().toList());
        }
        endTurn();
      }
    }
    findValidRealPlayerCards();
  }

  // find cards to play & signal to front end
  private void findValidRealPlayerCards() {
    // user is only allowed to select valid cards
    updateValidCardsInView(myGame.findCurrPlayerValidCardsToPlay().stream().toList());
    if (checkIfValidCardsToPlay()) {
      updateValidCardsInView(myGame.findCurrPlayerValidCardsToPlay().stream().toList());
    } else {
      drawCard();
    }
  }

  /**
   * get the valid cards to play from the model and communicate them to the view
   *
   * @param validCardsToPlay
   */
  public void updateValidCardsInView(List<Card> validCardsToPlay) {
    CardCollection currPlayerHand = myGame.getCurrPlayer().getPlayerHand();
    List<Integer> validCardsLocations = currPlayerHand.findCardsOrderingInCollection(
        validCardsToPlay);
    displayController.updateValidCardsInPlayerHand(validCardsLocations);
  }

  private void endRealPlayerTurn() {
    playOutTurnsByCPUPlayers();
  }

  /**
   * play a card for a CPU
   */
  public void playRandomValidCard(List<Card> validCardsToPlay) {
    System.out.format(Resources.getKey(Config.CURRENT_PLAYER_ID), myGame.getCurrPlayerId());
    int cardToPlayIndex = new Random().nextInt(validCardsToPlay.size());
    Card cardToPlay = validCardsToPlay.get(cardToPlayIndex);
    printCardPlayedByCPU(cardToPlay);
    playCard(cardToPlay);
    endTurn();
  }

  private void printCardPlayedByCPU(Card card) {
    System.out.format(Resources.getKey(Config.CARD_PLAYED_CPU), card.getCardColor());
    for (CardInfo info : card.getCardInfo()) {
      System.out.format(Config.TWO_STRING_FORMAT, info.getType(), info.getParam());
    }
    System.out.format(Config.NEW_LINE);
  }

  /**
   * play card button handler, plays the selected card for the user
   */
  public void playRealPlayerTurn(Card cardPlayed) { // RENAME -- only used for real player
    playCard(cardPlayed);
    endTurn();
    endRealPlayerTurn();
  }

  private void playASingleCard(Card cardToPlay) {
    myGame.currPlayerPlayCard(cardToPlay);
    displayController.updateCurrentPlayersHand(
        myGame.getCurrPlayer().getAllCards());
    checkPirateCard();
    displayController.updatePlayDeck(cardToPlay);
    executeAllActions(cardToPlay);
    try {
      TimeUnit.MILLISECONDS.sleep(100);
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
  }

  private void checkPirateCard() {
    if (myGame instanceof PirateGame) {
      System.out.format(Resources.getKey(Config.PIRATE_GAME));
      displayController.updateGivenPlayersHand(myGame.getPlayers());
    }
  }

  /**
   * play a given card
   *
   * @param cardToPlay Card instance from the model to play
   */
  public void playCard(Card cardToPlay) {
    playASingleCard(cardToPlay);
    if (cardToPlay.getCardColor() == CardColor.NONE) { // wild card
      if (myGame.isGameOver()) {
        return;
      }
      if (myGame.getCurrPlayerId() != 0) {
        playRandomValidCard(myGame.findCurrPlayerValidCardsToPlay().stream().toList());
      } else {
        findValidRealPlayerCards();
      }
    }
  }

  /**
   * get the cards in the current player's hand from the model
   *
   * @return list of cards in the current player's hand from the model
   */
  public List<Card> getCurrentPlayersModelHand() {
    return myGame.getCurrPlayer().getAllCards();
  }

  /**
   * draw a card and update the view accordingly
   */
  public void drawCard() {
    Card cardDrawn = drawCardFromModel();
    displayController.updatePlayDeck(myGame.getGameBoard().peekPlayDeckTopCard());
    if (myGame.getGameBoard().ifValidToPlay(cardDrawn)) { // --> check card drawn is valid to play
      if (myGame.getCurrPlayerId() != 0) {
        playCard(cardDrawn);
      } else {
        updateValidCardsInView(List.of(cardDrawn));
      }
    }
  }

  private Card drawCardFromModel() {
    try {
      Card cardDrawn = myGame.currPlayerDrawCard();
      displayController.updateCurrentPlayersHand(myGame.getCurrPlayer().getAllCards());
      return cardDrawn;
    } catch (NoPossibleCardsToDrawException e) {
      logger.info(e.getMessage());
      myView.noMorePossibleCardsToDraw();
      return null;
    }
  }

  private boolean checkIfValidCardsToPlay() {
    return !myGame.findCurrPlayerValidCardsToPlay().isEmpty();
  }

  private void executeAllActions(Card playedCard) {
    for (CardInfo info : playedCard.getCardInfo()) {
      executeAction(info);
    }
  }

  private void executeAction(CardInfo info) {
    String uniqueClassName = getFormattedClassName(info.getType());
    if (Config.VALID_EXECUTORS.contains(uniqueClassName)) {
      myGame.executeAction(info.getParam(), makeExecutorWithObserversAdded(uniqueClassName));
    }
  }

  private Executor makeExecutorWithObserversAdded(String uniqueClassName) {
    try {
      Executor executor = (Executor) createExecutorInstanceViaReflection(uniqueClassName);
      executor.addObserver(uniqueClassName.toLowerCase(),
          new ExecutorObserver(myGame, this, displayController));
      executor.addObserver(Config.INCREMENT_NUM, this);
      executor.addObserver(Config.EXECUTE_CASCADED, this);
      return executor;
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new InvalidActionTypeException(
          myResources.getString(InvalidActionTypeException.class.getSimpleName()));
    }
  }

  private String getFormattedClassName(String className) {
    String lowercaseUniqueClassName = className.toLowerCase();
    String firstLetter = lowercaseUniqueClassName.substring(0, 1).toUpperCase();
    return firstLetter + lowercaseUniqueClassName.substring(1);
  }

  private Object createExecutorInstanceViaReflection(String uniqueClassName)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    Class<?> newClass = getClassViaReflection(uniqueClassName, Config.EXECUTOR_CLASSES_PREFIX,
        Config.EXECUTOR_CLASSES_SUFFIX);
    Constructor<?> classConstr = newClass.getConstructor();
    return classConstr.newInstance();
  }

  private Class<?> getClassViaReflection(String uniqueClassName, String prefixClassName,
      String suffixClassName) throws ClassNotFoundException {
    String className = String.format(Config.THREE_STRING_NO_SPACE, prefixClassName,
        uniqueClassName,
        suffixClassName);
    return Class.forName(className);
  }

  /**
   * adds this controller instance as an observer for different properties
   */
  public void addObservers() {
    myView.addObserver(Config.ADD_LOAD_FILE_OBSERVERS, this);
    myView.addObserver(Config.CHANGED_DISPLAY_TO_DEFAULT_GAME, this);
    myView.addObserver(Config.CHANGED_DISPLAY_TO_NEW_GAME, this);
    myView.addObserver(Config.MY_GAME_DISPLAY, this);
    myView.addObserver(ooga.Config.GAME_READY_TO_SETUP, this);
  }

  /**
   * gets called when a property this class is observing gets changed
   *
   * @param evt PropertyChangeEvent instance that holds the name of the property that has changed
   *            and its new value
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    ReflectionMethodHandler reflectionHandler = new ReflectionMethodHandler();
    event = evt;
    try {
      reflectionHandler.handleMethod(event.getPropertyName(), Config.CONTROLLER_CLASSPATH)
          .invoke(Controller.this);
    } catch (IllegalAccessException e) {
      logger.error(e.getMessage());
    } catch (InvocationTargetException e) {

      logger.error(e.getMessage());
    }
  }

  private void executeCascadedActions() {
    myGame.playerDrawCards(myGame.getNextPlayerId(), numCardsToDrawNext);
    int newNextPlayerId = myGame.getNextPlayerId(myGame.getNextPlayerId());
    myGame.setNextPlayer(
        newNextPlayerId); // effectively makes next player draw & then skips their turn
  }

  private void incrementNumCards() {
    numCardsToDrawNext += (int) event.getNewValue();
  }

  private void displayChangedToGameChooser() {
    addLoadFileObserver();
  }

  private void displayChangedToNewGameDisplay() {
    if (filePath == null) {
      displayChangedToDefaultGame();
    } else {
      generateNewGameWithChosenFile();
    }
  }

  private void loadFileToCreateNewGame() throws Exception {
    filePath = (String) event.getNewValue();
    myView.getMyDisplay().switchDisplay(Config.GAME_DISPLAY);
  }

  private void addLoadFileObserver() {
    Display display = myView.getMyDisplay();
    display.addObserver(Config.LOAD_FILE_FOR_NEW_GAME, this);
  }

  private void generateNewGameWithChosenFile() {
    try {
      myGame = dataController.startNewGame(filePath);
      backOfCardFilePath = dataController.getBackOfCardFilePath();
    } catch (Exception e) {
      UserAlert.showError(e);
      logger.info(e.getMessage());
    }
  }

  private void displayChangedToDefaultGame() {
    try {
      myGame = dataController.startNewGame(Config.DEFAULT_BASIC_GAME);
    } catch (Exception e) {
      UserAlert.showError(e);
      logger.info(e.getMessage());
    }
  }

  private void initializeHandsAndDecksInGameSetup() {
    initializePlayerHandsAtStartOfGame();
  }

  private void saveMyGameDisplay() {
    gameDisplay = (GameDisplay) event.getNewValue();
  }

  /**
   * increments the number of cards the next player w/o a stackable draw card would need to draw
   *
   * @param numCardsToDraw number of cards the next player w/o a stackable draw card would need to
   *                       draw
   */
  public void incrementNumCardsToDraw(int numCardsToDraw) {
    numCardsToDrawNext += numCardsToDraw;
  }

  /**
   * get the final number of cards the player w/o a stackable draw card would need to draw
   *
   * @return final number of cards the player w/o a stackable draw card would need to draw
   */
  public int getCascadedNumCardsToDraw() {
    return numCardsToDrawNext;
  }

  /**
   * get a reference to the DataController instance
   *
   * @return DataController instance
   */
  public DataController getDataController() {
    return dataController;
  }

  /**
   * get a reference to the View instance
   *
   * @return View instance]
   */
  public View getMyView() {
    return myView;
  }

  /**
   * get a reference to the GameDisplay instance being displayed
   *
   * @return GameDisplay instance being displayed
   */
  public GameDisplay getGameDisplay() {
    return gameDisplay;
  }

  /**
   * gets the current player's id from the model
   *
   * @return current player's id from the model
   */
  public int getCurrentPlayerId() {
    return myGame.getCurrPlayerId();
  }


}

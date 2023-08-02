package ooga.view.display;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import ooga.view.CenterDecks;
import ooga.view.Config;
import ooga.view.PlayerHand;
import ooga.view.resources.Resources;
import ooga.view.View;
import ooga.view.display.profileDisplay.ProfileSettings;

public class GameDisplay extends Display {
  /**
   * Key for intro gameplay message.
   */
  private String GAMEPLAY_MESSAGE = "GamePlayMessage";

  private static PlayerHand myPlayerHand;

  private static CenterDecks myCenterDecks;
  private static BorderPane players;
  private String backOfCardFilePath;

  private Timeline myAnimation;

  /**
   * Constructor for GameDisplay class.
   *
   */
  public GameDisplay(View v) {
    super(v);
    myCenterDecks = new CenterDecks();
    myPlayerHand = new PlayerHand();
    updateObservers();
  }

  private void updateObservers(){
    Object[] centerDeckAndHands = { myCenterDecks, myPlayerHand};

    Config.MY_VIEW.notifyObservers(ooga.Config.CHANGED_DISPLAY_TO_NEW_GAME, null);
    Config.MY_VIEW.notifyObservers(ooga.Config.MY_GAME_DISPLAY, this);
    Config.MY_VIEW.notifyObservers(ooga.Config.GAME_READY_TO_SETUP, centerDeckAndHands);
  }

  private void updateTheDisplay(){
    Config.MY_VIEW.getRoot().setCenter(players);
  }
  /**
   * Override method creates Game Display.
   *
   * @return Display of game table with players hands/cards
   */
  @Override
  public BorderPane createDisplay() {
    createPlayerHands();
    return players;
  }

  public void createPlayerHands() {
    initPlayersDisplay();
    HBox centerDecks = myCenterDecks.createCenterDecks(myPlayerHand, backOfCardFilePath);
    players.setCenter(centerDecks);
    setupNumberOfPlayerHands();
    updateTheDisplay();
  }

  private void setupNumberOfPlayerHands() {
    if (myPlayerHand.getNumberOfPlayers() == 2) {
      players.setBottom(myPlayerHand.createUserHand(ProfileSettings.getCurrentUser()));
      players.setTop(myPlayerHand.createTopOpponentHand(Resources.getKey("Player1")));
    }
    if (myPlayerHand.getNumberOfPlayers() == 3) {
      players.setBottom(myPlayerHand.createUserHand(ProfileSettings.getCurrentUser()));
      players.setLeft(myPlayerHand.createLeftOpponentHand(Resources.getKey("Player1")));
      players.setRight(myPlayerHand.createRightOpponentHand(Resources.getKey("Player2")));
    }
    else {
      players.setBottom(myPlayerHand.createUserHand(ProfileSettings.getCurrentUser()));
      players.setTop(myPlayerHand.createTopOpponentHand(Resources.getKey("Player2")));
      players.setLeft(myPlayerHand.createLeftOpponentHand(Resources.getKey("Player1")));
      players.setRight(myPlayerHand.createRightOpponentHand(Resources.getKey("Player3")));
    }
  }

  private void initPlayersDisplay(){
    players = new BorderPane();
    super.setTitle(players, new Label(Config.MY_RESOURCES.getString(GAMEPLAY_MESSAGE)));
  }

  public void setMyCenterDecks(HBox deck) {
    myCenterDecks.setDeckDisplay(deck);
    players.setCenter(myCenterDecks.createCenterDecks(myPlayerHand, backOfCardFilePath));
  }

  public void setBackOfCardFilePath(String filePath) {
    backOfCardFilePath = filePath;
  }

  private void startSimulation() {
    if (myAnimation != null) {
      myAnimation.stop();
    }
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(
        new KeyFrame(Duration.seconds(5.0),
            e -> createPlayerHands()));
    myAnimation.play();
  }
}

package ooga;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.view.resources.Resources;

public class Main extends Application {

  /**
   * Stage variable.
   */
  private Stage myStage;

  /**
   * Starts the application by building a view object and setting the Scene.
   *
   * @param stage the stage being set.
   */
  @Override
  public void start(final Stage stage) {
    myStage = stage;

    // Get Resources and set title
    ResourceBundle resources = Resources.getResources(Resources.getPath());
    assert resources != null;
    myStage.setTitle(resources.getString(ooga.view.Config.TITLE_KEY));

    // Show GUI
    Controller controller = new Controller(myStage, getGameDimensions());
    controller.generateView();
  }

  public Stage getStage() {
    return myStage;
  }

  /**
   * This method returns the dimensions of the game.
   */
  private int[] getGameDimensions() {
    return new int[]{Config.MY_HEIGHT, Config.MY_WIDTH};
  }
}


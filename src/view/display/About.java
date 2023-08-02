package ooga.view.display;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ooga.view.Config;
import ooga.view.resources.Resources;
import ooga.view.View;

/**
 * This class contains information pertaining to the About display in the UNO game. It explains the
 * rules of UNO to the user, how to win the game, and provides information about the game's creators
 * and purpose for the program's creation.
 *
 * @author Cate Schick cms168
 */
public final class About extends Display {

  /**
   * Constructor for Display class.
   *
   * @param v View object associated with Display.
   */
  public About(final View v) {
    super(v);
  }

  /**
   * This method creates the About Display.
   *
   * @return BorderPane containing About display content.
   */
  @Override
  public BorderPane createDisplay() {
    BorderPane myPane = new BorderPane();
    myPane.getStyleClass().add(Config.INFO_DISPLAY);

    // Add information about the game, how to win, and about creators.
    VBox howToPlay = addEntry(Resources.getKey(Config.HOW_TO_PLAY_TITLE),
        Resources.getKey(Config.HOW_TO_PLAY_CONTENT));
    VBox howToWin = addEntry(Resources.getKey(Config.HOW_TO_WIN_TITLE),
        Resources.getKey(Config.HOW_TO_WIN_CONTENT));
    VBox creator = addEntry(Resources.getKey(Config.CREATOR_TITLE),
        Resources.getKey(Config.CREATOR_CONTENT));

    VBox container = new VBox();
    container.getChildren().addAll(howToPlay, howToWin, creator);

    myPane.setCenter(container);

    return myPane;
  }

  /**
   * This method adds an entry to the About display.
   *
   * @param title   Title of section.
   * @param content Section content.
   * @return Formatted entry in About display.
   */
  private VBox addEntry(final String title, final String content) {
    VBox v = new VBox();
    v.getStyleClass().add(Config.ABOUT_CONTENT_STYLE);

    Label label = new Label(title);
    styleTitle(label);

    Label description = new Label(content);
    description.getStyleClass().addAll(Config.MY_VIEW.getFont(), Config.LABEL_STYLE
        + Config.MY_VIEW.getSize(), Config.ABOUT_STYLE);

    v.getChildren().addAll(label, description);
    return v;
  }

  /**
   * This method properly styles the title of a section.
   *
   * @param title Title object to be styled.
   */
  private void styleTitle(final Label title) {
    title.getStyleClass().addAll(Config.MY_VIEW.getFont(),
        Config.TITLE_STYLE + Config.MY_VIEW.getSize(), Config.TITLE_STYLE);
    title.setWrapText(true);
  }
}

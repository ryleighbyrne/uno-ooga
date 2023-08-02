package ooga.view.display.designSettings;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import ooga.view.Config;
import ooga.view.resources.Resources;
import ooga.view.View;
import ooga.view.button.ViewButton;
import ooga.view.display.Display;

public final class ThemeDisplay extends Display {

  /**
   * Constructor for Theme Display.
   *
   * @param v View associated with display.
   */
  public ThemeDisplay(View v) {
    super(v);
  }

  /**
   * This method adds a display with theme options.
   *
   * @return A BorderPane with theme options.
   */
  @Override
  public BorderPane createDisplay() {
    BorderPane themeOptions = new BorderPane();
    setDisplayStyle(Config.INFO_DISPLAY, themeOptions);

    GridPane buttons = addButtons();

    themeOptions.setLeft(Config.MY_VIEW.getMyDisplay().makeBackButton(
        Config.DESIGN_SETTINGS_PATH + Config.DESIGN_SETTINGS));

    // Add title
    setTitle(themeOptions, new Label
        (Resources.getKey(Config.THEME_OPTIONS)));

    // Center button and theme descriptions.
    themeOptions.setCenter(buttons);

    return themeOptions;
  }

  /**
   * This method adds the buttons to the display.
   *
   * @return GridPane containing theme names and resource file information.
   */
  private GridPane addButtons() {
    GridPane buttons = new GridPane();
    buttons.getStyleClass().add(Config.THEME_STYLE);

    // Get theme options
    ArrayList<String> themes = Config.MY_VIEW.extractFileName(
        Config.THEME_FILE_PATH, Config.CSS);

    // Generate theme title and description from proper .properties file.
    addDescriptions(themes, buttons);

    return buttons;
  }

  /**
   * This method iterate through .css theme files and adds information.
   *
   * @param themes  ArrayList containing names of themes.
   * @param buttons GridPane information is added to.
   */
  private void addDescriptions(final List<String> themes,
      final GridPane buttons) {
    // Column counter
    int column = Integer.parseInt(Config.ZERO);

    for (String name : themes) {
      // Get button name & description from resource file
      Button b = ViewButton.makeButton(Resources.getKey(name + Config.THEME_TITLE),
          e -> Config.MY_VIEW.setTheme(
              name.toLowerCase().replaceAll(Config.SPACE, Config.BLANK)));

      Label description = new Label(Resources.getKey(name + Config.THEME_DESCRIPTION));
      description.getStyleClass().addAll(Config.MY_VIEW.getFont(),
          Config.LABEL_STYLE + Config.MY_VIEW.getSize());

      // Style and add to GridPane
      buttons.add(b, Integer.parseInt(Config.ZERO), column);
      buttons.add(description, 1, column);

      // Use Toolbar style for these buttons so they can all fit on display.
      ViewButton.setButtonSize(b, Config.TOOLBAR_BUTTON_STYLE);
      ViewButton.styleButtons(new Button[]{b}, null);

      column++;
    }
  }
}

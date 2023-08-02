package ooga.view.display;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import ooga.view.Config;
import ooga.view.View;
import javafx.scene.control.Label;
import ooga.view.button.ViewButton;
import ooga.view.resources.Resources;

/**
 * This class creates a Language Setting display, where a user can select from available languages.
 *
 * @author Cate Schick cms168
 */
public class LanguageSettings extends Display {

  /**
   * Constructor for Display class.
   *
   * @param v The view associated with Language Settings Display.
   */
  public LanguageSettings(final View v) {
    super(v);
  }

  /**
   * Override method that creates Language Settings Display.
   *
   * @return Settings showing available language options.
   */
  @Override
  public BorderPane createDisplay() {
    BorderPane languageSettings = new BorderPane();
    super.setTitle(languageSettings, new Label(
        Config.MY_RESOURCES.getString(Config.LANGUAGE_SETTINGS_TITLE)));
    languageSettings.setCenter(addLanguagePane());
    return languageSettings;
  }

  /**
   * Adds buttons displaying available languages.
   *
   * @return GridPane with language options.
   */
  private GridPane addLanguagePane() {
    GridPane languages = new GridPane();
    languages.getStyleClass().add(Config.GRIDPANE_STYLE);

    ArrayList<String> files = Config.MY_VIEW.
        extractFileName(Config.RESOURCE_FILE_PATH, Config.PROPERTY_EXTENSION);

    // Add button for each language
    addLanguages(files, languages);

    return languages;
  }

  /**
   * This method adds language options to display.
   *
   * @param files     List of property files at specified path.
   * @param languages
   */
  private void addLanguages(final List<String> files, final GridPane languages) {
    int row = 0;
    int col = 0;
    for (String name : files) {

      // Add button that changed path to desired scene
      Button b = ViewButton.makeButton(Resources.getKey(name),
          event -> {
            Resources.setPath(name.toLowerCase());
            Config.MY_VIEW.updateScene(Config.LANGUAGE_SETTINGS);
          });

      languages.add(b, row, col);

      ViewButton.setButtonSize(b, Config.BUTTON_STYLE);
      ViewButton.styleButtons(new Button[]{b}, null);

      row++;
      if (row >= Config.LANGUAGES_MAX_PER_ROW) {
        row = 0;
        col++;
      }
    }
  }
}

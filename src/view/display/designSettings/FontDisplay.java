package ooga.view.display.designSettings;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import ooga.view.resources.Resources;
import ooga.view.Config;
import ooga.view.View;
import ooga.view.button.ViewButton;
import ooga.view.display.Display;

public final class FontDisplay extends Display {

  /**
   * Constructor for Font Display.
   *
   * @param v View associated with display.
   */
  public FontDisplay(View v) {
    super(v);
  }


  /**
   * This method adds a display with theme options.
   *
   * @return A BorderPane with theme options.
   */
  @Override
  public BorderPane createDisplay() {
    BorderPane fontOptions = new BorderPane();
    GridPane buttons = addFontOptions();
    buttons.getStyleClass().add(Config.GRIDPANE_STYLE);

    fontOptions.setLeft(Config.MY_VIEW.getMyDisplay().makeBackButton(
        Config.DESIGN_SETTINGS_PATH + Config.DESIGN_SETTINGS));

    // Add title
    setTitle(fontOptions, new Label
        (Resources.getKey(Config.FONT_OPTIONS)));

    // Center button and theme descriptions.
    fontOptions.setCenter(buttons);

    return fontOptions;
  }

  /**
   * This method sets up a BorderPane where font options are displayed to the user.
   *
   * @return A BorderPane with font options.
   */
  private GridPane addFontOptions() {
    GridPane fontOptions = new GridPane();
    fontOptions.getStyleClass().add(Config.GRIDPANE_STYLE);

    iterateFonts(fontOptions);

    return fontOptions;
  }

  /**
   * Iterates through font list and creates display content.
   *
   * @param display Display buttons are being added to.
   */
  private void iterateFonts(final GridPane display) {
    int row = Integer.parseInt(Config.ZERO);
    int col = Integer.parseInt(Config.ZERO);

    for (Font font : getFonts()) {
      // Properly format font
      String fontName = font.getFamily();
      String formatted = fontName.replace(Config.FONT_EXTENSION, Config.BLANK);

      // Create button for each font and set to that font.
      Button b = ViewButton.makeButton(
          Resources.getKey(formatted),
          e -> changeFont(formatted));

      // Clear old styles and add updated
      b.getStyleClass().clear();
      b.getStyleClass().addAll(Config.BUTTON_STYLE,
          Config.BUTTON_STYLE + Config.MY_VIEW.getSize(), formatted);

      display.add(b, col, row);

      row++;
      if (row >= Config.FONT_DISPLAY_MAX_PER_ROW) {
        row = 0;
        col++;
      }
    }
  }

  /**
   * Change font of program to selected font through View.java.
   *
   * @param font Selected font.
   */
  private void changeFont(final String font) {
    Config.MY_VIEW.setFont(font);
  }

  /**
   * Returns list of available fonts
   *
   * @return List of fonts.
   */
  private Font[] getFonts() {
    return new Font[]{Font.font(Config.ARIAL), Font.font(Config.CHALKDUSTER),
        Font.font(Config.COPPERPLATE), Font.font(Config.FUTURA), Font.font(Config.GEORGIA),
        Font.font(Config.IMPACT), Font.font(Config.LUMINARI), Font.font(Config.MONACO),
        Font.font(Config.NOTEWORTHY), Font.font(Config.PAPYRUS), Font.font(Config.PHOSPHATE),
        Font.font(Config.SIGNPAINTER)};
  }
}

package ooga.view.display.designSettings;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ooga.view.Config;
import ooga.view.View;
import ooga.view.button.ViewButton;
import ooga.view.display.Display;
import ooga.view.resources.Resources;

public final class SizeDisplay extends Display {

  /**
   * Constructor for SizeDisplay class.
   *
   * @param v View object associated with display.
   */
  public SizeDisplay(View v) {
    super(v);
  }

  /**
   * Creates the size option display.
   *
   * @return BorderPane with size options
   */
  @Override
  public BorderPane createDisplay() {
    BorderPane options = new BorderPane();

    // Add title
    setTitle(options, new Label
        (Resources.getKey(Config.SIZE_OPTIONS)));

    options.setLeft(makeBackButton(Config.DESIGN_SETTINGS_PATH + Config.DESIGN_SETTINGS));
    options.setCenter(addSizeOptions());

    return options;
  }

  /**
   * This method sets up a BorderPane where font options are displayed to the user.
   *
   * @return A BorderPane with font size options.
   */
  private VBox addSizeOptions() {
    VBox buttons = new VBox();

    for (String size : new String[]{Config.SMALL, Config.MEDIUM, Config.LARGE, Config.XLARGE}) {
      Button b = ViewButton.makeButton(Resources.getKey(size.replaceAll(Config.DASH, Config.BLANK)),
          e -> setFontSize(size));

      // Style
      ViewButton.styleButtons(new Button[]{b}, buttons);
    }

    // Add size options
    return buttons;
  }

  /**
   * This method sets the font in a .css file to a particular size.
   *
   * @param newSize The new font size.
   */
  private void setFontSize(final String newSize) {
    Config.MY_VIEW.setSize(newSize);
  }

}

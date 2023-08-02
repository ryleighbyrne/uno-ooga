package ooga.view.display.designSettings;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import ooga.view.resources.Resources;
import javafx.scene.layout.HBox;
import ooga.view.Config;
import ooga.view.View;
import ooga.view.button.ViewButton;
import ooga.view.display.Display;

/**
 * This class handles methods related to the Design Settings, where users select a font, font size,
 * and theme for the project.
 *
 * @author Cate Schick cms168
 */
public final class DesignSettings extends Display {

  /**
   * Constructor for Display class.
   */
  public DesignSettings(View v) {
    super(v);
  }

  /**
   * Override method creates DesignSettings display screen.
   *
   * @return BorderPane with display content.
   */
  @Override
  public BorderPane createDisplay() {
    BorderPane designDisplay = new BorderPane();

    // Set Display title
    setTitle(designDisplay, new Label(
        Resources.getKey(Config.DESIGN_SETTINGS_TITLE)));

    // Add display content to display and center
    designDisplay.setCenter(addSettingsContent());

    return designDisplay;
  }

  /**
   * This method sets design setting content options into an organized display.
   */
  private HBox addSettingsContent() {
    // Add font, themes, font size
    HBox settings = new HBox();

    // Create buttons that navigate to proper displays
    Button font = ViewButton.makeButton(Resources.getKey(Config.FONT_OPTIONS), e ->
        this.switchDisplay(Config.DESIGN_SETTINGS_PATH + Config.FONT_DISPLAY));

    Button theme = ViewButton.makeButton(Resources.getKey(Config.THEME_OPTIONS), e ->
        this.switchDisplay(Config.DESIGN_SETTINGS_PATH + Config.THEME_DISPLAY));

    Button size = ViewButton.makeButton(Resources.getKey(Config.SIZE_OPTIONS), e ->
        this.switchDisplay(Config.DESIGN_SETTINGS_PATH + Config.SIZE_DISPLAY));

    // Style and add buttons to HBox
    Button[] buttons = new Button[]{font, theme, size};
    ViewButton.styleButtons(buttons, settings);

    return settings;
  }
}

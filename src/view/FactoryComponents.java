package ooga.view;

import javafx.scene.control.Label;

public class FactoryComponents {

  /**
   * Int for default card width
   */
  private static final int cardWidth = 50;
  /**
   * Int for default card height
   */
  private static final int cardHeight = 90;
  /**
   * String key for Card
   */
  private static final String CARD_STYLE = "Card";
  /**
   * String key for Card Text
   */
  private static final String CARD_TEXT_STYLE = "Card_Text";
  /**
   * String key for Label
   */
  private static final String LABEL_STYLE = "Label";
  /**
   * String for back of card
   */
  private static final String UNO = "UNO";


  /**
   * Constructor for FactoryComponents class.
   */
  public FactoryComponents() {}

  /**
   * Method that creates a Label.
   *
   * @param text Label text.
   * @return Label with text.
   */
  public Label createLabel(String text) {
    Label label = new Label(text);
    label.getStyleClass().add(LABEL_STYLE);
    return label;
  }
}

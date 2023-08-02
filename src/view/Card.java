package ooga.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.List;
import ooga.view.resources.Resources;

public class Card extends StackPane {

  /**
   * Int for default card width
   */
  private static final int cardWidth = 50;
  /**
   * Int for default card height
   */
  private static final int cardHeight = 90;
  /**
   * Int for default user card width
   */
  private static final int userCardWidth = 75;
  /**
   * Int for default user card height
   */
  private static final int userCardHeight = 135;
  /**
   * String key for Card
   */
  private static final String CARD_STYLE = "Card";
  /**
   * String key for Selected Card
   */
  private static final String SELECTED_TEXT_STYLE = "Selected_Text";
  /**
   * String key for Selected Card
   */
  private static final String SELECTED_CARD_STYLE = "Selected_Card";
  /**
   * String key for playable Card
   */
  private static final String VALID_TEXT_STYLE = "Valid_Text";
  /**
   * String key for Card Text
   */
  private static final String CARD_TEXT_STYLE = "Card_Text";
  /**
   * String key for Back of Card Text
   */
  private static final String BACK_CARD_TEXT_STYLE = "Back_Card_Text";
  /**
   * String key for Deck Text
   */
  private static final String DECK_TEXT_STYLE = "Deck_Text";
  /**
   * String for number type cards
   */
  private static final String NUMBER = "number";
  /**
   * String for draw type cards
   */
  private static final String DRAW = "draw";
  /**
   * String for plus symbol
   */
  private static final String PLUS = "+";
  /**
   * List to hold card's value (skip in skip 2, draw in draw 2)
   */
  private List<String> myValue;
  /**
   * List to hold parameters for a card ( 2 in skip 2, 1 in blue 1)
   */
  private List<Integer> myParameters;
  /**
   * String to hold card's color
   */
  private String myColor;
  /**
   * Rectangle to hold card border
   */
  private Rectangle myCardBorder;
  /**
   * Text to hold card's number
   */
  private Text myNumber;
  /**
   * Image for back of card;
   */
  private ImageView myImage;
  /**
   * Boolean to signify whether a card is currently selected
   */
  private Boolean isSelected;
  /**
   * Boolean to signify whether a card is valid to play
   */
  private Boolean isValid;


  /**
   * Constructor for Card class. This constructor is for face-up cards
   */
  public Card(List<String> value, String color, List<Integer> parameters) {
    assignCardData(value, color, parameters);
    isSelected = false;
    isValid = false;
    String cardColor = color + CARD_STYLE;
    myCardBorder = new Rectangle(userCardWidth, userCardHeight);
    myCardBorder.getStyleClass().add(cardColor);
    setCardText();
  }

  /**
   * Constructor for Card class. This constructor is for face-down cards! (opponents)
   */
  public Card(List<String> value, String color, boolean isTopPlayer, List<Integer> parameters) {
    assignCardData(value, color, parameters);
    createBackOfCard(isTopPlayer);
  }

  /**
   * Constructor for Card class. This constructor is for face-down cards with an Image for their back! (opponents)
   */
  public Card(List<String> value, String color, boolean isTopPlayer, List<Integer> parameters, String cardBack) {
    assignCardData(value, color, parameters);
    createBackOfCard(isTopPlayer);
    if (cardBack != null && !cardBack.equals("")) {
      addImageToBackOfCard(isTopPlayer, cardBack);
    }

  }

  private void assignCardData(List<String> value, String color, List<Integer> parameters) {
    myValue = value;
    myColor = color;
    myParameters = parameters;
  }

//  private void createBackOfCard(boolean isTopPlayer) {
//    myCardBorder =
//        isTopPlayer ? new Rectangle(cardWidth, cardHeight) : new Rectangle(cardHeight, cardWidth);
//    myCardBorder.getStyleClass().add(CARD_STYLE);
//    myNumber = new Text(UNO);
//    myNumber.getStyleClass().add(BACK_CARD_TEXT_STYLE);
//    myImage = new ImageView("resources/themes/cards/angryBirds.png");
//    myImage.setFitHeight(cardWidth);
//    myImage.setFitWidth(cardHeight);
//    myImage.setRotate(isTopPlayer ? 90 : 0);
//    this.getChildren().addAll(myCardBorder, myImage);
//  }

  private void createBackOfCard(boolean isTopPlayer) {
    myCardBorder =
        isTopPlayer ? new Rectangle(cardWidth, cardHeight) : new Rectangle(cardHeight, cardWidth);
    myCardBorder.getStyleClass().add(CARD_STYLE);
    myNumber = new Text(Resources.getKey(Config.UNO));
    myNumber.getStyleClass().add(BACK_CARD_TEXT_STYLE);
    this.getChildren().addAll(myCardBorder, myNumber);
  }

  private void addImageToBackOfCard(boolean isTopPlayer, String backOfCardPath) {
    String[] localPath = backOfCardPath.split("src/");
    myImage = new ImageView(localPath[1]);
    myImage.setFitHeight(cardWidth);
    myImage.setFitWidth(cardHeight);
    myImage.setRotate(isTopPlayer ? 90 : 0);
    this.getChildren().add(myImage);
  }

  /**
   * Method that returns whether a card is currently selected
   *
   * @return Boolean isSelected
   */
  public boolean getIsSelected() {
    return isSelected;
  }

  /**
   * Method that sets whether a card is selected
   *
   * @param tf new selected state (boolean) of card
   */
  public void setIsSelected(boolean tf) {
    if (isValid) {
      isSelected = tf;
      this.getChildren().remove(myCardBorder);
      this.getChildren().remove(myNumber);
      if (isSelected) {
        myCardBorder.getStyleClass().add(SELECTED_CARD_STYLE);
        myNumber.getStyleClass().add(SELECTED_TEXT_STYLE);
      } else {
        if (myNumber.getStyleClass().contains(SELECTED_TEXT_STYLE)) {
          myCardBorder.getStyleClass().remove(SELECTED_CARD_STYLE);
          myNumber.getStyleClass().remove(SELECTED_TEXT_STYLE);
        }
      }
      this.getChildren().add(myCardBorder);
      this.getChildren().add(myNumber);
    }
  }


  /**
   * Method that returns whether a card is currently valid to play
   *
   * @return Boolean isValid
   */
  public boolean getIsValid() {
    return isValid;
  }

  /**
   * Method that sets whether a card is selected
   *
   * @param tf new selected state (boolean) of card
   */
  public void setIsValid(boolean tf) {
    isValid = tf;
    this.getChildren().remove(myNumber);
    if (isValid) {
      myNumber.getStyleClass().add(VALID_TEXT_STYLE);
    } else {
      myNumber.getStyleClass().remove(VALID_TEXT_STYLE);
    }
    this.getChildren().add(myNumber);
  }

  /**
   * Method that returns a card's value
   *
   * @return Int value of card
   */
  public List<String> getValue() {
    return myValue;
  }

  /**
   * Method that sets a card's value
   *
   * @param value new value (int) of card
   */
  public void setValue(List<String> value) {
    myValue = value;
  }

  /**
   * Method that returns a card's color
   *
   * @return String of card's color
   */
  public String getColor() {
    return myColor;
  }

  /**
   * Method that returns a card's number
   * @return List of integers of cards related numbers (2 in skip 2, 1 in blue 1)
   */
  public List<Integer> getParameters() {
    return myParameters;
  }

  /**
   * Method that sets a card's color
   *
   * @param color new color (String) of card
   */
  public void setColor(String color) {
    myColor = color;
  }

  public void setParameters(List<Integer> parameters) {
    myParameters = parameters;
  }

  /**
   * Method that sets a card's dimensions
   *
   * @param x New width of card
   * @param y New Height of card
   */
  public void setDimensions(int x, int y) {
    if (x > myCardBorder.getWidth()) {
      myNumber.getStyleClass().removeAll();
      myNumber.getStyleClass().add(DECK_TEXT_STYLE);
    }
    myCardBorder.setWidth(x);
    myCardBorder.setHeight(y);
//    myImage.setFitHeight(x);
//    myImage.setFitWidth(y);
  }

  private void setCardText() {
    boolean special = false;
    ImageView symbol = new ImageView("resources/themes/cards/reverse.png");
    if (myValue.contains(Resources.getKey(Config.NUMBER))) {
      String number = Integer.toString(myParameters.get(0));
      myNumber = new Text(number);
    } else if (myValue.contains(DRAW)) {
      String number = Integer.toString(myParameters.get(0));
      myNumber = new Text(PLUS + number);
    } else {
      special = true;
      //ImageView symbol = new ImageView("resources/themes/cards/reverse.png");
      myNumber = new Text(Character.toString(myValue.get(0).charAt(0)).toUpperCase());
    }
    myNumber.getStyleClass().add(CARD_TEXT_STYLE);
    this.getChildren().addAll(myCardBorder, myNumber);
  }
}

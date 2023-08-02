/**
 * defines the info on a card, can get the type of card (number, skip, draw, etc.) and its integer
 * parameter (number 3, skip 2, draw 4, etc.)
 */
public interface CardInfo {

  String getType();

  int getParam();

}
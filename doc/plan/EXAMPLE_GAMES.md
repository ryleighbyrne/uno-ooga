# Example Games - Variations of UNO

### Summary of Three Variations

All three of these games use the same basic UNO rules. The first version is called My First UNO,
which has fewer cards, so we will aim to implement and integrate this version first. The second
version is called Regular UNO, and will be the complete version of UNO we will implement. It will
have the same rules and card images as traditional UNO games. The last version is a modded version
of UNO called Angry Birds UNO. All cards will have angry bird background images on the back and
front instead of just a colored background, and there will be a new special type of card called the
King Pig Card.

### Similarities & Differences

All the variations will implement the same basic gameplay of drawing a card from the DRAW pile
and/or discarding a playable card to the DISCARD pile. The game logic, UI, data input, and win
conditions of all the variations will be pretty similar.

However, the complete UNO and modded UNO will have more types of cards, so we will have to implement
more use cases and extend features of basic UNO. Additionally, with modded UNO, we will have to
change the UI for all the cards and the theme of the game display. Lastly, we will also be
implementing more challenging features in the basic UNO version such as allowing users to create a
custom type of card during gameplay and create a new game from a game area editor.

## BASIC UNO (My First Uno)

Cards:

* 4 colors: red/green/blue/yellow - Cards #1-7 for each color (28 total)
* draw 2 cards (4)
* Wild cards (4)

Game Set up:

* same as basic, but only deal 5 cards to each player

## Regular UNO

#### Game Objective

* The first player to play all cards in their hand wins.

#### Game Setup

* Deck is shuffled. Each player is dealt 7 cards.
* The remaining cards are placed face down to form a draw pile.
* Turn over the top card of the draw pile to begin a discard pile.
* Top card selected will always be a number card (aka red 4, blue 9, etc).

#### Game Play

* Default gameplay direction is clockwise, starting with a randomly chosen player.
* In a player’s turn:
    * Match the top card on the DISCARD pile either by number, color, or word by playing a card.
    * If you don't have anything that matches, you must pick a card from the DRAW pile. If you draw
      a card you can play, play it.

No Reneging:

* You must play a card if you have a playable card. You cannot choose to draw a card instead.

Note:

* You can always play a wild card and then a card of your choice.
* You can only play a wild draw 4 card when you have no other valid cards to play.
* If the DRAW pile is depleted & game hasn’t ended, reshuffle the DISCARD pile and continue game
  play.

Examples of game play:

* Top card is a red 7. Can play any red card or yellow/green/blue 7.
* Top card is a blue skip. Can play any blue card or yellow/green/red skip.

#### Cards

112 cards as follows:

* 19 Blue cards - 0 to 9 (1 zero and 2 of each 1-9)
* 19 Green cards - 0 to 9 (1 zero and 2 of each 1-9)
* 19 Red cards - 0 to 9 (1 zero and 2 of each 1-9)
* 19 Yellow cards - 0 to 9 (1 zero and 2 of each 1-9)
* 8 Skip cards - 2 each in Blue, Green, Red and Yellow
* 8 Reverse cards - 2 each in Blue, Green, Red and Yellow
* 8 Draw 2 cards - 2 each in Blue, Green, Red and Yellow
* 4 Wild cards
* 4 Wild Draw 4 cards
* 4 Blank cards - 1 of each color, new type of card with custom rule
    * Players can make a new type of card & add a special rule
    * Should be part of a UI for game setup or happen in the middle of the gameplay when first blank
      card is seen.

#### Card Descriptions

Draw 2

* When you play this card, the next person must draw 2 cards and forfeit his/her turn.
* May only be played on a matching color or on another Draw 2 card.

Reverse

* Reverses the direction of play.
* May only be played on a matching color or on another Reverse card.

Skip

* Next person to play skips their turn.
* May only be played on a matching color or on another Skip card.

Wild

* When you play this card, you may change the color being played to any color (including the current
  color) to continue play.
* You may play a Wild card even if you have another playable card in hand.

Wild Draw 4

* This card allows you to call the next color played and requires the next player to pick 4 cards
  from the DRAW pile and forfeit his/her turn. However, there is a hitch!
* You can only play this card when you don't have a card in your hand that matches the color of the
  card previously played. (in the basic version)

## UNO MOD (ANGRY BIRDS UNO)

(same overall rules as basic game)

* All cards have an angry bird pictures on them (in addition to what was on the basic cards)

#### New special card: King Pig Card

* You'll feel indestructible when you play this card! Call out a color.
* Then, starting with the player to your left and going clockwise, give one card from the draw pile
  to each opponent in order until someone receives a card of the color you selected
* This card is also a Wild card, so after doing the above ^, continue by playing a card of the color
  you called and resume game play in the current direction.

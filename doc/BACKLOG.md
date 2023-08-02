## Use Cases

### Alicia

* red skip card is played by the player before you --> your turn is skipped
* draw card deck runs out --> discard deck is shuffled & becomes the new draw deck
* blue #7 card is played by the player before you --> what cards in your hand are valid to play?
* player tries to play an invalid card (need to play a green card or a #5 card, player tries to play
  a red #2)
* wild card is played, color changes
* 2 wild draw 4 cards are played in a row before you, now you must draw 8 cards & have your turn
  skipped

### Ryleigh

* wild card draw 4 is played --> red is chosen as color to continue play --> next player draws four
  and loses their turn
* yellow draw 2 card is played before you --> you draw two --> still have no valid cards in hand -->
  you draw again
* two reverse cards are played in a row
* three skip cards are played in a row
* wild card is played -> color changes to red -> player plays a red #7 -> next player plays a green
  7 -> color is now green
* player has one card left -> have to broadcast that they have "uno"

### Luke

* user clicks on unplayable card when the last card was green --> error message is shown suggesting
  they use a green card
* opponent only has one card left --> message is displayed saying "Uno!"
* a yellow card is played --> center pane showing the deck becomes yellow
* draw card runs out --> deck is removed from scene, message saying "deck being shuffled", new deck
  appears
* 3 wild draw 4 cards are played in a row --> message saying "+12!" appears
* game ends --> Message appears saying "____ won!" and asks if you want to play again

### Keith
* A valid SIM file is used to start up a new game --> game begins correctly
* An invalid SIM file is selected to start up a new game --> throws Exception describing the problem
* A user saves a game --> new SIM file is created in a specified folder, containing the information about the game
* A user tries to log in with a correct username/password --> their info is retrieved from the database, and they get logged in appropriately
* A user tries to log in with an invalid username/password --> they get a message saying this is an invalid username/password combination
* A user makes a profile to save to the database --> their info is saved to the database
* A card file (SIM or JSON?) is read in --> a new Card is instantiated

### Cate

* Uno is supported in at least 4 languages and the program can dynamically change between them
* Users can switch between program displays easily with ONE method that uses reflection
* Users can edit game preferences, including background color of display and font
* Users can choose between several .css theme settings
* Users can set up and switch between game profiles that are saved in a .JSON file
* Users can save current game state and return to it at a later time.
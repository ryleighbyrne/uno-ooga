## Design Plan

Our UNO variations will share the same logic of game creation, data parsing, view components, and
game execution logic. However, there will difference between the variations such as rules for
gameplay, card decks, custom cards, and display theme. We would like to set up a flexible structure
for data handling between the controller, model, and view to support any future extensions that
might require new types of data or more data in the game. Additionally, we would like to first
develop a very basic version of UNO with limited cards and rules and design it to be open to
extension.

### Model

The model will allow the controller to create specific cards from the given data file to create a
custom card deck for the game. This will allow for users to easily change the cards they want
through the data file, and allow them to load in pre-existing card decks and modify them. The design
will also support dynamic creation of new types of custom cards (such as a card that can reverse the
game's direction and cause the next person to draw 2 cards) by mixing the existing types of cards in
the complete variation of UNO. The model will also handle the entire game flow logic of UNO such as
shuffling decks, distributing cards, determining if cards are valid to play, who will play a card
next, who wins, etc. and support communication of game data and player statuses to the controller. 

### View

The view will initially load an intro display welcoming the user to the program. 
It will then allow the user to select between the following buttons in the ToolBar, each leading to a different display: New Game,
Save Game, Game Settings, Design Settings, Language Settings, and Profile Settings. The view will
support dynamic language change in the application, and will update the display as the user plays the
game. By abstracting the Display class, this program is flexible and capable of adding new displays
easily. Additionally, the displays are set using a reflection method in an attempt to minimize as
much code as possible.

### Controller
The controller will mediate between the view and model, passing information back and forth as necessary. 
When a user customizes (through the GUI) cards or other settings affecting the backend, such as number of players, the controller 
will notify the backend/model. Also, when a user plays a card on the GUI, the backend will be notified. 
During the CPU turns, the controller will alert the front end of the changes. 
ooga
====

This project implements a player for multiple related games.

Names:

* Alicia Wu (atw27)
* Cate Schick (cms168)
* Keith Cressman (kac154)
* Luke Josephy (lrj11)
* Ryleigh Byrne (rmb96)

### Timeline

Start Date: 11/3/2021

Finish Date: 12/6/2021

Hours Spent: 200

### Primary Roles

**Alicia Wu (atw27)**

My assigned module was the model module that contains code implementing game logic. I worked on
designing the model module and implementing, refactoring, debugging, and testing it. The code that I
wrote for this is in the model package in the src folder and the ModelTests package in the test
folder. I also helped on the controller module by setting up the observer/observable design pattern
used to communicate between the different modules, implementing parts of the controller module and
connecting it to the model and view, setting up tests, and debugging integration issues. The code
that I wrote for this is in the controller package in the src folder.

**Cate Schick (cms168)**

I worked on the View for this project, and my code can be found in the view package. I worked on all
displays except GameDisplay.java, as well as the classes in the button, dialog, resources, toolbar,
and userAlert packages. I implemented the frontend functionality for supporting profiles, languages,
custom games, and design settings.

**Keith Cressman (kac154)**

I worked on the ooga/data package. I was responsible for code to create a game based on a 
json file, as well as saving a game to a json file of the same format. I also worked on 
some methods to get specific values out of these json files. In addition, 
I worked on handling player profiles (adding/removing/editing profiles). 

**Luke Josephy (lrj11)**

I worked entirely on the View for this project. More specifically, I focused on the game play itself
with regards to the front-end. I spent the majority of my efforts on developing the Card, PlayerHand,
GameDisplay, and CenterDecks classes. 

**Ryleigh Byrne (rmb96)**

My assigned module was the controller module that contains the code for the integration of the view and the model, 
as well as handling the overall flow of the game. I worked on designing the controller module and implementing, designing, 
debugging, and testing these classes. I worked on implementing the observer/observable design patter within the 
controller. I worked heavily to debug integration issues in both the view and the model. Overall I led the 
integration of the application and was responsible for connecting the frontend and the backend through the controller, 
keeping them separated and fully independent of each other. 
### Resources Used

* Design patterns book: Head First Design Patterns by Freeman & Freeman
* Property change listener
  documentation: https://docs.oracle.com/javase/tutorial/uiswing/events/propertychangelistener.html
* Run time
  exceptions: https://stackoverflow.com/questions/2190161/difference-between-java-lang-runtimeexception-and-java-lang-exception

### Running the Program

Main class: Main.java

Data files needed: data module folder, resources folder. 
To create a game, a json file is needed which is in the format of the files in 
data/UnoGames. This file describes things in the game like the cards, type of Uno, 
and more. 

#### Features implemented

Fully Integrated Gameplay Features

* start a new basic default game
* play a card
* CPUs playing their turns
* drawing cards automatically when no cards in your hand are valid to play
* updating the top card in the discard deck when cards are played
* updating the CPUs hands when playing turns
* checking win/lose conditions and displaying that to the user
* changing themes

Model Features

* all game logic & game actions are fully implemented
* checks for invalid cards & conditions that would break the game, and throws exceptions to the
  controller/data modules
* action executors that work with controller to execute action cards such as draw 2, reverse 1, skip
  2, etc. are fully implemented in the model

View Features

* Users can choose between 12 font options, 7 theme options, and 4 font-size options that are
  dynamically implemented.
* The program can dynamically change between 20 language options.
* Users can create, delete, edit, and switch between existing profiles.
* Users can edit their profile username, profile picture, password, and biography.
* Users can generate a custom game where they are able to change the number of players, number of
  default cards, game version, and card deck.

#### 4 Game Variations

Basic

* a description of our basic UNO can be found in the doc/EXAMPLE_GAMES.md under the Regular UNO
  section.

Random

* the next player is decidedly randomly
* the same player can go twice in a row
* this means that playing cards like draw 2 could actually cause you to draw 2 cards yourself

Pirate

* A number 7 card of any color is a Pirate Card
* Playing this card will cause you to swap your hand of cards with a random player's hand of cards
* This is a risky card to play -- you might end up with fewer or more cards than before!

Modded (Angry Birds!)

* different data values in the json file
* backCardImagePath key in json files allows users to change the image display on the back of the
  cards

### Notes/Assumptions

Note:

* Output statements that track game play are printed to the data/gameRunLogs, these files can be used
  to track what is happening in the game and each player's cards.

Assumptions or Simplifications:

* start with the user playing first (originally random, changed to this to best show integrated
  functionality)
* play 1 card during a player's turn (unless wild card is played)
* 2-4 players in an UNO game
* numbers can only be 0 or positive
* players must start with 0 or more cards
* wild cards must be of color none
* if the type of the card is not a generic type recognize in UNO: ...., then it will behave like a
  number card
* When checking if an unstarted game is possible to create, we check that the number of cards in the
  draw deck is at least the number of players * number of cards per player + 2 (a constant variable
  in the Config file). Two was chosen here because that would allow for one card in the play deck
  and one card in the draw deck and allowed us to test small decks with expected behaviors, however,
  this value can easily be changed.
* for json data files, an unstarted game (aka started key set to false) will ignore the players and
  playDeck keys if they are provided
* for simplicity, in the game area editor, a card can only take on 2 types, but this can be easily
  expanded by adding more pop-ups
* for simplicity, the maximum number of profiles users can create is 10. After that, an existing
  profile needs to be deleted.

Interesting data files:

* bad data files in data/UnoGames/badInputFiles
* BasicUnoDefault.json in data/UnoGames
* BasicUnoNumbersAndReverse.json in data/UnoGames
* ModdedUno.json in data/UnoGames
* PirateUno.json in data/UnoGames
* RandomTurnsUno.json in data/UnoGames
* TwoPlayerUno.json in data/UnoGames

Known Bugs:

* valid cards that are highlighted to be shown to the player can sometimes be incorrect
    * this sometimes leads to a "stuck" game because you are shown valid cards, but they are
      actually invalid to play
    * restart the game if this happens
* sometimes the view doesn't correctly update the drawn card after the real player automatically
  draws a card
* sometimes if one of the CPU players displayed on the sides of the screen has too many cards, the
  real player's cards will get pushed below the visible area of the screen
* action cards other than reverse are not currently functional through the controller pipeline
    * we have made a deck of all numbers and reverse cards (
      data/UnoGames/BasicUnoNumbersAndReverse.json) that do not include the faulty cards

#### Challenge Features:

*Saving & loading games* We are able to load all features of a game out of the json file
describing it, including if the game has already started and has players. We can currently
save all aspects of a game except for the pictures on the back of the card. 

*Player Profiles* – Users are able to create new profiles, edit the information in these profiles,
and switch between other existing profiles. The program is able to remember user preferences (
language, theme, size, font), and these preferences will be implemented upon user login. Users can
edit their username, biography, and password, and they can select from a wide range of options for a
profile picture.

*Game Area Editor* – Users are able to generate a custom game. They can set the number of players,
number of default cards, and game version. Additionally, they can create custom cards that will be
added to the basic deck. This custom game is saved to a JSON file and uploaded immediately upon a
user generating a custom game.

### Impressions

We thought this project was awesome! It was our first time breaking down such a big and complex
game, which seemed much easier to implement at first thought. We were used to getting a list of
different features our project needed to include, so it was fun but challenging to decide that for
ourselves. We had to really break the game down and understand all the rules and card behaviors in
UNO, and some of us barely knew the game when we started! It was definitely a very challenging
project to take on, and we wished we had more time to really iron out all the bugs and present a
polished project. Despite our best attempts to create a good set of APIs, there were some moving
parts that we hadn't originally thought of and had to work out on the fly. However, we definitely
worked hard and overcame a lot of issues during this project, so it was a great way to end the
course.

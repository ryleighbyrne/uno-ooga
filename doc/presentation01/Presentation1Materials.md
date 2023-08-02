# Functionality Implemented in Each Module


## Model:
* Can create a new game of UNO by giving game the # of players, number of cards for each player to start with, and information on all of the cards in this game
* Can play cards & draw cards with validation of “same card types” → aka red skip can be played on red #7, green reverse can be played on blue reverse, etc.
* Functionality implemented for all types of cards (number, skip, reverse, draw, wild)
* Draw deck never runs out, can shuffle decks, can sort your hand of cards (easier for player to see cards)
* Can check to see if there’s a winner

###Model Next Features to Add:
* Work with controller to add observer/observable patterns to help with execution of some cards (ex: draw 2 being played by multiple players in a row, need to track the total # of cards to be drawn, need to get user input)
* Refactor code to be cleaner & use reflection for subclass instantiation
* Implement other gameplay variations (multiple cards played in a turn, work with controller for fast turns variation)
* See if AI player and Real player classes need to be fleshed out more
* Organize exceptions / create more exception classes
* Refactor enums card color class to be made dynamically so the user can choose what card colors are in this game for customization

## View:
* View can dynamically switch between 5 languages
* Profile settings: currently can view information about user and switch to guest
* Design settings: can switch between 7 different theme options
* Initial game setup: load/upload existing file, create custom game or start a basic game
* Game play: select a card and play it or draw a new card from the deck

### View Next Features
* Add more resource files for more language options
* Profile settings: switch users and create/delete user
* Design settings: changing font and font size
* Once connected with controller, initialize game play

## Controller:
* Controller generates initial view of application
* DataController class initializes new game with a given file, still needs connection to view
* Methods to populate view with player hand is set up in DataController, needs connection to front end
* Logic for connecting to model for playing one card is flush out, still needs connection to view

### Controller features to add:
* Working on planning out how to implement Observer/Observed design pattern using property change observables - still very much a work in progress to fully implement this
* AI player game flow needs implemented (handling choosing which card CPU player should play if more than one card is valid)
* Testing for cotntroller needs flushed out once it is connect to both model and view

## Data:
* Can load in a new game from a JSON file and return the corresponding Game object
* Can save/add a new profile to the profiles “database”
* Can load information about a profile from the “database”



### Data features to add:
* Actually make a real database for the profiles instead of storing them in a JSON file
* Handle other features in a game’s JSON file besides just the draw deck (shouldn’t be too difficult)
* Save a current game to a JSON file
* Edit a given profile (should be easier if we have a real database instead of 1 JSON file)

# Extension Features

## Load/Save Game:
* Allow users to add new game variants by loading a set of configuration files or load a previously saved game to resume playing it.
* Allow users to save the current state of any active game, so they can suspend a game, come back later, and load the game state back in to continue playing.
* PROGRESS: // FILL IN

## Player Profiles:
* Allow users to log in, choose an avatar to be used within the game player, view personal high scores, and save their preferences (e.g., name, password, image, age (if parental controls are implemented), and favorite variants, tokens, colors, etc). Player information should be saved between runs of the program.
* PROGRESS:
  * Currently the current user is hard coded, eventually will be able to switch between all available users.
  * Password check
  * Users can switch to guest

## Game Area Editor:
* Allow users to design the game's initial configuration and its content dynamically using a GUI, rather than just editing text data files. Users should be able to load any existing data for editing or save their design in the common format.
* Need connection between frontend and backend

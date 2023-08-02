Functionality Implemented in Each Module

Model:
* Added functionality for other game variations
* Added lots of exceptions for edge cases / bad input
* Connected with controller to implement actions cards
* Implemented observer/observable pattern
* Refactored stacking of actions



Model Next Features to Add:
* Add support for all cheat keys
* Test action cards through controller

View:
* Added functionality to create, switch, and view profiles

View Next Features
* Allow users to create a custom deck

Controller:
* Implemented observer/observable pattern
* Connected controller with model

Controller features to add:
* Need to connect controller with view
* Need to add controller tests
* Need to validate entire game flow
* Need to add functionality to handle all the buttons in view

Data:
* Can save an existing game to a json file, can load in a new game from json file
* Can load in a paused game, with players that each have a hand, and use that to resume playing

Data features to add:
* Move the player profiles to a Firebase database
* Rigorous checking that the game files are logically correct
* Use reflection based on the Unotype key in the game file, to create a Game of the appropriate subclass

Extension Features

Load/Save Game:
* Loading an “unstarted” game works fine
* Loading a “started” game is Mostly working on keith’s branch at the time of the presentation but not yet merged, still needs some changes
* Saving a game is complete, although will need a little more work for saving customized games with the game editor

Player Profiles:
* Works with almost full functionality (aside from editing player profiles), but we are working on moving the profiles to an actual database instead of just a json file.

Game Area Editor:
* Have not worked on it yet at the time of the presentation


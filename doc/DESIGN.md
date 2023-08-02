# OOGA Design Final

### Alicia Wu, Cate Schick, Keith Cressman, Luke Josephy, Ryleigh Byrne

## Team Roles and Responsibilities

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

I worked on the ooga/data package. I was responsible for code to create a game based on a json file,
as well as saving a game to a json file of the same format. I also worked on some methods to get
specific values out of these json files. In addition, I worked on handling player profiles (
adding/removing/editing profiles).

**Luke Josephy (lrj11)**

I worked entirely on the View for this project. More specifically, I focused on the game play itself
with regards to the front-end. I spent the majority of my efforts on developing the Card,
PlayerHand, GameDisplay, and CenterDecks classes.

**Ryleigh Byrne (rmb96)**

My assigned module was the controller module that contains the code for the integration of the view
and the model, as well as handling the overall flow of the game. I worked on designing the
controller module and implementing, designing, debugging, and testing these classes. I worked on
implementing the observer/observable design patter within the controller. I worked heavily to debug
integration issues in both the view and the model. Overall I led the integration of the application
and was responsible for connecting the frontend and the backend through the controller, keeping them
separated and fully independent of each other.

## Design goals

#### What Features are Easy to Add

There are many features of the program that are relatively easy to add new modifications to. One
example of this is the ability of users to add custom .css style sheets or custom language resource
files to the program. The program is able to read files in the theme and language repository and
return the files in them; therefore, if a user were to upload custom files, they would just have to
make sure to add keys for these filenames in the program's existing resource files. Similarly, we
could add new themes and languages to the program in its later releases.

Additionally, it is easy to create new profiles, and add functionality to the existing profiles.
To do this, it would just be necessary to add the desired keys in profiles.JSON. An example
of extension to this feature would include allowing the user to upload custom profile pictures to their profiles.

## High-level Design

We wanted to create a design that would allow for new UNO variations to be added easily and make
reusable components that could be extended for different applications. Our UNO variations share the
same logic of game creation, data parsing, view components, and game execution logic, but have
slight differences in implementation.

#### Core Classes

**Model**
The model package is responsible for implementing game logic. The controller mainly interacts with
the model through the CardGame interface. This is an abstraction that hides implementation details
on how game logic, game components, and player actions are implemented. The controller module also
observes Executor instances in the model using the observer/observable pattern to be notified when a
card is played by another player that will impact the user directly.

**Controller**

The controller package is responsible for communicating changes in the view to the model, and the model to 
the view, keeping them completely independent of one another. The controller also uses model APIs to handle 
logic regarding a player's turn, and communicates updates in the game to view and the model appropriately. 
The Controller class is responsible for initializing the application and manages the playing out of turns and delegating
updates in the model. The DisplayController class works with the Controller class to handle updates to and from the view
when the game changes (i.e, a turn is played). The DataController class is responsible for working with the data module 
to initialize a new game with a chosen/default file. The ExecutorObserver class handles communication with the card action 
executors and handles when a card is played by another player that impacts the user (like skip or draw). The ReflectionMethodHandler
class handles reflection of methods, specifically used by the DisplayController and Controller classes when an observable 
these classes are observing gets changed.

**Data**

The data package is responsible for handling the game files (in .json format) and the profiles, which are
all stored in a .json file. The GameStarter class is used to create a game instance given the path to a
.json game file. The GameSaver class is used to take in a Game instance and create/save a correpsonding
.json file describing the game. The ProfileHandler class is used to access or edit profiles in the database.

The data/dataExceptions package contains a bunch of Exception classes that are used to signify problematic situations
with the data. The data/dataResources package contains DataConfig.java, a class which just stores certain constant variables
and keys to the data files. The rest of the cards in the data package are mostly there for ease of converting
between JSON code and Java objects (like a Card object).

**View**

The View is responsible for creating and showing the user displays.

Classes in the display package are responsible for generating this content and returning BorderPanes
containing buttons, texts, and other interactive elements for the user to navigate their way through
the program. There are packages within the display package that focus on specific elements of the
program (like design settings or game settings).

Classes in the dialog, button, and userAlert packages focus on interactive elements for the user. As
the name suggests, these classes present dialogs, buttons, and alerts to the user, respectively.
These classes work with the content in the displays to ensure that the user is able to interact with
the program and either input information, click on buttons, or be notified when something goes
wrong.

Resources.java focuses on the resource files and generating text for the program. The class is
written in a way where it is easy for the user to dynamically change the language of the program and
search for certain keys within resources files.

These classes interact to create an engaging GUI that the user can use to navigate the game and its
additional features.

## Assumptions that Affect the Design

Assumptions that affect design:

* wild cards must be of color none
* real player always has id #0

#### Features Affected by Assumptions

These assumptions were made initially to simplify the implementation of UNO while we worked on
getting a basic, integrated version of the game.

We require that wild cards be of card color none when loading in a game file or making a custom
game. This was a simplification we made because it made sure that wild cards would be validated
correctly and could be played on any card. This made our design and implementation simpler, but can
definitely be refactored because it makes the controller check to see if a card of color none is
being played and if so, asks the user for another card. However, if we wanted to create a new card
of color none that has a different behavior than the wild card, this simplification could be
problematic. This simplification made the implementation of our current features easier, but given
more time, we would remove it and refactor our code accordingly to be more flexible and extendable.

Our second assumption affecting design is that the real player (aka the user) always has the id #0.
This was a simplification we made to make it easier for the view, model, and controller to all
identify the real player easily, but it is not the best design choice. In the current design, there
are times when a player's id is checked and different actions are taken depending on whether the
player is a real player or not. However, we could refactor our code to encapsulate more of this "
decision" logic and create an abstraction that would allow for different actions to be executed
depending on what type of player it is. For instance, we could use polymorphism to do this and
create a hierarchy of classes/interfaces whose sole purpose is to execute this logic and instantiate
them via reflection depending on what subclass a Player instance is (RealPlayer class vs AIPlayer
class). We could do this in the view and model and create different controller classes to handle
their communication. Given more time, this is also something that we would definitely work on to
remove this assumption and improve the design of our code. This new design would also support the
addition of multiple real players, allowing for multiple people to play the game together in "
hotseat" mode.

## Significant differences from Original Plan

There are some differences from our original plan as we were not able to implement all the features
we had planned, but overall we followed the same high level design that we had initially planned. 
Some of the integration did not go as planned, and there are bugs when updating the valid cards played. This 
can make game play difficult. Although we stuck to the plan when implementing this feature, we ended up running out of 
time to fully debug this issue. Overall, we were able to follow our plan pretty closely. A significant design decision that
was not in the original plan was the implementation of the **Observer Design Pattern.** This choice ended up being one that 
benefited the progression of our project greatly, as we were able to implement challenge features like the Game Area Editor.
We ultimately wanted to support play that did not rely on the assumption that the real player will start each turn, but had to 
make this change when debugging, in order to show off the other work we did for the project. This difference impacted the extendability 
of our project, as now only one real player is supported. This was not ultimately in the plan, as we had hoped to leave 
our project open to extension of different types of players. We also did not have time to implement all 12 cheat codes. 
## New Features HowTo

#### Easy to Add Features

* An easy feature to add would be saving games to a file in the middle of the game. The back end for this is all set up.
  The only necessary change would be a button which, upon being clicked, would save the game.

* Adding new game variations would also be fairly easy. Many variations can be accomplished
  simply by adding a new subclass of Game, similar to PirateGame. You would also have to add on to the two properties
  files in src/data/dataResources, as these enable the use of reflection to create an instance of the correct subclass.


#### Other Features not yet Done

If presented with more time, there are a few additions we would have made to our program. For one,
we would have introduced the ability to generate two gameplays simultaneously. This was not
prioritized as we felt it was not integral to the spirit of UNO, but it would be an enjoyable
addition for players who seek a bit more stimulation. Furthermore, we would have introduced proper 
simulation of cards being played. In other words, the CPUs turns are essentially skipped from the view and
completed immediately. However, we would have created a stepping mechanism to simulate what feels
like a human opponent contemplating their decision. Lastly, with regard to the custom game editor,
we would have created a live display of a new card being created. Currently, creating a new card to
add to the deck solely asks what features the user wants to add, but we would have created a
real-time generation of the card, to show the user what the card they are creating looks like.

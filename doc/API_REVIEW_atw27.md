## API REVIEW - atw27

CardCollection API

### Part 1

How does your API encapsulate your implementation decisions?

* doesn't expose how the cards are stored / maintained in the class
* focuses on interactions with the collection of cards that the interface represents

What abstractions is your API built on?

* card collection is just a group of cards that can be interacted with
* cards can be added / removed from the card collection and transferred to other card collections
* cards represent a card in a card game

What about your API's design is intended to be flexible?

* actions are referred to in a general way
    * for example - reorganizing a card collection can mean sorting for a hand of cards and
      shuffling for a deck of cards

What exceptions (error cases) might occur in your API and how are they addressed?

* if a user tries to remove a card that is not found in this card collection a CardNotFoundException
  will be thrown
* if a user tries to transfer out a card that is not found in this card collection a
  CardNotFoundException will be thrown

### Part 2

How do you justify that your API is easy to learn?

* method names are easy to read and understand
* comments can also provide direction for user of API

How do you justify that API leads to readable code?

* the API helps outline specific interactions that can be made with this abstraction
* helps person implementing the API organize their ideas into different interactions/the given
  methods to override

How do you justify that API is hard to misuse?

* interactions that would cause exceptions are handled
* API clearly outlines the public methods of this abstraction

Why do you think your API design is good (also define what your measure of good is)?

* it does a good job of encapsulating implementation details and focuses on interactions
* it is easy to read and understand
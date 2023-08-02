## API REVIEW - LAB
### Luke Josephy (lrj11) - Front-end (w/ hh210)

### What I got out of it:
* Some of my classes have become cluttered and could use refactoring
  * PlayerHand, specifically, is relatively difficult to digest and could be differentiated into a class for the user's hand and a class for the opponent's hands
* The Card factory is particularly useful and well-designed
  * It just needs to be finished and account for more types of cards
* I am missing comments on some of my public methods, which leads to a greater difficulty in understanding the purpose and parameters
* The implementation of the actual displaying of screens is well-designed, as the switchDisplay and setDisplayAs methods make it very easy to add and utilize new displays.
* Using a map would probably be more efficient in both calling the specific hand in question, and adding cards to the aforementioned hand
  * As opposed to the current multiple methods being used to create each individual hand in a different manner
* Overall, I think we're making good progress in properly designing the front-end!
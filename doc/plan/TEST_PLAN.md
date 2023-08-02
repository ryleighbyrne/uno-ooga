## Testing Plan

### Testing Strategies

1. Team members should develop a test soon after developing each function / method. This will allow its functionality to be fresh of mind and will allow for easier debugging later on when knowing which methods work as designed.
2. Testing should be done at growing sizes. For example, as explained above, we plan to test each function individually after its implementation, and then we will begin to test the integration of different methods, and then the integration of classes, and then the overall integration of view, engine, and model. 

### Feature Testing 

1. Pass button tests
   1. Clicking pass when in turn should allow the next player to go 
   2. Clicking pass when out of turn should throw an error
   3. One test should check that the pass button is existent and not null
   
2. Playing a card tests
   1. One test should check that the card/rectangle is existent and not null
   2. Clicking the card (provided it's playable) when in turn should update the active deck to show that card and progress the game to the next player
   3. Clicking the card (when it's not playable) should throw an error
   
3. Draw Button Tests
   1. Clicking draw when in turn should update the draw deck count and add a new card to the user's deck
   2. Clicking draw when out of turn should throw an error
   3. One test should check that the pass button is existent and not null

4. "UNO" Tests
   1. When a player has one card left, it should throw a message saying "UNO"
   2. If all players have at least one card, no message should be thrown
   3. If a player has no cards left, a message ending the game should be thrown
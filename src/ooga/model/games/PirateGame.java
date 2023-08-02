package ooga.model.games;

import java.util.List;
import java.util.Map;
import java.util.Random;
import ooga.Config;
import ooga.model.cardcollection.CardCollection;
import ooga.model.cardcollection.DeckCardCollection;
import ooga.model.cardcollection.hand.PlayerHand;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;
import ooga.model.players.Player;

public class PirateGame extends Game {

  public PirateGame(int numPlayers, int numCardsToBeginWith,
      Map<CardColor, List<CardInfo[]>> allCardsInfo) {
    super(numPlayers, numCardsToBeginWith, allCardsInfo);
  }

  public PirateGame(DeckCardCollection drawDeck,
      DeckCardCollection playDeck,
      List<Player> allPlayers) {
    super(drawDeck, playDeck, allPlayers);
  }

  @Override
  /**
   * if the card played is a #7, the player's hand will be swapped with a random player's hand
   *
   * @param cardToPlay card the current player will play
   */
  public void currPlayerPlayCard(Card cardToPlay) {
    super.currPlayerPlayCard(cardToPlay);
    if (isPirateCard(cardToPlay)) {
      executePirateSwap();
    }
  }

  private void executePirateSwap() {
    CardCollection currPlayerHand = getCurrPlayer().getPlayerHand();
    int randomPlayerId = new Random().nextInt(getNumPlayers());
    while (randomPlayerId == getCurrPlayerId()) {
      randomPlayerId = new Random().nextInt(getNumPlayers());
    }
    CardCollection randPlayerHand = getPlayerById(randomPlayerId).getPlayerHand();
    CardCollection tempHand = new PlayerHand(Config.EMPTY_CARD_COLLECTION);
    currPlayerHand.transferAll(tempHand);
    randPlayerHand.transferAll(currPlayerHand);
    tempHand.transferAll(randPlayerHand);
  }

  private boolean isPirateCard(Card cardToPlay) {
    for (CardInfo info : cardToPlay.getCardInfo()) {
      if (Config.PIRATE_SPECIAL_TYPE.equals(info.getType())
          && Config.PIRATE_SPECIAL_NUM == info.getParam()) {
        return true;
      }
    }
    return false;
  }
}

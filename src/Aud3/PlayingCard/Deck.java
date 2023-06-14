package Aud3.PlayingCard;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {

    private PlayingCard[] cards;
    private boolean[] isDealt;
    private int dealtTotal;

    public Deck() {
        cards = new PlayingCard[52];
        for (int i = 0; i < PlayingCardType.values().length; i++) {
            for (int j = 0; j < 13; j++) {
                cards[i * 13 + j] = new PlayingCard(j + 2, PlayingCardType.values()[i]);
            }
        }
    }

    public PlayingCard[] getCards() {
        return cards;
    }

    public void setCards(PlayingCard[] cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(" ");
        for (PlayingCard card : cards)
            s.append(card.toString()).append("\n");

        return s.toString();
    }

    public boolean hasCardsLeft() {
        return (cards.length - dealtTotal) > 0;
    }

    public PlayingCard[] shuffle()
    {
        List<PlayingCard> playingCardTypeList=Arrays.asList(cards);
        Collections.shuffle(playingCardTypeList);
        return cards;

    }
    public PlayingCard dealCard()
    {
        if(!hasCardsLeft())
            return null;
        int card=(int)(Math.random()*52);
        if (!isDealt[card])
        {
            isDealt[card]=true;
            dealtTotal++;
            return cards[card];
        }
        return dealCard();
    }
}


package Aud3.PlayingCard;

public class PlayingCard {

    private int rank;
    private PlayingCardType playingCardType;

    public PlayingCard(int rank, PlayingCardType playingCardType) {
        this.rank = rank;
        this.playingCardType = playingCardType;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public PlayingCardType getPlayingCardType() {
        return playingCardType;
    }

    public void setPlayingCardType(PlayingCardType playingCardType) {
        this.playingCardType = playingCardType;
    }

    @Override
    public String toString() {
     return String.format("%d %s",rank,playingCardType.name());
    }


}

package blackjack;

/**
 * Card - A Card object representing one playing card from the standard deck
 */
public class Card {	
	private CardValue value;
	private CardSuit suit;
	private boolean faceUp;

	public Card(CardValue value, CardSuit suit) {
		this.value = value;
		this.suit = suit;
		this.faceUp = true;  // By default cards are face up
	}
	
	public CardValue getValue() {
		return value;
	}
	
	public CardSuit getSuit() {
		return suit;
	}
	
	public boolean isFacedUp() {
		return faceUp;
	}
	
	public void setFaceUp() {
		faceUp = true;
	}

	public void setFaceDown() {
		faceUp = false;
	}
	
	public boolean equals(Object o){
		if (value == ((Card)o).value && suit == ((Card)o).suit)
			return true;
		return false;
	}
	
	public int hashCode() {
		return value.getIntValue() * 4 + suit.ordinal();	
	}
	
	public String toString() {
		String result = "";
		result += value + " of " + suit + " ";
		result += (faceUp ? "(FaceUp)": "(FaceDown)");
		return result;
	}
}
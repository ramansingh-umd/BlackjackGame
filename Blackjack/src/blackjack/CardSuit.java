package blackjack;
public enum CardSuit {
	SPADES("s"), DIAMONDS("d"), HEARTS("h"), CLUBS("c");
	
	CardSuit(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	private String value;
}
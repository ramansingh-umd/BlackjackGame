package blackjack;

public enum CardValue { 
	Ace("1"), Two("2"), Three("3"), Four("4"), Five("5"), Six("6"), Seven("7"), 
	Eight("8"), Nine("9"), Ten("10"), Jack("J"), Queen("Q"), King("K");
	
	CardValue(String valueIn) { value = valueIn; }
	public String getValue() { return value; }
	public int getIntValue() { 
		switch(this) {
			case Jack:
				return 10;
			case Queen:
				return 10;
			case King:
				return 10;
			default:
				return Integer.parseInt(value);
		}
	}
	private String value;
}
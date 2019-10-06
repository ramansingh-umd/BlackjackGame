package blackjack;

/**
 * BlackJackEngine - interface defining the methods expected from a class
 * 					 implementing the Blackjack game logic.
 * 
 * @see Card
 */
public interface BlackjackEngine {
	public static final int DRAW 			 = 1;
	public static final int LESS_THAN_21 	 = 2;
	public static final int BUST 	 		 = 3;
	public static final int BLACKJACK 		 = 4;
	public static final int HAS_21	 		 = 5;
	public static final int DEALER_WON 		 = 6;
	public static final int PLAYER_WON 		 = 7;
	public static final int GAME_IN_PROGRESS = 8;
	
	/**
	 * Returns the number of decks being used. 
	 * @return number of decks
	 */
	public int getNumberOfDecks();
	
	
	/**
	 * Creates and shuffles the card deck(s) using a random number generator.
	 */
	public void createAndShuffleGameDeck();
	
	
	/**
	 * Returns the current deck of cards.
	 * @return Card array representing deck of cards.
	 */
	public Card[] getGameDeck();
	
	
	/**
	 * Creates a new deck of cards, and assigns cards to the dealer and player.
	 * A total of four cards are dealt in the following order:
	 * Player (face up), Dealer (face down), Player (face up), Dealer (face  up).
	 * Once the cards have been dealt, the game's status will be GAME_IN_PROGRESS.
	 * Delete the bet amount from the account.
	 */
	public void deal();
	
	/**
	 * Returns dealer's cards.
	 * @return Card array representing the dealer's cards.
	 */
	public Card[] getDealerCards();
	
	
	/**
	 * Returns an array representing the possible value(s) associated with the 
	 * dealer's cards if the cards represent a value less than or equal to 21.
	 * @return Integer array representing the possible value(s) or null if cards
	 * represent a value higher than 21.  The array will have a size of 1 if only
	 * one value is associated with the set of cards, and a size of two if two
	 * values are possible.  For the case of an array of size two, the smaller value
	 * must appear in the first array entry.
	 */
	public int[] getDealerCardsTotal();
	
	
	/**
	 * Returns an integer value that can assume the values
	 * LESS_THAN_21 if the dealer's cards have a value less than 21,
	 * BUST if the dealer's cards have a value greater than 21, and
	 * BLACKJACK if the dealer has an Ace along with a "10", Jack, Queen, or King.
	 * If the dealer's cards have a value equivalent to 21 and the hand does
	 * not correspond to a blackjack, HAS_21 will be returned.
	 * @return Integer value that corresponds to one of the following: 
	 * LESS_THAN_21, BUST, BLACKJACK, HAS_21
	 */
	public int getDealerCardsEvaluation();
	
	
	/**
	 * Returns player's cards.
	 * @return Card array representing the player's cards.
	 */
	public Card[] getPlayerCards();
	
	
	/**
	 * Returns an array representing the possible value(s) associated with the 
	 * player's cards if the cards represent a value less than or equal to 21.
	 * @return integer array representing the possible value(s) or null if cards
	 * represent a value higher than 21.  The array will have a size of 1 if only
	 * one value is associated with the set of cards, and a size of two if two
	 * values are possible.  For the case of an array of size two, the smaller value
	 * must appear in the first array entry.
	 */
	public int[] getPlayerCardsTotal();
	
	
	/**
	 * Returns an integer value that can assume the values
	 * LESS_THAN_21 if the player's cards have a value less than 21,
	 * BUST if the players's cards have a value greater than 21, and
	 * BLACKJACK if the player has an Ace along with a "10", Jack, Queen, or King.
	 * If the players' cards have a value equivalent to 21 and the hand does
	 * not correspond to a blackjack, HAS_21 will be returned.
	 * @return Integer value that corresponds to one of the following: 
	 * LESS_THAN_21, BUST, BLACKJACK, HAS_21
	 */
	public int getPlayerCardsEvaluation();
	
	/**
	 * Retrieves a card from the deck and assigns the card to the player.
	 * The new sets of cards will be evaluated.  If the player busts, the game
	 * is over and the games's status will be updated to DEALER_WON.  Otherwise
	 * the game's status is GAME_IN_PROGRESS.
	 */
	public void playerHit();
	
	
	/**
	 * Flips the dealer's card that is currently face down 
	 * and assigns cards to the dealer as long as the dealer 
	 * doesn't bust and the cards have a value less than 16.  Once the dealer
	 * has a hand with a value greater than or equal to 16, and less than or equal to 21, 
	 * the hand will be compared against the player's hand and whoever has the
	 * hand with a highest value will win the game. If both have the same value 
	 * we have a draw.  The game's status will be updated to one of
	 * the following values: DEALER_WON, PLAYER_WON, or DRAW.  The player's
	 * account will be updated with a value corresponding to twice the bet amount if 
	 * the player wins.  If there is a draw the player's account will be updated
	 * with the only the bet amount. 
	 *
	 */
	public void playerStand();
	
	
	/** 
	 * Updates the bet amount to the provided value 
	 */
	public void setBetAmount(int amount);
	
	
	/**
	 * Returns an integer representing the bet amount.
	 * @return bet amount.
	 */
	public int getBetAmount();
	
	
	/**
	 * Updates the player's account with the parameter value.
	 * @param amount 
	 */
	public void setAccountAmount(int amount);
	
	
	/**
	 * Returns the player's account amount
	 * @return account amount
	 */
	public int getAccountAmount();
	
	
	/**
	 * Returns an integer representing the game status.   
	 * @return DRAW, PLAYER_WON, DEALER_WON OR GAME_IN_PROGRESS
	 */
	public int getGameStatus();
}
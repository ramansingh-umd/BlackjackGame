// 09/05/2017
// Simulates a game of Blackjack

package blackjack;
import java.util.*;

public class Blackjack implements BlackjackEngine {

	private ArrayList<Card> deckOfCards;
	private ArrayList<Card> playerCards;
	private ArrayList<Card> dealerCards;
	private Random randomGenerator;
	private int gameStatus;
	private int account;
	private int bet;
	private int numberOfDecks;
	
	/**
	 * Constructor you must provide.  Initializes the player's account 
	 * to 200 and the initial bet to 5.  Feel free to initialize any other
	 * fields. Keep in mind that the constructor does not define the 
	 * deck(s) of cards.
	 * @param randomGenerator
	 * @param numberOfDecks
	 */
	public Blackjack(Random randomGenerator, int numberOfDecks) {
		deckOfCards = new ArrayList<Card>();
		playerCards = new ArrayList<Card>();
		dealerCards = new ArrayList<Card>();
		account = 200;
		bet = 5;
		this.numberOfDecks = numberOfDecks;
		this.randomGenerator = randomGenerator;
		deckOfCards = new ArrayList<Card>();
		gameStatus = GAME_IN_PROGRESS;
	}

	// Returns how many decks (of cards) are currently being used
	public int getNumberOfDecks() {
		return numberOfDecks;
	}

	// Clears the deck, creates a new deck, and shuffles afterwards
	public void createAndShuffleGameDeck() {

		CardValue[] values = CardValue.values();
		CardSuit[] suits = CardSuit.values();

		deckOfCards.clear();
		
		// Create the deck with as many decks desired
		for (int numDecks = 0; numDecks < numberOfDecks; numDecks++) {
			for (int suitIndex = 0; suitIndex < suits.length; suitIndex++) {
				for (int valueIndex = 0; valueIndex < values.length; valueIndex++) {
					deckOfCards.add(new Card(values[valueIndex], suits[suitIndex]));
				}
			}
		}

		Collections.shuffle(deckOfCards, randomGenerator);
	}

	// Returns the deck itself
	public Card[] getGameDeck() {
		return deckOfCards.toArray(new Card[deckOfCards.size()]);
	}

	// Clears the player's and dealer's hands and starts a new round of blackjack
	// Also takes away the bet amount from the total amount for the user
	public void deal() {

		playerCards.clear();
		dealerCards.clear();

		gameStatus = GAME_IN_PROGRESS;

		createAndShuffleGameDeck();

		// Always deal cards in this order
		
		playerCards.add(deckOfCards.remove(0));		
		
		Card faceDown = deckOfCards.remove(0);
		faceDown.setFaceDown();
		dealerCards.add(faceDown);
		
		playerCards.add(deckOfCards.remove(0));	
		dealerCards.add(deckOfCards.remove(0));
	
		account -= bet;
	}

	// Returns the dealer's hand
	public Card[] getDealerCards() {
		return dealerCards.toArray(new Card[dealerCards.size()]);
	}

	// Returns the dealer's possible total values
	public int[] getDealerCardsTotal() {		
		return getTotal(dealerCards);
	}

	// Return an evaluation of the dealer's hand
	public int getDealerCardsEvaluation() {	
		return getEvaluation(getDealerCardsTotal(), dealerCards);
	}

	// Return the player's hand
	public Card[] getPlayerCards() {
		return playerCards.toArray(new Card[playerCards.size()]);
	}

	// Return the player's possible total values
	public int[] getPlayerCardsTotal() {
		return getTotal(playerCards);
	}

	// Return an evaluation of the player's hand
	public int getPlayerCardsEvaluation() {
		return getEvaluation(getPlayerCardsTotal(), playerCards);
	}

	// Give the player another card
	public void playerHit() {

		playerCards.add(deckOfCards.remove(0));
		
		// Evaluate the new hand to check for a bust or not
		if (getPlayerCardsEvaluation() == BUST) {
			gameStatus = DEALER_WON;
		}
	}

	// Flips the dealer's card that is currently face down
	// Keep dealing cards to the dealer until he hits at least a soft 16 or busts
	public void playerStand() {

		dealerCards.get(0).setFaceUp();

		int[] dealerTotal = getDealerCardsTotal();

		// Dealer stops hitting when they bust or hit soft 16 or higher
		while (dealerTotal != null && dealerTotal[dealerTotal.length - 1] < 16) {	
			dealerCards.add(deckOfCards.remove(0));
			dealerTotal = getDealerCardsTotal();
		}

		int dealerEval = getEvaluation(dealerTotal, dealerCards);
		int playerEval = getPlayerCardsEvaluation();

		// The dealer's hand went over 21
		if (dealerEval == BUST) {
			gameStatus = PLAYER_WON;
			account += bet * 2;
		}

		// Both the player and the dealer have 21
		else if (playerEval != LESS_THAN_21 && dealerEval != LESS_THAN_21) {

			// Player has blackjack and dealer does not
			if (playerEval < dealerEval) {
				gameStatus = PLAYER_WON;
				account += bet * 2;
			}

			// Dealer has blackjack and player does not
			else if (dealerEval < playerEval) {
				gameStatus = DEALER_WON;
			}

			else {
				gameStatus = DRAW;
				account += bet;
			}
		}

		// Both the player and the dealer have a value less than 21
		else {

			int[] playerTotal = getPlayerCardsTotal();

			int playerAmount = playerTotal[playerTotal.length - 1];
			int dealerAmount = dealerTotal[dealerTotal.length - 1];

			// Check for who has the higher amount in their hand
			if (playerAmount > dealerAmount) {
				gameStatus = PLAYER_WON;
				account += bet * 2;
			}

			else if (playerAmount < dealerAmount) {
				gameStatus = DEALER_WON;
			}

			else {
				gameStatus = DRAW;
				account += bet;
			}
		}
	}

	// Returns the current game status
	public int getGameStatus() {
		return gameStatus;
	}

	// Sets the bet amount
	public void setBetAmount(int amount) {
		bet = amount;
	}

	// Returns the bet amount
	public int getBetAmount() {
		return bet;
	}

	// Set the account amount
	public void setAccountAmount(int amount) {	
		account = amount;
	}

	// Returns the account amount
	public int getAccountAmount() {
		return account;
	}

	// Return the possible values for the current hand
	private int[] getTotal(ArrayList<Card> currentHand) {

		int size = 1;
		int numAces = 0;

		// Check if current hand contains an ace
		for (Card aCard : currentHand) {

			if (aCard.getValue() == CardValue.Ace) {
				size = 2;
			}
		}
		
		int[] toReturn = new int[size];

		// Add up the possible total values
		for (Card aCard : currentHand) {
			
			if (aCard.getValue() == CardValue.Ace) {
				
				numAces++;
				
				// After the first ace, the ace's value should never be 11
				if (numAces > 1) {
					toReturn[1] += 1;
				}
				
				else {
					toReturn[1] += 11;
				}
				
				toReturn[0] += 1;
			}
			
			else {
				
				toReturn[0] += aCard.getValue().getIntValue();
				
				if (toReturn.length > 1) {
					toReturn[1] += aCard.getValue().getIntValue();
				}
			}
		}
		
		if (toReturn[0] > 21) {
			return null;
		}
		
		if (toReturn.length > 1 && toReturn[1] > 21) {
			return new int[] {toReturn[0]};
		}
		
		return toReturn;
	}
	
	// Return an evaluation of the current hand
	public int getEvaluation(int[] possibleValues, ArrayList<Card> currentHand) {

		if (possibleValues == null) {
			return BUST;
		}

		int total = possibleValues[possibleValues.length - 1];

		if (total == 21) {

			if (currentHand.size() == 2) {
				return BLACKJACK;
			}

			return HAS_21;
		}

		else {
			return LESS_THAN_21;
		}
	}
}
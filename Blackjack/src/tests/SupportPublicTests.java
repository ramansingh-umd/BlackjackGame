package tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import blackjack.*;

public class SupportPublicTests {
	public static String pubTest1DeckCreation() {
		Blackjack blackjack = setBlackJack();
		blackjack.createAndShuffleGameDeck();
		Card[] deck = blackjack.getGameDeck();
		return (getCardsString(deck));
	}
		
	public static String pubTest2GeneralValues() {
		Blackjack blackjack = setBlackJack();
		
		blackjack.createAndShuffleGameDeck();
		
		String results = "Number of Decks: ";
		results += blackjack.getNumberOfDecks() + "\n";
		
		blackjack.setBetAmount(20);		
		results += "Bet Amount: " + blackjack.getBetAmount() + "\n";
		
		blackjack.setAccountAmount(300);
		results += "Account Amount: " + blackjack.getAccountAmount();
		
		return results;
	}

	public static String pubTest3Deal() {
		Blackjack blackjack = setBlackJack();
		
		blackjack.deal();
		
		String results = "Dealer's Hand: " + "\n";
		results += getCardsString(blackjack.getDealerCards()) + "\n";
		
		results += "Players's Hand: " + "\n";
		results += getCardsString(blackjack.getPlayerCards()) + "\n";
		
		results += "Deck: " + "\n";
		results += getCardsString(blackjack.getGameDeck()) + "\n";

		String status;
		if (blackjack.getGameStatus() == Blackjack.GAME_IN_PROGRESS)
			status = "GAME_IN_PROGRESS";
		else
			status = "ERROR: Invalid state";
		
		results += "Status: " + status + "\n";
		
		return results;
	}

	public static String pubTest4Play1() {
		Blackjack blackjack = setBlackJack();
		
		blackjack.deal();
		blackjack.deal();
		String results = getCardsString(blackjack.getPlayerCards()) + "\n";
		results += getCardsString(blackjack.getDealerCards()) + "\n";
		results += "*** Hit ***\n";
		blackjack.playerHit();
		results += getCardsString(blackjack.getPlayerCards());
		
		String playerEvaluation = mapIntString(blackjack.getPlayerCardsEvaluation());
		String dealerEvaluation = mapIntString(blackjack.getDealerCardsEvaluation());
		results += "Player: " + playerEvaluation + "\n";
		results += "Dealer: " + dealerEvaluation + "\n";
		
		results += "Game Status: ";
		results += mapIntString(blackjack.getGameStatus());
		
		return results;
	}
	
	public static String pubTest5Play2() {
		Blackjack blackjack = setBlackJack();
		
		blackjack.deal();
		blackjack.deal();
		String results = getCardsString(blackjack.getPlayerCards()) + "\n";
		results += getCardsString(blackjack.getDealerCards()) + "\n";
		results += "*** Stand ***\n";
		blackjack.playerStand();
		results += "Player's Cards: " + "\n";
		results += getCardsString(blackjack.getPlayerCards());
		
		results += "Dealer's Cards: " + "\n";
		results += getCardsString(blackjack.getDealerCards());
		
		String playerEvaluation = mapIntString(blackjack.getPlayerCardsEvaluation());
		String dealerEvaluation = mapIntString(blackjack.getDealerCardsEvaluation());
		results += "Player: " + playerEvaluation + "\n";
		results += "Dealer: " + dealerEvaluation + "\n";
		
		
		int[] playerValues =  blackjack.getPlayerCardsTotal();
		int[] dealerValues =  blackjack.getDealerCardsTotal();
		
		results += "Player's total(s): ";
		for (int i=0; i<playerValues.length; i++)
			results += playerValues[i] + "\n";
		
		results += "Dealer's total(s): ";
		for (int i=0; i<dealerValues.length; i++) 
			results += dealerValues[i] + "\n";
		
		results += "Game Status: ";
		results += mapIntString(blackjack.getGameStatus());
		
		return results;
	}
	
	public static boolean correctResults(String filename, String results) {
        String officialResults="";
        try {
            BufferedReader fin = new BufferedReader(new FileReader(filename));
            
            String line;
            while ((line = fin.readLine()) != null) {
                officialResults += line + "\n";
            }
            
        }catch (IOException e) {
            System.out.println("File opening failed.");
            return false;
        } 
        
        results = removeBlanks(results);
        officialResults = removeBlanks(officialResults);
        
        if (results.equals(officialResults)) {
            return true;
        }
        
        return false;
    }
	
	public static String removeBlanks(String src) {
		StringBuffer resultsBuf = new StringBuffer();
		
		char curr;
		for (int i=0; i<src.length(); i++) {
			curr = src.charAt(i);
			if (curr != ' ' && curr != '\n')
				resultsBuf.append(curr);
		}
		return resultsBuf.toString();
	}
	
	/**** Private methods ****/
	private static Blackjack setBlackJack() {
		Random randomGenerator = new Random(1234567L);
		int numberOfDecks = 1;
		Blackjack blackjack = new Blackjack(randomGenerator, numberOfDecks);
	    return blackjack;
	}
	
	private static String getCardsString(Card[] array) {
		String result = "";
		for (int i=0; i<array.length; i++) {
			result += array[i] + "\n";
		}
		return result;
	}
	
	private static String mapIntString(int value) {
		String result;
		switch (value) {
			case Blackjack.DRAW:
				result = "DRAW";
				break;
			case Blackjack.LESS_THAN_21:
				result = "LESS_THAN_21";
				break;
			case Blackjack.BUST:
				result = "BUST";
				break;
			case Blackjack.BLACKJACK:
				result = "BLACKJACK";
				break;
			case Blackjack.HAS_21:
				result = "HAS_21";
				break;
			case Blackjack.DEALER_WON:
				result = "DEALER_WON";
				break;
			case Blackjack.PLAYER_WON:
				result = "PLAYER_WON";
				break;
			case Blackjack.GAME_IN_PROGRESS:
				result = "GAME_IN_PROGRESS";
				break;
			default:
				result = "INVALID";
			    break;
		}
		return result;
	}
}
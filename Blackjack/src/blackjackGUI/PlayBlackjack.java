package blackjackGUI;
import blackjack.Blackjack;

import java.util.Random;

public class PlayBlackjack {
	public static void main(String[] args) {
		final boolean runningAsApplet = false;
		int numberOfDecks = 1;
		
		/* Creating the model component of the Model View Controller */
		final Blackjack blackjackModel =  new Blackjack(new Random(), numberOfDecks);
		
		/* Creating the view/controller component of the Model View Controller */
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new BlackjackGUI(blackjackModel,runningAsApplet).startGame();
			}
		});
	}
}
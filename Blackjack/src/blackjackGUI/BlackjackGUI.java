package blackjackGUI;
import blackjack.BlackjackEngine;
import blackjack.Card;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.*;


public class BlackjackGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	// constants used for sizing of frame and components in GUI
	static final int PLAYER_PADDING = 50;
	static final int TABLE_WIDTH = 750;
	static final int TABLE_HEIGHT = 400; 
	static final Color GREEN_FELT_TABLE = new Color(40, 180, 40);
	static final Dimension PREFERRED_BUTTON_DIM = new Dimension(105, 40);
	
	Dimension size;
	BlackjackEngine engine;
	JFrame frame;
	JLabel gameStatusLabel, accountLabel, betLabel;
	DealerGUI dealerGUI;
	PlayerGUI playerGUIOne;
	boolean runningAsApplet;
	
	public BlackjackGUI(BlackjackEngine engine, boolean runningAsApplet) {
		this.engine = engine;
		this.runningAsApplet = runningAsApplet;
		
		size = new Dimension(TABLE_WIDTH, TABLE_HEIGHT);
		setBackground(GREEN_FELT_TABLE);
		setLayout(new BorderLayout());
		
		/* Adding the dealer GUI */ 
		dealerGUI = new DealerGUI();
		add(dealerGUI, BorderLayout.NORTH);
		
		/* Adding a label to display game status */
		gameStatusLabel = new JLabel();
		gameStatusLabel.setHorizontalAlignment(JLabel.CENTER);
		gameStatusLabel.setFont(new Font("Courier", Font.BOLD, 34));
		accountLabel = new JLabel();
		add(gameStatusLabel, BorderLayout.CENTER);
		
		/* Adding the player GUI */
		playerGUIOne = new PlayerGUI(-1);
		add(playerGUIOne, BorderLayout.SOUTH);
		
		// Create a top-level window
		if (!runningAsApplet) {
			frame = new JFrame("Black Jack (21)");
			frame.setResizable(true);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setContentPane(this);
			frame.pack();
		}	
	}
	
	public JPanel getPanel() {
		return this;
	}
	
	public void startGame() {
		if (!runningAsApplet)
			frame.setVisible(true);
	}
	
	public void updateGameStatus() {
		int gameStatus = engine.getGameStatus();
		
		if (gameStatus == BlackjackEngine.DEALER_WON)
			gameStatusLabel.setText("Dealer Wins");
		else if (gameStatus == BlackjackEngine.PLAYER_WON) 
			gameStatusLabel.setText("Player Wins");
		else if (gameStatus == BlackjackEngine.DRAW)
			gameStatusLabel.setText("Draw");
		else
			gameStatusLabel.setText("");
		updateUI();
	}
	
	public Dimension getPreferredSize() {
		return size;
	}
	
	class PlayerGUI extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private JButton hitButton, standButton, betButton, betButton2, chipButton;
		private Card[] playerCards;
		private JPanel cardsPanel;
		private JLabel playerLabel;
		private int id;
		
		public PlayerGUI(int id) {
			this.id = id;
			setLayout(new BorderLayout());
			
			setBackground(GREEN_FELT_TABLE);
			/* Setting the cards panel */
			cardsPanel = new JPanel();
			cardsPanel.setBackground(GREEN_FELT_TABLE);
			add(cardsPanel, BorderLayout.NORTH);
			
			/* Setting the control buttons panel */
			hitButton = new JButton("Hit");
			hitButton.setBackground(GREEN_FELT_TABLE);
			hitButton.setPreferredSize(PREFERRED_BUTTON_DIM);
			standButton = new JButton("Stand");
			standButton.setBackground(GREEN_FELT_TABLE);
			standButton.setPreferredSize(PREFERRED_BUTTON_DIM);
			JPanel controlButtons = new JPanel();
			controlButtons.setBackground(GREEN_FELT_TABLE);
			controlButtons.add(hitButton);
			controlButtons.add(standButton);
			add(controlButtons, BorderLayout.CENTER);		
			
			/* Setting player info */
			betLabel = new JLabel("Current Bet "+engine.getBetAmount());
			betLabel.setForeground(Color.BLUE);
			betButton = new JButton("Increase Bet");
			betButton2 = new JButton("Decrease Bet");
			chipButton = new JButton("Add Chips");
			playerLabel = new JLabel();
			playerLabel.setFont(new Font("Courier", Font.BOLD, 20));
			accountLabel = new JLabel("Account " + engine.getAccountAmount());
			JPanel supportPanel = new JPanel();
			supportPanel.setBackground(GREEN_FELT_TABLE);
			supportPanel.add(playerLabel);
			supportPanel.add(betLabel);
			supportPanel.add(betButton);
			supportPanel.add(betButton2);
			supportPanel.add(accountLabel);
			supportPanel.add(chipButton);
			add(supportPanel, BorderLayout.SOUTH);
			
			/* Registering the buttons */
			hitButton.addActionListener(this);
			standButton.addActionListener(this);
			betButton.addActionListener(this);
			betButton2.addActionListener(this);
			chipButton.addActionListener(this);
		}
		
		public void drawPlayerCards(Card[] cards) {
			playerCards = cards;
			cardsPanel.removeAll();
			for (int i=0; i< playerCards.length; i++) {
				cardsPanel.add(new CardImage(playerCards[i]));
			}	
					
			updateUI();
		}
		
		public void updatePlayerInfo(int status) {
			String playerMessage;
			if (id == -1) {
				playerMessage = "Player has ";
			}
			else { 
				playerMessage = "Player #" + id + " has ";
			}
			
			if (status == BlackjackEngine.LESS_THAN_21) {
				int[] possibleSums = engine.getPlayerCardsTotal();
				if (possibleSums != null) {
					if (possibleSums.length == 1) {
						playerMessage += possibleSums[0];
					} else {
						playerMessage += possibleSums[0] + " or " + possibleSums[1];
					}
				} 
			} else if (status == BlackjackEngine.BUST) {
				playerMessage += "busted";
			} else if (status == BlackjackEngine.BLACKJACK) { 
				playerMessage += "Black Jack";
			} else if (status == BlackjackEngine.HAS_21) {
				playerMessage += "21";
			}
			
			playerLabel.setText(playerMessage);
			updateUI();
		}
		
		
		
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == hitButton) {
				if (engine.getGameStatus() == BlackjackEngine.GAME_IN_PROGRESS) {
					engine.playerHit();
					drawPlayerCards(engine.getPlayerCards());
					updatePlayerInfo(engine.getPlayerCardsEvaluation());
				}
			} else if (source == standButton) {
				if (engine.getGameStatus() == BlackjackEngine.GAME_IN_PROGRESS) {
					engine.playerStand();
					dealerGUI.drawDealerCards(engine.getDealerCards());
					dealerGUI.updateDealerInfo(engine.getDealerCardsEvaluation());
				}
			} else if (source == betButton) {
				if (engine.getGameStatus() != BlackjackEngine.GAME_IN_PROGRESS) {
					engine.setBetAmount(engine.getBetAmount() + 1);			
				} else {
					JOptionPane.showMessageDialog(BlackjackGUI.this, "Cannot change bet while game is in progress");
				}
			} else if (source == betButton2) {
				if (engine.getGameStatus() != BlackjackEngine.GAME_IN_PROGRESS) {
					if (engine.getBetAmount() <= 1)
						JOptionPane.showMessageDialog(BlackjackGUI.this, "Bet too small");
					engine.setBetAmount(engine.getBetAmount() - 1);
				} else {
					JOptionPane.showMessageDialog(BlackjackGUI.this, "Cannot change bet while game is in progress");
				}
			} else if (source == chipButton) {
				engine.setAccountAmount(engine.getAccountAmount() + 100);
			}
			else {
				System.out.println("ERROR in BlackJackGUI");
			}
			
			betLabel.setText("Current Bet "+engine.getBetAmount());
			accountLabel.setText("Account " + engine.getAccountAmount());
			
			updateGameStatus();
			updateUI();
		}
	}
	
	class DealerGUI extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private JButton dealButton;
		private Card[] dealerCards;
		private JPanel cardsPanel;
		private JLabel dealerLabel;
		
		public DealerGUI() {
			setLayout(new BorderLayout());
			
			setBackground(GREEN_FELT_TABLE);
			/* Setting the cards panel */
			cardsPanel = new JPanel();
			cardsPanel.setBackground(GREEN_FELT_TABLE);
			add(cardsPanel, BorderLayout.NORTH);
			
			/* Setting the dealer's label and button */			
			dealerLabel = new JLabel();
			dealerLabel.setFont(new Font("Courier", Font.BOLD, 20));
			JPanel supportPanel = new JPanel();
			dealButton = new JButton("Deal");
			dealButton.setBackground(GREEN_FELT_TABLE);
			dealButton.setPreferredSize(PREFERRED_BUTTON_DIM);
			supportPanel.setBackground(GREEN_FELT_TABLE);
			supportPanel.add(dealerLabel);
			supportPanel.add(dealButton);
			add(supportPanel, BorderLayout.CENTER);	
			
			/* Registering the button */
			dealButton.addActionListener(this);
		}
		
		public void drawDealerCards(Card[] cards) {
			dealerCards = cards;
			cardsPanel.removeAll();
			for (int i=0; i< dealerCards.length; i++) {
				cardsPanel.add(new CardImage(dealerCards[i]));
			}
			
			updateUI();
		}
		
		public void updateDealerInfo(int status) {
			if (status != BlackjackEngine.GAME_IN_PROGRESS) {
				String dealerMessage = "Dealer has ";
				if (status == BlackjackEngine.LESS_THAN_21) {
					int[] possibleSums = engine.getDealerCardsTotal();
					if (possibleSums != null) {
						if (possibleSums.length == 1) {
							dealerMessage += possibleSums[0];
						} else {
							dealerMessage += possibleSums[1];
						}
					} 
				} else if (status == BlackjackEngine.BUST) {
					dealerMessage += "busted";
				} else if (status == BlackjackEngine.BLACKJACK) { 
					dealerMessage += "Black Jack";
				} else if (status == BlackjackEngine.HAS_21) {
					dealerMessage += "21";
				} 
				dealerLabel.setText(dealerMessage);
			} else {
				dealerLabel.setText("");
			}
			updateUI();
		}
		
		public void actionPerformed(ActionEvent e) {
			engine.deal();
			dealerGUI.updateDealerInfo(engine.getGameStatus());
			updateGameStatus();
			drawDealerCards(engine.getDealerCards());
			accountLabel.setText("Account " + engine.getAccountAmount());
			playerGUIOne.updatePlayerInfo(engine.getPlayerCardsEvaluation());
			playerGUIOne.drawPlayerCards(engine.getPlayerCards());
			playerGUIOne.updateUI();
		}
	}
	  
	/* Static block for feel and look */
	static {
	   try {
	      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	   } catch (Exception e) {}
	}
}
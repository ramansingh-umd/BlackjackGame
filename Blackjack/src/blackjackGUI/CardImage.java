package blackjackGUI;
import java.awt.*;
import javax.swing.*;
import blackjack.Card;

public class CardImage extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int CARD_WIDTH = 71;  /* 71 original */
	public static final int CARD_HEIGHT = 96; /* 96 original */
	private Card card;
	private Image image;
	private int xPos = 0, yPos = 0;
	
	public CardImage(Card c) {
		card = c;
		setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
	}
	
	/* Returns the pathname of the .gif file that looks like this card */
	public String getImageFileName() {
		return "src/images/" + card.getSuit().getValue() + card.getValue().getValue().toLowerCase() + ".gif";
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		String cardName;
		if (!card.isFacedUp())
			cardName = "src/images/b2fv.gif";  // face-down
		else
			cardName = getImageFileName();
		
		ImageIcon imageIcon = new ImageIcon(cardName);
		image = imageIcon.getImage();
		
		g.drawImage(image, xPos, yPos, CARD_WIDTH, CARD_HEIGHT, this);
	}
}
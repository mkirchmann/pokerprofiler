package de.neuenberger.poker.common.ui.util;

import java.awt.Color;

public class CardLabelSmall extends CardLabel {
	
	public CardLabelSmall() {
		super(false);
	}
	
	
	public void setColor(int color)
    {
		if(color == 2) {
            setIcon(CardChooser.IMG_SPADES_SMALL);
            this.setForeground(Color.BLACK);
        } else if(color == 1) {
            setIcon(CardChooser.IMG_HEARTS_SMALL);
            this.setForeground(Color.RED);
        } else if(color == 3) {
            setIcon(CardChooser.IMG_CLUBS_SMALL);
            this.setForeground(Color.BLACK);
        } else if(color == 0) {
            setIcon(CardChooser.IMG_DIAMONDS_SMALL);
            this.setForeground(Color.RED);
        } else {
            setIcon(null);
            this.setForeground(Color.GRAY);
        }
    }
}

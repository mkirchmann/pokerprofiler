// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CardLabel.java

package de.neuenberger.poker.common.ui.util;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import de.neuenberger.poker.common.model.Card;

// Referenced classes of package de.neuenberger.pokercalc.ui.util:
//            CardChooser

public class CardLabel extends JLabel {

	protected boolean bigFont = true;

	public CardLabel() {
		this(true);
	}

	public CardLabel(final boolean bigFont) {
		this.bigFont = bigFont;
		this.setCard(null);
		setup();
		this.setCard(null);
	}

	public CardLabel(final String str) {
		setText(str);
		setup();
	}

	protected void setup() {
		setBackground(Color.WHITE);
		setOpaque(true);
		if (bigFont == true) {
			setFont(BIGFONT);
		} else if (bigFont == false) {
			setFont(SMALLFONT);
		}
	}

	public void setCard(final Card c) {
		if (c == null) {
			this.setColor(-1);
			this.setText("-");
		} else {
			this.setText(Card.toString(c.getRank()));
			this.setColor(c.getColor());
		}
	}

	public void setColor(final int color) {
		if (color == 2) {
			setIcon(CardChooser.IMG_SPADES);
			this.setForeground(Color.BLACK);
		} else if (color == 1) {
			setIcon(CardChooser.IMG_HEARTS);
			this.setForeground(Color.RED);
		} else if (color == 3) {
			setIcon(CardChooser.IMG_CLUBS);
			this.setForeground(Color.BLACK);
		} else if (color == 0) {
			setIcon(CardChooser.IMG_DIAMONDS);
			this.setForeground(Color.RED);
		} else {
			setIcon(null);
			this.setForeground(Color.GRAY);
		}
	}

	public static final Font BIGFONT = new Font("Arial", 1, 28);
	public static final Font SMALLFONT = new Font("Arial", 1, 9);

}
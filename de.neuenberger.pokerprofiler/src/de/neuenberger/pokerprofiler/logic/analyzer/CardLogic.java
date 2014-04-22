package de.neuenberger.pokerprofiler.logic.analyzer;

import de.neuenberger.poker.common.model.Card;

public class CardLogic extends BaseLogic {
	public static boolean isAx(Card card[]) {
		return card[0].getRank()==Card.ACE || card[1].getRank()==Card.ACE;
	}
	
	public static boolean isKx(Card card[]) {
		return card[0].getRank()==Card.KING || card[1].getRank()==Card.KING;
	}
	
	public static boolean isSuited(Card card[]) {
		return card[0].getColor()==card[1].getColor();
	}
	
	public static boolean isConnected(Card card[]) {
		return card[0].getRank()==card[1].getRank()+1 || card[0].getRank()==card[1].getRank()-1;
	}
	
	public static boolean isUnclassified(Card card[]) {
		return !(isAx(card) || isKx(card) || isSuited(card) || isConnected(card));
	}

	public static boolean isPair(Card[] card) {
		return card[0].getRank()==card[1].getRank();
	}
}

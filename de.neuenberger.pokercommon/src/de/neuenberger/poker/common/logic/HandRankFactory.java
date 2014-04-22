package de.neuenberger.poker.common.logic;

import java.util.Vector;

import sun.misc.Compare;
import sun.misc.Sort;
import de.neuenberger.poker.common.model.Card;
import de.neuenberger.poker.common.model.util.CardUtils;

// Referenced classes of package de.neuenberger.pokercalc.logic:
//            FourOfAKind, HandRank, StraightAndFlush, FullHouse, 
//            Triplet, TwoPair, Pair, TopCard

public class HandRankFactory implements Compare {

	private HandRankFactory() {
		debug = false;
	}

	public HandRank getHandRank(final Card cardIn[]) {
		final Card cards[] = new Card[cardIn.length];
		for (int i = 0; i < cardIn.length; i++) {
			cards[i] = cardIn[i];
		}

		CardUtils.sortCardVector(cards);
		HandRank validHandRank = null;
		final Vector vec = new Vector();
		validHandRank = new FourOfAKind(cards);
		if (validHandRank.isValid()) {
			vec.add(validHandRank);
		}
		validHandRank = new StraightAndFlush(cards);
		if (validHandRank.isValid()) {
			vec.add(validHandRank);
		}
		validHandRank = new FullHouse(cards);
		if (validHandRank.isValid()) {
			vec.add(validHandRank);
		}
		validHandRank = new Triplet(cards);
		if (validHandRank.isValid()) {
			vec.add(validHandRank);
		} else {
			validHandRank = new TwoPair(cards);
			if (validHandRank.isValid()) {
				vec.add(validHandRank);
			} else {
				validHandRank = new Pair(cards);
				if (validHandRank.isValid()) {
					vec.add(validHandRank);
				} else {
					vec.add(new TopCard(cards));
				}
			}
		}
		final HandRank hr[] = new HandRank[vec.size()];
		vec.toArray(hr);
		Sort.quicksort(hr, this);
		if (debug) {
			System.out.println("handrank for " + CardUtils.nameCards(cards)
					+ " is " + hr[0].toString());
		}
		return hr[0];
	}

	public static HandRankFactory getInstance() {
		return instance;
	}

	@Override
	public int doCompare(final Object arg0, final Object arg1) {
		return -((HandRank) arg0).compare((HandRank) arg1);
	}

	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

	private static HandRankFactory instance = new HandRankFactory();
	boolean debug;

}
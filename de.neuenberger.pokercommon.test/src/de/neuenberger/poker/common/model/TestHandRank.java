package de.neuenberger.poker.common.model;

import java.util.Vector;

import sun.misc.Sort;
import de.neuenberger.poker.common.logic.HandRank;
import de.neuenberger.poker.common.logic.HandRankFactory;

public class TestHandRank {

	public TestHandRank() {
	}

	public static void main(final String argv[]) {
		final Card c1[] = { new Card(14, 3), new Card(5, 3), new Card(4, 3),
				new Card(2, 3), new Card(7, 3), new Card(9, 3), new Card(3, 3) };
		final Card c2[] = { new Card(14, 1), new Card(5, 3), new Card(4, 3),
				new Card(2, 3), new Card(7, 3), new Card(9, 3), new Card(3, 3) };
		final Card c3[] = { new Card(14, 1), new Card(5, 3), new Card(4, 0),
				new Card(2, 3), new Card(7, 0), new Card(9, 2), new Card(3, 3) };
		final Card c4[] = { new Card(14, 1), new Card(5, 3), new Card(4, 0),
				new Card(2, 3), new Card(8, 0), new Card(6, 2), new Card(3, 3) };
		final Card c5[] = { new Card(14, 1), new Card(14, 3), new Card(4, 0),
				new Card(8, 3), new Card(8, 0), new Card(6, 2), new Card(3, 3) };
		final Card c6[] = { new Card(14, 1), new Card(14, 3), new Card(4, 0),
				new Card(7, 3), new Card(7, 0), new Card(6, 2), new Card(3, 3) };
		final HandRankFactory hrf = HandRankFactory.getInstance();
		HandRank hr = null;
		hr = hrf.getHandRank(c1);
		hr = hrf.getHandRank(c2);
		hr = hrf.getHandRank(c3);
		hr = hrf.getHandRank(c4);
		final HandRank hr1 = hrf.getHandRank(c5);
		final HandRank hr2 = hrf.getHandRank(c6);
		System.out.println(hr1.compare(hr2));
		final Deck deck = Deck.getInstance();
		final TexasHoldem th = new TexasHoldem();
		th.setPlayers(4);
		th.getPlayer()[0] = deck.gimmeCard(14, 2);
		th.getPlayer()[1] = deck.gimmeCard(14, 3);
		final Vector vec = new Vector();
		hrf.setDebug(false);
		new MainController();
	}

	public static float getProbabilityOf(final int players, final int rank1,
			final int rank2, final boolean suited) {
		final HandRankFactory hrf = HandRankFactory.getInstance();
		final Deck deck = Deck.getInstance();
		final TexasHoldem th = new TexasHoldem();
		th.setPlayers(players);
		if (!suited || rank1 == rank2) {
			th.getPlayer()[0] = deck.gimmeCard(rank1, 2);
			th.getPlayer()[1] = deck.gimmeCard(rank2, 3);
		} else {
			th.getPlayer()[0] = deck.gimmeCard(rank1, 2);
			th.getPlayer()[1] = deck.gimmeCard(rank2, 2);
		}
		final Vector vec = new Vector();
		final int ges = 5000;
		int pWon = 0;
		hrf.setDebug(false);
		for (int i = 0; i < ges; i++) {
			vec.clear();
			deck.getShuffledCards();
			th.dealRound(deck);
			final Card cards[] = th.getCardsOfPlayer();
			final HandRank hrP1 = hrf.getHandRank(cards);
			vec.add(hrP1);
			final Card pCards[][] = new Card[8][7];
			for (int x = 0; x < th.getPlayers(); x++) {
				pCards[x] = th.getCardsOfRndPlayer(x);
				final HandRank hrrp = hrf.getHandRank(pCards[x]);
				hrrp.setAssignedID(x + 1);
				vec.add(hrrp);
			}

			final HandRank hrnkCompare[] = new HandRank[vec.size()];
			vec.toArray(hrnkCompare);
			Sort.quicksort(hrnkCompare, hrf);
			if (hrnkCompare[0] == hrP1) {
				pWon++;
			}
		}

		return (float) pWon / (float) ges;
	}
}
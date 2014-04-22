// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ProbabilityCalculator.java

package de.neuenberger.pokercalc.logic;

import java.util.Vector;

import sun.misc.Sort;
import de.neuenberger.poker.common.logic.HandRank;
import de.neuenberger.poker.common.logic.HandRankFactory;
import de.neuenberger.poker.common.model.Deck;
import de.neuenberger.poker.common.model.GameDescription;
import de.neuenberger.poker.common.model.TexasHoldem;
import de.neuenberger.pokercalc.model.util.ComprehensiveHandProbability;

// Referenced classes of package de.neuenberger.pokercalc.logic:
//            HandRankFactory, HandRank

public class ProbabilityCalculator implements Runnable {

	public ProbabilityCalculator() {
		comprehensiveHandProbability = new ComprehensiveHandProbability();
		numberOfDeals = 10000L;
		players = 1;
	}

	public GameDescription getGameDescription() {
		return gameDescription;
	}

	public void setGameDescription(final GameDescription gameDescription) {
		this.gameDescription = gameDescription;
	}

	public long getNumberOfDeals() {
		return numberOfDeals;
	}

	public void setNumberOfDeals(final long numberOfDeals) {
		this.numberOfDeals = numberOfDeals;
	}

	public int getPlayers() {
		return players;
	}

	public void setPlayers(final int players) {
		this.players = players;
	}

	@Override
	public void run() {
		if (comprehensiveHandProbability == null) {
			System.err.println("no model defined");
			return;
		}
		final HandRankFactory hrf = HandRankFactory.getInstance();
		final Deck deck = Deck.getInstance();
		final TexasHoldem th = new TexasHoldem();
		th.setPlayers(players);
		th.getPlayer()[0] = gameDescription.getPlayerCards()[0][0];
		th.getPlayer()[1] = gameDescription.getPlayerCards()[0][1];
		if (gameDescription.getFlop()[0] != null
				&& gameDescription.getFlop()[1] != null
				&& gameDescription.getFlop()[2] != null) {
			th.setFixFlop(true);
			th.getFlop()[0] = gameDescription.getFlop()[0];
			th.getFlop()[1] = gameDescription.getFlop()[1];
			th.getFlop()[2] = gameDescription.getFlop()[2];
			if (gameDescription.getTurn() != null) {
				th.setFixTurn(true);
				th.setTurn(gameDescription.getTurn());
				if (gameDescription.getRiver() != null) {
					th.setFixRiver(true);
					th.setRiver(gameDescription.getRiver());
				}
			}
		}
		final Vector vec = new Vector();
		hrf.setDebug(false);
		comprehensiveHandProbability.normalizeToNumberOfTries(numberOfDeals);
		comprehensiveHandProbability.setQuietUpdate(true);
		for (long i = 0L; i < numberOfDeals; i++) {
			vec.clear();
			deck.getShuffledCards();
			th.dealRound(deck, true, true);
			final de.neuenberger.poker.common.model.Card cards[] = th
					.getCardsOfPlayer();
			final HandRank hrP1 = hrf.getHandRank(cards);
			vec.add(hrP1);
			final de.neuenberger.poker.common.model.Card pCards[][] = new de.neuenberger.poker.common.model.Card[8][7];
			for (int x = 0; x < th.getPlayers(); x++) {
				pCards[x] = th.getCardsOfRndPlayer(x);
				final HandRank hrrp = hrf.getHandRank(pCards[x]);
				hrrp.setAssignedID(x + 1);
				vec.add(hrrp);
			}

			final HandRank hrnkCompare[] = new HandRank[vec.size()];
			vec.toArray(hrnkCompare);
			Sort.quicksort(hrnkCompare, hrf);
			if (hrnkCompare[0].compare(hrnkCompare[1]) == 0) {
				comprehensiveHandProbability.getTieProbability()
						.increaseFromID(hrnkCompare[1].getRank());
			} else if (hrnkCompare[0] == hrP1) {
				comprehensiveHandProbability.getWinProbability()
						.increaseFromID(hrnkCompare[0].getRank());
			} else {
				comprehensiveHandProbability.getLooseProbability()
						.increaseFromID(hrnkCompare[1].getRank());
			}
			if (interrupted) {
				System.out.println("Interrupted");
				return;
			}
		}

		comprehensiveHandProbability.setQuietUpdate(false);
		finished = true;
		System.out.println("Calculation Finished");
	}

	public ComprehensiveHandProbability getComprehensiveHandProbability() {
		return comprehensiveHandProbability;
	}

	public void setComprehensiveHandProbability(
			final ComprehensiveHandProbability comprehensiveHandProbability) {
		this.comprehensiveHandProbability = comprehensiveHandProbability;
	}

	public boolean isFinished() {
		return finished;
	}

	public boolean isInterrupted() {
		return interrupted;
	}

	public void setInterrupted(final boolean interrupted) {
		this.interrupted = interrupted;
	}

	GameDescription gameDescription;
	ComprehensiveHandProbability comprehensiveHandProbability;
	long numberOfDeals;
	int players;
	boolean interrupted;
	boolean finished;
}
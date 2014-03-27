package de.neuenberger.pokerprofiler.logic.analyzer;

import de.neuenberger.pokercalc.logic.HandRank;
import de.neuenberger.pokerprofiler.model.PlayerShowDownSummary;

public class PlayerShowDownSummaryLogic extends BaseLogic {
	public static void addHandRank(PlayerShowDownSummary playerShowDownSummary, HandRank hr) {
		int rank=hr.getRank();
		switch (rank) {
			case HandRank.TOPCARD:
				playerShowDownSummary.setNumberOfHighCard(playerShowDownSummary.getNumberOfHighCard()+1);
				break;
			case HandRank.PAIR:
				playerShowDownSummary.setNumberOfPairs(playerShowDownSummary.getNumberOfPairs()+1);
				break;
			case HandRank.TWOPAIR:
				playerShowDownSummary.setNumberOfTwoPair(playerShowDownSummary.getNumberOfTwoPair()+1);
				break;
			case HandRank.TRIPLET:
				playerShowDownSummary.setNumberOfTriplet(playerShowDownSummary.getNumberOfTriplet()+1);
				break;
			case HandRank.STRAIGHT:
				playerShowDownSummary.setNumberOfStraight(playerShowDownSummary.getNumberOfStraight()+1);
				break;
			case HandRank.FLUSH:
				playerShowDownSummary.setNumberOfFlush(playerShowDownSummary.getNumberOfFlush()+1);
				break;
			case HandRank.FULLHOUSE:
				playerShowDownSummary.setNumberOfFullHouse(playerShowDownSummary.getNumberOfFullHouse()+1);
				break;
			case HandRank.POKER:
				playerShowDownSummary.setNumberOfQuads(playerShowDownSummary.getNumberOfQuads()+1);
				break;
			case HandRank.STRAIGHTFLUSH:
				playerShowDownSummary.setNumberOfStraightFlush(playerShowDownSummary.getNumberOfStraightFlush()+1);
				break;
		}
	}

}

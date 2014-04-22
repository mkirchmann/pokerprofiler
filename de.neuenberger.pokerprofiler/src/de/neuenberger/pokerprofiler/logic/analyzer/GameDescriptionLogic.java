package de.neuenberger.pokerprofiler.logic.analyzer;

import de.neuenberger.poker.common.logic.HandRank;
import de.neuenberger.poker.common.logic.HandRankFactory;
import de.neuenberger.poker.common.model.Card;
import de.neuenberger.pokerprofiler.model.Bet;
import de.neuenberger.pokerprofiler.model.GameDescription;
import de.neuenberger.pokerprofiler.model.GamePlay;
import de.neuenberger.pokerprofiler.model.PlayerDescription;

public class GameDescriptionLogic extends BaseLogic {
	public static float getBigBlind(GameDescription gd) {
		GamePlay gpArr[]=gd.getGamePlayArray();
		
		for (int i=0; i<gpArr.length; i++) {
			if (gpArr[i]!=null) {
				if (gpArr[i].getPosition()==GamePlay.POSITION_BIGBLIND) {
					return gpArr[i].getBlind();
				}
			}
		}
		return -1.0f;
	}

	public static float getPreFlopCollectedPot(GameDescription gameDescription) {
		float ret=0.0f;
		for (int i=0; i<gameDescription.getGamePlayArray().length; i++) {
			GamePlay gp=gameDescription.getGamePlayArray()[i];
			if (gp!=null) {
				ret+=BetLogic.sumAllBets(gp.getPreFlopBets())+gp.getAnte()+gp.getBlind();
			}
		}
		return ret;
	}
	
	public static float getMaximumWonPot(GameDescription gd) {
		float max=0.0f;
		GamePlay gpArr[]=gd.getGamePlayArray();
		
		for (int i=0; i<gpArr.length; i++) {
			if (gpArr[i]!=null) {
				max=Math.max(max, gpArr[i].getPot());
			}
		}
		return max;
	}
	
	public static int countPlayersAtShowDown(GameDescription gd) {
		int pasd=0;
		GamePlay gpArr[]=gd.getGamePlayArray();
		
		for (int i=0; i<gpArr.length; i++) {
			if (gpArr[i]!=null) {
				if (GamePlayLogic.isReachingShowDown(gpArr[1])) {
					pasd++;
				}
			}
		}
		return pasd;
	}
	
	public static boolean containsPlayerDescription(GameDescription gd, PlayerDescription pd) {
		GamePlay[] gamePlayArray = gd.getGamePlayArray();
		for (GamePlay gp:gamePlayArray) {
			if (gp!=null) {
				if (gp.getPlayer()==pd) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static int getBoardRank(GameDescription gd, int size) {
		Card card[]=new Card[size];
		for (int i=0; i<card.length; i++) {
			card[i]=gd.getBoardCards()[i];
		}
		HandRank rank = HandRankFactory.getInstance().getHandRank(card);
		return rank.getRank();
	}
	
	public static int getBoardRankFlop(GameDescription gd) {
		return getBoardRank(gd, 3);
	}
	public static int getBoardRankTurn(GameDescription gd) {
		return getBoardRank(gd, 4);
	}
	public static int getBoardRankRiver(GameDescription gd) {
		return getBoardRank(gd, 5);
	}
}

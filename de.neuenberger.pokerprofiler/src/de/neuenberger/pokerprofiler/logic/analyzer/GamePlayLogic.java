package de.neuenberger.pokerprofiler.logic.analyzer;

import de.neuenberger.pokercalc.logic.HandRank;
import de.neuenberger.pokercalc.logic.HandRankFactory;
import de.neuenberger.pokercalc.model.Card;
import de.neuenberger.pokercalc.model.TexasHoldem;
import de.neuenberger.pokerprofiler.model.Bet;
import de.neuenberger.pokerprofiler.model.GameDescription;
import de.neuenberger.pokerprofiler.model.GamePlay;

public class GamePlayLogic extends BaseLogic {
	public static float getInvestedMoney(GamePlay gamePlay) {
		return gamePlay.getAnte()+gamePlay.getBlind()+getVoluntarilyInvestedMoney(gamePlay);
	
	}

	public static float getVoluntarilyInvestedMoney(GamePlay gamePlay) {
		float ret=0.0f;
		ret+=getSummedPreFlopBets(gamePlay);
		ret+=getSummedFlopBets(gamePlay);
		ret+=getSummedTurnBets(gamePlay);
		ret+=getSummedRiverBets(gamePlay);
		
		return ret;
	}
	
	
	public static boolean isVoluntaryGamePlay(GamePlay gamePlay) {
		if (gamePlay.getPosition()==GamePlay.POSITION_BIGBLIND && getSummedFlopBets(gamePlay)==0.0f) {
			return false;
		}
		
		if (gamePlay.getFoldState()==GamePlay.FOLD_PREFLOP) {
			return false;
		}
		return true;
	}
	
	
	public static float getSummedPreFlopBets(GamePlay gamePlay) {
		return BetLogic.sumAllBets(gamePlay.getPreFlopBets());
	}
	
	public static float getSummedFlopBets(GamePlay gamePlay) {
		return BetLogic.sumAllBets(gamePlay.getFlopBets());
	}
	
	public static float getSummedTurnBets(GamePlay gamePlay) {
		return BetLogic.sumAllBets(gamePlay.getTurnBets());
	}
	
	public static float getSummedRiverBets(GamePlay gamePlay) {
		return BetLogic.sumAllBets(gamePlay.getRiverBets());
	}
	

	/**
	 * Should only be called in case the cards are known
	 * @param gp
	 * @return
	 */
	public static boolean isTrashPlay(GamePlay gp) {
		int points=getPocketHutchinsonPoints(gp);
		if (getSummedPreFlopBets(gp)>0.0f) {
			Card[] cards = gp.getPlayerCards();
			return (points<25 || cards[0].getRank()<Card.TEN || cards[1].getRank()<Card.TEN);			
		} else {
			return false;
		}
	}
	
	public static boolean isMonsterPlay(GamePlay gp) {
		int points=getPocketHutchinsonPoints(gp);
		if (points>35) {
			return true;
		}
		return false;
	}
	
	public static boolean isPreflopSlowPlay(GamePlay gp) {
		float bb=GameDescriptionLogic.getBigBlind(gp.getGameDescription());
		float relativeBB=(gp.getBlind()+gp.getPreFlopBets()[0].getAmount())/bb;
		return  (relativeBB<4.0f && isMonsterPlay(gp));
	}
	
	
	public static int getPocketHutchinsonPoints(GamePlay gamePlay) {
		if (isHandKnown(gamePlay)) {
			
			Card ca=gamePlay.getPlayerCards()[0];
			Card cb=gamePlay.getPlayerCards()[1];
			return getHutchinsonPoints(ca, cb);
			
			
		} else {
			return 0;
		}
	}

	
	
	private static int getHutchinsonPoints(Card ca, Card cb) {
		if (ca.getRank()<cb.getRank()) {
			return getHutchinsonPoints(cb, ca);
		}
		int points=getCardPoint(ca)+getCardPoint(cb);
		if (ca.getRank()==cb.getRank()) {
			points+=10;
		} else if (ca.getRank()-1==cb.getRank()) {
			points+=3;
		} else if (ca.getRank()-2==cb.getRank()) {
			points+=2;
		} else if (ca.getRank()-3==cb.getRank()) {
			points+=1;
		}
		
		if (ca.getColor()==cb.getColor()) {
			points+=4;
		}
		
		return points;
	}
	
	private static int getCardPoint(Card card) {
		if (card.getRank()>=Card.TEN) {
			return card.getRank()+1;
		} else {
			return card.getRank();
		}
	}
	
	public static boolean isHandKnown(GamePlay gamePlay) {
		return gamePlay.getPlayerCards()[0]!=null && gamePlay.getPlayerCards()[1]!=null;
	}
	
	public static boolean isReachingFlop(GamePlay gp) {
		if (BetLogic.containsAllIn(gp.getPreFlopBets())) {
			return true;
		}
			
		return (gp.getFlopBets().length>0);
	}
	
	public static boolean isReachingTurn(GamePlay gp) {
		if (isReachingFlop(gp)==false) {
			return false;
		}
		if (BetLogic.containsAllIn(gp.getFlopBets())) {
			return true;
		}
		return (gp.getTurnBets().length>0);
	}
	public static boolean isReachingRiver(GamePlay gp) {
		if (isReachingTurn(gp)==false) {
			return false;
		}
		if (BetLogic.containsAllIn(gp.getTurnBets())) {
			return true;
		}
		return (gp.getRiverBets().length>0);
	}
	
	public static boolean isReachingShowDown(GamePlay gp) {
		if (isReachingRiver(gp)==false) {
			return false;
		}
		return !isFoldingRiver(gp);
	}
	
	public static boolean isFoldingRiver(GamePlay gp) {
		return BetLogic.containsFold(gp.getRiverBets());
	}
	
	public static boolean isPreFlopAggressor(GamePlay gp) {
		Bet betArr[]=gp.getPreFlopBets();
		return BetLogic.isLastEntryRaise(betArr);
	}
	
	public static boolean hasFlopContinuationBetSize(GamePlay gp) {
		Bet betArr[]=gp.getFlopBets();
		if (betArr.length==0) {
			return false;
		}
		float amt=betArr[0].getAmount();
		float pot=GameDescriptionLogic.getPreFlopCollectedPot(gp.getGameDescription());
		return (BetLogic.isBet(betArr) || BetLogic.isRaise(betArr)) && amt>0.2f*pot && amt<0.9f*pot;
	}
	
	public static GamePlay getPreFlopAggressor(GamePlay gp) {
		GameDescription gd=gp.getGameDescription();
		
		GamePlay gpArr[]=gd.getGamePlayArray();
		for (int i=0; i<gpArr.length; i++) {
			if (gpArr[i]!=null) {
				if (isPreFlopAggressor(gpArr[i])) {
					return gpArr[i];
				}
			}
		}
		return null;
	}
	
	public static int getHandRank(GamePlay gp, int sizeOfBoard) {
		Card card[]=new Card[sizeOfBoard+2];
		for (int i=0; i<sizeOfBoard; i++) {
			card[i]=gp.getGameDescription().getBoardCards()[i];
		}
		card[sizeOfBoard  ]=gp.getPlayerCards()[0];
		card[sizeOfBoard+1]=gp.getPlayerCards()[1];
		HandRank rank = HandRankFactory.getInstance().getHandRank(card);
		return rank.getRank();
	}
	
	public static int getHandRankFlop(GamePlay gp) {
		return getHandRank(gp, 3);
	}
	public static int getHandRankTurn(GamePlay gp) {
		return getHandRank(gp, 4);
	}
	public static int getHandRankRiver(GamePlay gp) {
		return getHandRank(gp, 5);
	}
}

package de.neuenberger.pokerprofiler.model;

import java.util.Vector;

import de.neuenberger.pokercalc.model.Card;


public class GamePlay {
	PlayerDescription player;
	GameDescription gameDescription;
	
	public static final int FOLD_SIT_OUT = -1;
	public static final int FOLD_NO_FOLD = 0;
	public static final int FOLD_PREFLOP = 1;
	public static final int FOLD_FLOP    = 2;
	public static final int FOLD_TURN    = 3;
	public static final int FOLD_RIVER   = 4;
	
	public static final int POSITION_NA         = 0;
	public static final int POSITION_SMALLBLIND = 1;
	public static final int POSITION_BIGBLIND   = 2;
	public static final int POSITION_UTG        = 3;
	public static final int POSITION_EARLY      = 4;
	public static final int POSITION_MIDDLE     = 5;
	public static final int POSITION_CUTOFF     = 6;
	public static final int POSITION_DEALER     = 7;
	
	
	private static String POSITION_NAME[]=new String[]{"N/A","Small Blind","Big Blind","UTG", "EARLY","MIDDLE","CUTOFF","DEALER"};
	
	
	private Vector<Bet> preFlopBet=new Vector<Bet>();
	private Vector<Bet> flopBet=new Vector<Bet>();
	private Vector<Bet> turnBet=new Vector<Bet>();
	private Vector<Bet> riverBet=new Vector<Bet>();
	
	int foldState;
	float pot;
	float unansweared;
	int position;
	
	float blind;
	float ante;
	
	Card playerCards[]=new Card[2];
	
	
	public GamePlay(PlayerDescription pd, GameDescription gd) {
		player=pd;
		gameDescription=gd;
	}

	public int getFoldState() {
		return foldState;
	}

	public void setFoldState(int foldState) {
		this.foldState = foldState;
	}

	public PlayerDescription getPlayer() {
		return player;
	}

	public void setPlayer(PlayerDescription player) {
		this.player = player;
	}

	public GameDescription getGameDescription() {
		return gameDescription;
	}

	public void setGameDescription(GameDescription gameDescription) {
		this.gameDescription = gameDescription;
	}

	public float getPot() {
		return pot;
	}

	public void setPot(float pot) {
		this.pot = pot;
	}
	
	public void addPreFlopBet(Bet bet) {
		preFlopBet.add(bet);
		checkFold(bet,FOLD_PREFLOP);
	}
	public void addFlopBet(Bet bet) {
		flopBet.add(bet);
		checkFold(bet,FOLD_FLOP);
	}
	public void addTurnBet(Bet bet) {
		turnBet.add(bet);
		checkFold(bet,FOLD_TURN);
	}
	public void addRiverBet(Bet bet) {
		riverBet.add(bet);
		checkFold(bet,FOLD_RIVER);
	}
	
	public Bet[] getPreFlopBets() {
		return getBetArray(preFlopBet);
	}
	
	public Bet[] getFlopBets() {
		return getBetArray(flopBet);
	}
	
	public Bet[] getTurnBets() {
		return getBetArray(turnBet);
	}
	
	public Bet[] getRiverBets() {
		return getBetArray(riverBet);
	}
	
	private void checkFold(Bet bet, int fold) {
		if (bet.getType()==Bet.TYPE_FOLD) {
			this.foldState=fold;
		}
	}
	
	protected Bet[] getBetArray(Vector<Bet> vec) {
		Bet betArr[]=new Bet[vec.size()];
		vec.toArray(betArr);
		return betArr;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getPositionAsString() {
		return POSITION_NAME[this.position];
	}

	public float getAnte() {
		return ante;
	}

	public void setAnte(float ante) {
		this.ante = ante;
	}

	public float getBlind() {
		return blind;
	}

	public void setBlind(float blind) {
		this.blind = blind;
	}

	public Card[] getPlayerCards() {
		return playerCards;
	}

	public float getUnansweared() {
		return unansweared;
	}

	public void setUnansweared(float unansweared) {
		this.unansweared = unansweared;
	}
}

package de.neuenberger.pokerprofiler.parts;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.neuenberger.poker.common.parts.IController;
import de.neuenberger.pokerprofiler.logic.analyzer.BaseLogic;
import de.neuenberger.pokerprofiler.logic.analyzer.PlayerDescriptionLogic;
import de.neuenberger.pokerprofiler.model.PlayerDescription;
import de.neuenberger.pokerprofiler.model.PlayerShowDownSummary;
import de.neuenberger.pokerprofiler.ui.PlayerDescriptionSummaryPanel;

public class PlayerDescriptionSummaryPart implements IController {

	PlayerDescriptionSummaryPanel playerDescriptionSummaryPanel;
	
	DecimalFormat df=new DecimalFormat("0.00");
	
	public static final Color COLOR_DANGER=new Color(200,0,200);
	public static final Color COLOR_CAREFUL=new Color(200,0,0);
	public static final Color COLOR_GOOD   =new Color(0,128,0);
	
	public static final Color COLOR_BLUE      = Color.BLUE;
	public static final Color COLOR_LIGHTBLUE = new Color(100,100,255);
	int gamePlayCount=-1;
	Object oldFilter=null;
	
	PlayerDescription playerDescription;
	
	public PlayerDescriptionSummaryPart(boolean compact) {
		playerDescriptionSummaryPanel=new PlayerDescriptionSummaryPanel(compact);
	}
	
	
	public Component getComponent() {
		return playerDescriptionSummaryPanel;
	}

	public void saveComponents() {
		// TODO Auto-generated method stub

	}

	public void setPlayerDescription(PlayerDescription playerDescription) {
		this.playerDescription=playerDescription;
		if (playerDescription==null) {
			clear();
			oldFilter=null;
			gamePlayCount=-1;
			return;
		}
		if (oldFilter==PlayerDescriptionLogic.getContentFilter() && playerDescription.getSizeOfGamePlay()==gamePlayCount) {
			return;
		} else {
			oldFilter=PlayerDescriptionLogic.getContentFilter();
			gamePlayCount=playerDescription.getSizeOfGamePlay();
		}
		Thread thread=new Thread(){
			public void run() {
				updateItems();
			}
		};
		thread.start();
	}
	
	synchronized protected void updateItems() {
		PlayerDescription playerDescription = this.playerDescription;
		if (playerDescription==null) {
			clear();
			return;
		}
		int hands=playerDescription.getSizeOfGamePlay();
		float slowPlay=PlayerDescriptionLogic.getPlaysMonsterSlowProbability(playerDescription);
		float trashPlay=PlayerDescriptionLogic.getPlaysTrashProbability(playerDescription);
		
		float pot=((PlayerDescriptionLogic.sumCollectedPots(playerDescription)-PlayerDescriptionLogic.sumInvestment(playerDescription)))*(100.f/(float)hands);
		float vpip=PlayerDescriptionLogic.getVpiP(playerDescription);
		float pfRaise=PlayerDescriptionLogic.getPreFlopRaise(playerDescription);
		float pfAgg=PlayerDescriptionLogic.getPreFlopAggression(playerDescription);
		
		float check=PlayerDescriptionLogic.getBettingStyleProbability(playerDescription, PlayerDescriptionLogic.BET_STYLE_CHECK);
		float fold=PlayerDescriptionLogic.getBettingStyleProbability(playerDescription, PlayerDescriptionLogic.BET_STYLE_FOLD);
		float call=PlayerDescriptionLogic.getBettingStyleProbability(playerDescription, PlayerDescriptionLogic.BET_STYLE_CALL);
		float bet=PlayerDescriptionLogic.getBettingStyleProbability(playerDescription, PlayerDescriptionLogic.BET_STYLE_BET);
		float raise=PlayerDescriptionLogic.getBettingStyleProbability(playerDescription, PlayerDescriptionLogic.BET_STYLE_RAISE);
		
		
		float cf=PlayerDescriptionLogic.getBettingStyleProbability(playerDescription, PlayerDescriptionLogic.BET_STYLE_CHECK_FOLD);
		float cc=PlayerDescriptionLogic.getBettingStyleProbability(playerDescription, PlayerDescriptionLogic.BET_STYLE_CHECK_CALL);
		float cr=PlayerDescriptionLogic.getBettingStyleProbability(playerDescription, PlayerDescriptionLogic.BET_STYLE_CHECK_RAISE);
		float bf=PlayerDescriptionLogic.getBettingStyleProbability(playerDescription, PlayerDescriptionLogic.BET_STYLE_BET_FOLD);
		float rf=PlayerDescriptionLogic.getBettingStyleProbability(playerDescription, PlayerDescriptionLogic.BET_STYLE_RAISE_FOLD);
		
		float cbProb=PlayerDescriptionLogic.getContiBetProbability(playerDescription);
		float cbfProb=PlayerDescriptionLogic.getContiBetFoldProbability(playerDescription);
		float wts=PlayerDescriptionLogic.wentToShowDown(playerDescription);
		float wwsf=PlayerDescriptionLogic.wonWhenSawFlop(playerDescription);
		float wsd=PlayerDescriptionLogic.wonAtShowdown(playerDescription);
		
		float pocketAx=PlayerDescriptionLogic.getPocketAxProbability(playerDescription);
		float pocketKx=PlayerDescriptionLogic.getPocketKxProbability(playerDescription);
		float pocketSuited=PlayerDescriptionLogic.getPocketSuitedProbability(playerDescription);
		float pocketConn=PlayerDescriptionLogic.getPocketConnectedProbability(playerDescription);
		float pocketPair=PlayerDescriptionLogic.getPocketUnclassifiedProbability(playerDescription);
		
		
		float bluffFlopIfBet=PlayerDescriptionLogic.getBluffBetFlopProbability(playerDescription);
		float bluffTurnIfBet=PlayerDescriptionLogic.getBluffBetTurnProbability(playerDescription);
		float bluffRiverIfBet=PlayerDescriptionLogic.getBluffBetRiverProbability(playerDescription);
		
		float callFlopWithNothing=PlayerDescriptionLogic.getCallWithNothingFlopProbability(playerDescription);
		float callTurnWithNothing=PlayerDescriptionLogic.getCallWithNothingTurnProbability(playerDescription);
		float callRiverWithNothing=PlayerDescriptionLogic.getCallWithNothingRiverProbability(playerDescription);
		
		playerDescriptionSummaryPanel.getJlCheckFold().setText(df.format(cf));
		playerDescriptionSummaryPanel.getJlCheckCall().setText(df.format(cc));
		playerDescriptionSummaryPanel.getJlCheckRaise().setText(df.format(cr));
		playerDescriptionSummaryPanel.getJlBetFold().setText(df.format(bf));
		playerDescriptionSummaryPanel.getJlRaiseFold().setText(df.format(rf));
		
		playerDescriptionSummaryPanel.getJlPlaysSlow().setText(df.format(slowPlay));
		playerDescriptionSummaryPanel.getJlPlaysTrash().setText(df.format(trashPlay));
		playerDescriptionSummaryPanel.getJlVpiP().setText(df.format(vpip));
		playerDescriptionSummaryPanel.getJlPFRaise().setText(df.format(pfRaise));
		playerDescriptionSummaryPanel.getJlPFAggression().setText(df.format(pfAgg));
		
		playerDescriptionSummaryPanel.getJlHands().setText(""+hands);
		playerDescriptionSummaryPanel.getTfName().setText(playerDescription.getName());
		playerDescriptionSummaryPanel.getJlWin().setText(df.format(pot));
		
		
		playerDescriptionSummaryPanel.getJlRaise().setText(df.format(raise));
		playerDescriptionSummaryPanel.getJlCheck().setText(df.format(check));
		playerDescriptionSummaryPanel.getJlCall().setText(df.format(call));
		playerDescriptionSummaryPanel.getJlBet().setText(df.format(bet));
		playerDescriptionSummaryPanel.getJlFold().setText(df.format(fold));
		
		playerDescriptionSummaryPanel.getJlContibet().setText(df.format(cbProb));
		playerDescriptionSummaryPanel.getJlContibetFold().setText(df.format(cbfProb));
		playerDescriptionSummaryPanel.getJlWentToShowDown().setText(df.format(wts));
		playerDescriptionSummaryPanel.getJlWonWhenSawFlop().setText(df.format(wwsf));
		playerDescriptionSummaryPanel.getJlWinsShowDown().setText(df.format(wsd));
		
		playerDescriptionSummaryPanel.getJlPocketAx().setText(df.format(pocketAx));
		playerDescriptionSummaryPanel.getJlPocketKx().setText(df.format(pocketKx));
		playerDescriptionSummaryPanel.getJlPocketSuited().setText(df.format(pocketSuited));
		playerDescriptionSummaryPanel.getJlPocketConn().setText(df.format(pocketConn));
		playerDescriptionSummaryPanel.getJlPocketPair().setText(df.format(pocketPair));
		
		playerDescriptionSummaryPanel.getJlBluffFlopIfBet().setText(df.format(bluffFlopIfBet));
		playerDescriptionSummaryPanel.getJlBluffTurnIfBet().setText(df.format(bluffTurnIfBet));
		playerDescriptionSummaryPanel.getJlBluffRiverIfBet().setText(df.format(bluffRiverIfBet));
		
		playerDescriptionSummaryPanel.getJlCallFlopWithNothing().setText(df.format(callFlopWithNothing));
		playerDescriptionSummaryPanel.getJlCallTurnWithNothing().setText(df.format(callTurnWithNothing));
		playerDescriptionSummaryPanel.getJlCallRiverWithNothing().setText(df.format(callRiverWithNothing));
		
		setComponentColor(playerDescriptionSummaryPanel.getJlBetFold(), bf, false, 45.f, 15.f, 5.f);
		setComponentColor(playerDescriptionSummaryPanel.getJlFold(), fold, true, 25.f, 40.f, 50.f);
		setComponentColor(playerDescriptionSummaryPanel.getJlPlaysTrash(), trashPlay, false, 30.f, 45.f, 75.f);
		setComponentColor(playerDescriptionSummaryPanel.getJlCheckRaise(), cr, true, 1.f, 5.f, 10.f);
		setComponentColor(playerDescriptionSummaryPanel.getJlVpiP(), vpip, false, 33.f, 20.f, 10.f);
		setComponentColor(playerDescriptionSummaryPanel.getJlWinsShowDown(), wsd, true, 40.f, 50.f, 60.f);
		setComponentColor(playerDescriptionSummaryPanel.getJlWentToShowDown(), wts, false, 50.f, 40.f, 30.f);
		setComponentColor(playerDescriptionSummaryPanel.getJlContibetFold(), cbfProb, false, 20.f, 20.f, 10.f);
		setComponentColor(playerDescriptionSummaryPanel.getJlWonWhenSawFlop(), wwsf, true, 35.f, 34.f, 50.f);
		setComponentColor(playerDescriptionSummaryPanel.getJlContibet(), cbProb, true, 10.f, 25.f, 50.f);
		setComponentColor(playerDescriptionSummaryPanel.getJlPlaysSlow(), slowPlay, true, 3.f, 3.f, 20.f);
		setComponentColor(playerDescriptionSummaryPanel.getJlBluffFlopIfBet(), bluffFlopIfBet, false, 0.3f, 0.2f, 0.1f);
		setComponentColor(playerDescriptionSummaryPanel.getJlBluffTurnIfBet(), bluffTurnIfBet, false, 0.3f, 0.2f, 0.1f);
		setComponentColor(playerDescriptionSummaryPanel.getJlBluffRiverIfBet(), bluffRiverIfBet, false, 0.3f, 0.2f, 0.1f);
		
		setComponentColor(playerDescriptionSummaryPanel.getJlCallFlopWithNothing(), callFlopWithNothing, false, 0.3f, 0.2f, 0.1f);
		setComponentColor(playerDescriptionSummaryPanel.getJlCallTurnWithNothing(), callTurnWithNothing, false, 0.3f, 0.2f, 0.1f);
		setComponentColor(playerDescriptionSummaryPanel.getJlCallRiverWithNothing(), callRiverWithNothing, false, 0.3f, 0.2f, 0.1f);
		
		setSecureness(playerDescriptionSummaryPanel.getJlHands(), hands);
		
		this.setShowDownProbabilities(playerDescription);
	}
	
	public void clear() {
		Component[] components = playerDescriptionSummaryPanel.getComponents();
		for (int i=0; i<components.length; i++) {
			if (components[i] instanceof JLabel) {
				JLabel jLabel = (JLabel) components[i];
				jLabel.setText("");
			} else if (components[i] instanceof JTextField) {
				JTextField jtf = (JTextField) components[i];
				jtf.setText("");
			}
			components[i].setBackground(Color.BLACK);
		}
		
	}
	
	private void setShowDownProbabilities(PlayerDescription playerDescription) {
		PlayerShowDownSummary summary = PlayerDescriptionLogic.getHandRankProbabilityAtShowDown(playerDescription);
		
		
		int denominator=summary.getSumOfAll();
		
		float highCard=BaseLogic.getPercentage(summary.getNumberOfHighCard(), denominator);
		float pair=BaseLogic.getPercentage(summary.getNumberOfPairs(), denominator);
		float twoPair=BaseLogic.getPercentage(summary.getNumberOfTwoPair(), denominator);
		float triplet=BaseLogic.getPercentage(summary.getNumberOfTriplet(), denominator);
		float straight=BaseLogic.getPercentage(summary.getNumberOfStraight(), denominator);
		float flush=BaseLogic.getPercentage(summary.getNumberOfFlush(), denominator);
		float fullHouse=BaseLogic.getPercentage(summary.getNumberOfFullHouse(), denominator);
		float quads=BaseLogic.getPercentage(summary.getNumberOfQuads(), denominator);
		float straightFlush=BaseLogic.getPercentage(summary.getNumberOfStraightFlush(), denominator);
		
		playerDescriptionSummaryPanel.getJlSD_HighCard().setText(df.format(highCard));
		playerDescriptionSummaryPanel.getJlSD_Pair().setText(df.format(pair));
		playerDescriptionSummaryPanel.getJlSD_TwoPair().setText(df.format(twoPair));
		playerDescriptionSummaryPanel.getJlSD_Triple().setText(df.format(triplet));
		playerDescriptionSummaryPanel.getJlSD_Straight().setText(df.format(straight));
		playerDescriptionSummaryPanel.getJlSD_Flush().setText(df.format(flush));
		playerDescriptionSummaryPanel.getJlSD_FullHouse().setText(df.format(fullHouse));
		playerDescriptionSummaryPanel.getJlSD_Quads().setText(df.format(quads));
		playerDescriptionSummaryPanel.getJlSD_StraightFlush().setText(df.format(straightFlush));
		
		
	}

	protected void setSecureness(JLabel jl, int number) {
		if (number<10) {
			jl.setBackground(COLOR_BLUE);
			jl.setOpaque(true);
		} else if (number<30) {
			jl.setBackground(COLOR_LIGHTBLUE);
			jl.setOpaque(true);
		} else {
			jl.setBackground(null);
			jl.setOpaque(false);
		}
	}
	
	protected void setComponentColor(Component comp, float value, boolean bib, float good, float careful, float dangerous) {
		boolean isDanger =false;
		boolean isCareful=false;
		boolean isGood   =false;
		if (bib) {
			if (value<good) {
				isGood=true;
			} else if (value>dangerous) {
				isDanger=true;
			} else if (value>careful) {
				isCareful=true;
			}
		} else {
			if (value>good) {
				isGood=true;
			} else if (value<dangerous) {
				isDanger=true;
			} else if (value<careful) {
				isCareful=true;
			}
		}
		
		if (isDanger) {
			setColorToComponent(comp, COLOR_DANGER);
		} else if (isCareful) {
			setColorToComponent(comp, COLOR_CAREFUL);
		} else if (isGood) {
			setColorToComponent(comp, COLOR_GOOD);
		} else {
			setColorToComponent(comp, null);
		}
	}
	
	protected void setColorToComponent(Component comp, Color color) {
		if (comp instanceof JComponent) {
			JComponent new_name = (JComponent) comp;
			new_name.setOpaque(color!=null);
		}
		if (color!=null) {
			comp.setBackground(color);
		}
	}

	public PlayerDescriptionSummaryPanel getPlayerDescriptionSummaryPanel() {
		return playerDescriptionSummaryPanel;
	}

}

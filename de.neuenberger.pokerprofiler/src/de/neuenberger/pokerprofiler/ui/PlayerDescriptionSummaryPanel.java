package de.neuenberger.pokerprofiler.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.neuenberger.pokerprofiler.PokerProfiler;

import sun.awt.VariableGridLayout;

public class PlayerDescriptionSummaryPanel extends JPanel {
	
	
	public static final ImageIcon IMG_C = new ImageIcon(PokerProfiler.class.getResource("images/c.png"));
	public static final ImageIcon IMG_F = new ImageIcon(PokerProfiler.class.getResource("images/f.png"));
	public static final ImageIcon IMG_B = new ImageIcon(PokerProfiler.class.getResource("images/b.png"));
	public static final ImageIcon IMG_R = new ImageIcon(PokerProfiler.class.getResource("images/r.png"));
	public static final ImageIcon IMG_C_F = new ImageIcon(PokerProfiler.class.getResource("images/c_f.png"));
	public static final ImageIcon IMG_B_F = new ImageIcon(PokerProfiler.class.getResource("images/b_f.png"));
	public static final ImageIcon IMG_R_F = new ImageIcon(PokerProfiler.class.getResource("images/r_f.png"));
	public static final ImageIcon IMG_C_C = new ImageIcon(PokerProfiler.class.getResource("images/c_c.png"));
	public static final ImageIcon IMG_C_R = new ImageIcon(PokerProfiler.class.getResource("images/c_r.png"));
	

	public static final ImageIcon IMG_TRASH   = new ImageIcon(PokerProfiler.class.getResource("images/trashplay.png"));
	public static final ImageIcon IMG_VPIP    = new ImageIcon(PokerProfiler.class.getResource("images/vpip.png"));
	public static final ImageIcon IMG_AGGR    = new ImageIcon(PokerProfiler.class.getResource("images/aggressive.png"));
	public static final ImageIcon IMG_SLOW    = new ImageIcon(PokerProfiler.class.getResource("images/slowplay.png"));
	public static final ImageIcon IMG_CONTI   = new ImageIcon(PokerProfiler.class.getResource("images/contibet.png"));
	public static final ImageIcon IMG_CONTIBF = new ImageIcon(PokerProfiler.class.getResource("images/contibetfold.png"));
	public static final ImageIcon IMG_WONWSF  = new ImageIcon(PokerProfiler.class.getResource("images/wonwhensawflop.png"));
	public static final ImageIcon IMG_WINNER  = new ImageIcon(PokerProfiler.class.getResource("images/winner.png"));
	
	public static final ImageIcon IMG_WONSD   = new ImageIcon(PokerProfiler.class.getResource("images/wonshowdown.png"));
	public static final ImageIcon IMG_WTS     = new ImageIcon(PokerProfiler.class.getResource("images/wenttoshowdown.png"));
	public static final ImageIcon IMG_HANDS   = new ImageIcon(PokerProfiler.class.getResource("images/hands.png"));
	public static final ImageIcon IMG_DWIN    = new ImageIcon(PokerProfiler.class.getResource("images/dollarwin.png"));
	
	public static final ImageIcon IMG_SD_HIGHCARD     = new ImageIcon(PokerProfiler.class.getResource("images/highcard.png"));
	public static final ImageIcon IMG_SD_PAIR         = new ImageIcon(PokerProfiler.class.getResource("images/pair.png"));
	public static final ImageIcon IMG_SD_TWOPAIR      = new ImageIcon(PokerProfiler.class.getResource("images/twopair.png"));
	public static final ImageIcon IMG_SD_TRIPLETS     = new ImageIcon(PokerProfiler.class.getResource("images/triplets.png"));
	public static final ImageIcon IMG_SD_STRAIGHT     = new ImageIcon(PokerProfiler.class.getResource("images/straight.png"));
	public static final ImageIcon IMG_SD_FLUSH        = new ImageIcon(PokerProfiler.class.getResource("images/flush.png"));
	public static final ImageIcon IMG_SD_FULLHOUSE    = new ImageIcon(PokerProfiler.class.getResource("images/fullhouse.png"));
	public static final ImageIcon IMG_SD_QUADS        = new ImageIcon(PokerProfiler.class.getResource("images/quads.png"));
	public static final ImageIcon IMG_SD_STRAIGHTFLSH = new ImageIcon(PokerProfiler.class.getResource("images/straightflush.png"));
	
	public static final ImageIcon IMG_POCKET_AX        = new ImageIcon(PokerProfiler.class.getResource("images/pocket_ax.png"));
	public static final ImageIcon IMG_POCKET_KX        = new ImageIcon(PokerProfiler.class.getResource("images/pocket_kx.png"));
	public static final ImageIcon IMG_POCKET_CONNECTOR = new ImageIcon(PokerProfiler.class.getResource("images/pocket_connector.png"));
	public static final ImageIcon IMG_POCKET_SUITED    = new ImageIcon(PokerProfiler.class.getResource("images/pocket_suited.png"));
	public static final ImageIcon IMG_POCKET_PAIR        = new ImageIcon(PokerProfiler.class.getResource("images/pocket_pair.png"));
	
	JTextField tfName=new JTextField();
	JLabel jlWin=new JLabel();
	JLabel jlHands=new JLabel();
	
	JLabel jlPlaysTrash=new JLabel();
	JLabel jlPlaysSlow=new JLabel();
	JLabel jlVpiP=new JLabel();
	JLabel jlPFRaise=new JLabel();
	JLabel jlPFAggression=new JLabel();
	
	JLabel jlCheckRaise=new JLabel();
	JLabel jlCheckFold=new JLabel();
	JLabel jlCheckCall=new JLabel();
	JLabel jlBetFold=new JLabel();
	JLabel jlRaiseFold=new JLabel();
	
	JLabel jlFold=new JLabel();
	JLabel jlCheck=new JLabel();
	JLabel jlCall=new JLabel();
	JLabel jlBet=new JLabel();
	JLabel jlRaise=new JLabel();
	
	
	JLabel jlContibet=new JLabel();
	JLabel jlContibetFold=new JLabel();
	
	JLabel jlWonWhenSawFlop=new JLabel();
	JLabel jlWinsShowDown=new JLabel();
	JLabel jlWentToShowDown=new JLabel();
	
	JLabel jlSD_HighCard=new JLabel();
	JLabel jlSD_Pair=new JLabel();
	JLabel jlSD_TwoPair=new JLabel();
	JLabel jlSD_Triple=new JLabel();
	JLabel jlSD_Straight=new JLabel();
	JLabel jlSD_Flush=new JLabel();
	JLabel jlSD_FullHouse=new JLabel();
	JLabel jlSD_Quads=new JLabel();
	JLabel jlSD_StraightFlush=new JLabel();
	
	JLabel jlPocketAx=new JLabel();
	JLabel jlPocketKx=new JLabel();
	JLabel jlPocketSuited=new JLabel();
	JLabel jlPocketConn=new JLabel();
	JLabel jlPocketPair=new JLabel();
	
	
	JLabel jlBluffFlopIfBet = new JLabel();
	JLabel jlBluffTurnIfBet = new JLabel();
	JLabel jlBluffRiverIfBet = new JLabel();
	
	JLabel jlCallFlopWithNothing = new JLabel();
	JLabel jlCallTurnWithNothing = new JLabel();
	JLabel jlCallRiverWithNothing = new JLabel();
	
	
	public static final Font FONT_SMALL=new Font("Arial",0,10);
	
	public PlayerDescriptionSummaryPanel(boolean compact) {
		if (compact) {
			setupCompact();
			setSmallFont();
		} else {
			setupComplete();
		}
		
		
		
		jlHands.setToolTipText("Known Hands");
		jlWin.setToolTipText("$/100 Hands");
		
		jlPlaysSlow.setToolTipText("Preflop Slowplay");
		jlPlaysTrash.setToolTipText("Voluntary Trash Play");
		jlVpiP.setToolTipText("Voluntary Invested $ in Pot");
		jlPFRaise.setToolTipText("% Preflop raises");
		jlPFAggression.setToolTipText("% Preflop Aggression");
		
		jlCheckFold.setToolTipText("check / fold");
		jlCheckCall.setToolTipText("check / call");
		jlCheckRaise.setToolTipText("check / raise");
		jlBetFold.setToolTipText("bet / fold");
		jlRaiseFold.setToolTipText("raise / fold");
		
		jlCheck.setToolTipText("% Postflop 1st Action: Check");
		jlCall.setToolTipText("% Postflop 1st Action: Call");
		jlRaise.setToolTipText("% Postflop 1st Action: Raise");
		jlBet.setToolTipText("% Postflop 1st Action: Bet");
		jlFold.setToolTipText("% Postflop 1st Action: Fold");
		
		
		jlContibet.setToolTipText("Puts continuation Bet");
		jlContibetFold.setToolTipText("Folds to continuation Bet");
		jlWonWhenSawFlop.setToolTipText("% Won when saw Flop");
		jlWentToShowDown.setToolTipText("% Went to Showdown");
		jlWinsShowDown.setToolTipText("% Won Showdown");
		
		jlWin.setIcon(IMG_DWIN);
		jlHands.setIcon(IMG_HANDS);
		jlPlaysTrash.setIcon(IMG_TRASH);
		jlCheckFold.setIcon(IMG_C_F);
		jlCheckCall.setIcon(IMG_C_C);
		jlCheckRaise.setIcon(IMG_C_R);
		jlBetFold.setIcon(IMG_B_F);
		jlRaiseFold.setIcon(IMG_R_F);
		
		jlPlaysSlow.setIcon(IMG_SLOW);
		jlPFAggression.setIcon(IMG_AGGR);
		jlVpiP.setIcon(IMG_VPIP);
		jlContibet.setIcon(IMG_CONTI);
		jlContibetFold.setIcon(IMG_CONTIBF);
		
		
		jlWonWhenSawFlop.setIcon(IMG_WONWSF);
		jlWentToShowDown.setIcon(IMG_WTS);
		jlWinsShowDown.setIcon(IMG_WONSD);
		
		jlSD_HighCard.setToolTipText("HighCard at Showdown");
		jlSD_Pair.setToolTipText("Pair at Showdown");
		jlSD_TwoPair.setToolTipText("TwoPair at Showdown");
		jlSD_Triple.setToolTipText("Triplet at Showdown");
		jlSD_Straight.setToolTipText("Straight at Showdown");
		jlSD_Flush.setToolTipText("Flush at Showdown");
		jlSD_FullHouse.setToolTipText("Fullhouse at Showdown");
		jlSD_Quads.setToolTipText("Quads at Showdown");
		jlSD_StraightFlush.setToolTipText("StraightFlush at Showdown");
		
		
		jlBluffFlopIfBet.setToolTipText("Bluffbet Flop");
		jlBluffTurnIfBet.setToolTipText("Bluffbet Turn");
		jlBluffRiverIfBet.setToolTipText("Bluffbet River");
		
		jlCallFlopWithNothing.setToolTipText("Call on Flop with nothing");
		jlCallTurnWithNothing.setToolTipText("Call on Turn with nothing");
		jlCallRiverWithNothing.setToolTipText("Call on River with nothing");
		
		jlPocketAx.setToolTipText("Ax");
		jlPocketKx.setToolTipText("Kx");
		jlPocketSuited.setToolTipText("Suited");
		jlPocketConn.setToolTipText("Connectors");
		jlPocketPair.setToolTipText("Pocket Pair");
		
		jlPocketAx.setIcon(IMG_POCKET_AX);
		jlPocketKx.setIcon(IMG_POCKET_KX);
		jlPocketConn.setIcon(IMG_POCKET_CONNECTOR);
		jlPocketSuited.setIcon(IMG_POCKET_SUITED);
		jlPocketPair.setIcon(IMG_POCKET_PAIR);
		
		jlSD_HighCard.setIcon(IMG_SD_HIGHCARD);
		jlSD_Pair.setIcon(IMG_SD_PAIR);
		jlSD_TwoPair.setIcon(IMG_SD_TWOPAIR);
		jlSD_Triple.setIcon(IMG_SD_TRIPLETS);
		jlSD_Straight.setIcon(IMG_SD_STRAIGHT);
		jlSD_Flush.setIcon(IMG_SD_FLUSH);
		jlSD_FullHouse.setIcon(IMG_SD_FULLHOUSE);
		jlSD_Quads.setIcon(IMG_SD_QUADS);
		jlSD_StraightFlush.setIcon(IMG_SD_STRAIGHTFLSH);
		
		tfName.setFont(FONT_SMALL);
		
		this.setBackground(Color.BLACK);
		this.setAllForegroundColors(Color.LIGHT_GRAY, Color.BLACK);
	}
	
	

	protected void setupCompact() {
		GridLayout vgrid=new GridLayout(5,3);
		
		vgrid.setColumns(3);
		vgrid.setRows(5);
		this.setLayout(vgrid);
		tfName.setEditable(false);
		
		this.add(jlVpiP);
		this.add(tfName);
		this.add(jlHands);
		
		this.add(jlPlaysTrash);
		this.add(jlPlaysSlow);
		this.add(jlBet);
		
		this.add(jlFold);
		this.add(jlBetFold);
		this.add(jlContibetFold);
		
		this.add(jlWonWhenSawFlop);
		this.add(jlWentToShowDown);
		this.add(jlWinsShowDown);
		
		this.add(jlBluffFlopIfBet);
		this.add(jlCallFlopWithNothing);
		this.add(jlBluffRiverIfBet);
		
	}
	
	protected void setupComplete() {
		GridLayout vgrid=new GridLayout(10,5);
		
		vgrid.setColumns(5);
		vgrid.setRows(10);
		this.setLayout(vgrid);
		tfName.setEditable(false);
		
		this.add(tfName);
		this.add(jlHands);
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(jlWin);
		
		this.add(jlPlaysSlow);
		this.add(jlPlaysTrash);
		this.add(jlVpiP);
		this.add(jlPFRaise);
		this.add(jlPFAggression);
		
		
		
		this.add(jlCheckFold);
		this.add(jlCheckCall);
		this.add(jlCheckRaise);
		this.add(jlBetFold);
		this.add(jlRaiseFold);
		
		
		this.add(jlFold);
		this.add(jlCheck);
		this.add(jlCall);
		this.add(jlBet);
		this.add(jlRaise);
		
		this.add(jlContibet);
		this.add(jlContibetFold);
		this.add(jlWonWhenSawFlop);
		this.add(jlWentToShowDown);
		this.add(jlWinsShowDown);
		
		
		this.add(new JLabel());
		this.add(jlSD_HighCard);
		this.add(jlSD_Pair);
		this.add(jlSD_TwoPair);
		this.add(jlSD_Triple);
		this.add(jlSD_Straight);
		this.add(jlSD_Flush);
		this.add(jlSD_FullHouse);
		this.add(jlSD_Quads);
		this.add(jlSD_StraightFlush);
		
		this.add(jlPocketPair);
		this.add(jlPocketAx);
		this.add(jlPocketKx);
		this.add(jlPocketSuited);
		this.add(jlPocketConn);
		
		this.add(jlBluffFlopIfBet);
		this.add(jlBluffTurnIfBet);
		this.add(jlBluffRiverIfBet);
		this.add(new JLabel());
		this.add(new JLabel());
		
		this.add(jlCallFlopWithNothing);
		this.add(jlCallTurnWithNothing);
		this.add(jlCallRiverWithNothing);
		this.add(new JLabel());
		this.add(new JLabel());
	}

	private void setAllForegroundColors(Color foreground, Color background) {
		
		for (int i=0; i<this.getComponentCount(); i++) {
			this.getComponent(i).setForeground(foreground);
			this.getComponent(i).setBackground(background);
		}
		
	}
	
	private void setSmallFont() {
		for (int i=0; i<this.getComponentCount(); i++) {
			Component comp = getComponent(i);
			comp.setFont(FONT_SMALL);
		}
	}

	public JLabel getJlBetFold() {
		return jlBetFold;
	}

	public JLabel getJlCheckCall() {
		return jlCheckCall;
	}

	public JLabel getJlCheckFold() {
		return jlCheckFold;
	}

	public JLabel getJlCheckRaise() {
		return jlCheckRaise;
	}

	public JLabel getJlPlaysSlow() {
		return jlPlaysSlow;
	}

	public JLabel getJlPlaysTrash() {
		return jlPlaysTrash;
	}

	public JLabel getJlRaiseFold() {
		return jlRaiseFold;
	}

	public JTextField getTfName() {
		return tfName;
	}

	public JLabel getJlWin() {
		return jlWin;
	}

	public JLabel getJlBet() {
		return jlBet;
	}

	public JLabel getJlCall() {
		return jlCall;
	}

	public JLabel getJlCheck() {
		return jlCheck;
	}

	public JLabel getJlFold() {
		return jlFold;
	}

	public JLabel getJlRaise() {
		return jlRaise;
	}

	public JLabel getJlVpiP() {
		return jlVpiP;
	}

	public JLabel getJlPFRaise() {
		return jlPFRaise;
	}

	public JLabel getJlPFAggression() {
		return jlPFAggression;
	}

	public JLabel getJlContibet() {
		return jlContibet;
	}

	public JLabel getJlContibetFold() {
		return jlContibetFold;
	}

	public JLabel getJlWentToShowDown() {
		return jlWentToShowDown;
	}

	public JLabel getJlWinsShowDown() {
		return jlWinsShowDown;
	}

	public JLabel getJlWonWhenSawFlop() {
		return jlWonWhenSawFlop;
	}

	public JLabel getJlHands() {
		return jlHands;
	}

	public JLabel getJlSD_Flush() {
		return jlSD_Flush;
	}

	public JLabel getJlSD_FullHouse() {
		return jlSD_FullHouse;
	}

	public JLabel getJlSD_HighCard() {
		return jlSD_HighCard;
	}

	public JLabel getJlSD_Pair() {
		return jlSD_Pair;
	}

	public JLabel getJlSD_Quads() {
		return jlSD_Quads;
	}

	public JLabel getJlSD_Straight() {
		return jlSD_Straight;
	}

	public JLabel getJlSD_StraightFlush() {
		return jlSD_StraightFlush;
	}

	public JLabel getJlSD_Triple() {
		return jlSD_Triple;
	}

	public JLabel getJlSD_TwoPair() {
		return jlSD_TwoPair;
	}

	public JLabel getJlPocketAx() {
		return jlPocketAx;
	}

	public JLabel getJlPocketConn() {
		return jlPocketConn;
	}

	public JLabel getJlPocketKx() {
		return jlPocketKx;
	}

	public JLabel getJlPocketPair() {
		return jlPocketPair;
	}

	public JLabel getJlPocketSuited() {
		return jlPocketSuited;
	}

	public JLabel getJlBluffFlopIfBet() {
		return jlBluffFlopIfBet;
	}

	public JLabel getJlBluffRiverIfBet() {
		return jlBluffRiverIfBet;
	}

	public JLabel getJlBluffTurnIfBet() {
		return jlBluffTurnIfBet;
	}



	public JLabel getJlCallFlopWithNothing() {
		return jlCallFlopWithNothing;
	}



	public JLabel getJlCallRiverWithNothing() {
		return jlCallRiverWithNothing;
	}



	public JLabel getJlCallTurnWithNothing() {
		return jlCallTurnWithNothing;
	}
}

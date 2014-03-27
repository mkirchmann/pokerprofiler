package de.neuenberger.pokerprofiler.logic.analyzer;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.text.NumberFormatter;

import de.neuenberger.pokerprofiler.model.Bet;

public class BetLogic extends BaseLogic {
	
	static DecimalFormat df = new DecimalFormat("0.00");
	public static float sumAllBets(Bet betArr[]) {
		float ret=0.0f;
		for (int i=0; i<betArr.length; i++) {
			ret+=betArr[i].getAmount();
		}
		return ret;
	}
	
	public static String sumAllBetsAsString(Bet betArr[]) {
		float v=sumAllBets(betArr);
		try {
			return df.format(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""+v;
	}
	
	public static String getBetsAsString(Bet betArr[]) {
		StringBuffer strBuf=new StringBuffer();
		
		strBuf.append(sumAllBetsAsString(betArr));
		if (betArr.length==0) {
			return strBuf.toString();
		}
		strBuf.append(" = ");
		for (int i=0; i<betArr.length; i++) {
			if (i!=0) {
				strBuf.append(", ");
			}
			strBuf.append(getBetString(betArr[i]));
		}
		
		return strBuf.toString();
	}
	
	public static String getBetString(Bet bet) {
		StringBuffer strBuf=new StringBuffer();
		switch (bet.getType()) {
		case Bet.TYPE_BET:
			strBuf.append("b");
			break;
		case Bet.TYPE_CALL:
			strBuf.append("c");
			break;
		case Bet.TYPE_CHECK:
			strBuf.append("c");
			break;
		case Bet.TYPE_FOLD:
			strBuf.append("f");
			break;
		case Bet.TYPE_RAISE:
			strBuf.append("r");
			break;
		case Bet.TYPE_ALL_IN:
			strBuf.append("ai");
			break;
		case Bet.TYPE_N_A:
			strBuf.append("unq");
			break;
		}
		
		if (bet.getAmount()>0.0f) {
			strBuf.append(" ");
			strBuf.append(df.format(bet.getAmount()));
		}
		
		return strBuf.toString();
	}
	
	public static boolean containsFold(Bet betArr[]) {
		if (betArr.length==0) {
			return false;
		}
		return (betArr[betArr.length-1].getType()==Bet.TYPE_FOLD);
	}
	
	public static boolean containsAllIn(Bet betArr[]) {
		if (betArr.length==0) {
			return false;
		}
		return (betArr[betArr.length-1].getType()==Bet.TYPE_ALL_IN);
	}
	
	public static boolean isCheck(Bet betArr[]) {
		return betArr[0].getType()==Bet.TYPE_CHECK;
	}
	
	public static boolean isCheckRaise(Bet betArr[]) {
		if (betArr.length>1) {
			return isCheck(betArr) && betArr[1].getType()==Bet.TYPE_RAISE;
		}
		return false;
	}
	
	public static boolean isCheckFold(Bet betArr[]) {
		if (betArr.length>1) {
			return isCheck(betArr) && betArr[1].getType()==Bet.TYPE_FOLD;
		}
		return false;
	}
	
	public static boolean isBet(Bet betArr[]) {
		return betArr[0].getType()==Bet.TYPE_BET;
	}
	
	public static boolean isFold(Bet betArr[]) {
		return betArr[0].getType()==Bet.TYPE_FOLD;
	}
	
	public static boolean isRaise(Bet betArr[]) {
		return betArr[0].getType()==Bet.TYPE_RAISE;
	}
	
	public static boolean isBetFold(Bet betArr[]) {
		if (betArr.length>1) {
			return isBet(betArr) && betArr[1].getType()==Bet.TYPE_FOLD;
		}
		return false;
	}
	
	public static boolean isRaiseFold(Bet betArr[]) {
		if (betArr.length>1) {
			return isRaise(betArr) && betArr[1].getType()==Bet.TYPE_FOLD;
		}
		return false;
	}
	
	public static boolean isCheckCall(Bet betArr[]) {
		if (betArr.length>1) {
			return isCheck(betArr) && betArr[1].getType()==Bet.TYPE_CALL;
		}
		return false;
	}

	public static boolean isCall(Bet[] betArr) {
		return betArr[0].getType()==Bet.TYPE_CALL;
	}
	
	public static boolean isLastEntryRaise(Bet[] betArr) {
		if (betArr.length>0) {
			int t=betArr[betArr.length-1].getType();
			return t==Bet.TYPE_BET||t==Bet.TYPE_RAISE;
		}
		return false;
	}
}

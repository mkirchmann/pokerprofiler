package de.neuenberger.pokerprofiler.logic.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import sun.rmi.transport.proxy.CGIHandler;

import de.neuenberger.pokerprofiler.model.Bet;
import de.neuenberger.pokerprofiler.model.ChangeListenerFarm;
import de.neuenberger.pokerprofiler.model.GameDescription;
import de.neuenberger.pokerprofiler.model.GamePlay;
import de.neuenberger.pokerprofiler.model.PlayerDescription;
import de.neuenberger.pokerprofiler.model.PlayerDescriptionList;
import de.neuenberger.pokerprofiler.model.PlayerDescriptionListPool;
import de.neuenberger.pokerprofiler.model.PokerTableDescription;

abstract public class BaseAsciiHistoryAnalyzer extends AbstractHistoryAnalyzer {
	
	@Override
	protected void newGameStarted(String gameKey) {
		super.newGameStarted(gameKey);
		
		if (gameDescription.isAnalyzed()) {
			analysisState=AnalysisState.STATE_SKIP;
		} else {
			analysisState=AnalysisState.STATE_INITIALIZING;
		}
	}
	protected AnalysisState analysisState;
	
	
	
	public boolean acceptsFile(File file) {
		return true;
	}
	
	public void analyzeFile(File file) {
		String key=getKeyFromFile(file);
		pokerTableDescription=getPokerTableDescriptionFromString(key);
		BufferedReader br = null;
		try {
			br=new BufferedReader(new FileReader(file));
			String str="";
			while (str!=null) {
				str=br.readLine();
				if (str==null) {
					break;
				}
				AnalysisState oldAnalysisState=this.analysisState;
				GameDescription oldGameDescription=this.gameDescription;
				checkAnalysisStatus(str);
				if (oldAnalysisState==analysisState && analysisState!=AnalysisState.STATE_SKIP) {
					if (analysisState==AnalysisState.STATE_SUMMARY) {
						analyzeSummary(str);
					} else if (analysisState==AnalysisState.STATE_INITIALIZING) {
						analyzeInitialization(str);
					} else {
						analyzeBetString(str);
					}
				} else if (oldGameDescription!=null) {
					oldGameDescription.setAnalyzed(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br!=null) {
				try {
					br.close();
				} catch (IOException ignore) {
					// ignore
				}
			}
		}
		if (gameDescription!=null) {
			gameDescription.setAnalyzed(true);
		}
	}
	
	
	
	
	
	
	abstract protected Bet getBetFromRelevantString(String str);
	abstract protected void analyzeBetString(String str);
	abstract protected void analyzeInitialization(String str);
	abstract protected void analyzeSummary(String str);
	
	protected void putBet(GamePlay gp, Bet bet) {
		if (analysisState==AnalysisState.STATE_PREFLOP) {
			gp.addPreFlopBet(bet);
		} else if (analysisState==AnalysisState.STATE_FLOP) {
			gp.addFlopBet(bet);
		} else if (analysisState==AnalysisState.STATE_TURN) {
			gp.addTurnBet(bet);
		} else if (analysisState==AnalysisState.STATE_RIVER) {
			gp.addRiverBet(bet);
		} 
	}
	
	
	
	abstract protected void checkAnalysisStatus(String str);
	
	public AnalysisState getAnalysisState() {
		return analysisState;
	}
	public void setAnalysisState(AnalysisState analysisState) {
		this.analysisState = analysisState;
	}
	
	
}

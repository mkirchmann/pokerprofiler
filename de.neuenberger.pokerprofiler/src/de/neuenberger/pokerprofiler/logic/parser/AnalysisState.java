package de.neuenberger.pokerprofiler.logic.parser;

public class AnalysisState {
	public static final AnalysisState STATE_SKIP         = null;
	public static final AnalysisState STATE_INITIALIZING = new AnalysisState("ini");
	public static final AnalysisState STATE_PREFLOP      = new AnalysisState("preflop");
	public static final AnalysisState STATE_FLOP         = new AnalysisState("flop");
	public static final AnalysisState STATE_TURN         = new AnalysisState("turn");
	public static final AnalysisState STATE_RIVER        = new AnalysisState("river");
	public static final AnalysisState STATE_SUMMARY      = new AnalysisState("summary");
	
	
	private AnalysisState(String state) {
		
	}
}

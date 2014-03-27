package de.neuenberger.pokerprofiler.logic.parser;

import java.io.File;

import de.neuenberger.pokerprofiler.model.PlayerDescriptionList;

public interface IHistoryAnalyzer {
	public boolean acceptsFile(File file);
	public void analyzeFile(File file);
	public String getServer();
	public String getGameType();
	public String getParserId();
	public PlayerDescriptionList getPlayerDescriptionList();
	public String getConfigVar();
}

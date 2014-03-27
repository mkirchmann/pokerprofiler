package de.neuenberger.pokerprofiler.model.contentprovider.filter;

import de.neuenberger.pokerprofiler.logic.analyzer.GameDescriptionLogic;
import de.neuenberger.pokerprofiler.model.GamePlay;
import de.neuenberger.pokerprofiler.model.PlayerDescription;
import de.neuenberger.pokerprofiler.model.contentprovider.IContentFilter;

public class PlayerDescriptionFilter implements IContentFilter {

	PlayerDescription playerDescription;
	
	public PlayerDescriptionFilter(PlayerDescription pd) {
		this.playerDescription=pd;
	}
	
	public boolean accepts(Object obj) {
		if (obj instanceof PlayerDescription) {
			PlayerDescription pd=(PlayerDescription)obj;
		} else if (obj instanceof GamePlay) {
			GamePlay gp=(GamePlay)obj;
			return GameDescriptionLogic.containsPlayerDescription(gp.getGameDescription(),playerDescription);
		}
		return false;
	}

	public String getCriteriaDescription() {
		// TODO Auto-generated method stub
		return "Shows only games where "+playerDescription.getName()+" was involved.";
	}

	public String getFilterType() {
		return "Player scope filter";
	}
	
}

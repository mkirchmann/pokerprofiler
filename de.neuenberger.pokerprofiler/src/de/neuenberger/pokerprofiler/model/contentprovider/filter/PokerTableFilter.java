package de.neuenberger.pokerprofiler.model.contentprovider.filter;

import de.neuenberger.pokerprofiler.logic.analyzer.GameDescriptionLogic;
import de.neuenberger.pokerprofiler.logic.analyzer.PokerTableDescriptionLogic;
import de.neuenberger.pokerprofiler.model.GamePlay;
import de.neuenberger.pokerprofiler.model.PlayerDescription;
import de.neuenberger.pokerprofiler.model.PokerTableDescription;
import de.neuenberger.pokerprofiler.model.contentprovider.IContentFilter;

public class PokerTableFilter implements IContentFilter {

	PokerTableDescription pokerTableDescription;
	
	public PokerTableFilter(PokerTableDescription ptd) {
		this.pokerTableDescription=ptd;
	}

	public boolean accepts(Object obj) {
		if (obj instanceof PlayerDescription) {
			PlayerDescription pd = (PlayerDescription) obj;
			return PokerTableDescriptionLogic.isPlayerPartOfTable(pokerTableDescription, pd);
		} else if (obj instanceof GamePlay) {
			GamePlay gp = (GamePlay) obj;
			return gp.getGameDescription().getPokerTableDescription()==this.pokerTableDescription;
		}
		return false;
	}

	public String getCriteriaDescription() {
		return "Shows only games taken place at "+pokerTableDescription.getName();
	}

	public String getFilterType() {
		return "Pokertable Filter";
	}

}

package de.neuenberger.pokerprofiler.model.contentprovider.filter;

import de.neuenberger.pokerprofiler.model.contentprovider.IContentFilter;

public class DefaultFilter implements IContentFilter {

	public boolean accepts(Object obj) {
		return true;
	}

	public String getCriteriaDescription() {
		return "Accepts all";
	}

	public String getFilterType() {
		return "Default Filter";
	}

}

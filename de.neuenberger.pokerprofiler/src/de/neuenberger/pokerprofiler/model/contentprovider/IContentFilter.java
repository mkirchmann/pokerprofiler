package de.neuenberger.pokerprofiler.model.contentprovider;

public interface IContentFilter {
	public boolean accepts(Object obj);
	public String getFilterType();
	public String getCriteriaDescription();
}

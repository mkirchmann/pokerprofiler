package de.neuenberger.pokerprofiler.model;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import de.neuenberger.pokerprofiler.logic.analyzer.PokerTableDescriptionLogic;

public class PokerTableDescriptionChangeList extends DefaultTableModel {

	Vector<PokerTableDescription> vec = new Vector<PokerTableDescription>();
	HashMap<PokerTableDescription, Long> times = new HashMap<PokerTableDescription, Long>();
	String NAME_ARR[] = new String[] { "Name", "PPF", "Flop Seen", "FlopCost" };

	public PokerTableDescriptionChangeList() {
		ChangeListenerFarm.getInstance().addChangeListener(
				new ChangeListener() {

					@Override
					public void stateChanged(final ChangeEvent arg0) {
						if (arg0.getSource() instanceof PokerTableDescription) {
							final PokerTableDescription ptd = (PokerTableDescription) arg0
									.getSource();
							addIfNotExists(ptd);
						}
					}

				});
	}

	protected void addIfNotExists(final PokerTableDescription par_ptd) {
		boolean exists = false;
		for (int i = 0; i < vec.size(); i++) {
			final PokerTableDescription ptd = vec.get(i);
			if (ptd == par_ptd) {
				exists = true;
			}
		}
		if (exists == false) {
			vec.add(par_ptd);
			fireTableChanged(new TableModelEvent(this));
		}

		final long current = System.currentTimeMillis();

		times.put(par_ptd, new Long(current));

		for (int i = vec.size() - 1; i >= 0; i--) {
			final PokerTableDescription ptd = vec.get(i);
			final Long x = times.get(ptd);
			if (x < current - 360000) {
				vec.remove(ptd);
				fireTableChanged(new TableModelEvent(this));
			}
		}
	}

	@Override
	public Class<?> getColumnClass(final int arg0) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return NAME_ARR.length;
	}

	@Override
	public String getColumnName(final int arg0) {
		return NAME_ARR[arg0];
	}

	@Override
	public int getRowCount() {
		if (vec == null) {
			return 0;
		}
		return vec.size();
	}

	@Override
	public Object getValueAt(final int arg0, final int arg1) {
		final PokerTableDescription pokerTableDescription = vec.get(arg0);
		switch (arg1) {
		case 0:
			return pokerTableDescription.getName();
		case 1:
			return PokerTableDescriptionLogic
					.getPlayerSeeFlop(pokerTableDescription);
		case 2:
			return PokerTableDescriptionLogic
					.getPercentageFlopSeen(pokerTableDescription);
		case 3:
			return PokerTableDescriptionLogic
					.getFlopCost(pokerTableDescription);
		}
		return null;
	}

	@Override
	public boolean isCellEditable(final int arg0, final int arg1) {
		return false;
	}

	@Override
	public void setValueAt(final Object arg0, final int arg1, final int arg2) {

	}

	public PokerTableDescription[] getTablesAsArray() {
		final PokerTableDescription ptdArr[] = new PokerTableDescription[vec
				.size()];
		vec.toArray(ptdArr);
		return ptdArr;
	}

	public void removeAll() {
		vec.clear();
		fireTableChanged(new TableModelEvent(this));
	}

}

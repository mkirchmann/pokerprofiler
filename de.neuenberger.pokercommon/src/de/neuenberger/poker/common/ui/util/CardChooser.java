package de.neuenberger.poker.common.ui.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.neuenberger.poker.common.model.Card;

// Referenced classes of package de.neuenberger.poker.common.ui.util:
//            CardLabel

public class CardChooser extends JPanel {

	public CardChooser() {
		jTabbedPane = new JTabbedPane();
		jList = new JList();
		selectedColor = 3;
		selectedRank = -1;
		listener = new Vector();
		setLayout(new BorderLayout());
		jTabbedPane.addTab("N/A", new JLabel("N/A"));
		for (int i = 2; i <= 14; i++) {
			jTabbedPane.addTab(Card.toString(i),
					new CardLabel(Card.toString(i)));
		}

		add(jList, "West");
		add(jTabbedPane, "Center");
		setup();
	}

	public void addActionListener(final ActionListener al) {
		listener.add(al);
	}

	public void removeActionListener(final ActionListener al) {
		listener.remove(al);
	}

	protected void setup() {
		jList.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(final ListSelectionEvent arg0) {
						final int i = jList.getSelectedIndex();
						if (i == -1) {
							((CardLabel) jTabbedPane.getSelectedComponent())
									.setColor(-1);
						} else {
							final String v = (String) jList.getSelectedValue();
							if (v.equals("C")) {
								selectedColor = 3;
							} else if (v.equals("S")) {
								selectedColor = 2;
							} else if (v.equals("H")) {
								selectedColor = 1;
							} else if (v.equals("D")) {
								selectedColor = 0;
							}
							for (int x = 1; x < jTabbedPane.getTabCount(); x++) {
								final CardLabel cl = (CardLabel) jTabbedPane
										.getComponent(x);
								cl.setColor(selectedColor);
							}

							fireEvent();
						}
					}

				});
		final DefaultListModel defaultListModel = new DefaultListModel();
		jList.setModel(defaultListModel);
		defaultListModel.addElement("C");
		defaultListModel.addElement("S");
		defaultListModel.addElement("H");
		defaultListModel.addElement("D");
		jList.setCellRenderer(new DefaultListCellRenderer() {

			@Override
			public Component getListCellRendererComponent(final JList arg0,
					final Object arg1, final int arg2, final boolean arg3,
					final boolean arg4) {
				final Component comp = super.getListCellRendererComponent(arg0,
						arg1, arg2, arg3, arg4);
				final JLabel jLabel = new JLabel();
				jLabel.setOpaque(true);
				jLabel.setBackground(comp.getBackground());
				jLabel.setForeground(comp.getForeground());
				jLabel.setText(comp.getName());
				if (arg1 instanceof String) {
					final String str = (String) arg1;
					if (str.equals("C")) {
						jLabel.setIcon(CardChooser.IMG_CLUBS);
					} else if (str.equals("S")) {
						jLabel.setIcon(CardChooser.IMG_SPADES);
					} else if (str.equals("H")) {
						jLabel.setIcon(CardChooser.IMG_HEARTS);
					} else if (str.equals("D")) {
						jLabel.setIcon(CardChooser.IMG_DIAMONDS);
					}
				}
				return jLabel;
			}

		});
		jList.setSelectedIndex(0);
		jTabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				final int i = jTabbedPane.getSelectedIndex();
				if (i == 0) {
					selectedRank = -1;
				} else {
					selectedRank = (i - 1) + 2;
				}
				fireEvent();
			}

		});
	}

	protected void fireEvent() {
		final ActionEvent ae = new ActionEvent(this, 0, "");
		for (int i = 0; i < listener.size(); i++) {
			((ActionListener) listener.get(i)).actionPerformed(ae);
		}

	}

	public void setSelectedRank(final int s) {
		selectedRank = s;
		if (s == -1) {
			jTabbedPane.setSelectedIndex(0);
		} else {
			jTabbedPane.setSelectedIndex((s + 1) - 2);
		}
	}

	public void setSelectedColor(final int c) {
		final ListModel lm = jList.getModel();
		for (int i = 0; i < lm.getSize(); i++) {
			final String str = (String) lm.getElementAt(i);
			if (str.equals("C") && c == 3) {
				jList.setSelectedIndex(i);
				break;
			}
			if (str.equals("S") && c == 2) {
				jList.setSelectedIndex(i);
				break;
			}
			if (str.equals("H") && c == 1) {
				jList.setSelectedIndex(i);
				break;
			}
			if (!str.equals("D") || c != 0) {
				continue;
			}
			jList.setSelectedIndex(i);
			break;
		}

	}

	public int getSelectedRank() {
		return selectedRank;
	}

	public int getSelectedColor() {
		return selectedColor;
	}

	public static final ImageIcon IMG_SPADES;
	public static final ImageIcon IMG_HEARTS;
	public static final ImageIcon IMG_CLUBS;
	public static final ImageIcon IMG_DIAMONDS;

	public static final ImageIcon IMG_SPADES_SMALL;
	public static final ImageIcon IMG_HEARTS_SMALL;
	public static final ImageIcon IMG_CLUBS_SMALL;
	public static final ImageIcon IMG_DIAMONDS_SMALL;

	JTabbedPane jTabbedPane;
	JList jList;
	int selectedColor;
	int selectedRank;
	Vector listener;

	static {
		IMG_SPADES = new ImageIcon(
				de.neuenberger.poker.common.Dummy.class
						.getResource("images/Pik.png"));
		IMG_HEARTS = new ImageIcon(
				de.neuenberger.poker.common.Dummy.class
						.getResource("images/Herz.png"));
		IMG_CLUBS = new ImageIcon(
				de.neuenberger.poker.common.Dummy.class
						.getResource("images/Kreuz.png"));
		IMG_DIAMONDS = new ImageIcon(
				de.neuenberger.poker.common.Dummy.class
						.getResource("images/Karo.png"));

		IMG_SPADES_SMALL = new ImageIcon(
				de.neuenberger.poker.common.Dummy.class
						.getResource("images/Pik_small.png"));
		IMG_HEARTS_SMALL = new ImageIcon(
				de.neuenberger.poker.common.Dummy.class
						.getResource("images/Herz_small.png"));
		IMG_CLUBS_SMALL = new ImageIcon(
				de.neuenberger.poker.common.Dummy.class
						.getResource("images/Kreuz_small.png"));
		IMG_DIAMONDS_SMALL = new ImageIcon(
				de.neuenberger.poker.common.Dummy.class
						.getResource("images/Karo_small.png"));
	}
}
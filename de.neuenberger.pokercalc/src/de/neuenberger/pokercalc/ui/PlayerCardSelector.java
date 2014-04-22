// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   PlayerCardSelector.java

package de.neuenberger.pokercalc.ui;

import de.neuenberger.poker.common.ui.util.CardChooser;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class PlayerCardSelector extends JPanel
{

    public PlayerCardSelector()
    {
        GridLayout gLayout = new GridLayout();
        gLayout.setColumns(2);
        gLayout.setRows(1);
        gLayout.setVgap(5);
        setLayout(gLayout);
        add(cardChooserPocket[0]);
        add(cardChooserPocket[1]);
    }

    public CardChooser[] getCardChooserPocket()
    {
        return cardChooserPocket;
    }

    public void addActionListener(ActionListener al)
    {
        cardChooserPocket[0].addActionListener(al);
        cardChooserPocket[1].addActionListener(al);
    }

    public void removeActionListener(ActionListener al)
    {
        cardChooserPocket[0].removeActionListener(al);
        cardChooserPocket[1].removeActionListener(al);
    }

    public void clear()
    {
        cardChooserPocket[0].setSelectedRank(-1);
        cardChooserPocket[1].setSelectedRank(-1);
    }

    CardChooser cardChooserPocket[] = {
        new CardChooser(), new CardChooser()
    };
}
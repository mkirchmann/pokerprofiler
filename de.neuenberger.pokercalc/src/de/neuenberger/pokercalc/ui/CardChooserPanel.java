// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CardChooserPanel.java

package de.neuenberger.pokercalc.ui;

import de.neuenberger.poker.common.ui.util.CardChooser;
import de.neuenberger.poker.common.ui.util.CardLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Referenced classes of package de.neuenberger.pokercalc.ui:
//            PlayerCardSelector

public class CardChooserPanel extends JPanel
{

    public CardChooserPanel()
    {
        cardChooserTurn = new CardChooser();
        cardChooserRiver = new CardChooser();
        playerCardSelector = new PlayerCardSelector();
        jTabbedPaneInner = new JTabbedPane();
        jTabbedPaneTop = new JTabbedPane();
        flopPanel = new JPanel();
        turnAndRiverPanel = new JPanel();
        clearSelection = new JButton("Clear");
        pasteSelection = new JButton("Paste");
        jToolBar = new JToolBar();
        summaryPanel = new JPanel();
        playerPanel = new JPanel();
        communityPanel = new JPanel();
        turnLabel = new CardLabel();
        riverLabel = new CardLabel();
        communityLabel = new JLabel("Community Cards");
        playerLabel = new JLabel("Player Cards");
        setLayout(new BorderLayout());
        add(jTabbedPaneTop, "Center");
        add(jToolBar, "North");
        add(summaryPanel, "South");
        jToolBar.add(clearSelection);
        jToolBar.add(pasteSelection);
        clearSelection.setToolTipText("Clears all Selected Cards in this panel");
        pasteSelection.setToolTipText("Pastes hands from the clipboard (e.g. from a dealer log or hand history)");
        jTabbedPaneTop.addTab("Players", jTabbedPaneInner);
        jTabbedPaneTop.addTab("Flop", flopPanel);
        jTabbedPaneTop.addTab("Turn/River", turnAndRiverPanel);
        GridLayout gLayoutFlop = new GridLayout();
        gLayoutFlop.setColumns(1);
        gLayoutFlop.setRows(3);
        gLayoutFlop.setVgap(5);
        flopPanel.setLayout(gLayoutFlop);
        GridLayout gLayoutTurnAndRiver = new GridLayout();
        gLayoutTurnAndRiver.setColumns(1);
        gLayoutTurnAndRiver.setRows(2);
        gLayoutTurnAndRiver.setVgap(5);
        turnAndRiverPanel.setLayout(gLayoutFlop);
        jTabbedPaneInner.addTab("Player1", playerCardSelector);
        for(int i = 0; i < cardChooserFlop.length; i++)
            flopPanel.add(cardChooserFlop[i]);

        turnAndRiverPanel.add(cardChooserTurn);
        turnAndRiverPanel.add(cardChooserRiver);
        GridLayout gridLayout = new GridLayout();
        gridLayout.setColumns(1);
        gridLayout.setRows(2);
        summaryPanel.setLayout(gridLayout);
        summaryPanel.add(playerPanel);
        summaryPanel.add(communityPanel);
        playerPanel.setLayout(new FlowLayout(0));
        playerPanel.add(playerLabel);
        playerPanel.add(playerCard[0][0]);
        playerPanel.add(playerCard[0][1]);
        playerPanel.setBackground(Color.WHITE);
        playerPanel.setOpaque(true);
        communityPanel.setLayout(new FlowLayout(0));
        communityPanel.add(communityLabel);
        communityPanel.add(flopLabel[0]);
        communityPanel.add(flopLabel[1]);
        communityPanel.add(flopLabel[2]);
        communityPanel.add(turnLabel);
        communityPanel.add(riverLabel);
        communityPanel.setBackground(Color.WHITE);
        communityPanel.setOpaque(true);
        clearSelection.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0)
            {
                cardChooserFlop[0].setSelectedRank(-1);
                cardChooserFlop[1].setSelectedRank(-1);
                cardChooserFlop[2].setSelectedRank(-1);
                cardChooserTurn.setSelectedRank(-1);
                cardChooserRiver.setSelectedRank(-1);
                playerCardSelector.clear();
            }

        });
    }

    public CardChooser[] getCardChooserFlop()
    {
        return cardChooserFlop;
    }

    public CardChooser getCardChooserRiver()
    {
        return cardChooserRiver;
    }

    public CardChooser getCardChooserTurn()
    {
        return cardChooserTurn;
    }

    public PlayerCardSelector getPlayerCardSelector()
    {
        return playerCardSelector;
    }

    public JTabbedPane getJTabbedPaneInner()
    {
        return jTabbedPaneInner;
    }

    public CardLabel[] getFlopLabel()
    {
        return flopLabel;
    }

    public CardLabel getRiverLabel()
    {
        return riverLabel;
    }

    public CardLabel getTurnLabel()
    {
        return turnLabel;
    }

    public CardLabel[][] getPlayerCard()
    {
        return playerCard;
    }

    public JButton getClearSelection()
    {
        return clearSelection;
    }

    public JButton getPasteSelection()
    {
        return pasteSelection;
    }

    CardChooser cardChooserFlop[] = {
        new CardChooser(), new CardChooser(), new CardChooser()
    };
    CardChooser cardChooserTurn;
    CardChooser cardChooserRiver;
    PlayerCardSelector playerCardSelector;
    JTabbedPane jTabbedPaneInner;
    JTabbedPane jTabbedPaneTop;
    JPanel flopPanel;
    JPanel turnAndRiverPanel;
    JButton clearSelection;
    JButton pasteSelection;
    JToolBar jToolBar;
    JPanel summaryPanel;
    JPanel playerPanel;
    JPanel communityPanel;
    CardLabel flopLabel[] = {
        new CardLabel(), new CardLabel(), new CardLabel()
    };
    CardLabel turnLabel;
    CardLabel riverLabel;
    JLabel communityLabel;
    JLabel playerLabel;
    CardLabel playerCard[][] = {
        {
            new CardLabel(), new CardLabel()
        }
    };
}
// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MainController.java

package de.neuenberger.pokercalc.parts;

import de.neuenberger.poker.common.model.GameDescription;
import de.neuenberger.poker.common.ui.util.CardChooser;
import de.neuenberger.pokercalc.logic.ProbabilityCalculator;
import de.neuenberger.pokercalc.ui.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// Referenced classes of package de.neuenberger.pokercalc.parts:
//            ProbabilityTableController, CardChooserController, DetailedGameResultController

public class MainController
{

    public MainController()
    {
        mainFrame = new MainFrame();
        jSplitPane = new JSplitPane(1, true);
        jspLeftSplitPane = new JSplitPane(0, true);
        tableController = new ProbabilityTableController();
        cardDetailsController = new CardChooserController();
        detailedGameResultController = new DetailedGameResultController();
        probabilityCalcualator = null;
        jSplitPane.add(jspLeftSplitPane, "left");
        jSplitPane.add(cardDetailsController.getComponent(), "right");
        jSplitPane.setOneTouchExpandable(true);
        jspLeftSplitPane.setOneTouchExpandable(true);
        jspLeftSplitPane.add(tableController.getComponent(), "top");
        jspLeftSplitPane.add(detailedGameResultController.getComponent(), "bottom");
        mainFrame.add(jSplitPane, "Center");
        mainFrame.setVisible(true);
        mainFrame.addWindowListener(new WindowListener() {

            public void windowOpened(WindowEvent windowevent)
            {
            }

            public void windowClosing(WindowEvent arg0)
            {
                saveConfigurationMethod();
            }

            public void windowClosed(WindowEvent windowevent)
            {
            }

            public void windowIconified(WindowEvent windowevent)
            {
            }

            public void windowDeiconified(WindowEvent windowevent)
            {
            }

            public void windowActivated(WindowEvent windowevent)
            {
            }

            public void windowDeactivated(WindowEvent windowevent)
            {
            }

        });
        setup();
    }

    protected void setup()
    {
        tableController.getTablePanel().getJTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0)
            {
                newHand();
            }

        });
        DetailsGameResultPanel dgrp = detailedGameResultController.getDetailsGameResultPanel();
        dgrp.getJCalculate().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0)
            {
                calculateDetailedProbability();
            }

        });
    }

    public void calculateDetailedProbability()
    {
        GameDescription gd = cardDetailsController.getGameDescription();
        if(gd.getPlayerCards()[0][0] == null || gd.getPlayerCards()[0][1] == null)
        {
            JOptionPane.showMessageDialog(mainFrame, "No Pocket cards Selected!", "Please select some pocket cards", 1);
            return;
        }
        boolean reset = true;
        if(probabilityCalcualator == null)
            reset = false;
        else
        if(probabilityCalcualator.isFinished())
            reset = false;
        else
            probabilityCalcualator.setInterrupted(true);
        probabilityCalcualator = new ProbabilityCalculator();
        probabilityCalcualator.setGameDescription(gd);
        probabilityCalcualator.setPlayers(tableController.getOptionPanel().getSpinnerNumberModel().getNumber().intValue());
        Thread thread = new Thread(probabilityCalcualator);
        thread.start();
        de.neuenberger.pokercalc.model.util.ComprehensiveHandProbability comprehensiveHandProbability = probabilityCalcualator.getComprehensiveHandProbability();
        detailedGameResultController.getDetailsGameResultPanel().setModel(comprehensiveHandProbability);
    }

    protected void newHand()
    {
        int rank1 = tableController.getSelectedCard1();
        int rank2 = tableController.getSelectedCard2();
        boolean suited = tableController.isSelectionSuited();
        if(rank1 != -1 && rank2 != -1)
        {
            PlayerCardSelector pcs = cardDetailsController.getCardChooserPanel().getPlayerCardSelector();
            pcs.getCardChooserPocket()[0].setSelectedRank(rank1);
            pcs.getCardChooserPocket()[1].setSelectedRank(rank2);
        }
    }

    public void saveConfigurationMethod()
    {
        tableController.saveComponents();
        cardDetailsController.saveComponents();
    }

    MainFrame mainFrame;
    JSplitPane jSplitPane;
    JSplitPane jspLeftSplitPane;
    ProbabilityTableController tableController;
    CardChooserController cardDetailsController;
    DetailedGameResultController detailedGameResultController;
    ProbabilityCalculator probabilityCalcualator;
}
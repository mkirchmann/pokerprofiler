// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 18:44:20
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ProbabilityTableController.java

package de.neuenberger.pokercalc.parts;

import de.neuenberger.poker.common.parts.IController;
import de.neuenberger.pokercalc.io.ConfigurationReader;
import de.neuenberger.pokercalc.io.ConfigurationWriter;
import de.neuenberger.pokercalc.model.util.ProbabilityArray;
import de.neuenberger.pokercalc.model.util.ProbabilityModel;
import de.neuenberger.pokercalc.ui.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.*;

// Referenced classes of package de.neuenberger.pokercalc.parts:
//            IController, ProbabilityThread, OptionMediator, DealOneRound

public class ProbabilityTableController
    implements ActionListener, IController
{

    public ProbabilityTableController()
    {
        optionPanel = new OptionPanel();
        sliderPanel = new SliderPanel();
        tablePanel = new TablePanel();
        mainPanel = new JPanel();
        pThread = new ProbabilityThread();
        currentThread = new Thread(pThread);
        probabilityModel = new ProbabilityModel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(optionPanel, "North");
        mainPanel.add(sliderPanel, "South");
        mainPanel.add(tablePanel, "Center");
        setup();
    }

    protected void setup()
    {
        optionPanel.getJbProcess().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0)
            {
                startProcess();
            }

        });
        optionPanel.getJchkNormalized().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0)
            {
                pa.setNormalized(optionPanel.getJchkNormalized().isSelected());
            }

        });
        sliderPanel.getJSliderGreen().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent arg0)
            {
                setGreenThreshold();
            }

        });
        sliderPanel.getJSliderYellow().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent arg0)
            {
                setYellowThreshold();
            }

        });
        optionPanel.getJchkIncludeRiver().addActionListener(this);
        optionPanel.getJchkIncludeTurn().addActionListener(this);
        optionPanel.getJSpinner().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent arg0)
            {
                adaptPA();
            }

        });
        ConfigurationReader.loadConfiguration(probabilityModel);
        pa = probabilityModel.getProbabilityArray(OptionMediator.fromOptionPanel(optionPanel));
        tablePanel.setModel(pa);
        sliderPanel.getJSliderYellow().setValue(33);
        sliderPanel.getJSliderGreen().setValue(50);
        tablePanel.getJTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0)
            {
                table_selectionChanged(arg0);
            }

        });
    }

    protected void table_selectionChanged(ListSelectionEvent ev)
    {
        if(ev.getValueIsAdjusting())
            return;
        int c = tablePanel.getJTable().getSelectedColumn();
        int r = tablePanel.getJTable().getSelectedRow();
        boolean suited = c - 1 > r;
        if(c > 0 && r != -1)
        {
            OptionMediator om = OptionMediator.fromOptionPanel(optionPanel);
            DealOneRound.getMonteCarloValue(om.getPlayers(), (c + 2) - 1, r + 2, suited);
        }
    }

    protected void setYellowThreshold()
    {
        pa.setThresholdYellow(0.01F * (float)sliderPanel.getJSliderYellow().getValue());
    }

    protected void setGreenThreshold()
    {
        pa.setThresholdGreen(0.01F * (float)sliderPanel.getJSliderGreen().getValue());
    }

    protected void adaptPA()
    {
        OptionMediator oc = OptionMediator.fromOptionPanel(optionPanel);
        oc.toThread(pThread);
        pa = probabilityModel.getProbabilityArray(oc);
        setYellowThreshold();
        setGreenThreshold();
        tablePanel.setModel(pa);
    }

    protected void startProcess()
    {
        pThread = new ProbabilityThread();
        OptionMediator oc = OptionMediator.fromOptionPanel(optionPanel);
        oc.toThread(pThread);
        pThread.setTargetModel(pa);
        currentThread = new Thread(pThread);
        currentThread.start();
    }

    public void actionPerformed(ActionEvent arg0)
    {
        adaptPA();
    }

    public Component getComponent()
    {
        return mainPanel;
    }

    public void saveComponents()
    {
        ConfigurationWriter.saveConfiguration(probabilityModel);
    }

    public SliderPanel getSliderPanel()
    {
        return sliderPanel;
    }

    public TablePanel getTablePanel()
    {
        return tablePanel;
    }

    public int getSelectedCard1()
    {
        int c = tablePanel.getJTable().getSelectedColumn();
        int r = tablePanel.getJTable().getSelectedRow();
        if(c > 0 && r != -1)
            return (c + 2) - 1;
        else
            return -1;
    }

    public int getSelectedCard2()
    {
        int c = tablePanel.getJTable().getSelectedColumn();
        int r = tablePanel.getJTable().getSelectedRow();
        if(c > 0 && r != -1)
            return r + 2;
        else
            return -1;
    }

    public boolean isSelectionSuited()
    {
        int c = tablePanel.getJTable().getSelectedColumn();
        int r = tablePanel.getJTable().getSelectedRow();
        return c - 1 > r;
    }

    public OptionPanel getOptionPanel()
    {
        return optionPanel;
    }

    OptionPanel optionPanel;
    SliderPanel sliderPanel;
    TablePanel tablePanel;
    JPanel mainPanel;
    ProbabilityArray pa;
    ProbabilityThread pThread;
    Thread currentThread;
    ProbabilityModel probabilityModel;
}
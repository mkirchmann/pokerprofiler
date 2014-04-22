// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   OptionPanel.java

package de.neuenberger.pokercalc.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class OptionPanel extends JPanel
{

    public OptionPanel()
    {
        spinnerNumberModel = new SpinnerNumberModel();
        jchkIncludeTurn = new JCheckBox("With Turn", true);
        jchkIncludeRiver = new JCheckBox("With River", true);
        jchkNormalized = new JCheckBox("Normalized", true);
        jSpinner = new JSpinner();
        jComboBox = new JComboBox();
        jbProcess = new JButton("GO");
        setLayout(new FlowLayout());
        add(jchkIncludeTurn);
        add(jchkIncludeRiver);
        add(jchkNormalized);
        add(new JLabel("Number of Players:"));
        add(jSpinner);
        add(new JLabel("  Monte-Carlo Games:"));
        add(jComboBox);
        add(jbProcess);
        setup();
    }

    protected void setup()
    {
        jComboBox.addItem("1000");
        jComboBox.addItem("2500");
        jComboBox.addItem("5000");
        jComboBox.addItem("7500");
        jComboBox.addItem("10000");
        jComboBox.addItem("15000");
        jComboBox.addItem("20000");
        jComboBox.addItem("25000");
        jComboBox.addItem("50000");
        jComboBox.addItem("75000");
        jComboBox.addItem("100000");
        jComboBox.addItem("150000");
        jComboBox.addItem("200000");
        jComboBox.addItem("250000");
        jComboBox.addItem("500000");
        jComboBox.addItem("1000000");
        jComboBox.addItem("1500000");
        jComboBox.addItem("2000000");
        jComboBox.addItem("2500000");
        jComboBox.addItem("5000000");
        jComboBox.addItem("10000000");
        jComboBox.setSelectedIndex(2);
        spinnerNumberModel.setMinimum(new Integer(1));
        spinnerNumberModel.setMaximum(new Integer(8));
        spinnerNumberModel.setValue(new Integer(1));
        jSpinner.setModel(spinnerNumberModel);
        jchkIncludeTurn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0)
            {
                boolean stat = jchkIncludeTurn.isSelected();
                jchkIncludeRiver.setEnabled(stat);
            }

        });
    }

    public JCheckBox getJchkIncludeRiver()
    {
        return jchkIncludeRiver;
    }

    public JCheckBox getJchkIncludeTurn()
    {
        return jchkIncludeTurn;
    }

    public JCheckBox getJchkNormalized()
    {
        return jchkNormalized;
    }

    public JSpinner getJSpinner()
    {
        return jSpinner;
    }

    public SpinnerNumberModel getSpinnerNumberModel()
    {
        return spinnerNumberModel;
    }

    public JComboBox getJComboBox()
    {
        return jComboBox;
    }

    public JButton getJbProcess()
    {
        return jbProcess;
    }

    SpinnerNumberModel spinnerNumberModel;
    JCheckBox jchkIncludeTurn;
    JCheckBox jchkIncludeRiver;
    JCheckBox jchkNormalized;
    JSpinner jSpinner;
    JComboBox jComboBox;
    JButton jbProcess;
}
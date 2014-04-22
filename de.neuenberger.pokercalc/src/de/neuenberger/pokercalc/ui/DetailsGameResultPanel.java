// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DetailsGameResultPanel.java

package de.neuenberger.pokercalc.ui;

import java.awt.BorderLayout;
import java.io.PrintStream;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class DetailsGameResultPanel extends JPanel
    implements TableModelListener
{

    public DetailsGameResultPanel()
    {
        jTable = new JTable();
        jCalculate = new JButton("Refresh");
        jtb = new JToolBar();
        setLayout(new BorderLayout());
        add(jtb, "North");
        add(new JScrollPane(jTable), "Center");
        jtb.add(jCalculate);
        jTable.setAutoCreateColumnsFromModel(true);
    }

    public JButton getJCalculate()
    {
        return jCalculate;
    }

    public JTable getJTable()
    {
        return jTable;
    }

    public void setModel(TableModel tm)
    {
        if(jTable.getModel() != null)
            jTable.getModel().removeTableModelListener(this);
        jTable.setModel(tm);
        tm.addTableModelListener(this);
        jTable.setAutoCreateColumnsFromModel(true);
    }

    public void tableChanged(TableModelEvent arg0)
    {
        System.out.println("receiving change");
        Thread thread = new Thread() {

            public void run()
            {
                jTable.updateUI();
                jTable.repaint();
            }

        };
        SwingUtilities.invokeLater(thread);
    }

    JTable jTable;
    JButton jCalculate;
    JToolBar jtb;
}
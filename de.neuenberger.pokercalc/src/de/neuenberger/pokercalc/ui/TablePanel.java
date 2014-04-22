// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 18:41:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   TablePanel.java

package de.neuenberger.pokercalc.ui;

import de.neuenberger.pokercalc.model.util.ProbabilityArray;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

// Referenced classes of package de.neuenberger.pokercalc.ui:
//            FloatTableCellRenderer

public class TablePanel extends JPanel
    implements TableModelListener
{

    public TablePanel()
    {
        jTable = new JTable();
        setLayout(new BorderLayout());
        add(new JScrollPane(jTable), "Center");
        setup();
    }

    protected void setup()
    {
        jTable.setDefaultRenderer(java.lang.Float.class, new FloatTableCellRenderer());
        jTable.setAutoCreateColumnsFromModel(true);
    }

    public JTable getJTable()
    {
        return jTable;
    }

    public void setModel(ProbabilityArray pa)
    {
        if(jTable.getModel() != null)
            jTable.getModel().removeTableModelListener(this);
        jTable.setModel(pa);
        pa.addTableModelListener(this);
    }

    public void tableChanged(TableModelEvent arg0)
    {
        SwingUtilities.invokeLater(new  Runnable() {

			public void run() {
				jTable.updateUI();
			}
        	
        });
    }

    JTable jTable;
}
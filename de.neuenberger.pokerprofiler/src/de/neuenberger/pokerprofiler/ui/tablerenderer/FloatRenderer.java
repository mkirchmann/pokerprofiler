package de.neuenberger.pokerprofiler.ui.tablerenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Label;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class FloatRenderer extends DefaultTableCellRenderer {

	
	DecimalFormat df=new DecimalFormat("0.00");
	
	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
		Component comp=super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);

		if (arg1 instanceof Float) {
			Float f=(Float)arg1;
			if (f.floatValue()<0.0f) {
				comp.setForeground(Color.RED);
			} else if (f.floatValue()==0.0f) {
				comp.setForeground(Color.GRAY);
			} else {
				comp.setForeground(Color.BLACK);
			}
			this.setText(df.format(f.floatValue()));
		}
		
		return comp;
	}

}

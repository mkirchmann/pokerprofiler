// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   TablePanel.java

package de.neuenberger.pokercalc.ui;

import de.neuenberger.pokercalc.model.util.ProbabilityArray;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class FloatTableCellRenderer extends DefaultTableCellRenderer
{

    FloatTableCellRenderer()
    {
    }

    public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5)
    {
        Component comp = super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);
        ProbabilityArray pa = (ProbabilityArray)arg0.getModel();
        float v = ((Float)arg1).floatValue();
        if(v > pa.getThresholdGreen())
        {
            if(pa.getThresholdGreen() != 0.0F)
            {
                float r = v - pa.getThresholdGreen();
                comp.setBackground(mixColors(BESTCOLOR, CUSTOMGREEN, r));
            } else
            {
                comp.setBackground(Color.GREEN);
            }
        } else
        if(v > pa.getThresholdYellow())
        {
            float rangeDiff = pa.getThresholdGreen() - pa.getThresholdYellow();
            if(rangeDiff > 0.0F)
            {
                float r = (v - pa.getThresholdYellow()) / rangeDiff;
                comp.setBackground(mixColors(BESTCOLOR, Color.YELLOW, r));
            } else
            {
                comp.setBackground(Color.RED);
            }
            comp.setBackground(Color.YELLOW);
        } else
        if(v == 0.0F)
            comp.setBackground(Color.GRAY);
        else
        if(pa.getThresholdYellow() != 0.0F)
        {
            float r = v / pa.getThresholdYellow();
            r *= r;
            comp.setBackground(mixColors(Color.YELLOW, Color.RED, r));
        } else
        {
            comp.setBackground(Color.RED);
        }
        return comp;
    }

    public static Color mixColors(Color a, Color b, float r)
    {
        float invR = 1.0F - r;
        int red = (int)(r * (float)a.getRed() + invR * (float)b.getRed());
        int green = (int)(r * (float)a.getGreen() + invR * (float)b.getGreen());
        int blue = (int)(r * (float)a.getBlue() + invR * (float)b.getBlue());
        return new Color(red, green, blue);
    }

    static final Color BESTCOLOR = new Color(0, 128, 0);
    static final Color CUSTOMGREEN = new Color(128, 255, 128);

}
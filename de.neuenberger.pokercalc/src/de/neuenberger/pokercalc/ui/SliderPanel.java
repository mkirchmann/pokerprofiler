// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 18:40:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SliderPanel.java

package de.neuenberger.pokercalc.ui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class SliderPanel extends JPanel
{

    public SliderPanel()
    {
        jSliderYellow = new JSlider(0, 100);
        jSliderGreen = new JSlider(0, 100);
        setLayout(new BorderLayout());
        add(jSliderYellow, "North");
        add(jSliderGreen, "South");
    }

    public JSlider getJSliderGreen()
    {
        return jSliderGreen;
    }

    public JSlider getJSliderYellow()
    {
        return jSliderYellow;
    }

    JSlider jSliderYellow;
    JSlider jSliderGreen;
}
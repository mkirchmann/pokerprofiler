// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MainFrame.java

package de.neuenberger.pokercalc.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class MainFrame extends JFrame
{

    public MainFrame()
    {
        setSize(1000, 800);
        setLayout(new BorderLayout());
    }

    protected void processWindowEvent(WindowEvent arg0)
    {
        super.processWindowEvent(arg0);
        if(arg0.getID() == 201)
            System.exit(0);
    }
}
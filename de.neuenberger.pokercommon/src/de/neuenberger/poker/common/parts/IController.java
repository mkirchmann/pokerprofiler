// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   IController.java

package de.neuenberger.poker.common.parts;

import java.awt.Component;

public interface IController
{

    public abstract Component getComponent();

    public abstract void saveComponents();
}
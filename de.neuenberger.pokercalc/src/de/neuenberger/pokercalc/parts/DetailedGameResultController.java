// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DetailedGameResultController.java

package de.neuenberger.pokercalc.parts;

import de.neuenberger.poker.common.parts.IController;
import de.neuenberger.pokercalc.ui.DetailsGameResultPanel;
import java.awt.Component;

// Referenced classes of package de.neuenberger.pokercalc.parts:
//            IController

public class DetailedGameResultController
    implements IController
{

    public DetailedGameResultController()
    {
        detailsGameResultPanel = new DetailsGameResultPanel();
        setup();
    }

    protected void setup()
    {
    }

    public Component getComponent()
    {
        return detailsGameResultPanel;
    }

    public void saveComponents()
    {
    }

    public DetailsGameResultPanel getDetailsGameResultPanel()
    {
        return detailsGameResultPanel;
    }

    DetailsGameResultPanel detailsGameResultPanel;
}
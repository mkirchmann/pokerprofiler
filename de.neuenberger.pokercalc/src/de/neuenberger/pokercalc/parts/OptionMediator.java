// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   OptionMediator.java

package de.neuenberger.pokercalc.parts;

import de.neuenberger.pokercalc.ui.OptionPanel;
import javax.swing.*;

// Referenced classes of package de.neuenberger.pokercalc.parts:
//            ProbabilityThread

public class OptionMediator
{

    public OptionMediator()
    {
    }

    public static OptionMediator fromOptionPanel(OptionPanel optionPanel)
    {
        OptionMediator oc = new OptionMediator();
        oc.includeRiver = optionPanel.getJchkIncludeRiver().isSelected();
        oc.includeTurn = optionPanel.getJchkIncludeTurn().isSelected();
        oc.numberOfDeals = Integer.valueOf(optionPanel.getJComboBox().getSelectedItem().toString()).longValue();
        oc.players = optionPanel.getSpinnerNumberModel().getNumber().intValue();
        oc.normalized = optionPanel.getJchkNormalized().isSelected();
        return oc;
    }

    public void toThread(ProbabilityThread pThread)
    {
        pThread.setIncludeRiver(includeRiver);
        pThread.setIncludeTurn(includeTurn);
        pThread.setNumberOfDeals(numberOfDeals);
        pThread.setPlayers(players);
    }

    public int hashCode()
    {
        int add = 0;
        if(includeTurn)
        {
            add += 2;
            if(includeRiver)
                add++;
        }
        int hc = (players << 2) + add;
        return hc;
    }

    public boolean isIncludeRiver()
    {
        return includeRiver;
    }

    public void setIncludeRiver(boolean includeRiver)
    {
        this.includeRiver = includeRiver;
    }

    public boolean isIncludeTurn()
    {
        return includeTurn;
    }

    public void setIncludeTurn(boolean includeTurn)
    {
        this.includeTurn = includeTurn;
    }

    public long getNumberOfDeals()
    {
        return numberOfDeals;
    }

    public void setNumberOfDeals(long numberOfDeals)
    {
        this.numberOfDeals = numberOfDeals;
    }

    public int getPlayers()
    {
        return players;
    }

    public void setPlayers(int players)
    {
        this.players = players;
    }

    public boolean isNormalized()
    {
        return normalized;
    }

    public void setNormalized(boolean normalized)
    {
        this.normalized = normalized;
    }

    boolean normalized;
    boolean includeRiver;
    boolean includeTurn;
    int players;
    long numberOfDeals;
}
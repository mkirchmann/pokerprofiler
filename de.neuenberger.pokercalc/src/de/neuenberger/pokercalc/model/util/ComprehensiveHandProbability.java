// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ComprehensiveHandProbability.java

package de.neuenberger.pokercalc.model.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

// Referenced classes of package de.neuenberger.pokercalc.model.util:
//            HandProbabilityResult

public class ComprehensiveHandProbability
    implements TableModel, ActionListener
{

    public ComprehensiveHandProbability()
    {
        handProbabilityResult = new HandProbabilityResult[3];
        listener = new Vector();
        quietUpdate = false;
        for(int i = 0; i < 3; i++)
        {
            handProbabilityResult[i] = new HandProbabilityResult();
            handProbabilityResult[i].addActionListener(this);
        }

    }

    public HandProbabilityResult getWinProbability()
    {
        return handProbabilityResult[0];
    }

    public HandProbabilityResult getTieProbability()
    {
        return handProbabilityResult[1];
    }

    public HandProbabilityResult getLooseProbability()
    {
        return handProbabilityResult[2];
    }

    public void normalizeToNumberOfTries(long runs)
    {
        for(int i = 0; i < 3; i++)
            handProbabilityResult[i].divideAll(runs);

    }

    public int getRowCount()
    {
        return 10;
    }

    public int getColumnCount()
    {
        return 4;
    }

    public String getColumnName(int arg0)
    {
        switch(arg0)
        {
        case 0: // '\0'
            return "Type";

        case 1: // '\001'
            return "Player";

        case 2: // '\002'
            return "Tie";

        case 3: // '\003'
            return "Opponents";
        }
        return null;
    }

    public Class getColumnClass(int arg0)
    {
        if(arg0 == 0)
            return java.lang.String.class;
        else
            return java.lang.Float.class;
    }

    public boolean isCellEditable(int arg0, int arg1)
    {
        return false;
    }

    public Object getValueAt(int y, int x)
    {
        if(x == 0)
        {
            switch(y)
            {
            case 0: // '\0'
                return "High Card";

            case 1: // '\001'
                return "Pair";

            case 2: // '\002'
                return "Two Pairs";

            case 3: // '\003'
                return "Triplet";

            case 4: // '\004'
                return "Straight";

            case 5: // '\005'
                return "Flush";

            case 6: // '\006'
                return "Full House";

            case 7: // '\007'
                return "Poker";

            case 8: // '\b'
                return "Straight Flush";

            case 9: // '\t'
                return "Summary";
            }
        } else
        {
            int c = x - 1;
            switch(y)
            {
            case 0: // '\0'
                return new Float(handProbabilityResult[c].getHighCardProbability());

            case 1: // '\001'
                return new Float(handProbabilityResult[c].getPairProbability());

            case 2: // '\002'
                return new Float(handProbabilityResult[c].getTwopairProbability());

            case 3: // '\003'
                return new Float(handProbabilityResult[c].getTripletProbability());

            case 4: // '\004'
                return new Float(handProbabilityResult[c].getStraightProbability());

            case 5: // '\005'
                return new Float(handProbabilityResult[c].getFlushProbability());

            case 6: // '\006'
                return new Float(handProbabilityResult[c].getFullhouseProbability());

            case 7: // '\007'
                return new Float(handProbabilityResult[c].getPokerProbability());

            case 8: // '\b'
                return new Float(handProbabilityResult[c].getStraightflushProbability());

            case 9: // '\t'
                return new Float(handProbabilityResult[c].getProbability());
            }
        }
        return null;
    }

    public void setValueAt(Object obj, int i, int j)
    {
    }

    public void addTableModelListener(TableModelListener arg0)
    {
        listener.add(arg0);
    }

    public void removeTableModelListener(TableModelListener arg0)
    {
        listener.remove(arg0);
    }

    public void actionPerformed(ActionEvent arg0)
    {
        if(quietUpdate)
            return;
        TableModelEvent tme = new TableModelEvent(this);
        for(int i = 0; i < listener.size(); i++)
            ((TableModelListener)listener.get(i)).tableChanged(tme);

    }

    public boolean isQuietUpdate()
    {
        return quietUpdate;
    }

    public void setQuietUpdate(boolean quietUpdate)
    {
        this.quietUpdate = quietUpdate;
        actionPerformed(new ActionEvent(this, 0, "QuietUpdate"));
    }

    HandProbabilityResult handProbabilityResult[];
    Vector listener;
    boolean quietUpdate;
}
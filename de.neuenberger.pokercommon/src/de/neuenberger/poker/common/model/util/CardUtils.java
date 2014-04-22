// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CardUtils.java

package de.neuenberger.poker.common.model.util;

import de.neuenberger.poker.common.model.Card;
import sun.misc.Sort;

// Referenced classes of package de.neuenberger.pokercalc.model.util:
//            CardComparator

public class CardUtils
{

    public CardUtils()
    {
    }

    public static void sortCardVector(Card card[])
    {
        Sort.quicksort(card, CardComparator.getInstance());
    }

    public static String nameCards(Card cards[])
    {
        StringBuffer strBuf = new StringBuffer();
        for(int i = 0; i < cards.length; i++)
        {
            if(i != 0)
                strBuf.append(", ");
            strBuf.append(cards[i]);
        }

        return strBuf.toString();
    }
}
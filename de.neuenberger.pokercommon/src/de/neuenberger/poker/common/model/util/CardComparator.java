// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CardUtils.java

package de.neuenberger.poker.common.model.util;

import de.neuenberger.poker.common.model.Card;
import sun.misc.Compare;

class CardComparator
    implements Compare
{

    CardComparator()
    {
    }

    public static CardComparator getInstance()
    {
        return instance;
    }

    public int doCompare(Object arg0, Object arg1)
    {
        Card a = (Card)arg0;
        Card b = (Card)arg1;
        if (a==null) {
        	if (b==null) {
        		return 0;
        	} else {
        		return -1;
        	}
        }
        if (b==null) {
        	return 1;
        }
        return -Integer.valueOf(a.getRank()).compareTo(Integer.valueOf(b.getRank()));
    }

    private static CardComparator instance = new CardComparator();

}
// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 18:37:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   TopCard.java

package de.neuenberger.poker.common.logic;

import de.neuenberger.poker.common.model.Card;

// Referenced classes of package de.neuenberger.pokercalc.logic:
//            HandRank

public class TopCard extends HandRank
{

    public TopCard(Card cards[])
    {
        valid = true;
        for(int i = 0; i < 5 && i < cards.length; i++)
            highestRank[i] = cards[i].getRank();

        rank = 0;
    }

    public String toString()
    {
        return "Top Card " + Card.toString(highestRank[0]) + " and " + Card.toString(highestRank[1]) + ", " + Card.toString(highestRank[2]) + ", " + Card.toString(highestRank[3]) + " and " + Card.toString(highestRank[4]);
    }
}
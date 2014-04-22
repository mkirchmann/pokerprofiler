// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Pair.java

package de.neuenberger.poker.common.logic;

import de.neuenberger.poker.common.model.Card;

// Referenced classes of package de.neuenberger.pokercalc.logic:
//            HandRank

public class Pair extends HandRank
{

    public Pair(Card cards[])
    {
        int topIndex = 0;
        valid = false;
        if(cards.length <= 1)
            return;
        for(int i = 0; i < cards.length - 1; i++)
            if(cards[i].getRank() == cards[i + 1].getRank())
            {
                valid = true;
                highestRank[0] = cards[i].getRank();
                i++;
            } else
            if(topIndex < 3)
            {
                highestRank[topIndex + 1] = cards[i].getRank();
                topIndex++;
            }

        rank = 1;
    }

    public String toString()
    {
        return "Pair, " + Card.toString(highestRank[0]) + " with " + Card.toString(highestRank[1]) + ", " + Card.toString(highestRank[2]) + ", " + Card.toString(highestRank[3]) + ", " + Card.toString(highestRank[4]) + " Kicker";
    }
}
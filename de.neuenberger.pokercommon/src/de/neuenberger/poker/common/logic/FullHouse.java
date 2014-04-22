// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FullHouse.java

package de.neuenberger.poker.common.logic;

import de.neuenberger.poker.common.model.Card;

// Referenced classes of package de.neuenberger.pokercalc.logic:
//            HandRank

public class FullHouse extends HandRank
{

    public FullHouse(Card cards[])
    {
        boolean hasThree = false;
        boolean hasTwo = false;
        valid = false;
        if(cards.length <= 4)
            return;
        for(int i = 2; i <= 14; i++)
        {
            int count = HandRank.countRank(cards, i);
            if(count >= 3)
            {
                highestRank[0] = i;
                hasThree = true;
            } else
            if(count == 2)
            {
                highestRank[1] = i;
                hasTwo = true;
            }
        }

        valid = hasThree && hasTwo;
        rank = 7;
    }

    public String toString()
    {
        return "Full House, " + Card.toString(highestRank[0]) + " full of " + Card.toString(highestRank[1]);
    }
}
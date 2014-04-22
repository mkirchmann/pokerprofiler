// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TwoPair.java

package de.neuenberger.poker.common.logic;

import de.neuenberger.poker.common.model.Card;

// Referenced classes of package de.neuenberger.pokercalc.logic:
//            HandRank

public class TwoPair extends HandRank
{

    public TwoPair(Card cards[])
    {
        boolean foundOne = false;
        valid = false;
        if(cards.length <= 3)
            return;
        int highestAddedRank = -1;
        for(int i = 0; i < cards.length - 1; i++)
            if(cards[i].getRank() == cards[i + 1].getRank() && !valid)
            {
                if(foundOne)
                {
                    highestRank[1] = cards[i].getRank();
                    i++;
                    valid = true;
                } else
                {
                    foundOne = true;
                    highestRank[0] = cards[i].getRank();
                    i++;
                }
            } else
            if(highestAddedRank == -1)
            {
                highestAddedRank = cards[i].getRank();
                highestRank[2] = highestAddedRank;
            }

        rank = 2;
    }

    public String toString()
    {
        return "Two Pairs, " + Card.toString(highestRank[0]) + " and " + Card.toString(highestRank[1]) + ", " + Card.toString(highestRank[2]) + " Kicker";
    }
}

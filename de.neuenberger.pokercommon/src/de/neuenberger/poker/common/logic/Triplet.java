// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Triplet.java

package de.neuenberger.poker.common.logic;

import de.neuenberger.poker.common.model.Card;

// Referenced classes of package de.neuenberger.pokercalc.logic:
//            HandRank

public class Triplet extends HandRank
{

    public Triplet(Card cards[])
    {
        int currentRank = 0;
        valid = false;
        if(cards.length <= 2)
            return;
        for(int i = 0; i < cards.length - 2; i++)
            if(cards[i].getRank() == cards[i + 1].getRank() && cards[i + 1].getRank() == cards[i + 2].getRank())
            {
                highestRank[0] = cards[i].getRank();
                i += 2;
                valid = true;
            } else
            if(currentRank < 2)
            {
                highestRank[currentRank + 1] = cards[i].getRank();
                currentRank++;
            }

        rank = 3;
    }

    public String toString()
    {
        return "Three of a kind, " + Card.toString(highestRank[0]) + " with " + Card.toString(highestRank[1]) + ", " + Card.toString(highestRank[2]) + " Kicker";
    }
}

// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FourOfAKind.java

package de.neuenberger.poker.common.logic;

import de.neuenberger.poker.common.model.Card;

// Referenced classes of package de.neuenberger.pokercalc.logic:
//            HandRank

public class FourOfAKind extends HandRank
{

    public FourOfAKind(Card cards[])
    {
        int additionalCard = -1;
        valid = false;
        if(cards.length <= 3)
            return;
        for(int i = 0; i < cards.length - 3; i++)
            if(cards[i].getRank() == cards[i + 1].getRank() && cards[i + 1].getRank() == cards[i + 2].getRank() && cards[i + 2].getRank() == cards[i + 3].getRank())
            {
                valid = true;
                highestRank[0] = cards[i].getRank();
                i += 3;
            } else
            if(additionalCard == -1)
            {
                additionalCard = cards[i].getRank();
                highestRank[1] = additionalCard;
            }

        rank = 8;
    }

    public String toString()
    {
        return "Four of a kind " + Card.toString(highestRank[0]) + " with " + Card.toString(highestRank[1]) + " Kicker";
    }
}
// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 18:35:37
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   StraightAndFlush.java

package de.neuenberger.poker.common.logic;

import de.neuenberger.poker.common.model.Card;

// Referenced classes of package de.neuenberger.pokercalc.logic:
//            HandRank

public class StraightAndFlush extends HandRank
{

    public StraightAndFlush(Card cards[])
    {
        rank = 4;
        valid = false;
        if(cards.length <= 4)
            return;
        int colorCount[] = new int[4];
        for(int i = 0; i < cards.length; i++)
            colorCount[cards[i].getColor()]++;

        int currentFlushCard = 0;
        for(int i = 0; i < 4; i++)
        {
            if(colorCount[i] < 5)
                continue;
            rank = HandRank.FLUSH;
            valid = true;
            for(int x = 0; x < cards.length; x++) {
                if(cards[x].getColor() == i && currentFlushCard < 5)
                {
                    highestRank[currentFlushCard] = cards[x].getRank();
                    currentFlushCard++;
                }
            }

            if(HandRank.hasRank(cards, 14, i) && HandRank.hasRank(cards, 5, i) && HandRank.hasRank(cards, 4, i) && HandRank.hasRank(cards, 3, i) && HandRank.hasRank(cards, 2, i))
            {
                highestRank[0] = 5;
                highestRank[1] = 4;
                highestRank[2] = 3;
                highestRank[3] = 2;
                highestRank[4] = 1;
                rank = HandRank.STRAIGHTFLUSH;
            }
            for(int x = 0; x < cards.length; x++)
            {
                int cRank = cards[x].getRank();
                if(HandRank.hasRank(cards, cRank, i) && HandRank.hasRank(cards, cRank - 1, i) && HandRank.hasRank(cards, cRank - 2, i) && HandRank.hasRank(cards, cRank - 3, i) && HandRank.hasRank(cards, cRank - 4, i))
                {
                    highestRank[0] = cRank;
                    highestRank[1] = cRank-1;
                    highestRank[2] = cRank-2;
                    highestRank[3] = cRank-3;
                    highestRank[4] = cRank-4;
                    rank = HandRank.STRAIGHTFLUSH;
                    break;
                }
            }

            break;
        }

        if(!valid)
        {
        	rank=HandRank.STRAIGHT;
            if(HandRank.hasRank(cards, 14) && HandRank.hasRank(cards, 5) && HandRank.hasRank(cards, 4) && HandRank.hasRank(cards, 3) && HandRank.hasRank(cards, 2))
            {
                highestRank[0] = 5;
                highestRank[1] = 4;
                highestRank[2] = 3;
                highestRank[3] = 2;
                highestRank[4] = 1;
                valid = true;
            }
            for(int x = 0; x < cards.length; x++)
            {
                int cRank = cards[x].getRank();
                if(!HandRank.hasRank(cards, cRank - 1) || !HandRank.hasRank(cards, cRank - 2) || !HandRank.hasRank(cards, cRank - 3) || !HandRank.hasRank(cards, cRank - 4))
                    continue;
                highestRank[0] = cRank;
                highestRank[1] = cRank-1;
                highestRank[2] = cRank-2;
                highestRank[3] = cRank-3;
                highestRank[4] = cRank-4;
                valid = true;
                break;
            }

        }
    }

    public String toString()
    {
        if(rank == 9)
            if(highestRank[0] == 14)
                return "Royal Flash";
            else
                return "Straight Flash " + Card.toString(highestRank[0]) + " to " + Card.toString(highestRank[0] - 4);
        if(rank == 6)
            return "Flush with " + Card.toString(highestRank[0]) + " Kicker";
        if(rank == 4)
            return "Straight " + Card.toString(highestRank[0]) + " to " + Card.toString(highestRank[0] - 4);
        else
            return "no straight, no flush";
    }
}
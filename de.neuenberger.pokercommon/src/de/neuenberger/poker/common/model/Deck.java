// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Deck.java

package de.neuenberger.poker.common.model;

import sun.misc.Compare;
import sun.misc.Sort;

// Referenced classes of package de.neuenberger.pokercalc.model:
//            Card

public class Deck
    implements Compare
{

    public Deck()
    {
        deck = new Card[52];
        int currentCard = 0;
        for(int i = 2; i <= 14; i++)
        {
            for(int x = 0; x <= 3; x++)
            {
                Card card = new Card(i, x);
                deck[currentCard] = card;
                currentCard++;
            }

        }

    }

    public Card[] getShuffledCards()
    {
        for(int i = 0; i < deck.length; i++)
            deck[i].setRandom((int)(Math.random() * 32767D));

        Sort.quicksort(deck, this);
        return deck;
    }

    public int doCompare(Object arg0, Object arg1)
    {
        int r1 = ((Card)arg0).getRandom();
        int r2 = ((Card)arg1).getRandom();
        if(r1 < r2)
            return -1;
        return r2 >= r1 ? 0 : 1;
    }

    public Card gimmeCard(int rank, int color)
    {
        for(int i = 0; i < deck.length; i++)
            if(deck[i].getRank() == rank && deck[i].getColor() == color)
                return deck[i];

        return null;
    }

    public static Deck getInstance()
    {
        return instance;
    }

    public Card[] getDeck()
    {
        return deck;
    }

    Card deck[];
    private static Deck instance = new Deck();
    
    public Card[] getCardFromCSString(String str) {
    	String strArr[]=str.split(",");
    	Card retCard[]=new Card[strArr.length];
    	for (int i=0; i<strArr.length; i++) {
    		char r=strArr[i].trim().charAt(0);
    		char c=strArr[i].trim().charAt(1);
    		retCard[i]=gimmeCard(Card.getRankFromCharacter(r),Card.getColorFromCharacter(c));
    	}
    	return retCard;
    }
    
    public Card[] getCardFromSSString(String str) {
    	String strArr[]=str.split(" ");
    	Card retCard[]=new Card[strArr.length];
    	for (int i=0; i<strArr.length; i++) {
    		char r=strArr[i].trim().charAt(0);
    		char c=strArr[i].trim().charAt(1);
    		retCard[i]=gimmeCard(Card.getRankFromCharacter(r),Card.getColorFromCharacter(c));
    	}
    	return retCard;
    }

}

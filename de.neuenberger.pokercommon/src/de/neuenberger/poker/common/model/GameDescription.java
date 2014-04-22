// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GameDescription.java

package de.neuenberger.poker.common.model;



// Referenced classes of package de.neuenberger.pokercalc.model:
//            Card

public class GameDescription
{

    public GameDescription()
    {
        flop = new Card[3];
        playerCards = new Card[2][10];
        numberOfPlayers = 8;
    }

    public void setFlopCards(Card c[])
    {
        flop[0] = c[0];
        flop[1] = c[1];
        flop[2] = c[2];
    }

    public void setTurn(Card c)
    {
        turn = c;
    }

    public void setRiver(Card c)
    {
        river = c;
    }

    public void setPlayersCard(int p, Card c1, Card c2)
    {
        playerCards[p][0] = c1;
        playerCards[p][1] = c2;
    }

    public int getNumberOfPlayers()
    {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers)
    {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Card[] getFlop()
    {
        return flop;
    }

    public Card[][] getPlayerCards()
    {
        return playerCards;
    }

    public Card getRiver()
    {
        return river;
    }

    public Card getTurn()
    {
        return turn;
    }

    Card flop[];
    Card turn;
    Card river;
    Card playerCards[][];
    int numberOfPlayers;
}
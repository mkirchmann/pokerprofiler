// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TexasHoldem.java

package de.neuenberger.poker.common.model;



// Referenced classes of package de.neuenberger.pokercalc.model:
//            Card, Deck

public class TexasHoldem
{

    public TexasHoldem()
    {
        flop = new Card[3];
        turn = null;
        river = null;
        player = new Card[2];
        rndPlayer = new Card[9][2];
        players = 1;
        currentCard = 0;
    }

    public void dealRound(Deck deck)
    {
        dealRound(deck, true, true);
    }

    public void dealRound(Deck deck, boolean withTurn, boolean withRiver)
    {
        currentCard = 0;
        for(int i = 0; i < players; i++)
        {
            rndPlayer[i][0] = getNextCard(deck);
            rndPlayer[i][1] = getNextCard(deck);
        }

        dealFlop(deck);
        dealTurn(deck);
        dealRiver(deck);
    }

    public void dealFlop(Deck deck)
    {
        if(!fixFlop)
        {
            flop[0] = getNextCard(deck);
            flop[1] = getNextCard(deck);
            flop[2] = getNextCard(deck);
        }
    }

    public void dealTurn(Deck deck)
    {
        if(!fixTurn)
            turn = getNextCard(deck);
    }

    public void dealRiver(Deck deck)
    {
        if(!fixRiver)
            river = getNextCard(deck);
    }

    public void resetDealtCards()
    {
        flop[0] = null;
        flop[1] = null;
        flop[2] = null;
        turn = null;
        river = null;
    }

    public Card[] getCardsOfRndPlayer(int idx)
    {
        return cardsToArray(idx);
    }

    protected Card[] cardsToArray(int idx)
    {
        int size = 7;
        if(turn == null)
            size -= 2;
        else
        if(river == null)
            size--;
        Card card[] = new Card[size];
        card[2] = flop[0];
        card[3] = flop[1];
        card[4] = flop[2];
        if(idx == -1)
        {
            card[0] = player[0];
            card[1] = player[1];
        } else
        {
            card[0] = rndPlayer[idx][0];
            card[1] = rndPlayer[idx][1];
        }
        if(turn != null)
        {
            card[5] = turn;
            if(river != null)
                card[6] = river;
        }
        return card;
    }

    public Card[] getCardsOfPlayer()
    {
        return cardsToArray(-1);
    }

    public Card getNextCard(Deck deck)
    {
        Card card = deck.getDeck()[currentCard];
        currentCard++;
        if(card == player[0] || card == player[1])
            return getNextCard(deck);
        else
            return card;
    }

    public int getPlayers()
    {
        return players;
    }

    public void setPlayers(int players)
    {
        this.players = players;
    }

    public Card getRiver()
    {
        return river;
    }

    public void setRiver(Card river)
    {
        this.river = river;
    }

    public Card getTurn()
    {
        return turn;
    }

    public void setTurn(Card turn)
    {
        this.turn = turn;
    }

    public Card[] getFlop()
    {
        return flop;
    }

    public Card[] getPlayer()
    {
        return player;
    }

    public Card[][] getRndPlayer()
    {
        return rndPlayer;
    }

    public boolean isFixFlop()
    {
        return fixFlop;
    }

    public void setFixFlop(boolean fixFlop)
    {
        this.fixFlop = fixFlop;
    }

    public boolean isFixRiver()
    {
        return fixRiver;
    }

    public void setFixRiver(boolean fixRiver)
    {
        this.fixRiver = fixRiver;
    }

    public boolean isFixTurn()
    {
        return fixTurn;
    }

    public void setFixTurn(boolean fixTurn)
    {
        this.fixTurn = fixTurn;
    }

    Card flop[];
    Card turn;
    Card river;
    Card player[];
    Card rndPlayer[][];
    int players;
    int currentCard;
    boolean fixFlop;
    boolean fixTurn;
    boolean fixRiver;
}

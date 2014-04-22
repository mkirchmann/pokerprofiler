// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DealOneRound.java

package de.neuenberger.pokercalc.parts;

import de.neuenberger.poker.common.logic.HandRank;
import de.neuenberger.poker.common.logic.HandRankFactory;
import de.neuenberger.poker.common.model.Deck;
import de.neuenberger.poker.common.model.TexasHoldem;
import de.neuenberger.poker.common.model.util.CardUtils;

import java.io.PrintStream;
import java.util.Vector;
import sun.misc.Sort;

public class DealOneRound
{

    public DealOneRound()
    {
    }

    public static void getMonteCarloValue(int players, int rank1, int rank2, boolean suited)
    {
        HandRankFactory hrf = HandRankFactory.getInstance();
        Deck deck = Deck.getInstance();
        TexasHoldem th = new TexasHoldem();
        th.setPlayers(players);
        if(!suited || rank1 == rank2)
        {
            th.getPlayer()[0] = deck.gimmeCard(rank1, 2);
            th.getPlayer()[1] = deck.gimmeCard(rank2, 3);
        } else
        {
            th.getPlayer()[0] = deck.gimmeCard(rank1, 2);
            th.getPlayer()[1] = deck.gimmeCard(rank2, 2);
        }
        Vector vec = new Vector();
        hrf.setDebug(true);
        vec.clear();
        deck.getShuffledCards();
        th.dealRound(deck, true, true);
        System.out.println("dealt cards. Player has " + CardUtils.nameCards(th.getPlayer()));
        System.out.println("Flop is: " + CardUtils.nameCards(th.getFlop()));
        System.out.println("Turn is: " + th.getTurn());
        System.out.println("River is: " + th.getRiver());
        de.neuenberger.poker.common.model.Card cards[] = th.getCardsOfPlayer();
        HandRank hrP1 = hrf.getHandRank(cards);
        vec.add(hrP1);
        de.neuenberger.poker.common.model.Card pCards[][] = new de.neuenberger.poker.common.model.Card[8][7];
        for(int x = 0; x < th.getPlayers(); x++)
        {
            pCards[x] = th.getCardsOfRndPlayer(x);
            HandRank hrrp = hrf.getHandRank(pCards[x]);
            hrrp.setAssignedID(x + 1);
            vec.add(hrrp);
        }

        HandRank hrnkCompare[] = new HandRank[vec.size()];
        vec.toArray(hrnkCompare);
        Sort.quicksort(hrnkCompare, hrf);
        if(hrnkCompare[0] == hrP1)
            System.out.println("Player won.");
        else
            System.out.println("Player lost.");
        hrf.setDebug(true);
    }
}
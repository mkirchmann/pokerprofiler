// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 18:40:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ProbabilityThread.java

package de.neuenberger.pokercalc.parts;

import de.neuenberger.poker.common.logic.HandRank;
import de.neuenberger.poker.common.logic.HandRankFactory;
import de.neuenberger.poker.common.model.Deck;
import de.neuenberger.poker.common.model.TexasHoldem;
import de.neuenberger.pokercalc.model.util.ProbabilityArray;
import java.io.PrintStream;
import java.util.Vector;
import sun.misc.Sort;

public class ProbabilityThread
    implements Runnable
{

    public ProbabilityThread()
    {
        includeTurn = true;
        includeRiver = true;
        numberOfDeals = 2500L;
        players = 1;
        reset = true;
        finished = true;
    }

    public void run()
    {
        if(targetModel == null)
        {
            System.err.println("no model defined");
            return;
        }
        while(reset) 
        {
            reset = false;
            System.out.println("Starting calculation");
            for(int i = 14; i >= 2; i--)
            {
                for(int x = 14; x >= 2; x--)
                {
                    float v = getMonteCarloValue(i, x, x > i);
                    targetModel.setCardProbability(i, x, v);
                    if(reset)
                        break;
                }

                if(reset)
                    break;
            }

        }
        finished = true;
        targetModel.setFinished(true);
        targetModel.setAccuracy(numberOfDeals);
    }

    protected float getMonteCarloValue(int rank1, int rank2, boolean suited)
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
        long ges = numberOfDeals;
        long pWon = 0L;
        if(rank1 == rank2 && rank1 == 14)
            ges = numberOfDeals * 100L;
        hrf.setDebug(false);
        for(int i = 0; (long)i < ges; i++)
        {
            vec.clear();
            deck.getShuffledCards();
            th.dealRound(deck, includeTurn, includeRiver);
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
                pWon++;
        }

        return (float)pWon / (float)ges;
    }

    public ProbabilityArray getTargetModel()
    {
        return targetModel;
    }

    public void setTargetModel(ProbabilityArray targetModel)
    {
        this.targetModel = targetModel;
    }

    public void setIncludeRiver(boolean includeRiver)
    {
        this.includeRiver = includeRiver;
    }

    public void setIncludeTurn(boolean includeTurn)
    {
        this.includeTurn = includeTurn;
    }

    public void setNumberOfDeals(long numberOfDeals)
    {
        this.numberOfDeals = numberOfDeals;
    }

    public void setPlayers(int players)
    {
        this.players = players;
    }

    public void reset()
    {
        finished = false;
        reset = true;
    }

    public boolean isFinished()
    {
        return finished;
    }

    ProbabilityArray targetModel;
    boolean includeTurn;
    boolean includeRiver;
    long numberOfDeals;
    int players;
    boolean reset;
    boolean finished;
}
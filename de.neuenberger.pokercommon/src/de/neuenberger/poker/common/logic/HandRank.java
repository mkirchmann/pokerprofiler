// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   HandRank.java

package de.neuenberger.poker.common.logic;

import de.neuenberger.poker.common.model.Card;

public class HandRank
{

    public HandRank()
    {
        highestRank = new int[5];
    }

    public boolean isValid()
    {
        return valid;
    }

    public int getRank()
    {
        return rank;
    }

    protected static boolean hasRank(Card cards[], int rank)
    {
        return hasRank(cards, rank, -1);
    }

    protected static boolean hasRank(Card cards[], int rank, int color)
    {
        for(int i = 0; i < cards.length; i++)
            if(cards[i].getRank() == rank)
            {
                if(color == -1)
                    return true;
                if(cards[i].getColor() == color)
                    return true;
            }

        return false;
    }

    protected static int countRank(Card cards[], int rank)
    {
        int count = 0;
        for(int i = 0; i < cards.length; i++)
            if(cards[i].getRank() == rank)
                count++;

        return count;
    }

    protected static int countColor(Card cards[], int color)
    {
        int count = 0;
        for(int i = 0; i < cards.length; i++)
            if(cards[i].getColor() == color)
                count++;

        return count;
    }

    public int compare(HandRank hr)
    {
        if(rank < hr.getRank())
            return -1;
        if(rank == hr.getRank())
            return fineCompare(hr);
        else
            return 1;
    }

    public int fineCompare(HandRank hr)
    {
        for(int i = 0; i < 5; i++)
        {
            if(highestRank[i] < hr.highestRank[i])
                return -1;
            if(highestRank[i] > hr.highestRank[i])
                return 1;
        }

        return 0;
    }

    public int getAssignedID()
    {
        return assignedID;
    }

    public void setAssignedID(int assignedID)
    {
        this.assignedID = assignedID;
    }

    public static final int TOPCARD = 0;
    public static final int PAIR = 1;
    public static final int TWOPAIR = 2;
    public static final int TRIPLET = 3;
    public static final int STRAIGHT = 4;
    public static final int FLUSH = 6;
    public static final int FULLHOUSE = 7;
    public static final int POKER = 8;
    public static final int STRAIGHTFLUSH = 9;
    protected int rank;
    protected boolean valid;
    protected int assignedID;
    protected int highestRank[];
}
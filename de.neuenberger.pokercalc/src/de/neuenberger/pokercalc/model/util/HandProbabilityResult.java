// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   HandProbabilityResult.java

package de.neuenberger.pokercalc.model.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class HandProbabilityResult
{

    public HandProbabilityResult()
    {
        highCardProbability = 0.0F;
        pairProbability = 0.0F;
        twopairProbability = 0.0F;
        tripletProbability = 0.0F;
        fullhouseProbability = 0.0F;
        flushProbability = 0.0F;
        straightProbability = 0.0F;
        pokerProbability = 0.0F;
        straightflushProbability = 0.0F;
        normalizationFactor = 1.0F;
        listener = new Vector();
    }

    public void divideAll(float x)
    {
        normalizationFactor = x / 100F;
    }

    public float getFullhouseProbability()
    {
        return fullhouseProbability / normalizationFactor;
    }

    public void setFullhouseProbability(float fullhouseProbability)
    {
        this.fullhouseProbability = fullhouseProbability;
        fireChange();
    }

    public void increaseFullhouseProbability()
    {
        fullhouseProbability++;
        fireChange();
    }

    public float getHighCardProbability()
    {
        return highCardProbability / normalizationFactor;
    }

    public void setHighCardProbability(float highCardProbability)
    {
        this.highCardProbability = highCardProbability;
        fireChange();
    }

    public void increaseHighCardProbability()
    {
        highCardProbability++;
        fireChange();
    }

    public float getPairProbability()
    {
        return pairProbability / normalizationFactor;
    }

    public void setPairProbability(float pairProbability)
    {
        this.pairProbability = pairProbability;
        fireChange();
    }

    public void increasePairProbability()
    {
        pairProbability++;
        fireChange();
    }

    public float getPokerProbability()
    {
        return pokerProbability / normalizationFactor;
    }

    public void setPokerProbability(float pokerProbability)
    {
        this.pokerProbability = pokerProbability;
        fireChange();
    }

    public void increasePokerProbability()
    {
        pokerProbability++;
        fireChange();
    }

    public float getStraightflushProbability()
    {
        return straightflushProbability / normalizationFactor;
    }

    public void setStraightflushProbability(float straightflushProbability)
    {
        this.straightflushProbability = straightflushProbability;
        fireChange();
    }

    public void increaseStraightflushProbability()
    {
        straightflushProbability++;
        fireChange();
    }

    public float getFlushProbability()
    {
        return flushProbability / normalizationFactor;
    }

    public void setFlushProbability(float straightflushProbability)
    {
        flushProbability = straightflushProbability;
        fireChange();
    }

    public void increaseFlushProbability()
    {
        flushProbability++;
        fireChange();
    }

    public float getStraightProbability()
    {
        return straightProbability / normalizationFactor;
    }

    public void setStraightProbability(float straightProbability)
    {
        this.straightProbability = straightProbability;
        fireChange();
    }

    public void increaseStraightProbability()
    {
        straightProbability++;
        fireChange();
    }

    public float getTripletProbability()
    {
        return tripletProbability / normalizationFactor;
    }

    public void setTripletProbability(float tripletProbability)
    {
        this.tripletProbability = tripletProbability;
        fireChange();
    }

    public void increaseTripletProbability()
    {
        tripletProbability++;
        fireChange();
    }

    public float getTwopairProbability()
    {
        return twopairProbability / normalizationFactor;
    }

    public void setTwopairProbability(float twopairProbability)
    {
        this.twopairProbability = twopairProbability;
        fireChange();
    }

    public void increaseTwopairProbability()
    {
        twopairProbability++;
        fireChange();
    }

    public void increaseFromID(int id)
    {
        if(id == 0)
            highCardProbability++;
        if(id == 1)
            pairProbability++;
        if(id == 2)
            twopairProbability++;
        if(id == 3)
            tripletProbability++;
        if(id == 7)
            fullhouseProbability++;
        if(id == 4)
            straightProbability++;
        if(id == 8)
            pokerProbability++;
        if(id == 9)
            straightflushProbability++;
        if(id == 6)
            flushProbability++;
        fireChange();
    }

    public float getProbability()
    {
        return (highCardProbability + pairProbability + twopairProbability + tripletProbability + flushProbability + straightProbability + fullhouseProbability + pokerProbability + straightflushProbability) / normalizationFactor;
    }

    public void addActionListener(ActionListener al)
    {
        listener.add(al);
    }

    public void removeActionListener(ActionListener al)
    {
        listener.remove(al);
    }

    protected void fireChange()
    {
        ActionEvent e = new ActionEvent(this, 0, "Change");
        for(int i = 0; i < listener.size(); i++)
            ((ActionListener)listener.get(i)).actionPerformed(e);

    }

    float highCardProbability;
    float pairProbability;
    float twopairProbability;
    float tripletProbability;
    float fullhouseProbability;
    float flushProbability;
    float straightProbability;
    float pokerProbability;
    float straightflushProbability;
    float normalizationFactor;
    Vector listener;
}
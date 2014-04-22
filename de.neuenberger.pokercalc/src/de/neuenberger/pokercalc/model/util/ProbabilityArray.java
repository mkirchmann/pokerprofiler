// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ProbabilityArray.java

package de.neuenberger.pokercalc.model.util;

import de.neuenberger.poker.common.model.Card;

import java.util.Vector;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ProbabilityArray
    implements TableModel
{

    public ProbabilityArray()
    {
        listeners = new Vector();
        thresholdGreen = 0.5F;
        thresholdYellow = 0.3F;
        normalized = true;
        normalization = 0.5F;
        finished = false;
        probArray = new Float[13][13];
    }

    public void setCardProbability(int rank1, int rank2, float prob)
    {
        probArray[rank1 - 2][rank2 - 2] = Float.valueOf(prob);
        TableModelEvent tme = null;
        if(rank1 == 14 && rank2 == 14)
        {
            normalization = prob;
            new TableModelEvent(this);
        } else
        {
            tme = new TableModelEvent(this, rank1, rank2);
        }
        fireEvent(tme);
    }

    public Float getCardProbability(int rank1, int rank2)
    {
        return probArray[rank1 - 2][rank2 - 2];
    }

    protected void fireEvent(TableModelEvent tme)
    {
        for(int i = 0; i < listeners.size(); i++)
            ((TableModelListener)listeners.get(i)).tableChanged(tme);

    }

    public int getRowCount()
    {
        return probArray.length;
    }

    public int getColumnCount()
    {
        return probArray[0].length + 1;
    }

    public String getColumnName(int arg0)
    {
        if(arg0 == 0)
            return "x";
        else
            return Card.toString((arg0 + 2) - 1);
    }

    public Class getColumnClass(int arg0)
    {
        if(arg0 == 0)
            return java.lang.String.class;
        else
            return java.lang.Float.class;
    }

    public boolean isCellEditable(int arg0, int arg1)
    {
        return false;
    }

    public Object getValueAt(int arg0, int arg1)
    {
        if(arg1 == 0)
            return Card.toString(arg0 + 2);
        Float currentValue = probArray[arg0][arg1 - 1];
        if(currentValue == null)
            return new Float(0.0F);
        if(normalized)
            currentValue = new Float(probArray[arg0][arg1 - 1].floatValue() / normalization);
        return currentValue;
    }

    public void setValueAt(Object arg0, int arg1, int arg2)
    {
        probArray[arg1][arg2] = (Float)arg0;
    }

    public void addTableModelListener(TableModelListener arg0)
    {
        listeners.add(arg0);
    }

    public void removeTableModelListener(TableModelListener arg0)
    {
        listeners.remove(arg0);
    }

    public float getThresholdGreen()
    {
        return thresholdGreen;
    }

    public float getThresholdYellow()
    {
        return thresholdYellow;
    }

    public boolean isNormalized()
    {
        return normalized;
    }

    public void setNormalized(boolean normalized)
    {
        this.normalized = normalized;
        fireEvent(new TableModelEvent(this));
    }

    public void setThresholdGreen(float thresholdGreen)
    {
        this.thresholdGreen = thresholdGreen;
        fireEvent(new TableModelEvent(this));
    }

    public void setThresholdYellow(float thresholdYellow)
    {
        this.thresholdYellow = thresholdYellow;
        fireEvent(new TableModelEvent(this));
    }

    public boolean isFinished()
    {
        return finished;
    }

    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }

    public long getAccuracy()
    {
        return accuracy;
    }

    public void setAccuracy(long accuracy)
    {
        this.accuracy = accuracy;
    }

    Float probArray[][];
    Vector listeners;
    float thresholdGreen;
    float thresholdYellow;
    boolean normalized;
    float normalization;
    boolean finished;
    long accuracy;
}
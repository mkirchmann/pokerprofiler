// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ProbabilityModel.java

package de.neuenberger.pokercalc.model.util;

import de.neuenberger.pokercalc.parts.OptionMediator;
import java.util.HashMap;
import java.util.Set;

// Referenced classes of package de.neuenberger.pokercalc.model.util:
//            ProbabilityArray

public class ProbabilityModel
{

    public ProbabilityModel()
    {
        storedPA = new HashMap();
    }

    public ProbabilityArray getProbabilityArray(OptionMediator oc)
    {
        oc.hashCode();
        ProbabilityArray newPa = (ProbabilityArray)storedPA.get(Integer.valueOf(oc.hashCode()));
        if(newPa == null)
        {
            newPa = new ProbabilityArray();
            storedPA.put(Integer.valueOf(oc.hashCode()), newPa);
        }
        newPa.setNormalized(oc.isNormalized());
        return newPa;
    }

    public ProbabilityArray getProbabilityArray(Integer i)
    {
        ProbabilityArray newPa = (ProbabilityArray)storedPA.get(i);
        if(newPa == null)
        {
            newPa = new ProbabilityArray();
            storedPA.put(i, newPa);
        }
        return newPa;
    }

    public Integer[] getKeyElements()
    {
        Set set = storedPA.keySet();
        Integer intArr[] = new Integer[set.size()];
        set.toArray(intArr);
        return intArr;
    }

    HashMap storedPA;
}
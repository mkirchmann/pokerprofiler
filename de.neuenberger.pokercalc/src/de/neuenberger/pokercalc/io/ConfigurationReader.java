// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ConfigurationReader.java

package de.neuenberger.pokercalc.io;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import de.neuenberger.pokercalc.model.util.ProbabilityArray;
import de.neuenberger.pokercalc.model.util.ProbabilityModel;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

// Referenced classes of package de.neuenberger.pokercalc.io:
//            ExportSettings

public class ConfigurationReader
{

    public ConfigurationReader()
    {
    }

    public static void loadConfiguration(ProbabilityModel pModel)
    {
        try
        {
            DocumentBuilderFactoryImpl dbf = new DocumentBuilderFactoryImpl();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            File file = new File(ExportSettings.CONFIG_FILENAME);
            if(!file.exists())
                return;
            Document doc = docBuilder.parse(file);
            Element baseEl = doc.getDocumentElement();
            NodeList nl = baseEl.getElementsByTagName("ProbabilityArray");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element el = (Element)nl.item(i);
                Integer intObj = Integer.valueOf(el.getAttribute("hashcode"));
                ProbabilityArray pa = pModel.getProbabilityArray(intObj);
                pa.setFinished(true);
                NodeList valueList = el.getElementsByTagName("V");
                for(int x = 0; x < valueList.getLength(); x++)
                {
                    Element vEl = (Element)valueList.item(x);
                    int xV = Integer.parseInt(vEl.getAttribute("x"));
                    int yV = Integer.parseInt(vEl.getAttribute("y"));
                    float v = Float.parseFloat(vEl.getAttribute("v"));
                    pa.setCardProbability(xV, yV, v);
                }

            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
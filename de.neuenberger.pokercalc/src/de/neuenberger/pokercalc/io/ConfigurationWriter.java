// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ConfigurationWriter.java

package de.neuenberger.pokercalc.io;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import de.neuenberger.pokercalc.model.util.ProbabilityArray;
import de.neuenberger.pokercalc.model.util.ProbabilityModel;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

// Referenced classes of package de.neuenberger.pokercalc.io:
//            ExportSettings

public class ConfigurationWriter
{

    public ConfigurationWriter()
    {
    }

    public static void saveConfiguration(ProbabilityModel pModel)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactoryImpl.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            File file = new File(ExportSettings.CONFIG_FILENAME);
            Document doc = docBuilder.newDocument();
            Element baseEl = doc.createElement("BASE");
            doc.appendChild(baseEl);
            Integer intArr[] = pModel.getKeyElements();
            for(int i = 0; i < intArr.length; i++)
            {
                ProbabilityArray pa = pModel.getProbabilityArray(intArr[i]);
                if(pa.isFinished())
                {
                    Element el = doc.createElement("ProbabilityArray");
                    baseEl.appendChild(el);
                    el.setAttribute("hashcode", intArr[i].toString());
                    for(int x = 2; x <= 14; x++)
                    {
                        for(int y = 2; y <= 14; y++)
                        {
                            Element vEl = doc.createElement("V");
                            el.appendChild(vEl);
                            vEl.setAttribute("x", String.valueOf(x));
                            vEl.setAttribute("y", String.valueOf(y));
                            vEl.setAttribute("v", String.valueOf(pa.getCardProbability(x, y).floatValue()));
                        }

                    }

                }
            }

            javax.xml.transform.Source source = new DOMSource(doc);
            javax.xml.transform.Result result = new StreamResult(file);
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
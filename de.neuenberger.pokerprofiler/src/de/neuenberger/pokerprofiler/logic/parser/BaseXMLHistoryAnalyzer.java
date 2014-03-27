package de.neuenberger.pokerprofiler.logic.parser;

import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

abstract public class BaseXMLHistoryAnalyzer extends AbstractHistoryAnalyzer {

	public boolean acceptsFile(File file) {
		return true;
	}

	public void analyzeFile(File file) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			System.out.println("Analyzing: "+file.getName());
			Document document = builder.parse(file);
			pokerTableDescription=this.getPokerTableDescriptionFromString(file.getName());
			analyzeDocument(document.getDocumentElement());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (gameDescription!=null) {
			gameDescription.setAnalyzed(true);
		}
	}
	
	abstract protected void analyzeDocument(Element element);
	
	protected String getValueOfElement(Element el, String name) {
		Element elInner=getElementByName(el, name);
		String str=elInner.getTextContent();
		if (str==null) {
			return "";
		} else {
			return str;
		}
	}
	
	protected String getValueOfAttribute(Element el, String attr) {
		String str=el.getAttribute(attr);
		if (str==null) {
			return "";
		} else {
			return str;
		}
	}
	
	protected Element getElementByName(Element el, String name) {
		List<Element> list = this.getListOfElementsNamed(el, name);
		if (list.size()>0) {
			return (Element)list.get(0);
		}
		return null;
	}
	
	protected List<Element> getListOfElementsNamed(Element el, String name) {
		NodeList childNodes = el.getChildNodes();
		Vector<Element> vec=new Vector<Element>();
		vec.ensureCapacity(childNodes.getLength());
		for (int i=0; i<childNodes.getLength(); i++) {
			if (name.equals(childNodes.item(i).getNodeName())) {
				vec.add((Element)childNodes.item(i));
			}
		}
		return vec;
	}
}

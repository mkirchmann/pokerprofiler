package de.neuenberger.pokerprofiler.logic.parser;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Vector;

public class ParserPool {
	
	Vector<IHistoryAnalyzer> vecHistoryAnalyzers=new Vector<IHistoryAnalyzer>();
	
	
	
	
	IHistoryAnalyzer selectedHistoryAnalyzer;
	
	
	private static ParserPool instance=new ParserPool();
	
	private ParserPool() {
		
	}
	
	
	public IHistoryAnalyzer[] getRegisteredIds() {
		IHistoryAnalyzer str[]=new IHistoryAnalyzer[vecHistoryAnalyzers.size()];
		
		vecHistoryAnalyzers.toArray(str);
		
		return str;
	}


	public boolean addHistoryAnalyzer(IHistoryAnalyzer arg0) {
		if (selectedHistoryAnalyzer==null) {
			selectedHistoryAnalyzer=arg0;
		}
		return vecHistoryAnalyzers.add(arg0);
	}


	public IHistoryAnalyzer getSelectedHistoryAnalyzer() {
		return selectedHistoryAnalyzer;
	}


	public void setSelectedHistoryAnalyzer(IHistoryAnalyzer selectedHistoryAnalyzer) {
		this.selectedHistoryAnalyzer = selectedHistoryAnalyzer;
	}


	public static ParserPool getInstance() {
		return instance;
	}


	public void update() {
		try {
			FileInputStream fis=new FileInputStream(System.getProperty("user.home")+"/neuenberger_poker.properties");
			
			String var=selectedHistoryAnalyzer.getConfigVar();
			
			System.getProperties().load(fis);
			fis.close();
			
			String pathValue=System.getProperty(var);
			if (pathValue!=null) {
				parsePath(new File(pathValue));
				selectedHistoryAnalyzer.getPlayerDescriptionList().sort();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void parsePath(File file) {
		File fileArr[]=file.listFiles();
		if (fileArr==null) {
			return;
		}
		for (int i=0; i<fileArr.length; i++) {
			if (fileArr[i].isDirectory()) {
				parsePath(fileArr[i]);
			} else {
				analyzeFile(fileArr[i]);
			}
		}
	}


	protected void analyzeFile(File file) {
		if (selectedHistoryAnalyzer.acceptsFile(file)) {
			if (FileMark.needsUpdate(file)) {
				selectedHistoryAnalyzer.analyzeFile(file);
			}
		}
		
	}
}

class FileMark {

	private static HashMap<String,FileMark> hashMap=new HashMap<String,FileMark>();
	long oldLength=0;
	FileMark(File file) {
		oldLength=file.length();
	}
	
	
	public static boolean needsUpdate(File file) {
		FileMark fm=hashMap.get(file.toString());
		if (fm==null) {
			fm=new FileMark(file);
			hashMap.put(file.toString(),fm);
			return true;
		}
		long cl=file.length();
		if (cl!=fm.oldLength) {
			fm.oldLength=cl;
			return true;
		}
		return false;
	}
}

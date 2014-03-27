package de.neuenberger.pokerprofiler.logic.analyzer;

import java.text.DecimalFormat;

public class BaseLogic {
	
	static DecimalFormat decimalFormat=new DecimalFormat("0.");
	static DecimalFormat decimalFormat2=new DecimalFormat("0.00");
	public static float getPercentage(int nominator, int denominator) {
		if (denominator==0) {
			return -1.0f;
		} else {
			return 100.0f*nominator/denominator;
		}
	}
	
	/*public static String getFloatAsPercentageString(float v) {
		return decimalFormat.format(v)+"%";
	}*/
	
	public static String getFloatAsCurrencyString(float v) {
		return decimalFormat2.format(v);
	}
}

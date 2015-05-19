package org.digijava.module.aim.helper ;

import org.apache.log4j.Logger ;
import java.text.DecimalFormat ;

public class DecimalToText
{
	private static Logger logger = Logger.getLogger(DecimalToText.class) ;
	public static DecimalFormat mf = new DecimalFormat("###,###,###,###,###") ;
	
	public static String ConvertDecimalToText(double decimal )
	{
		if ( logger.isDebugEnabled() )
			logger.debug("Decimal passed : " + decimal ) ;
		String text = mf.format(decimal);
		//text = CurrencyWorker.insertDecmalPlaces(text);
		
		if ( logger.isDebugEnabled() )
			logger.debug("Text returned : " + text ) ;
		return text ; 
	}	
	
	public static String removeCommas(String s)
	{
		if (s != null) {	  
			return s.replaceAll(",","");
		} else {
			return "0";
		}
		
		/*
		if ( logger.isDebugEnabled() )
		logger.debug("removeCommas() passed : " + s ) ;
		String text = "" ;
		if ( s != null )	{
			char arr[] = s.toCharArray() ;
			text = "" ;
			for (int i = 0 ; i < s.length() ; i++ )
			{
				if ( arr[i] != ',' )
					text += arr[i] ;
			}
		}
		else
			text += "0.0";
			
		if ( logger.isDebugEnabled() )
			logger.debug("removeCommas() returned: " + text ) ;
		return text ;
		*/
	}

	/** @author jose
	 * Given a string like 3,92,000 the method will remove the commas and
	 * parse the double value out of the string and return a double.
	 * @param A string like 3,92,000  
	 * @return double  392000.00
	 */	
	public static double getDouble(String s)
	{
		String tmp = removeCommas(s) ;
		double value = 0;
		try {
			value = Double.parseDouble(tmp);
		} catch (NumberFormatException e) {
			logger.error("Exception from getDouble(): Tried to do parseDouble() on " + tmp);
		}
		return value;
	}		 

	/** @author jose
	 * Given 2 amounts in String format like 3,92,000 and 2,02,000
	 * return the difference.
	 * Assumption s1 > s2 
	 * @param String s1 say 3,92,000 , String s2 say 2,02,000
	 * @return String 190,000 
	 */	
	public static String getDifference(String s1, String s2)
	{
		double d1 = DecimalToText.getDouble(s1) ;
		double d2 = DecimalToText.getDouble(s2) ;
		double d3 = d1 - d2 ;
		return mf.format(d3) ;
	}		
	
	public static String getString(double d)	{
		return mf.format(d);
	}
}
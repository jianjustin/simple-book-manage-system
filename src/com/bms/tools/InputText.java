package com.bms.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputText {
	
	/**
	 *  正则表达式匹配函数
	 */
	private static boolean isMatch(String regex, String orginal){ 
		if (orginal == null || orginal.trim().equals("")) { 
			return false; 
		} 
		Pattern pattern = Pattern.compile(regex); 
		Matcher isNum = pattern.matcher(orginal); 
		return isNum.matches(); 
	}
	
	/**
	* 判断是否为正整数
	*/
	public static boolean isPositiveInteger(String orginal) { 
		return isMatch("^\\+{0,1}[1-9]\\d*", orginal); 
	}
	
	/**
	* 判断是否为正小数
	*/
	public static boolean isDecimal(String orginal){ 
		return isMatch("[+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal); 
	}
	
	/**
	* 判断是否为正实数
	*/
	public static boolean isRealNumber(String orginal){ 
		return isPositiveInteger(orginal) || isDecimal(orginal); 
	}

}

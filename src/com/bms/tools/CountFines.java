package com.bms.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class CountFines {
	
	private static Pattern p = Pattern.compile("(\\d{4})-(\\d{1,2})-(\\d{1,2})");//日期字符串模式匹配
	/**
	* 借书界面的自定义初始化函数
	*/
	public static Date stringToDate(String time) throws ParseException {
		Matcher m = p.matcher(time);
        if (!m.matches()) {
        	JOptionPane.showMessageDialog(null, "日期格式不对，或者和实际相差太远！\n");
        	return null;
        }
		return (new SimpleDateFormat("yyyy-MM-dd")).parse(time);
	}
	/**
	* 计算两个日期差距天数的函数
	*/
	public static long longOfTwoDate(Date first,Date second) throws ParseException{
		long a = ((second.getTime()-first.getTime())/1000/3600/24);
		if(first == null || second == null) {
			return 0;
		}
		if(a < 0) {
			JOptionPane.showMessageDialog(null, "还书时间早于借书时间！\n请重新输入！\n");
			return 0;
		}
		return a;
	}
	/**
	 *  还书天数变量，超过此数字开始计算罚款
	 */
	public static int days = 30;
	/**
	* 计算罚款函数
	*
	* @param first 开始日期的字符串
	* @param second 借书日期的字符串
	* @return double 读者的罚款值
	*/
	public static double countFines(String first, String second) {
		long a = 0;
		try {
			a = longOfTwoDate(stringToDate(first), stringToDate(second));
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "转换出错");
		}
		if(a < (long)days) {
			return 0.0;
		}
		return 0.5*(int)(a-30l);
	}

}

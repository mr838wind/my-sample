package com.wind.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import lombok.extern.slf4j.Slf4j;

/**
 * Tag Library.
 * @author Joldo
 * <%@taglib uri="/WEB-INF/tld-function" prefix="func"%>
 * ${func:formatDate(result.registerTime)}
 */
@Slf4j
public class TagFunction  {

	private static final NumberFormat numberFormat = new DecimalFormat("###,###");
	private static final NumberFormat doubleNumberFormat = new DecimalFormat("###,##0.0000");
	private static final NumberFormat percentNumberFormat = new DecimalFormat("###,##0.000"); //소수점아래 3자리

	public static String formatDate(String yyyymmdd){
		if( yyyymmdd == null || yyyymmdd.length() < 6 ){
			return "";
		}else{
			return yyyymmdd.substring(0, 4) + "." +yyyymmdd.substring(4, 6) + "." + yyyymmdd.substring(6, 8);
		}
	}

	public static String formatDateTime(String yyyyMMddhhmmss){
		if( yyyyMMddhhmmss == null || yyyyMMddhhmmss.length() < 14 ){
			return "";
		}else{
			return formatDate(yyyyMMddhhmmss) 
			+ " " + yyyyMMddhhmmss.substring( 8, 10) + ":" +yyyyMMddhhmmss.substring(10, 12) + ":" +yyyyMMddhhmmss.substring(12, 14);
		}
	}
	
	public static String formatNumber(String data){
		if( data == null ){
			return "";
		}else{
			return numberFormat.format(Double.valueOf(data));
		}
	}
	
	public static String formatDouble(String data){
		if( data == null ){
			return "";
		}else{
			return doubleNumberFormat.format(Double.valueOf(data));
		}
	}
	
	public static String formatPercent(String data){
		if( data == null ){
			return "";
		}else{
			return percentNumberFormat.format(Double.valueOf(data) * 100);
		}
	}
	
	
	public static void main(String[] args) {
		log.debug(formatNumber("32340.234234"));
		log.debug(formatDouble("32340.234234"));
		log.debug(formatPercent("32340.234234"));
	}
	
}

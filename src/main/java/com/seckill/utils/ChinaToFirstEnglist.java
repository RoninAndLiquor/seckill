package com.seckill.utils;

public class ChinaToFirstEnglist {
	public static String ChinaToEnglsh(String str){
		String pinyin = PinyingUtil.hanziToPinyin(str,",");
		String finalStr = "";
		String[] a = pinyin.split(",");
		for(int i=0;i<a.length;i++){
			finalStr += a[i].substring(0,1);
		}
		return finalStr;
	}
}

package com.seckill.test;

import java.math.BigDecimal;

import com.seckill.ChildClass;
import com.seckill.MatherClass;
import com.seckill.enums.SeckillState;
import com.seckill.utils.ChinaToFirstEnglist;
import com.seckill.utils.PinyingUtil;

public class Test {

	public static void main(String[] args){
		/*SeckillState stateOf = SeckillState.stateOf(1);
		System.out.println(stateOf.getInfo());*/
		BigDecimal bd = new BigDecimal("1.2999888888888888888888888888848");
		BigDecimal b2 = new BigDecimal("2.555");
		BigDecimal plus = bd.add(b2);
		double d = 2.123456789123456789123456789d;
		System.out.println(plus);
		//静态代码块只加载一次！
		//MatherClass mc = new MatherClass();
		MatherClass mc2 = new MatherClass();
		ChildClass cc = new ChildClass();
		//ChildClass cc2 = new ChildClass();
		String chinaToEnglsh = ChinaToFirstEnglist.ChinaToEnglsh("蒋帅锋");
		System.out.println(chinaToEnglsh);
		String hanziToPinyin = PinyingUtil.hanziToPinyin("蒋帅锋");
		System.out.println(hanziToPinyin);
		
	}
	
}

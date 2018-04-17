package com.seckill;

public class ChildClass extends MatherClass {

	public ChildClass(){
		System.out.println("ChildClass create!");
	}
	static{
		System.out.println("ChildClass static create!");
	}
	{
		System.out.println("ChildClass niming create!");
	}
}

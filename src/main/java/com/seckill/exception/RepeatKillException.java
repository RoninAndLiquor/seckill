package com.seckill.exception;

/**
 * 
 * @包名 :com.seckill.exception
 * @文件名 :RepeatKillException.java 
 * TODO 类作用：重复秒杀异常 （运行期异常）
 * @系统名称 : 测试
 * @Author: 蒋帅锋 
 * @Date: 2018年1月8日 下午2:17:19
 * @版本号   :v0.0.01
 */
public class RepeatKillException extends SeckillException{

	public RepeatKillException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	
	
}

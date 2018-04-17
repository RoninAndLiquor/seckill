package com.seckill.service;

import java.util.List;

import com.seckill.dto.Export;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

/**
 * 
 * @包名 :com.seckill.service
 * @文件名 :SeckillService.java 
 * TODO 类作用：业务接口  ：站在“使用者”的角度设计接口
 * 				三个方面：方法定义粒度，参数的简洁性 ，返回类型的正规性，
 * @系统名称 : 测试
 * @Author: 蒋帅锋 
 * @Date: 2018年1月8日 上午11:00:34
 * @版本号   :v0.0.01
 */
public interface SeckillService {

	List<Seckill> getSeckillList();
	
	Seckill getSeckillById(Long seckillId);
	
	/**
	 * 
	 * TODO 方法作用：获取秒杀地址 ，  如果尚未开始返回当前时间
	 * @param seckillId
	 * @return
	 * @Author: 蒋帅锋
	 * @Date: 2018年1月8日 下午2:11:56
	 */
	Export exportSeckillUrl(Long seckillId);
	
	/**
	 * 
	 * TODO 方法作用：秒杀的结果
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws SeckillException
	 * @throws SeckillCloseException
	 * @throws RepeatKillException
	 * @Author: 蒋帅锋
	 * @Date: 2018年1月8日 下午2:23:01
	 */
	SeckillExecution executeSeckill(Long seckillId,Long userPhone,String md5)
		throws SeckillException,SeckillCloseException,RepeatKillException;
	
	SeckillExecution executeSeckillProcedure(Long seckillId,Long userPhoen,String md5);
}

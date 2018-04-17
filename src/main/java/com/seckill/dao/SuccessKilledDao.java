package com.seckill.dao;

import org.apache.ibatis.annotations.Param;

import com.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {

	/**
	 * 
	 * TODO 方法作用：插入购买明细 可过滤重复秒杀
	 * @param seckillId
	 * @param userPhone
	 * @return
	 * @Author: 蒋帅锋
	 * @Date: 2018年1月5日 下午4:14:00
	 */
	int insertSuccessKilled(@Param("seckillId") Long seckillId,@Param("userPhone") Long userPhone);
	
	/**
	 * 
	 * TODO 方法作用：根据Id查询successKilled 并携带秒杀对象实体
	 * @param seckillId
	 * @return
	 * @Author: 蒋帅锋
	 * @Date: 2018年1月5日 下午4:16:29
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") Long seckillId,@Param("userPhone") Long userPhone);
	
}

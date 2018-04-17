package com.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.seckill.entity.Seckill;

public interface SeckillDao {
	
	/**
	 * 
	 * TODO 方法作用：减库存
	 * @param seckillId
	 * @param killTime
	 * @return
	 * @Author: 蒋帅锋
	 * @Date: 2018年1月5日 下午4:12:31
	 */
	int reduceNumber(@Param("seckillId")Long seckillId,@Param("killTime")Date killTime);
	/**
	 * 
	 * TODO 方法作用：根据ID查询秒杀商品
	 * @param seckillId
	 * @return
	 * @Author: 蒋帅锋
	 * @Date: 2018年1月5日 下午4:12:19
	 */
	Seckill queryById(Long seckillId);
	
	/**
	 * 
	 * TODO 方法作用：根据偏移量查询秒杀商品列表
	 * @param offet
	 * @param limit
	 * @return
	 * @Author: 蒋帅锋
	 * @Date: 2018年1月5日 下午4:11:52
	 */
	List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
	/**
	 * 
	 * TODO 方法作用：使用存储过程实现秒杀
	 * @param paramMap
	 * @Author: 蒋帅锋
	 * @Date: 2018年1月11日 上午11:06:27
	 */
	void killByProcedure(Map<String,Object> paramMap);
	
}

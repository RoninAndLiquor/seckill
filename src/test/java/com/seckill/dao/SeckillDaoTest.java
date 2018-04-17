package com.seckill.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.entity.Seckill;

/**
 * 
 * @包名 :com.seckill.dao
 * @文件名 :SeckillDaoTest.java 
 * TODO 类作用：
 * @系统名称 : 测试
 * @Author: 蒋帅锋 
 * @Date: 2018年1月5日 下午6:04:26
 * @版本号   :v0.0.01
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	
	@Autowired
	SeckillDao seckillDao;
	
	@Test
	public void testReduceNumber(){
		int reduceNumber = seckillDao.reduceNumber(1L, new Date());
		System.out.println(reduceNumber);
	}
	
	@Test
	public void testQueryById(){
		Seckill queryById = seckillDao.queryById(1L);
		System.out.println(queryById.toString());
	}
	//java 没有保存形参的记录 queryAll(int offset,int limit); --> queryAll(int arg0,int arg1);
	//解决方法 使用@Param(形参名称)
	@Test
	public void testQueryAll(){
		List<Seckill> queryAll = seckillDao.queryAll(0, 100);
		for(Seckill s:queryAll){
			System.out.println(s.toString());
		}
	}
	
}

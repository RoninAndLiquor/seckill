package com.seckill.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seckill.dao.cache.RedisDao;
import com.seckill.entity.Seckill;
import com.seckill.test.BaseTest;

public class CacheDaoTest extends BaseTest{

	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void getSeckillTest(){
		Long seckillId = 2L;
		Seckill seckill = redisDao.getSeckill(seckillId);
		if(seckill == null){
			Seckill queryById = seckillDao.queryById(seckillId);
			String putSeckill = redisDao.putSeckill(queryById);
			System.out.println(putSeckill);
			Seckill seckill2 = redisDao.getSeckill(seckillId);
			System.out.println(seckill2.toString());
		}
	}
	
	@Test
	public void putSeckillTest(){
		Seckill seckill = new Seckill();
		redisDao.putSeckill(seckill);
	}
	
}

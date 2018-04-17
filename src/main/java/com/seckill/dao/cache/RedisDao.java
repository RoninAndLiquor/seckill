package com.seckill.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.seckill.entity.Seckill;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {

	private final static Logger LOG = LoggerFactory.getLogger(RedisDao.class);
	
	private JedisPool jedisPool;
	
	private RuntimeSchema<Seckill> schema  = RuntimeSchema.createFrom(Seckill.class);
	
	public RedisDao(String ip,int port){
		jedisPool = new JedisPool(ip,port);
	}
	
	public Seckill getSeckill(Long seckillId){
		//redis操作逻辑
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckillId;
				//并没有实现内部序列化操作
				//get-->byte[] --> 反序列化 -->Object[Seckill]
				//采用自定义的序列化
				//Class protostuff:pojo  有set get方法
				byte[] bs = jedis.get(key.getBytes());
				String string = jedis.get(key);
				System.out.println("******数据*****"+string);
				if(bs != null){
					//空对象
					Seckill seckill = schema.newMessage();
					//seckillf反序列化
					ProtostuffIOUtil.mergeFrom(bs, seckill, schema);
					return seckill;
				}
			} finally{
				jedis.close();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return null;
	}
	
	public String putSeckill(Seckill seckill){
		//set Object(Seckill) -- 序列化 -- byte[]
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				//超时缓存
				int timeout = 60*60;
				//如果正确返回ok  错误返回错误信息
				String setex = jedis.setex(key.getBytes(), timeout, bytes);
				return setex;
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error(e.getMessage(),e);
		}
		return null;
	}
	
	public void init(){
		LOG.info("**** RedisDao开始初始化 ****");
	}
	public void destory(){
		LOG.info("**** RedisDao开始销毁 ****");
	}
}

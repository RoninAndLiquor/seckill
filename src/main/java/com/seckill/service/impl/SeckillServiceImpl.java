package com.seckill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.dao.cache.RedisDao;
import com.seckill.dto.Export;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillState;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService {
	
	private final static Logger LOG = LoggerFactory.getLogger(SeckillServiceImpl.class);
	
	public SeckillServiceImpl() {
		super();
		LOG.info("***** SeckillServiceImpl 初始化 ******");
	}
	
	private final String slat = "skdfj8~))232s~jdkfskv8àcna'**#&()!SDKFSSLKkdskd@@";
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	@Autowired
	private RedisDao redisDao;
	
	public List<Seckill> getSeckillList() {
		// TODO Auto-generated method stub
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getSeckillById(Long seckillId) {
		// TODO Auto-generated method stub
		return seckillDao.queryById(seckillId);
	} 

	public Export exportSeckillUrl(Long seckillId) {
		//优化点  缓存优化 超时的基础上维护一致性 DispatcherServlet Servlet
		 Seckill seckill = redisDao.getSeckill(seckillId);
		if(seckill == null){
			seckill = seckillDao.queryById(seckillId);
			if(seckill ==null){
				return new Export(false,seckillId);
			}else{
				redisDao.putSeckill(seckill);
			}
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date now = new Date();
		if(now.getTime()<startTime.getTime() || now.getTime()>endTime.getTime()){
			return new Export(false,seckillId,now.getTime(),startTime.getTime(),endTime.getTime());
		}
		String md5 = getMd5(seckillId);
		return new Export(true,md5,seckillId);
	}

	private String getMd5(Long seckillId){
		String base = seckillId+"/"+slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		LOG.info("*** md5 ***"+md5);
		return md5;
	}
	/**
	 * 
	 * TODO 方法作用：执行秒杀
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws SeckillException
	 * @throws SeckillCloseException
	 * @throws RepeatKillException
	 * @Author: 蒋帅锋
	 * @Date: 2018年1月8日 下午4:18:32
	 * 
	 * 使用注解控制事务方法的优点：
	 * 1.开发团队达成一致的约定，明确标注事务方法的编程风格
	 * 2.保证事务方法执行的时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
	 * 3.不是所有的方法都需要事务，如只有一条修改操作、只读操作不需要事务控制
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public SeckillExecution executeSeckill(Long seckillId, Long userPhone,
			String md5) throws SeckillException, SeckillCloseException,
			RepeatKillException {
		String defaultMd5 = getMd5(seckillId);
		if(md5 ==null || !md5.equals(defaultMd5)){
			throw new SeckillException("seckill data rewrite!");
		}
		Date nowDate = new Date();
		try {
			int intCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			if(intCount<=0){
				throw new RepeatKillException("seckill repeated");
			}
			else{
				//减库存 ， 热点商品竞争  会拿到MySql的行级锁
				//将插入和update 调换顺序 降低rowLock的时间
				int updateCount = seckillDao.reduceNumber(seckillId, nowDate);
				if(updateCount<=0){
					throw new SeckillCloseException("seckill is closed");
				}
				else{
					//秒杀成功
					SuccessKilled queryByIdWithSeckill = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId,SeckillState.SUCCESS,queryByIdWithSeckill);
				}	
			}
		}
		catch (SeckillCloseException e) {
			throw e;
		}
		catch (RepeatKillException e) {
			throw e;
		}
		catch (Exception e) {
			throw new SeckillException("seckill inner error:"+e.getMessage());
		}
	}
	/**
	 * 
	 * TODO 方法作用：根据存储过程完成秒杀
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @Author: 蒋帅锋
	 * @Date: 2018年1月11日 上午11:20:20
	 */
	public SeckillExecution executeSeckillProcedure(Long seckillId,
			Long userPhone, String md5) {
		if(md5 ==null || !md5.equals(getMd5(seckillId))){
			return new SeckillExecution(seckillId,SeckillState.DATA_REWRITE);
		}
		Date killTime = new Date();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("seckillId", seckillId);
		map.put("phone", userPhone);
		map.put("killTime", killTime);
		map.put("result", null);
		//执行完存储过程之后 result被赋值
		try {
			seckillDao.killByProcedure(map);
			Integer result = MapUtils.getInteger(map, "result",-2);
			if(result == 1){
				SuccessKilled se = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId,SeckillState.SUCCESS,se);
			}else{
				return new SeckillExecution(seckillId,SeckillState.stateOf(result));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			return new SeckillExecution(seckillId,SeckillState.INNER_ERROR);
		}
	}

}

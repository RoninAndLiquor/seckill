package com.seckill.service;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seckill.dto.Export;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.test.BaseTest;

public class SeckillServiceTest extends BaseTest{
	
	private final static Logger LOG = LoggerFactory.getLogger(SeckillServiceTest.class);
	
	private ObjectMapper om = new ObjectMapper();

	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void getSeckillList(){
		List<Seckill> seckillList = seckillService.getSeckillList();
		LOG.info("list={}", seckillList);
	}
	
	@Test
	public void exprotSeckillUrl() throws JsonProcessingException{
		Long seckillId = 1L;
		Export exportSeckillUrl = seckillService.exportSeckillUrl(seckillId);
		LOG.info("exprot={}",om.writeValueAsString(exportSeckillUrl));
	}
	
	@Test
	public void executeSeckill() throws JsonProcessingException{
		Long id = 2L;
		Long userPhone = 15553685560L;
		String md5 = "12fa6f9d73d29e16a8fe526cf9ce76e4";
		try {
			SeckillExecution executeSeckill = seckillService.executeSeckill(id, userPhone, md5);
			LOG.info("executeSeckill={}",om.writeValueAsString(executeSeckill));
		}catch (RepeatKillException e) {
			LOG.error(e.getMessage());
		}catch (SeckillCloseException e) {
			LOG.error(e.getMessage());
		}catch (SeckillException e) {
			LOG.error(e.getMessage());
		}
		
	}
	
	@Test
	public void executeSeckillLogic() throws JsonProcessingException{
		Long seckillId = 8L;
		Export export = seckillService.exportSeckillUrl(seckillId);
		if(export.isExposed()){
			Long userPhone = 15553685560L;
			String md5 = export.getMd5();
			try {
				SeckillExecution executeSeckill = seckillService.executeSeckill(seckillId, userPhone, md5);
				LOG.info("executeSeckill={}",om.writeValueAsString(executeSeckill));
			}catch (RepeatKillException e) {
				LOG.error(e.getMessage());
			}catch (SeckillCloseException e) {
				LOG.error(e.getMessage());
			}catch (SeckillException e) {
				LOG.error(e.getMessage());
			}
		}else{
			LOG.warn("export={}",export);
		}
		LOG.info("exprot={}",om.writeValueAsString(export));
	}
	
	@Test
	public void seckillByProcedure() throws JsonProcessingException{
		Long seckillId = 1L;
		Long userPhone = 15536532252L;
		Export exportSeckillUrl = seckillService.exportSeckillUrl(seckillId);
		String md5 = exportSeckillUrl.getMd5();
		SeckillExecution result = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
		System.out.println(om.writeValueAsString(result));
	}
	
}

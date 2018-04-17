package com.seckill.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seckill.entity.SuccessKilled;
import com.seckill.test.BaseTest;

public class SuccessKilledTest extends BaseTest{

	@Autowired
	private SuccessKilledDao succ;
	
	@Test
	public void getById() throws JsonProcessingException{
		SuccessKilled queryByIdWithSeckill = succ.queryByIdWithSeckill(3L,15016218852L);
		ObjectMapper om = new ObjectMapper();
		System.out.println(om.writeValueAsString(queryByIdWithSeckill));
	}
	
	@Test
	public void insert(){
		int insertSuccessKilled = succ.insertSuccessKilled(3L, 15016218852L);
		System.out.println(insertSuccessKilled);
		
	}
}

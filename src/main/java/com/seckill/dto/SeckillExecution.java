package com.seckill.dto;

import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillState;

/**
 * 
 * @包名 :com.seckill.dto
 * @文件名 :SeckillExecution.java 
 * TODO 类作用：封装执行秒杀后的结果
 * @系统名称 : 测试
 * @Author: 蒋帅锋 
 * @Date: 2018年1月8日 下午2:13:33
 * @版本号   :v0.0.01
 */
public class SeckillExecution {

	private Long seckillId;
	
	private Integer state;
	
	private String stateInfo;
	
	private SuccessKilled successKilled;

	public Long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}

	public SeckillExecution(Long seckillId,SeckillState seckillState,
			SuccessKilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = seckillState.getCode();
		this.stateInfo = seckillState.getInfo();
		this.successKilled = successKilled;
	}

	public SeckillExecution(Long seckillId,SeckillState seckillState) {
		super();
		this.seckillId = seckillId;
		this.state = seckillState.getCode();
		this.stateInfo = seckillState.getInfo();
	}
	
	
	
}

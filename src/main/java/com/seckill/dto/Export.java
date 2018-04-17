package com.seckill.dto;

/**
 * 
 * @包名 :com.seckill.dto
 * @文件名 :Export.java 
 * TODO 类作用：暴露秒杀地址
 * @系统名称 : 测试
 * @Author: 蒋帅锋 
 * @Date: 2018年1月8日 下午2:08:03
 * @版本号   :v0.0.01
 */
public class Export {
	
	//是否开始秒杀
	private boolean exposed;
	
	//一种加密规则
	private String md5;
	
	//秒杀ID
	private Long seckillId;
	
	//当前时间
	private Long now;
	
	//开始时间
	private Long start;
	
	//结束时间
	private Long end;
	
	public boolean isExposed() {
		return exposed;
	}

	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}

	public Long getNow() {
		return now;
	}

	public void setNow(Long now) {
		this.now = now;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public Export(boolean exposed, String md5, Long seckillId) {
		super();
		this.exposed = exposed;
		this.md5 = md5;
		this.seckillId = seckillId;
	}

	

	public Export(boolean exposed, Long seckillId, Long now, Long start,
			Long end) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.now = now;
		this.start = start;
		this.end = end;
	}

	public Export(boolean exposed, Long seckillId) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
	}
	
	
	
}

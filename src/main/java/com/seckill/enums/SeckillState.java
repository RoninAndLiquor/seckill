package com.seckill.enums;

public enum SeckillState {
	SUCCESS(1,"成功秒杀"),
	
	END(0,"秒杀结束"),
	
	REPEAT_KILL(-1,"重复秒杀"),
	
	INNER_ERROR(-2,"系统异常"),
	
	DATA_REWRITE(-3,"URL篡改");

    
    private int code;
    
    private String info;
    
    SeckillState(int code,String info) {
    	this.code = code;
    	this.info = info;
    }

	public int getCode() {
		return code;
	}

	public String getInfo() {
		return info;
	}
    
    
    public static SeckillState stateOf(int index){
    	for(SeckillState s:values()){
    		if(s.getCode() == index){
    			return s;
    		}
    	}
    	return null;
    }
    
    
}

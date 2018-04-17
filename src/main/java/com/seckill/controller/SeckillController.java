package com.seckill.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seckill.dto.Export;
import com.seckill.dto.SeckillExecution;
import com.seckill.dto.SeckillResult;
import com.seckill.entity.Seckill;
import com.seckill.enums.SeckillState;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.service.SeckillService;

/**
 * 
 * @包名 :com.seckill.controller
 * @文件名 :SeckillController.java 
 * TODO 类作用：秒杀Controller
 * @系统名称 : 测试
 * @Author: 蒋帅锋 
 * @Date: 2018年1月9日 下午2:00:40
 * @版本号   :v0.0.01
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
	
	private final static Logger LOG = LoggerFactory.getLogger(SeckillController.class);

	@Autowired
	private SeckillService seckillService;
	
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String list(Model model){
		return "list";
	}
	
	@RequestMapping(value="/listData.json",method = RequestMethod.POST)
	@ResponseBody
	public List<Seckill> listData(){
		List<Seckill> seckillList = seckillService.getSeckillList();
		return seckillList;
	}
	
	@RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId,Model model){
		if(seckillId==null){
			return "redirect:/seckill/list";
		}
		Seckill seckillById = seckillService.getSeckillById(seckillId);
		if(seckillById ==null){
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckillById);
		return "detail";
	}
	@RequestMapping(value ="/{seckillId}/exposer" , method = RequestMethod.POST,
			produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<Export>  exposer(@PathVariable("seckillId") Long seckillId){
		SeckillResult<Export> result;
		try {
			Export exportSeckillUrl = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Export>(true,exportSeckillUrl);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new SeckillResult<Export>(false,e.getMessage());
		}
		return result;
	}
	@RequestMapping(value = "/{seckillId}/{md5}/execute",method=RequestMethod.POST,
			produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
			@PathVariable("md5") String md5,
			@CookieValue(value = "killPhone",required=false)Long killPhone){
		if(killPhone == null){
			return new SeckillResult<SeckillExecution>(false,"未注册");
		}
		SeckillResult<SeckillExecution> result;
		try {
			SeckillExecution executeSeckill = seckillService.executeSeckillProcedure(seckillId, killPhone, md5);
			result = new SeckillResult<SeckillExecution>(true,executeSeckill);
		} catch(RepeatKillException e){
			SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillState.REPEAT_KILL);
			result = new SeckillResult<SeckillExecution>(true,seckillExecution);
		} catch(SeckillCloseException e){
			SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillState.END);
			result = new SeckillResult<SeckillExecution>(true,seckillExecution);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillState.INNER_ERROR);
			result = new SeckillResult<SeckillExecution>(true,seckillExecution);
		}
		return result;
	}
	
	@RequestMapping(value = "/time/now",method = RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time(HttpServletRequest request){
		return new SeckillResult<Long>(true,System.currentTimeMillis());
	}
}

package com.seckill.encoding;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.seckill.entity.Seckill;

public class MyTest {

	public static void main(String[] args) throws UnsupportedEncodingException{
		
		String c = "%E6%88%91%E7%9A%84";
		System.out.println(URLDecoder.decode(c));
	}
	public void str(HttpServletRequest req ,HttpServletResponse res,Seckill seckill) throws UnsupportedEncodingException{
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=utf-8");
		res.setHeader("contentType", "text/html;charset=utf-8");
		HttpSession session = req.getSession();
		session.setAttribute(String.valueOf(seckill.getSeckillId()), seckill);
		Object attribute = session.getAttribute(String.valueOf(seckill.getSeckillId()));
		if(attribute!=null){
			Seckill sec = (Seckill) attribute;
		}
		session.setMaxInactiveInterval(3600);
		req.getHeader("SESSIONID");
	}
}

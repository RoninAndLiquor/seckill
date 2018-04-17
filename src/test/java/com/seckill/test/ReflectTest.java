package com.seckill.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.collections.ComparatorUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seckill.dto.Export;

public class ReflectTest {

	public static void main(String[] args) throws ClassNotFoundException, 
		NoSuchMethodException, SecurityException, 
		InstantiationException, IllegalAccessException, 
		IllegalArgumentException, InvocationTargetException, JsonProcessingException{
		ObjectMapper om = new ObjectMapper();
		Class<?> clazz = Class.forName("com.seckill.dto.Export");
		Constructor<?> constructor = clazz.getConstructor(boolean.class,Long.class);
		Export export = (Export) constructor.newInstance(true,123l);
		Method method = clazz.getMethod("setMd5", String.class);
		method.invoke(export, "123465789ssdfsdf");
		System.out.println(om.writeValueAsString(export));
		Field[] fields = clazz.getDeclaredFields();
		for(Field f : fields){
			System.out.println(f.getName());
		}
		System.out.println("——————————————————————————————————");
		Method[] methods = clazz.getMethods();
		for(Method m : methods){
			for(Field f : fields){
				if(m.getName().toLowerCase().contains(f.getName().toLowerCase())){
					System.out.println(m.getName());
				}
			}
		}
		int i = 3;
		int j = 5;
		System.out.println(i);
		System.out.println(j);
	}
	
}

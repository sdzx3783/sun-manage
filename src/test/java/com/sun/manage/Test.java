package com.sun.manage;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

import org.apache.commons.codec.digest.DigestUtils;

/**  
 * @Title:  Test.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月6日 下午6:27:19   
 */
public class Test {
	public static void main(String[] args) {
		
		BigDecimal d1=new BigDecimal("3");
		BigDecimal d2=new BigDecimal("7");
		BigDecimal divide = d2.divide(d1,2,BigDecimal.ROUND_HALF_UP);
		System.out.println(divide);
		ClassLoader classLoader = Test.class.getClassLoader();
		URL resource = classLoader.getResource("com//sun//manage//Test.class");
		System.out.println(resource.getPath());
		try {
			String md5 = DigestUtils.md5Hex(new FileInputStream(resource.getFile()));
			System.out.println(md5);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	//08d2468d37f23f46568d248ccb7a7c93
}

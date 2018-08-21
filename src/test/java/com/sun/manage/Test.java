package com.sun.manage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.util.Base64Utils;

/**  
 * @Title:  Test.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月6日 下午6:27:19   
 */
public class Test {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		
		/*BigDecimal d1=new BigDecimal("3");
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
		} */
		
		FileInputStream fileInputStream = new FileInputStream("C:\\Users\\admin\\Pictures\\veer-314152897.jpg");
		FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\admin\\Pictures\\veer-copy.jpg");
		byte[] byt = new byte[fileInputStream.available()];
		
		fileInputStream.read(byt);
		String encodeToString = Base64Utils.encodeToString(byt);
		System.out.println(encodeToString);
		byte[] decodeFromString = Base64Utils.decodeFromString(encodeToString);
		fileOutputStream.write(decodeFromString);
	}
	//08d2468d37f23f46568d248ccb7a7c93
	
	
}

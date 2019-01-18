package com.sun.manage;


/**  
 * @Title:  Test1.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月22日 上午10:26:33   
 */
public class Test1 {
	public static void main(String[] args) {
		String a = "xiaomeng2";
		final String b = "xiaomeng";
		String d = "xiaomeng";
		String c = b + 2;
		String e = d + 2;
		System.out.println((a == c));
		System.out.println((a == e));
		System.out.println((a == e.intern()));
	}
}

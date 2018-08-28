package com.sun.manage;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;


/**  
 * @Title:  Test1.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月22日 上午10:26:33   
 */
public class Test1 {
	public static void main(String[] args) {
		JSONObject json=new JSONObject();
		Map<String,Integer> map=new HashMap<>();
		map.put("count", 111);
		json.put("info", map);
		json.put("count", 22);
		System.out.println(json.containsKey("count"));
	}
}

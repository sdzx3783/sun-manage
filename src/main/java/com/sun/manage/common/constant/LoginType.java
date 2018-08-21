package com.sun.manage.common.constant;

/**  
 * @Title:  LoginType.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月9日 下午2:32:36   
 */
public enum LoginType {
	password(0,"密码模式登陆"),
	weixin(1,"微信登陆");
	
	private int type;
	private String name;
	private LoginType(int type,String name){
		this.type=type;
		this.name=name;
	}
	
	public static LoginType  getLoginTypeByType(int type) {
		LoginType[] values = LoginType.values();
		LoginType result=null;
		for (LoginType loginType : values) {
			if(loginType.type==type) {
				result=loginType;
				break;
			}
		}
		return result;
	}
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}

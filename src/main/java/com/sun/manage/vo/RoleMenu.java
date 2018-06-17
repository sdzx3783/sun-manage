package com.sun.manage.vo;

public class RoleMenu {
	private String role;
	private String menu;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	
	public RoleMenu(String menu,String role) {
		super();
		this.role = role;
		this.menu = menu;
	}
	@Override
	public String toString() {
		return "RoleMenu [role=" + role + ", menu=" + menu + "]";
	}
	
}

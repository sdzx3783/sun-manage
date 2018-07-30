package com.sun.manage.entity.mongo;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sun.manage.entity.sys.SysUser;

import lombok.Getter;
import lombok.Setter;
/**
 * @author Administrator
 *
 */
@Getter
@Setter
/**
 * 保存到mongoDB的bean也可以不使用@Document注解
 * https://blog.csdn.net/mitkey/article/details/53958675
 * @author Administrator
 *
 */
@Document(collection="nonDocInterfacesBean")
public class NonDocInterfacesBean {
	@Id
	private String id;
	private String name;
	private Date date;
	private Integer age;
	private Double d;
	private List<SysUser> users;
	private SysUser sysUser;
	@Override
	public String toString() {
		return "NonDocInterfacesBean [id=" + id + ", name=" + name + ", date=" + date + ", age=" + age + ", d=" + d
				+ ", users=" + users + ", sysUser=" + sysUser + "]";
	}

}

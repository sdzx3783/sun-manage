package com.sun.manage.entity;

import java.util.Date;
import javax.persistence.*;

/**  
 * @Title:  Test.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月9日 上午10:50:12   
 */
/**  
 * @Title:  Test.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月9日 上午10:50:16   
 */
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Date ctime;

    private Integer status;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return ctime
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * @param ctime
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "Test [id=" + id + ", name=" + name + ", ctime=" + ctime + ", status=" + status + "]";
	}
    
}
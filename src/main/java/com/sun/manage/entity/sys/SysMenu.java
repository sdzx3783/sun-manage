package com.sun.manage.entity.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "sys_menu",uniqueConstraints={@UniqueConstraint(columnNames={"alias"},name="idx_alias")})
@DynamicInsert
public class SysMenu implements Serializable
{
	private static final long serialVersionUID = 4326362232140178503L;
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
    private String alias;
    private String link;
    private Integer sort;
    private Integer status;
    private Integer isShow;
    @JsonProperty("parentId")
    @Column(name = "parent_id")
    private Integer parentId;
    
    private String path;
    
    @Transient
    private List<SysMenu> childMenus;
    
    private Date createdTime;

    private Date modifiedTime;

    
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<SysMenu> getChildMenus() {
		return childMenus;
	}
	public void setChildMenus(List<SysMenu> childMenus) {
		this.childMenus = childMenus;
	}
    
	
	@PrePersist
    public void beforeAdd(){
        modifiedTime=createdTime=new Date();
    }
    @PreUpdate
    public void beforeModified(){
        modifiedTime=new Date();
    }

    @Column(updatable = false, name = "created_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreatedTime() {
        return createdTime;
    }
    @Column(updatable = false, name = "modified_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getModifiedTime() {
        return modifiedTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
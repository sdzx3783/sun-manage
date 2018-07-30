package com.sun.manage.entity.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.manage.common.api.Contants;
@Entity
@Table(name="sys_user")
@DynamicInsert
public class SysUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3653608355198574743L;

	private String account;
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

    private String wechatId;

    private String userName;
    
    private String nickName;

    private String password;

    private String email;

    private String mobile;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    private String address;

    private Integer gender;

    private String province;

    private String city;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ctime;

    private String lastLoginIp;

    private Date lastLoginTime;
    
    private String loginway;
    
    private Integer state;
    
    private String deviceToken;

    private String salt;

	private Integer type;
	
	@JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role_rel", joinColumns= {
    		@JoinColumn(name="user_id")},inverseJoinColumns= {@JoinColumn(name = "role_id")})
	private Set<SysRole> roles=new HashSet<>();
	
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getCtime() {
        return ctime;
    }
    
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

	public String getLoginway() {
		return loginway;
	}

	public void setLoginway(String loginway) {
		this.loginway = loginway;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Set<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}

	public boolean isSuperUser(){
        if (type!=null&&type== Contants.USER_TYPE_SUPER_SYS){
            return true;
        }
        return false;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysUser other = (SysUser) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	
}
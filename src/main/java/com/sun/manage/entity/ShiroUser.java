package com.sun.manage.entity;


import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/14.
 */
public class ShiroUser implements Serializable
{
    private static final long serialVersionUID = -1373760761780840081L;

    private Integer id;

    private String account;

    private String name;

    private String imageUrl;
    public ShiroUser(Integer id, String account, String name)
    {
        super();
        this.id = id;
        this.account = account;
        this.name = name;
    }

    public Integer getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getAccount() {
        return account;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    
    
    @Override
    public String toString()
    {
        return account;
    }

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		return result;
	}

    /**
     * 重载hashCode,只计算loginName;
     */
//    @Override
//    public int hashCode()
//    {
////        return com.google.common.base.Objects.hashCode(account);
//    	
//    }
    

    /**
     * 重载equals,只计算loginName;
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
       ShiroUser other = (ShiroUser)obj;
        if (account == null)
        {
            if (other.account != null)
            {
                return false;
            }
        }
        else if (!account.equals(other.account))
        {
            return false;
        }
        return true;
    }

}
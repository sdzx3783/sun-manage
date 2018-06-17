package com.sun.manage.repository.sys;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sun.manage.entity.sys.SysUser;
import com.sun.manage.repository.BaseRepository;

public interface SysUserRepository extends BaseRepository<SysUser, Integer>
{
	//当需要更改的字段少时，例如仅仅对单个时间或者状态进行更新，可以在对应的repository添加update方法
	@Modifying
    @Query(value="UPDATE SysUser s SET s.state= :state WHERE s.id= :id")
    int  updateState(@Param("state")int state,@Param("id")int id);
    
    //默认@query 里面的JPQL， 要使用类User 和该类的属性.   把sys_user 改为 SysUser
    @Query(value="select s from SysUser s where s.account=?1")
    List<SysUser> getByAccount(String account);
}

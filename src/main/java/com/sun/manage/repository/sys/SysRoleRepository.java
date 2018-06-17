package com.sun.manage.repository.sys;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sun.manage.entity.sys.SysRole;
import com.sun.manage.repository.BaseRepository;

public interface SysRoleRepository extends BaseRepository<SysRole, Integer>
{
	@Modifying
    @Query(value="UPDATE SysRole s SET s.status= 1 WHERE s.id= :id")
    void delete(Integer id);
}

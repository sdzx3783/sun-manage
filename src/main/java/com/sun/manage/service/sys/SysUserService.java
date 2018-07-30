package com.sun.manage.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.manage.common.util.Digests;
import com.sun.manage.common.util.Encodes;
import com.sun.manage.common.util.EncryptUtil;
import com.sun.manage.entity.sys.SysMenu;
import com.sun.manage.entity.sys.SysUser;
import com.sun.manage.repository.sys.SysUserRepository;
import com.sun.manage.service.BaseService;

@Service
public class SysUserService extends BaseService<SysUser, Integer>
{
	@Autowired
    private SysUserRepository systemUserRepository;
	private static final int SALT_SIZE = 8;
	

	public List<SysMenu> getMenuByUserId(Integer userId) {
		return null;
	}
    public void entryptPassword(SysUser user) {
    	byte[] salt = Digests.generateSalt(SALT_SIZE);
    	user.setSalt(Encodes.encodeHex(salt));
    	String entryptPassword = EncryptUtil.entryptPassword(user.getPassword(), salt);
    	user.setPassword(entryptPassword);
    }
    /**
     * 更新用户状态
     * @param state
     * @param userid
     * @return
     */
    @Transactional
	public int updateUserState(int state,Integer userid) {
		return systemUserRepository.updateState(state, userid);
	}
    
	/**
	 * 根据用户账号获取用户
	 * @param account
	 * @return
	 */
   // @Transactional
	public SysUser getByAccount(String account) {
		List<SysUser> byAccount = systemUserRepository.getByAccount(account);
		//byAccount.get(0).setCity("深圳"); 此处会不会发送update语句跟该方法是否配置有事务有关
		return (byAccount==null || byAccount.size()==0)?null:byAccount.get(0);
	}
	@Transactional
	public void testTransaction(String account){
		List<SysUser> byAccount = systemUserRepository.getByAccount(account);
		byAccount.get(0).setCity("深圳"); //此处会不会发送update语句跟该方法是否配置有事务有关
	}
}

package com.bmcc.vgop.data.usergroup;

import java.util.List;

import com.bmcc.vgop.data.usergroup.po.UserGroupQueryConfig;
import com.bmcc.vgop.data.usergroup.po.UserGroupQueryLog;
import com.bmcc.vgop.web.usergroup.vo.UserGroupReq;
import com.bmcc.vgop.web.usergroup.vo.UserGroupResp;

public interface UserGroupDao {
	
	public List<UserGroupQueryConfig> getGroupQueryConfig();
	public String getRetBySql(String sql,String phone);
	
	public UserGroupQueryLog saveRightLog(UserGroupReq req);
	public void updateGroupQueryLog(UserGroupResp resp, UserGroupQueryLog log);
	

}

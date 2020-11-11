package com.bmcc.vgop.service.usergroupIf;

import com.bmcc.vgop.web.usergroup.vo.UserGroupReq;
import com.bmcc.vgop.web.usergroup.vo.UserGroupResp;

public interface UserGroupService {

	public UserGroupResp userGroupQuery(UserGroupReq req);
}

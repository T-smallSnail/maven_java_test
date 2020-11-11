package com.bmcc.vgop.data.rightlog;

import com.bmcc.vgop.data.rightlog.po.RightLog;
import com.bmcc.vgop.web.subserv.vo.SubReq;
import com.bmcc.vgop.web.subserv.vo.SubResp;

public interface RightLogDao {
	
	public RightLog saveRightLog(SubReq req);
	
	public void updateRightLog(SubResp resp,RightLog log);

}

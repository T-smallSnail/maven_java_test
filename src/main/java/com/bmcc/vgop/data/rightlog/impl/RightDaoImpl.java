package com.bmcc.vgop.data.rightlog.impl;

import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bmcc.vgop.data.common.db.impl.GenericDaoDb2;
import com.bmcc.vgop.data.rightlog.RightLogDao;
import com.bmcc.vgop.data.rightlog.po.RightLog;
import com.bmcc.vgop.web.subserv.vo.Bizs;
import com.bmcc.vgop.web.subserv.vo.Results;
import com.bmcc.vgop.web.subserv.vo.SubReq;
import com.bmcc.vgop.web.subserv.vo.SubResp;

@SuppressWarnings("unchecked")
@Repository
@Transactional(value = "db2TransactionManager")
public class RightDaoImpl extends GenericDaoDb2<RightLog, Long> implements RightLogDao {

	/**
	 * 保存请求日志
	 */
	@Override
	public RightLog saveRightLog(SubReq req) {
		RightLog log = new RightLog();
		log.setPhone(req.getPhone());
		log.setTransId(req.getTransId());
		log.setChannelId(req.getChannelId());
		StringBuilder sbu = new StringBuilder(80);
		for (Bizs biz : req.getBizs()) {
			sbu.append(biz.getBizCode()).append(",");
		}
		log.setBizs(sbu.toString());
		log.setCreate_time(new Date());
		this.save(log);
		return log;
	}

	/**
	 * 更新返回日志
	 */
	@Override
	public void updateRightLog(SubResp resp, RightLog log) {
		StringBuilder sbu = new StringBuilder(200);
		if(resp.getResults()!=null) {
			for (Results r : resp.getResults()) {
				sbu.append(r.getOfferId()).append("|").append(r.getRetCode()).append(",");
			}
		}
		log.setResp_offerId_retCode(sbu.toString());
		log.setResp_vgop_msg(resp.getMsg());
		log.setResp_time(new Date());
		this.save(log);
	}

}

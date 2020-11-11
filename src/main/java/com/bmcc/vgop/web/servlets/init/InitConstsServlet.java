package com.bmcc.vgop.web.servlets.init;

import ch.qos.logback.classic.Logger;
import com.bmcc.vgop.common.consts.DataConsts;
import com.bmcc.vgop.common.consts.vo.OfferIdObj;
import com.bmcc.vgop.data.bizs.BizsDao;
import com.bmcc.vgop.data.bizs.po.BizOfferid;
import com.bmcc.vgop.data.consts.ConstantDao;
import com.bmcc.vgop.data.consts.UsersubConstantsDao;
import com.bmcc.vgop.data.consts.po.PhoneSubstitutionConf;
import com.bmcc.vgop.data.consts.po.TVgopUsersubConstants;
import com.bmcc.vgop.data.mobidentify.MobidentifyDao;
import com.bmcc.vgop.data.mobidentify.po.TVgopUsersubMobileIdentifyChannelConfig;
import com.bmcc.vgop.data.mobidentify.po.TVgopUsersubMobileIdentifyChannelStypeRef;
import com.bmcc.vgop.data.thirdchannel.CrmChannelInfoDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmChannelInfoEntity;
import com.bmcc.vgop.data.thirdchannel.CrmProdunctInfoDao;
import com.bmcc.vgop.data.thirdchannel.po.TVgopUsersubCallCrmProductInfoEntity;
import com.bmcc.vgop.data.usergroup.UserGroupDao;
import com.bmcc.vgop.data.usergroup.po.UserGroupQueryConfig;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "InitConstsServlet", urlPatterns = {"/init"}, loadOnStartup = 20)
@Service
public class InitConstsServlet extends HttpServlet {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(InitConstsServlet.class);

    BizsDao bizsDao;

    UserGroupDao userGroupDao;

    ConstantDao constantDao;

    CrmChannelInfoDao channelInfoDao;

    CrmProdunctInfoDao produnctInfoDao;

    MobidentifyDao mobidentifyDao;

    UsersubConstantsDao usersubConstantsDao;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String t = req.getParameter("t");
        if ("reinit".equals(t)) {
            logger.info("初始化业务编码与offerid对应关系 start");
            this.initBizOfferId();
            logger.info("初始化业务编码与offerid对应关系 end");
        } else if ("conts".equals(t)) {
            logger.info("初始化Constant数据 start");
            this.initConstantData();
            logger.info("初始化Constant数据 end");
        }else if("channel".equals(t)){
            logger.info("初始化ThirdChannel数据 start");
            this.initThirdChannelData();
            logger.info("初始化ThirdChannel数据 end");
        }else if ("crmProduct".equals(t)){
            logger.info("初始化crm产品数据 start");
            this.initCrmProductData();
            logger.info("初始化crm产品数据 end");
        }else if ("mobileIndetify".equals(t)){

            logger.info("初始化渠道与查询类型对应关系 start");
            this.initChannelStypeRef();
            logger.info("初始化渠道与查询类型对应关系 end");

            logger.info("初始化渠道与盐值对应关系 start");
            this.initChannelSignSalt();
            logger.info("初始化渠道与盐值对应关系 end");
        } else if("conts".equals(t)){
            this.initUserSubConstants();
        }
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        WebApplicationContext webApplicationContext = (WebApplicationContext) servletConfig.getServletContext().getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
        bizsDao = webApplicationContext.getBean(BizsDao.class);
        userGroupDao = webApplicationContext.getBean(UserGroupDao.class);
        constantDao = webApplicationContext.getBean(ConstantDao.class);
        channelInfoDao = webApplicationContext.getBean(CrmChannelInfoDao.class);
        produnctInfoDao = webApplicationContext.getBean(CrmProdunctInfoDao.class);
        mobidentifyDao = webApplicationContext.getBean(MobidentifyDao.class);
        usersubConstantsDao = webApplicationContext.getBean(UsersubConstantsDao.class);


        logger.info("初始化业务编码与offerid对应关系 start");
        this.initBizOfferId();
        logger.info("初始化业务编码与offerid对应关系 end");

        logger.info("初始化人群查询code对应sql start");
        this.initGroupCodeSql();
        logger.info("初始化人群查询code对应sql end");

        logger.info("初始化Constant数据 start");
        this.initConstantData();
        logger.info("初始化Constant数据 end");

        logger.info("初始化渠道与查询类型对应关系 start");
        this.initChannelStypeRef();
        logger.info("初始化渠道与查询类型对应关系 end");

        logger.info("初始化渠道与盐值对应关系 start");
        this.initChannelSignSalt();
        logger.info("初始化渠道与盐值对应关系 end");


        //初始化第三方渠道同一订购接口相关的常量----start
        logger.info("初始化ThirdChannel数据 start");
        this.initThirdChannelData();
        logger.info("初始化ThirdChannel数据 end");

        logger.info("初始化crm产品数据 start");
        this.initCrmProductData();
        logger.info("初始化crm产品数据 end");
        //初始化第三方渠道同一订购接口相关的常量----end


        this.initUserSubConstants();


    }

    /**
     * 初始化usersub的系统常量
     * @author : pancho
     * @date : 2020/4/22 22:06
     * @return : void
     */
    private void initUserSubConstants() {
        DataConsts.USERSUB_CONTS.clear();
        List<TVgopUsersubConstants> list =  usersubConstantsDao.getAllValidConsts();
        for(TVgopUsersubConstants conts:list){
            DataConsts.USERSUB_CONTS.put(conts.getConstKey(),conts.getConstValue());
        }
        logger.info("初始化usersub系统常量完成！");
    }

    private void initCrmProductData() {
        DataConsts.CRM_PRODUCT_MSG.clear();
        List<TVgopUsersubCallCrmProductInfoEntity> productList = produnctInfoDao.getAllProduct();
        String offerId;
        for (TVgopUsersubCallCrmProductInfoEntity crmProduct:productList){
            offerId = crmProduct.getOfferId();
            DataConsts.CRM_PRODUCT_MSG.put(offerId,crmProduct);
        }
    }

    private void initThirdChannelData() {
        DataConsts.THIRD_CHANNEL_MSG.clear();
        List<TVgopUsersubCallCrmChannelInfoEntity> thirdChannelMsgList = channelInfoDao.getThirdChannelMsg();
        String appId;
        for (TVgopUsersubCallCrmChannelInfoEntity thirdChannelMsg:thirdChannelMsgList){
            appId = thirdChannelMsg.getAppId();
            DataConsts.THIRD_CHANNEL_MSG.put(appId,thirdChannelMsg);
        }
    }

    private void initBizOfferId() {
        DataConsts.BIZCODE_OFFERID.clear();
        List<BizOfferid> list = bizsDao.getBizOfferIds();
        OfferIdObj obj = null;
        for (BizOfferid bf : list) {
            obj = new OfferIdObj();
            obj.setOfferidForFree(bf.getOfferidForFree());
            obj.setOfferidNoFree(bf.getOfferidNoFree());
            DataConsts.BIZCODE_OFFERID.put(bf.getBizCode(), obj);
        }
    }

    private void initConstantData() {
        DataConsts.PHONE_CONF.clear();
        List<PhoneSubstitutionConf> list = constantDao.geUsersubConstant();
        for (PhoneSubstitutionConf bf : list) {
            DataConsts.PHONE_CONF.put(bf.getAppid(), bf);
        }
    }

    private void initGroupCodeSql() {
        DataConsts.GROUP_CODE_SQL.clear();
        List<UserGroupQueryConfig> list = userGroupDao.getGroupQueryConfig();
        for (UserGroupQueryConfig c : list) {
            DataConsts.GROUP_CODE_SQL.put(c.getCode(), c.getSql());
        }
    }


    /**
     * 初始化加载第三方渠道与查询类型对应关系
     */
    public void initChannelStypeRef(){
        DataConsts.MOBILE_IDENTIFY_REF_LIST.clear();
        List<TVgopUsersubMobileIdentifyChannelStypeRef> refList = mobidentifyDao.initChannelStypeRef();
        DataConsts.MOBILE_IDENTIFY_REF_LIST.addAll(refList);
    }

    /**
     * 初始化加载第三方渠道与盐值对应关系
     */
    public void initChannelSignSalt(){
        DataConsts.MOBILE_IDENTIFY_SIGN_MAP.clear();
        List<TVgopUsersubMobileIdentifyChannelConfig> channelConfigs = mobidentifyDao.initChannelSignSalt();
        for (TVgopUsersubMobileIdentifyChannelConfig channelConfig : channelConfigs){
            //将查询到到关系放入内存
            DataConsts.MOBILE_IDENTIFY_SIGN_MAP.put(channelConfig.getAppId(),channelConfig.getSignSalt());
        }
    }
}

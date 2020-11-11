package com.bmcc.vgop.web.thirdchannel.controller;

import com.bmcc.vgop.service.thirdchannel.ThirdChannelCheckOrderBusinessService;
import com.bmcc.vgop.service.thirdchannel.ThirdChannelGetAccessTokenService;
import com.bmcc.vgop.service.thirdchannel.ThirdChannelOrderCrmProductService;
import com.bmcc.vgop.service.thirdchannel.ThirdChannelRemoveAccessTokenService;
import com.bmcc.vgop.service.thirdchannel.ThirdChannelSendSmsMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：pancho
 * @date ：Created in 2019/10/10 16:53
 * @description : 第三方渠道统一订购接口
 */
@RestController
@RequestMapping("/thirdChannel")
public class ThirdpartOrderCrmController {

    @Autowired
    ThirdChannelGetAccessTokenService getAccessTokenService;

    @Autowired
    ThirdChannelOrderCrmProductService orderCrmProductService;

    @Autowired
    ThirdChannelSendSmsMsgService sendSmsMsgService;

    @Autowired
    ThirdChannelRemoveAccessTokenService removeAccessTokenService;

    @Autowired
    ThirdChannelCheckOrderBusinessService checkOrderBusiness;

    @RequestMapping(value = "/sendSmsMsg", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public String sendSmsMsg(@RequestBody String param) {
        return sendSmsMsgService.sendSmsMsg(param);
    }

    @RequestMapping(value = "/getAccessToken", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public String getAccessToken(@RequestBody String param) {
        return getAccessTokenService.getAccessToken(param);
    }

    @RequestMapping(value = "/orderCrmProduct", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public String orderCrmProduct(@RequestBody String param) {
        return orderCrmProductService.orderCrmProduct(param);
    }

    @RequestMapping(value = "/removeAccessToken", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public String removeToken(@RequestBody String param) {
        return removeAccessTokenService.removeToken(param);
    }

    @RequestMapping(value = "/checkOrderBusiness", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public String checkOrderBusiness(@RequestBody String param) {
        return checkOrderBusiness.checkOrderBusiness(param);
    }




}

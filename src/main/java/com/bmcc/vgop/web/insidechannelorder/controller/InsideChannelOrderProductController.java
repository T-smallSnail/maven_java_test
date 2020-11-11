package com.bmcc.vgop.web.insidechannelorder.controller;

import com.bmcc.vgop.service.insidechannelorder.InsideChannelOrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：pancho
 * @date ：Created in 2020/4/22 16:50
 * @description :内部渠道订购接口
 */
@RestController
@RequestMapping("/insidechannel")
public class InsideChannelOrderProductController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    InsideChannelOrderProductService orderProductService;

    @RequestMapping(value = "/orderProduct", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public String orderProduct(String param){
        return orderProductService.orderProduct(param,request);

    }

}

package cn.pancho.crm;

import com.bmcc.vgop.webclient.service.crmopenapi.CrmOpenApiService;
import com.bmcc.vgop.webclient.service.crmopenapi.obj.createflow.CreateFlowResultObj;

import com.bmcc.vgop.webclient.service.crmopenapi.obj.qry009.Qry009RetObj;
import com.bmcc.vgop.webclient.service.crmopenapi.obj.sub.SubRetObj;
import config.CrmOpenApiConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ：pancho
 * @date ：Created in 2019/9/18 16:24
 * @description :
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CrmOpenApiConfig.class)
public class CrmPro {

    String project_name = "test";


    String phone = "15810860391";


    @Autowired
    CrmOpenApiService crmOpenApiService;

    @Before
    public void gfd(){
//        ApplicationContext context = new AnnotationConfigApplicationContext(CrmOpenApiConfig.class);
//        crmOpenApiService = context.getBean(CrmOpenApiService.class);
    }


    @Test
    public void flowCreate() {

        //调能开接口
        String offerid = "111002051816";
        String commodityCode = "556581";
        String size = "5120";

        CreateFlowResultObj retObj = crmOpenApiService.flowCreate_002(phone , offerid, commodityCode, size, project_name);
        System.out.println("接口, 返回 : "+retObj.isSucc());



    }


    @Test
    public void subPro(){

        String offerid = "111602000144";

        //调能开充值接口

        SubRetObj retObj = crmOpenApiService.subPro_015(phone, offerid, project_name);
        System.out.println("能开接口, 返回 : "+retObj.isSucc());

    }




    @Test
    public void qry_009(){


        String offerid = "111004265820";
        //调能开充值接口
        Qry009RetObj qry009RetObj = crmOpenApiService.qry_009(phone, offerid, project_name);
        System.out.println("能开接口, 返回 : "+qry009RetObj.getMessage());

    }









}

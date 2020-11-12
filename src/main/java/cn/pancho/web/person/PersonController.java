package cn.pancho.web.person;

import cn.pancho.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：pancho
 * @date ：Created in 2019/7/11 18:51
 * @description :
 */
@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(String userName,String password){

        personService.savePerson(userName,password);

        return "hello";

    }
}

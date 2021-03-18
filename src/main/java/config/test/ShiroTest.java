package config.test;

import cn.pancho.data.entity.Person;
import cn.pancho.data.mapper.PersonDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ：pancho
 * @date ：Created in 2021/3/15 11:31
 * @description :
 */

@RunWith(SpringJUnit4ClassRunner.class)

@ActiveProfiles("dev")
//AppConfig.java中去掉WebConfig.class,BaseWebConfig.class
@ContextConfiguration(classes = AppConfigTest.class)
public class ShiroTest {


    @Autowired
    PersonDao personDao;

    @Test
    public void savePerson() {

        Person p = new Person();
        p.setAddress("光山2");
        p.setLastName("32");
        p.setLastName("23");
        personDao.insert(p);
    }

}

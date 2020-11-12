package cn.pancho.service.person;


import cn.pancho.data.entity.Person;
import cn.pancho.data.mapper.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：pancho
 * @date ：Created in 2019/7/11 18:53
 * @description :
 */
@Service
public class PsersonServiceImpl implements PersonService{


    @Autowired
    PersonDao personDao;

    @Override
    @Transactional(value="mysqlTransactionManager" ,readOnly = false)
    public void doFact() {

        //手写xml-sql
//        Person personById = personDao.findPersonById(11);
//        System.out.println(personById.getAddress());

        //insert
//        Person p = new Person();
//        p.setAddress("中关村");
//        personDao.insert(p);

//        personDao.deleteById(12);

//        for (int i = 0; i < 10; i++) {
//            Person p = new Person();
//            p.setAddress("中关村"+i);
//            personDao.insert(p);
//        }


//        Map<String, Object> map = new HashMap<String,Object>();
//        map.put("Address","中关村1");
//        personDao.deleteByMap(map);

        //根据条件构造器删除
//        QueryWrapper<Person> id = new QueryWrapper<Person>().eq("id", 16);
//        personDao.delete(id);

//        List<Integer> list = new ArrayList();
//        list.add(18);
//        list.add(19);
//        personDao.deleteBatchIds(list);

//        Person p = new Person();
//        p.setId(24);
//        p.setAddress("休士顿");
//        personDao.updateById(p);

        //userUpdateWrapper
//        //修改值
//        Person user = new Person();
//        user.setId(23);
//        user.setAddress("伦敦");
//        //修改条件s
//        UpdateWrapper<Person> userUpdateWrapper = new UpdateWrapper<>();
//        userUpdateWrapper.eq("address", "中关村8");
//        int update = personDao.update(user, userUpdateWrapper);
//        System.out.println(update);


//        Map<String, Object> map = new HashMap<String,Object>();
//        map.put("Address","中关村5");
//        List<Person> people = personDao.selectByMap(map);
//        System.out.println(people.size());

        //多条会报错
//        QueryWrapper<Person> id = new QueryWrapper<Person>().eq("Address", "中关村5");
//        Person person = personDao.selectOne(id);
//        System.out.println(person.getAddress());


        //查询结果封装map中
//        QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
//        queryWrapper.isNotNull("address");
//        List<Map<String, Object>> maps = personDao.selectMaps(queryWrapper);
//        for (Map<String, Object> map : maps) {
//            System.out.println(map);
//        }


//        Page<Person> page = new Page<>(1, 5);
//        QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
//        IPage<Person> userIPage = personDao.selectPage(page, queryWrapper);
//        System.out.println(userIPage);



//        Page<Person> page = new Page<>(1, 5);
//        QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
//        IPage<Map<String, Object>> mapIPage = personDao.selectMapsPage(page, queryWrapper);
//        System.out.println(mapIPage);

    }

    @Override
    @Transactional(value="mysqlTransactionManager" ,readOnly = false)
    public void savePerson(String userName,String password) {

        Person p = new Person();
        p.setAddress("中关村");
        p.setLastName(userName);
        p.setLastName(password);
        personDao.insert(p);
    }


}

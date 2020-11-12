package cn.pancho.data.mapper;


import cn.pancho.vgop.data.person.po.Person;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import config.annotation.MysqlMapper;
import org.apache.ibatis.annotations.Insert;


@MysqlMapper
public interface PersonDao extends BaseMapper<Person> {

    Person findPersonById(Integer id);

    void insertPerson();



    @Insert("insert into person values (5,'pancho11111','tian','tianjing','china')")
    void savePerson();
}

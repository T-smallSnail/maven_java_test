package config;


import com.bmcc.vgop.common.consts.system.SysBaseConsts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@Configuration

/*设置spring管理bean扫描范围*/
@ComponentScan("cn.pancho")

/*配置环境*/
@PropertySource(value={"classpath:"+SysBaseConsts.APP_CONFIG},ignoreResourceNotFound=false,encoding=SysBaseConsts.CHAR_SET)

/*加载数据库配置*/
@PropertySource(value={"classpath:/config/db.properties"},ignoreResourceNotFound=false,encoding=SysBaseConsts.CHAR_SET)



@Import(value = {BaseConfig.class,MysqlConfig.class,WebConfig.class,BaseWebConfig.class})
public class AppConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}

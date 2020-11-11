package config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.bmcc.vgop.common.consts.system.SysBaseConsts;
import com.bmcc.vgop.data.common.mybatis.CustomMybatisConfiguration;
import com.bmcc.vgop.data.common.mybatis.CustomMybatisMapperRefresh;
import config.annotation.MysqlMapper;
import config.consts.MysqlEnum;
import config.datasource.MysqlRountingDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan("config.aop")

/** 加载数据库基础配置 。 **/
@PropertySource(value = { "classpath:/config/mysql_base.properties" }, ignoreResourceNotFound = false, encoding = SysBaseConsts.CHAR_SET)

/** 加载属于mysql的mapper，mapper需要加MysqlMapper注解**/

/** 加载数据源**/
@MapperScan(basePackages = "cn.**.data.mapper", annotationClass = MysqlMapper.class, sqlSessionTemplateRef = "mySqlSessionTemplate")
@Import(value = { MysqlMasterConfig.class
		//, MysqlSlaveConfig.class 
		})

public class MysqlConfig {

	/**
	 * 引用mapper的代码中方法需要加上MysqlDataSourceType注解，用以选择读写库。
	 */
	@Autowired
	@Bean("mysqlRountingDataSource")
    MysqlRountingDataSource threadLocalRountingDataSource(@Qualifier("mysqlMasterDataSource") DataSource mysqlMasterDataSource
                                                          //, @Qualifier("mysqlSlaveDataSource_1") DataSource mysqlSlaveDataSource_1
			) {
		MysqlRountingDataSource mysqlRountingDataSource = new MysqlRountingDataSource();
		mysqlRountingDataSource.setDefaultTargetDataSource(mysqlMasterDataSource);

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(MysqlEnum.MASTER, mysqlMasterDataSource);
//		map.put(MysqlEnum.SLAVE, mysqlSlaveDataSource_1);
		mysqlRountingDataSource.setTargetDataSources(map);

		return mysqlRountingDataSource;
	}

	@Bean("mysqlGlobalConfig")
	public GlobalConfig globalConfig() {

		DbConfig dbConfig = new DbConfig();
		dbConfig.setKeyGenerator(new H2KeyGenerator());

		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setDbConfig(dbConfig);
		globalConfig.setBanner(false);
		return globalConfig;
	}

	/**
	 * mysql对应xml，必须要以m_mapper结尾。
	 * 
	 * @param dataSource
	 * @param globalConfig
	 * @return
	 * @throws Exception
	 */
	@Bean("mysqlSessionFactory")
	@Autowired
	public SqlSessionFactory mysqlSessionFactory(@Qualifier("mysqlRountingDataSource") MysqlRountingDataSource dataSource, @Qualifier("mysqlGlobalConfig") GlobalConfig globalConfig) throws Exception {
		MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setGlobalConfig(globalConfig);
//		factoryBean.setPlugins(new Interceptor[] { new PerformanceInterceptor(), new PaginationInterceptor() });
		factoryBean.setMapperLocations((new PathMatchingResourcePatternResolver()).getResources("classpath*:mapper/*_m_mapper.xml"));
		factoryBean.setConfiguration(new CustomMybatisConfiguration());
		factoryBean.afterPropertiesSet();

		return factoryBean.getObject();
	}

	@Bean("mySqlSessionTemplate")
	@Autowired
	public SqlSessionTemplate SqlSessionTemplate(@Qualifier("mysqlSessionFactory") SqlSessionFactory sqlfactory) {
		SqlSessionTemplate SqlSessionTemplate = new SqlSessionTemplate(sqlfactory);
		return SqlSessionTemplate;
	}

	@Bean("mysqlTransactionManager")
	@Autowired
	public DataSourceTransactionManager mysqlTransactionManager(@Qualifier("mysqlRountingDataSource") MysqlRountingDataSource dataSource) {
		DataSourceTransactionManager manager = new DataSourceTransactionManager();
		manager.setDataSource(dataSource);
		return manager;
	}

	/**
	 * 仅测试环境加载
	 * 解决修改mapper.xml中sql时需重启服务器问题。
	 */
	@Profile("dev")
	@Bean("mysqlCustomMybatisMapperRefresh")
	@Autowired
	public CustomMybatisMapperRefresh customMybatisMapperRefresh(@Qualifier("mysqlSessionFactory") SqlSessionFactory sqlfactory) throws IOException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		return new CustomMybatisMapperRefresh(resolver.getResources("classpath*:mapper/*_m_mapper.xml"), sqlfactory, 10, 2, true);
	}

}

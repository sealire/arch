package org.leesia.datasource.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Auther: leesia
 * @Date: 2018/9/10 17:39
 * @Description:
 */
@Configuration
@MapperScan(basePackages = "org.leesia.datasource.dao.ds2", sqlSessionFactoryRef = "ds2SqlSessionFactory")
public class DataSource2Config {

//    @Bean(name = "ds2dataSourceProperties")
//    @Qualifier("ds2dataSourceProperties")
//    @ConfigurationProperties(prefix = "spring.datasource.ds2")
//    public DataSourceProperties ds2dataSourceProperties() {
//        return new DataSourceProperties();
//    }

    /**
     * 配置test1数据库
     */
    @Bean(name = "ds2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.ds2")
    public DataSource ds2DataSource() {
        return DataSourceBuilder.create().build();
//        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * ds2 sql会话工厂
     */
    @Bean(name = "ds2SqlSessionFactory")
    public SqlSessionFactory ds2SqlSessionFactory(@Qualifier("ds2DataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
      bean.setMapperLocations(
              new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/ds2/*.xml"));
        return bean.getObject();
    }

    /**
     * ds2 事物管理
     */
    @Bean(name = "ds2TransactionManager")
    public DataSourceTransactionManager ds2TransactionManager(@Qualifier("ds2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "ds2SqlSessionTemplate")
    public SqlSessionTemplate ds2SqlSessionTemplate(@Qualifier("ds2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

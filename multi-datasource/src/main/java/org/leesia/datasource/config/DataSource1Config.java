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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Auther: leesia
 * @Date: 2018/9/10 17:39
 * @Description:
 */
@Configuration
@MapperScan(basePackages = "org.leesia.datasource.dao.ds1", sqlSessionFactoryRef = "ds1SqlSessionFactory")
public class DataSource1Config {

    /**
     * 配置test1数据库
     */
    @Bean(name = "ds1DataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public DataSource ds1DataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * ds1 sql会话工厂
     */
    @Bean(name = "ds1SqlSessionFactory")
    @Primary
    public SqlSessionFactory ds1SqlSessionFactory(@Qualifier("ds1DataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
//      bean.setMapperLocations(
//              new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
        return bean.getObject();
    }

    /**
     * ds1 事物管理
     */
    @Bean(name = "ds1TransactionManager")
    @Primary
    public DataSourceTransactionManager ds1TransactionManager(@Qualifier("ds1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "ds1SqlSessionTemplate")
    public SqlSessionTemplate ds1SqlSessionTemplate(@Qualifier("ds1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

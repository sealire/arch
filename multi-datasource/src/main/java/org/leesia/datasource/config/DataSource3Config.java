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
 * @Date: 2018/9/13 08:37
 * @Description:
 */
@Configuration
@MapperScan(basePackages = "org.leesia.datasource.dao.ds3", sqlSessionFactoryRef = "ds3SqlSessionFactory")
public class DataSource3Config {

    /**
     * 配置ds3数据库
     */
    @Bean(name = "ds3DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.ds3")
    public DataSource ds3DataSource() {
        return DataSourceBuilder.create().build();
//        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * ds3 sql会话工厂
     */
    @Bean(name = "ds3SqlSessionFactory")
    public SqlSessionFactory ds3SqlSessionFactory(@Qualifier("ds3DataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/ds3/*.xml"));
        return bean.getObject();
    }

    /**
     * ds3 事物管理
     */
    @Bean(name = "ds3TransactionManager")
    public DataSourceTransactionManager ds3TransactionManager(@Qualifier("ds3DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "ds3SqlSessionTemplate")
    public SqlSessionTemplate ds3SqlSessionTemplate(@Qualifier("ds3SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

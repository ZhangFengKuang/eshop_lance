package com.roncoo.eshop.inventory;

import com.roncoo.eshop.inventory.listener.InitListener;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.security.auth.message.config.RegistrationListener;
import java.util.HashSet;
import java.util.Set;

@EnableAutoConfiguration
@SpringBootApplication //标识为spring 入口
@ComponentScan //开启组件扫描
@MapperScan("com.roncoo.eshop.inventory.mapper")//mapper包扫描
public class Application {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		//数据源
		return new DataSource();
	}

	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		//读取mybatis下的配置数
		//resolver.getResource("classpath:/mybatis/*.xml");
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
		//返回一个sqlsession 可以操作数据库
		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		//根据给定数据源，创建一个数据源事务处理器实例
		return new DataSourceTransactionManager(dataSource());
	}

	/**
	 * redis Cluster的集群master节点
	 */
	@Bean
	public JedisCluster jedisCluster() {
		//redisCluster 地址集
		Set<HostAndPort> jedisClusterNodes = new HashSet<>();
		jedisClusterNodes.add(new HostAndPort("192.168.0.107",7001));
		jedisClusterNodes.add(new HostAndPort("192.168.0.109",7007));
		jedisClusterNodes.add(new HostAndPort("192.168.0.108",7003));
		JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
		return jedisCluster;
	}

	/**
	 * 注册监听器
	 */
	@Bean
	public ServletListenerRegistrationBean servletListenerRegistrationBean() {
		ServletListenerRegistrationBean listenerRegistrationBean = new ServletListenerRegistrationBean();
		listenerRegistrationBean.setListener(new InitListener());
		return listenerRegistrationBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

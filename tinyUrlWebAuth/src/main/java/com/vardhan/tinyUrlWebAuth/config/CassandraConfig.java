package com.vardhan.tinyUrlWebAuth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.oss.driver.api.core.CqlSession;
import com.vardhan.tinyUrlWebAuth.dao.LoginDAO;
import com.vardhan.tinyUrlWebAuth.dao.TinyUrlDAO;
import com.vardhan.tinyUrlWebAuth.dao.impl.LoginDAOImpl;
import com.vardhan.tinyUrlWebAuth.dao.impl.TinyUrlDAOImpl;
import com.vardhan.tinyUrlWebAuth.service.LoginService;
import com.vardhan.tinyUrlWebAuth.service.TinyUrlService;
import com.vardhan.tinyUrlWebAuth.service.impl.LoginServiceImpl;
import com.vardhan.tinyUrlWebAuth.service.impl.TinyUrlServiceImpl;

@Configuration
@EnableCassandraRepositories(basePackages = { "org.springframework.data.cassandra.*" })
public class CassandraConfig {

	@Value("${spring.data.cassandra.contact-points}")
	String contactPoint;

	@Value("${spring.data.cassandra.keyspace}")
	String keySpace;

	@Value("${spring.data.cassandra.username}")
	String userName;

	@Value("${spring.data.cassandra.password}")
	String password;

	@Value("${tiny.datacenter}")
	String dataCenter;

	@Bean
	public CqlSessionFactoryBean session() {

		CqlSessionFactoryBean session = new CqlSessionFactoryBean();
		session.setContactPoints("127.0.0.1");
		session.setKeyspaceName("tiny");
		session.setUsername("admin");
		session.setPassword("admin@123");
		session.setLocalDatacenter("datacenter1");

		return session;
	}

	@Bean
	public SessionFactoryFactoryBean sessionFactory(CqlSession session, CassandraConverter converter) {

		SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
		sessionFactory.setSession(session);
		sessionFactory.setConverter(converter);
		sessionFactory.setSchemaAction(SchemaAction.NONE);

		return sessionFactory;
	}

	@Bean
	public CassandraMappingContext mappingContext(CqlSession cqlSession) {

		CassandraMappingContext mappingContext = new CassandraMappingContext();
		mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cqlSession));

		return mappingContext;
	}

	@Bean
	public CassandraConverter converter(CassandraMappingContext mappingContext) {
		return new MappingCassandraConverter(mappingContext);
	}

	@Bean
	public CassandraOperations cassandraTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
		return new CassandraTemplate(sessionFactory, converter);
	}

	@Bean
	public LoginDAO loginDao() {
		return new LoginDAOImpl();
	}

	@Bean
	public TinyUrlService tinyUrlService() {
		return new TinyUrlServiceImpl();

	}

	@Bean
	public TinyUrlDAO tinyUrlDAO() {
		return new TinyUrlDAOImpl();
	}

}

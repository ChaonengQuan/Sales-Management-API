package com.chaonengquan.util;

import com.github.fluent.hibernate.cfg.scanner.EntityScanner;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/*- define following environment variable before run
-Ddatabase.driver=org.postgresql.Driver
-Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect
-Ddatabase.url=jdbc:postgresql://localhost:5431/myDatabase
-Ddatabase.user=chaonengquan
-Ddatabase.password=Quanchaoneng.523
*/

public class HibernateUtil {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    //Singleton design pattern, eager initialization
    private static final HibernateUtil hibernateUtil = new HibernateUtil();

    private HibernateUtil() {
    }

    public static HibernateUtil getHibernateUtil() {
        return hibernateUtil;
    }

    //Singleton Design Pattern, static block because we want exception handling
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = initSessionFactory();
        } catch (Exception e) {
            logger.error("Exception while initializing hibernate util.. ");
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static SessionFactory initSessionFactory() {
//        String dbDriver = "org.postgresql.Driver";
//        String dbDialect = "org.hibernate.dialect.PostgreSQL9Dialect";
//        String dbUrl = "jdbc:postgresql://localhost:5431/myDatabase";
//        String dbUser = "chaonengquan";
//        String dbPassword = "Quanchaoneng.523";
        String dbDriver = System.getProperty("database.driver");
        String dbDialect = System.getProperty("database.dialect");
        String dbUrl = System.getProperty("database.url");
        String dbUser = System.getProperty("database.user");
        String dbPassword = System.getProperty("database.password");

        String[] modelPackages = {"com.chaonengquan.model"};    //array of packages

        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, dbDriver);
        settings.put(Environment.DIALECT, dbDialect);
        settings.put(Environment.URL, dbUrl);
        settings.put(Environment.USER, dbUser);
        settings.put(Environment.PASS, dbPassword);
        settings.put(Environment.SHOW_SQL, "true");         //show sql statements
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "validate");
        configuration.setProperties(settings);

        EntityScanner.scanPackages(modelPackages).addTo(configuration);        //provide sessionFactory mapping information
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        ServiceRegistry serviceRegistry = registryBuilder.applySettings(configuration.getProperties()).build();
        SessionFactory localSessionFactory = configuration.buildSessionFactory(serviceRegistry);

        return localSessionFactory;
    }

    public static Session getSession() throws HibernateException {
        Session retSession = null;
        try {
            retSession = sessionFactory.openSession();
        } catch (Throwable t) {
            logger.error("Exception while getting session.. ");
            t.printStackTrace();
        }
        if (retSession == null) {
            logger.error("session is discovered null");
        }
        return retSession;
    }


    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        SessionFactory sessionFactory1 = HibernateUtil.getSessionFactory();
        logger.info("Success generate sessionFactory ={}", sessionFactory.toString());
        logger.info("Success generate sessionFactory, hashcode={}", sessionFactory.hashCode());
        Session session = sessionFactory.openSession();
//        Session session1 = sessionFactory.openSession();
        logger.info("The end");
        session.close();
    }


}

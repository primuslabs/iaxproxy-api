/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iaxproxy.provisioning;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Appender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author mgamble
 */
public class RestfulInterface extends Application {

    private static final Logger logger = Logger.getLogger(RestfulInterface.class);
    static JedisPool jedisPool;

    public static JedisPool getJedisPool() {
        return jedisPool;
    }

    public static Logger getLogInterface() {
        return logger;
    }

    @Override
    public void stop() {
        logger.info("Shutting down......");
        jedisPool.destroy();
    }

    public synchronized Restlet createInboundRoot() {

        Properties properties = new Properties();
        String tomcatPath = System.getProperty("catalina.home", "/opt/tomcat");
        String propertiesPath = tomcatPath + "/conf/IAXProxyAPI.properties";
        boolean propertiesLoaded = false;
        try {
            FileInputStream fis;

            fis = new FileInputStream(propertiesPath);
            properties.load(fis);
            fis.close();
            propertiesLoaded = true;
        } catch (Exception ex) {
            properties = new Properties();
        }
        String redisHost = properties.getProperty("redisHost", "127.0.0.1");
        String service_log_path = properties.getProperty("service_log_path", "logs/IAXProxyAPI.log");
        String service_log_format = properties.getProperty("service_log_format", "%d{ISO8601} [%-5p] %m%n");
        String service_log_detail = properties.getProperty("service_log_detail", "DEBUG");
        String serviceLogPath = service_log_path.matches("^/.*$") ? service_log_path : tomcatPath + "/" + service_log_path;

        Enumeration<Appender> appenders = logger.getAllAppenders();
        if (!appenders.hasMoreElements()) {
            try {
                logger.addAppender(new DailyRollingFileAppender(new PatternLayout(service_log_format), serviceLogPath, "'.'yyyy-MM-dd"));
                if (service_log_detail.compareTo("DEBUG") == 0) {
                    logger.setLevel(Level.ALL);
                } else if (service_log_detail.compareTo("INFO") == 0) {
                    logger.setLevel(Level.INFO);
                }
                logger.info(String.format("Init: Logger using pattern \"%s\", log file \"%s\", detail level of %s", service_log_format, serviceLogPath, service_log_detail.compareTo("DEBUG") == 0 ? "DEBUG" : "INFO"));
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(RestfulInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        } else {
            logger.info("Init: Logger already setup");
        }
        //	String loggerProperties = servletContext.getInitParameter(ServletContextListener.LOGGER_CTX_PARAM);


        logger.info("Setting up JEdisPool to " + redisHost + ".....");
        try {
            jedisPool = new JedisPool(new JedisPoolConfig(), redisHost);
        } catch (Exception ex) {
            logger.fatal("Could not create jedisPool: " + ex);
        }
        logger.info("Version " + Version.getSpecification() + " (" + Version.getImplementation() + ") Ready to rock.");
        Router router = new Router(getContext());
        router.attach("/v1/user/{username}", UserResource.class);

        return router;
    }
}

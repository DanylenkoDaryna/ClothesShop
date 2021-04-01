package ua.nure.danylenko.epam.web.listener;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * The ContextListener class implements ServletContextListener provides initialisation of logger, servlet context and
 * command container
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class ContextListener implements ServletContextListener {

    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    public void contextDestroyed(ServletContextEvent event) {
        WEB_LOG.info("Servlet context destruction starts");
        // no op
        WEB_LOG.info("Servlet context destruction finished");
    }

    public void contextInitialized(ServletContextEvent event) {
        WEB_LOG.info("Servlet context initialization starts");

        ServletContext servletContext = event.getServletContext();
        initLog4J(servletContext);
        initCommandContainer();

        WEB_LOG.info("Servlet context initialization finished");
    }

    /**
     * Initializes log4j framework.
     *
     * @param servletContext
     */
    private void initLog4J(ServletContext servletContext) {
        WEB_LOG.info("Log4J initialization started");
        try {
            DOMConfigurator.configure(
                    servletContext.getRealPath("log4j2.xml"));
            WEB_LOG.info("Log4j has been initialized");
        } catch (Exception ex) {
            WEB_LOG.error("Cannot configure Log4j");
            ex.printStackTrace();
        }
        WEB_LOG.info("Log4J initialization finished");
    }

    /**
     * Initializes CommandContainer.
     *
     */
    private void initCommandContainer() {

        // initialize commands container
        // just load class to JVM
        try {
            Class.forName("ua.nure.danylenko.epam.web.command.CommandContainer");
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Cannot initialize Command Container");
        }
    }
}
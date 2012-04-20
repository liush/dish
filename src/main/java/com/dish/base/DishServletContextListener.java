package com.dish.base;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-3-29
 * Time: 下午8:11
 * To change this template use File | Settings | File Templates.
 */
public class DishServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("resources", servletContext.getContextPath() + "/resources");
        servletContext.setAttribute("contextPath", servletContext.getContextPath());
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

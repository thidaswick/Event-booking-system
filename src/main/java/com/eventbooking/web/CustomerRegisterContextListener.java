package com.eventbooking.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;

/**
 * Ensures {@code /register} is mapped even when deployment metadata (web.xml / annotations)
 * is not picked up consistently by embedded Jetty during {@code mvn jetty:run}.
 * Declared in {@code web.xml} (not {@code @WebListener}) so it always runs.
 */
public class CustomerRegisterContextListener implements ServletContextListener {

    private static final String NAME = "CustomerRegisterServlet";
    private static final String PATH = "/register";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        ServletRegistration existing = ctx.getServletRegistration(NAME);
        if (existing != null) {
            return;
        }
        ServletRegistration.Dynamic reg = ctx.addServlet(NAME, CustomerRegisterServlet.class);
        if (reg == null) {
            return;
        }
        reg.addMapping(PATH);
    }
}

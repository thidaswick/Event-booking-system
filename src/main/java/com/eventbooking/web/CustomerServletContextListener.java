package com.eventbooking.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;

/**
 * Ensures customer list/edit/delete URLs work under embedded Jetty ({@code mvn jetty:run}).
 */
public class CustomerServletContextListener implements ServletContextListener {

    private record Mapping(String name, Class<? extends jakarta.servlet.http.HttpServlet> servlet, String path) {}

    private static final Mapping[] MAPPINGS = {
            new Mapping("CustomerListServlet", CustomerListServlet.class, "/customers/list"),
            new Mapping("CustomerEditServlet", CustomerEditServlet.class, "/customers/edit"),
            new Mapping("CustomerDeleteServlet", CustomerDeleteServlet.class, "/customers/delete"),
    };

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        for (Mapping m : MAPPINGS) {
            if (ctx.getServletRegistration(m.name()) != null) {
                continue;
            }
            ServletRegistration.Dynamic reg = ctx.addServlet(m.name(), m.servlet());
            if (reg != null) {
                reg.addMapping(m.path());
            }
        }
    }
}

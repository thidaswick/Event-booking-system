package com.eventbooking.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;

public class CustomerAuthContextListener implements ServletContextListener {

    private record Mapping(String name, Class<? extends jakarta.servlet.http.HttpServlet> servlet, String path) {}

    private static final Mapping[] MAPPINGS = {
            new Mapping("CustomerLoginServlet", CustomerLoginServlet.class, "/login"),
            new Mapping("CustomerLogoutServlet", CustomerLogoutServlet.class, "/logout"),
            new Mapping("CustomerAccountServlet", CustomerAccountServlet.class, "/account"),
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

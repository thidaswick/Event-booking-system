package com.eventbooking.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;

/**
 * Ensures admin package CRUD URLs are mapped when {@code @WebServlet} metadata is not picked up by Jetty.
 */
public class PackageServletContextListener implements ServletContextListener {

    private record Mapping(String name, Class<? extends jakarta.servlet.http.HttpServlet> servlet, String path) {}

    private static final Mapping[] MAPPINGS = {
            new Mapping("AdminPackageListServlet", AdminPackageListServlet.class, "/admin/packages"),
            new Mapping("AdminPackageCreateServlet", AdminPackageCreateServlet.class, "/admin/packages/create"),
            new Mapping("AdminPackageEditServlet", AdminPackageEditServlet.class, "/admin/packages/edit"),
            new Mapping("AdminPackageDeleteServlet", AdminPackageDeleteServlet.class, "/admin/packages/delete"),
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

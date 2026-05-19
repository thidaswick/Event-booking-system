package com.eventbooking.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;

/**
 * Ensures contact CRUD URLs are mapped when {@code @WebServlet} metadata is not picked up
 * by embedded Jetty during {@code mvn jetty:run}. Declared in {@code web.xml}.
 */
public class ContactServletContextListener implements ServletContextListener {

    private record Mapping(String name, Class<? extends jakarta.servlet.http.HttpServlet> servlet, String path) {}

    private static final Mapping[] MAPPINGS = {
            new Mapping("ContactListServlet", ContactListServlet.class, "/contacts/list"),
            new Mapping("ContactCreateServlet", ContactCreateServlet.class, "/contacts/create"),
            new Mapping("ContactEditServlet", ContactEditServlet.class, "/contacts/edit"),
            new Mapping("ContactDeleteServlet", ContactDeleteServlet.class, "/contacts/delete"),
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

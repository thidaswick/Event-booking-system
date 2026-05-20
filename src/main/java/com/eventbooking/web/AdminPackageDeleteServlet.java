package com.eventbooking.web;

import com.eventbooking.service.PackageService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminPackageDeleteServlet", urlPatterns = "/admin/packages/delete")
public class AdminPackageDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (!AdminSession.requireAdmin(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("packageId");
        PackageService service = AppContext.packageService(getServletContext());
        try {
            service.deleteById(id);
            response.sendRedirect(AdminSession.packagesRedirect(request, "deleted"));
        } catch (Exception ex) {
            response.sendRedirect(AdminSession.packagesRedirect(request, "deletefailed"));
        }
    }
}

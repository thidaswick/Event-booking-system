package com.eventbooking.web;

import com.eventbooking.model.StudioPackage;
import com.eventbooking.service.PackageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminPackageListServlet", urlPatterns = "/admin/packages")
public class AdminPackageListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AdminSession.requireAdmin(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        PackageService service = AppContext.packageService(getServletContext());
        List<StudioPackage> packages = service.listAll();
        request.setAttribute("packages", packages);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/packages-list.jsp").forward(request, response);
    }
}

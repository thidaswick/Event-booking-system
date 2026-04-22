package com.eventbooking.web;

import com.eventbooking.model.MediaPackage;
import com.eventbooking.service.PackageCatalog;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Shows available packages using a polymorphic list of MediaPackage objects.
 */
@WebServlet(name = "PackagesServlet", urlPatterns = "/packages")
public class PackagesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<MediaPackage> packages = PackageCatalog.allPackages();
        request.setAttribute("packages", packages);
        request.getRequestDispatcher("/WEB-INF/jsp/packages.jsp").forward(request, response);
    }
}

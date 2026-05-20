package com.eventbooking.web;

import com.eventbooking.model.StudioPackage;
import com.eventbooking.service.PackageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "AdminPackageEditServlet", urlPatterns = "/admin/packages/edit")
public class AdminPackageEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AdminSession.requireAdmin(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("packageId");
        PackageService service = AppContext.packageService(getServletContext());
        Optional<StudioPackage> found = service.findById(id == null ? "" : id);
        if (found.isEmpty()) {
            response.sendRedirect(AdminSession.packagesRedirect(request, "notfound"));
            return;
        }
        request.setAttribute("formTitle", "Edit package");
        request.setAttribute("pkg", found.get());
        request.setAttribute("formAction", request.getContextPath() + "/admin/packages/edit");
        request.getRequestDispatcher("/WEB-INF/jsp/admin/package-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AdminSession.requireAdmin(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("packageId");
        if (id == null || id.isBlank()) {
            response.sendRedirect(AdminSession.packagesRedirect(request, "notfound"));
            return;
        }
        StudioPackage updated = AdminPackageCreateServlet.readFromRequest(request);
        updated.setPackageId(id);
        String validationError = AdminPackageCreateServlet.validate(updated);
        if (validationError != null) {
            forwardForm(request, response, validationError, updated);
            return;
        }
        PackageService service = AppContext.packageService(getServletContext());
        try {
            service.update(updated);
            response.sendRedirect(AdminSession.packagesRedirect(request, "updated"));
        } catch (IOException | IllegalArgumentException ex) {
            forwardForm(request, response, "Could not update: " + ex.getMessage(), updated);
        }
    }

    private void forwardForm(HttpServletRequest request, HttpServletResponse response,
                             String error, StudioPackage pkg)
            throws ServletException, IOException {
        request.setAttribute("error", error);
        request.setAttribute("formTitle", "Edit package");
        request.setAttribute("pkg", pkg);
        request.setAttribute("formAction", request.getContextPath() + "/admin/packages/edit");
        request.getRequestDispatcher("/WEB-INF/jsp/admin/package-form.jsp").forward(request, response);
    }
}

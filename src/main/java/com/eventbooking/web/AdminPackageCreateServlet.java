package com.eventbooking.web;

import com.eventbooking.model.StudioPackage;
import com.eventbooking.service.PackageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminPackageCreateServlet", urlPatterns = "/admin/packages/create")
public class AdminPackageCreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AdminSession.requireAdmin(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("formTitle", "Add package");
        request.setAttribute("pkg", new StudioPackage());
        request.setAttribute("formAction", request.getContextPath() + "/admin/packages/create");
        request.getRequestDispatcher("/WEB-INF/jsp/admin/package-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AdminSession.requireAdmin(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        PackageService service = AppContext.packageService(getServletContext());
        StudioPackage input = readFromRequest(request);
        String validationError = validate(input);
        if (validationError != null) {
            showFormWithError(request, response, validationError, input, "Add package");
            return;
        }
        try {
            service.createNew(input);
            response.sendRedirect(AdminSession.packagesRedirect(request, "created"));
        } catch (IOException ex) {
            showFormWithError(request, response, "Could not save package: " + ex.getMessage(), input, "Add package");
        }
    }

    static StudioPackage readFromRequest(HttpServletRequest request) {
        StudioPackage p = new StudioPackage();
        p.setCode(request.getParameter("code"));
        p.setTitle(request.getParameter("title"));
        p.setPriceLabel(request.getParameter("priceLabel"));
        p.setSummary(request.getParameter("summary"));
        p.setDeliverables(request.getParameter("deliverables"));
        return p;
    }

    static String validate(StudioPackage p) {
        if (p.getCode() == null || p.getCode().isBlank()) {
            return "Please enter a package code (e.g. PH-BASIC).";
        }
        if (p.getTitle() == null || p.getTitle().isBlank()) {
            return "Please enter a title.";
        }
        if (p.getPriceLabel() == null || p.getPriceLabel().isBlank()) {
            return "Please enter a price label.";
        }
        if (p.getSummary() == null || p.getSummary().isBlank()) {
            return "Please enter a summary.";
        }
        if (p.getDeliverables() == null || p.getDeliverables().isBlank()) {
            return "Please enter deliverables.";
        }
        return null;
    }

    private void showFormWithError(HttpServletRequest request, HttpServletResponse response,
                                   String error, StudioPackage pkg, String formTitle)
            throws ServletException, IOException {
        request.setAttribute("error", error);
        request.setAttribute("formTitle", formTitle);
        request.setAttribute("pkg", pkg);
        request.setAttribute("formAction", request.getContextPath() + "/admin/packages/create");
        request.getRequestDispatcher("/WEB-INF/jsp/admin/package-form.jsp").forward(request, response);
    }
}

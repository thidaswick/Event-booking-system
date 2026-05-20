package com.eventbooking.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public final class AdminSession {

    public static final String ATTR_ADMIN = "adminLoggedIn";

    private AdminSession() {
    }

    public static void login(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute(ATTR_ADMIN, Boolean.TRUE);
    }

    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(ATTR_ADMIN);
        }
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        return Boolean.TRUE.equals(session.getAttribute(ATTR_ADMIN));
    }

    public static boolean requireAdmin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (isLoggedIn(request)) {
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/admin/login");
        return false;
    }

    public static String customersRedirect(HttpServletRequest request, String msg) {
        return request.getContextPath() + "/admin/customers?msg=" + msg;
    }

    public static String packagesRedirect(HttpServletRequest request, String msg) {
        return request.getContextPath() + "/admin/packages?msg=" + msg;
    }
}

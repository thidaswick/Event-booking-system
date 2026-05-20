package com.eventbooking.web;

import com.eventbooking.model.Contact;
import com.eventbooking.service.ContactService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ContactListServlet", urlPatterns = "/contacts/list")
public class ContactListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ContactService service = AppContext.contactService(getServletContext());

        String q = request.getParameter("q");
        List<Contact> contacts;
        boolean searchActive = q != null && !q.isBlank();
        if (searchActive) {
            String t = q.trim();
            if (t.matches("(?i)CT\\d+")) {
                contacts = service.search(t, "");
            } else {
                contacts = service.search("", t);
            }
        } else {
            contacts = service.listAll();
        }

        request.setAttribute("contacts", contacts);
        request.setAttribute("q", q == null ? "" : q);
        request.setAttribute("searchActive", searchActive);
        request.setAttribute("adminArea", AdminSession.isLoggedIn(request));
        request.getRequestDispatcher("/WEB-INF/jsp/contacts-list.jsp").forward(request, response);
    }
}

package com.eventbooking.web;

import com.eventbooking.model.Contact;
import com.eventbooking.service.ContactService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "ContactEditServlet", urlPatterns = "/contacts/edit")
public class ContactEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("contactId");
        ContactService service = AppContext.contactService(getServletContext());
        Optional<Contact> found = service.findById(id == null ? "" : id);
        if (found.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/contacts/list?msg=notfound");
            return;
        }
        request.setAttribute("formTitle", "Edit contact message");
        request.setAttribute("contact", found.get());
        request.setAttribute("formAction", request.getContextPath() + "/contacts/edit");
        request.getRequestDispatcher("/WEB-INF/jsp/contact-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("contactId");
        if (id == null || id.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/contacts/list?msg=notfound");
            return;
        }
        ContactService service = AppContext.contactService(getServletContext());
        Contact updated = readContact(request, id);
        String validationError = ContactCreateServlet.validate(updated);
        if (validationError != null) {
            request.setAttribute("error", validationError);
            request.setAttribute("formTitle", "Edit contact message");
            request.setAttribute("contact", updated);
            request.setAttribute("formAction", request.getContextPath() + "/contacts/edit");
            request.getRequestDispatcher("/WEB-INF/jsp/contact-form.jsp").forward(request, response);
            return;
        }
        try {
            service.update(updated);
            response.sendRedirect(request.getContextPath() + "/contacts/list?msg=updated");
        } catch (IOException | IllegalArgumentException ex) {
            request.setAttribute("error", "Could not update: " + ex.getMessage());
            request.setAttribute("formTitle", "Edit contact message");
            request.setAttribute("contact", updated);
            request.setAttribute("formAction", request.getContextPath() + "/contacts/edit");
            request.getRequestDispatcher("/WEB-INF/jsp/contact-form.jsp").forward(request, response);
        }
    }

    private static Contact readContact(HttpServletRequest request, String contactId) {
        Contact c = ContactCreateServlet.readContactFromRequest(request);
        c.setContactId(contactId);
        c.setCreatedAt(request.getParameter("createdAt"));
        return c;
    }
}

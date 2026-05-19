package com.eventbooking.web;

import com.eventbooking.model.Contact;
import com.eventbooking.service.ContactService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ContactCreateServlet", urlPatterns = "/contacts/create")
public class ContactCreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("formTitle", "Contact us");
        request.setAttribute("contact", new Contact());
        request.setAttribute("formAction", request.getContextPath() + "/contacts/create");
        request.getRequestDispatcher("/WEB-INF/jsp/contact-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ContactService service = AppContext.contactService(getServletContext());
        Contact input = readContactFromRequest(request);
        String validationError = validate(input);
        if (validationError != null) {
            showFormWithError(request, response, validationError, input);
            return;
        }
        try {
            Contact saved = service.createNew(input);
            response.sendRedirect(request.getContextPath()
                    + "/contacts/list?msg=created&id=" + saved.getContactId());
        } catch (IOException ex) {
            showFormWithError(request, response, "Could not save message: " + ex.getMessage(), input);
        }
    }

    private void showFormWithError(HttpServletRequest request, HttpServletResponse response,
                                  String error, Contact contact)
            throws ServletException, IOException {
        request.setAttribute("error", error);
        request.setAttribute("formTitle", "Contact us");
        request.setAttribute("contact", contact);
        request.setAttribute("formAction", request.getContextPath() + "/contacts/create");
        request.getRequestDispatcher("/WEB-INF/jsp/contact-form.jsp").forward(request, response);
    }

    static Contact readContactFromRequest(HttpServletRequest request) {
        Contact c = new Contact();
        c.setFullName(request.getParameter("fullName"));
        c.setEmail(request.getParameter("email"));
        c.setPhone(request.getParameter("phone"));
        c.setSubject(request.getParameter("subject"));
        c.setMessage(request.getParameter("message"));
        c.setStatus(request.getParameter("status"));
        return c;
    }

    static String validate(Contact c) {
        if (c.getFullName() == null || c.getFullName().isBlank()) {
            return "Please enter your name.";
        }
        if (c.getEmail() == null || c.getEmail().isBlank()) {
            return "Please enter your email.";
        }
        if (!c.getEmail().matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            return "Please enter a valid email address.";
        }
        if (c.getSubject() == null || c.getSubject().isBlank()) {
            return "Please enter a subject.";
        }
        if (c.getMessage() == null || c.getMessage().isBlank()) {
            return "Please enter your message.";
        }
        return null;
    }
}

package com.eventbooking.web;

import com.eventbooking.service.ContactService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ContactDeleteServlet", urlPatterns = "/contacts/delete")
public class ContactDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("contactId");
        ContactService service = AppContext.contactService(getServletContext());
        try {
            service.deleteById(id);
            response.sendRedirect(request.getContextPath() + "/contacts/list?msg=deleted");
        } catch (Exception ex) {
            response.sendRedirect(request.getContextPath() + "/contacts/list?msg=deletefailed");
        }
    }
}

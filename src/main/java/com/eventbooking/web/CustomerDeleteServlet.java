package com.eventbooking.web;

import com.eventbooking.service.CustomerService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * DELETE: removes one customer row from {@code customers.txt}.
 */
@WebServlet(name = "CustomerDeleteServlet", urlPatterns = "/customers/delete")
public class CustomerDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("customerId");
        CustomerService service = AppContext.customerService(getServletContext());
        try {
            service.deleteById(id);
            response.sendRedirect(request.getContextPath() + "/customers/list?msg=deleted");
        } catch (Exception ex) {
            response.sendRedirect(request.getContextPath() + "/customers/list?msg=deletefailed");
        }
    }
}

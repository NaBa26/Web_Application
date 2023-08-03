package com.java.webapplication;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
// ... other imports
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ... other methods

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uname = request.getParameter("uname");
        String urpassword = request.getParameter("rpassword");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // Validate input
        if (uname == null || uname.isEmpty() || urpassword == null || urpassword.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()) {
            // Handle invalid input with a forward to the error page
            request.setAttribute("errorMessage", "Invalid input. Please fill all fields.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
            return; // Important: Stop further processing and return
        }

        // Check if passwords match
        if (!urpassword.equals(password)) {
            // Handle password mismatch with a forward to the error page
            request.setAttribute("errorMessage", "Passwords do not match. Please re-enter.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
            return; // Important: Stop further processing and return
        }

        
        Members member = new Members(uname, password, urpassword, email);
        RegisterDao r = new RegisterDao();
        
        boolean isEmailRegistered = r.isEmailRegistered(email);
        if (isEmailRegistered) {
            // Handle email already registered with a forward to the error page
            request.setAttribute("errorMessage", "This email address is already registered.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
            return; // Important: Stop further processing and return
        }
        
        boolean isInserted = false;
        try {
            isInserted = r.insert(member);
        } catch (Exception e) {
            // Handle other exceptions (e.g., database connection issues) with a forward to the error page
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
            return; // Important: Stop further processing and return
        }

        if (isInserted) {
            // Registration successful, redirect to success page
            response.sendRedirect("success.jsp");
        } else {
            // Handle insertion failure with a forward to the error page
            request.setAttribute("errorMessage", "Failed to register. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
            return; // Important: Stop further processing and return
        }
    }
}

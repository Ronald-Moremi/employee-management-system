package servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String empIdStr = request.getParameter("employee_id");
        String name = request.getParameter("name");
        String department = request.getParameter("department");
        String role = request.getParameter("role");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String errorMessage = null;

        // INPUT VALIDATION 
        if (!empIdStr.matches("\\d+")) {
            errorMessage = "Employee ID must be numeric.";
        } else if (!name.matches("[a-zA-Z ]+")) {
            errorMessage = "Full Name should contain letters only.";
        } else if (!department.matches("[a-zA-Z ]+")) {
            errorMessage = "Department should contain letters only.";
        } else if (!role.matches("[a-zA-Z ]+")) {
            errorMessage = "Role should contain letters only.";
        } else if (!phone.matches("0\\d{9}")) {
            errorMessage = "Phone number must start with 0 and contain 10 digits.";
        } else if (password == null || password.isEmpty()) {
            errorMessage = "Password is required. Please enter a valid password.";
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        int empId = Integer.parseInt(empIdStr);

        try (Connection conn = ConnectionProvider.getConnection()) {
            // Check for existing employee_id
            PreparedStatement checkIdStmt = conn.prepareStatement("SELECT * FROM Employee WHERE employee_id = ?");
            checkIdStmt.setInt(1, empId);
            ResultSet idResult = checkIdStmt.executeQuery();
            if (idResult.next()) {
                errorMessage = "The Employee ID you entered is already registered.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Check for existing email
            PreparedStatement checkEmailStmt = conn.prepareStatement("SELECT * FROM Employee WHERE email = ?");
            checkEmailStmt.setString(1, email);
            ResultSet emailResult = checkEmailStmt.executeQuery();
            if (emailResult.next()) {
                errorMessage = "The email address you provided is already in use.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Insert into Employee table
            PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO Employee (employee_id, name, department, role, phone, email) VALUES (?, ?, ?, ?, ?, ?)"
            );
            pstmt.setInt(1, empId);
            pstmt.setString(2, name);
            pstmt.setString(3, department);
            pstmt.setString(4, role);
            pstmt.setString(5, phone);
            pstmt.setString(6, email);
            pstmt.executeUpdate();

            // Insert into Login table
            try (Connection loginConn = ConnectionProvider.getLoginConnection()) {
                PreparedStatement loginpstmt = loginConn.prepareStatement(
                    "INSERT INTO Login (employee_id, password) VALUES (?, ?)"
                );
                loginpstmt.setInt(1, empId);
                loginpstmt.setString(2, password);
                loginpstmt.executeUpdate();
            }

            // Redirect to login page
            response.sendRedirect("login.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred during registration. Please try again later.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}


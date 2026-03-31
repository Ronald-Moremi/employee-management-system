package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String empIdStr = request.getParameter("employee_id");
        String password = request.getParameter("password");

        try {
            int empId = Integer.parseInt(empIdStr);

            try (Connection conn = ConnectionProvider.getLoginConnection()) {
                PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM Login WHERE employee_id = ? AND password = ?"
                );
                pstmt.setInt(1, empId);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    try (Connection empConn = ConnectionProvider.getConnection()) {
                        PreparedStatement emppstmt = empConn.prepareStatement(
                            "SELECT name FROM Employee WHERE employee_id = ?"
                        );
                        emppstmt.setInt(1, empId);
                        ResultSet empRs = emppstmt.executeQuery();

                        if (empRs.next()) {
                            String empName = empRs.getString("name");

                            HttpSession session = request.getSession();
                            session.setAttribute("employee_id", empIdStr);
                            session.setAttribute("employee_name", empName);

                            response.sendRedirect("dashboard.jsp");
                            return;
                        } else {
                            request.setAttribute("errorMessage", "Employee not found.");
                        }
                    }
                } else {
                    request.setAttribute("errorMessage", "Invalid credentials. Please try again.");
                }

                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Employee ID must be a number.");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Login failed: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}

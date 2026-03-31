<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalDateTime, java.time.format.DateTimeFormatter" %>
<%
    if ("true".equals(request.getParameter("logout"))) {
        session.invalidate();
        response.sendRedirect("login.jsp");
        return;
    }

    String employeeName = (String) session.getAttribute("employeeName");
    String department = (String) session.getAttribute("department");
    String role = (String) session.getAttribute("role");
    String email = (String) session.getAttribute("email");
    String phone = (String) session.getAttribute("phone");

    if (employeeName == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    LocalDateTime now = LocalDateTime.now();
    String dateTime = now.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy HH:mm"));
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body>
    <div class="dashboard-container">
        <a href="dashboard.jsp?logout=true" class="logout-btn">Logout</a>

        <h1>Welcome, <span class="employee-name"><%= employeeName %></span>!</h1>
        <p class="date-time"><%= dateTime %></p>
        <form name="register" action="register.jsp" method="POST">
            <div class="profile-info">
                <h2><%= employeeName %></h2>
                <p>Department: <%= department %></p>
                <p>Role: <%= role %></p>
                <p>Email: <%= email %></p>
                <p>Phone: <%= phone %></p>
            </div>
        <div class="motivational-quote">
            <blockquote>“It's the way we're wired.” — Belgium Campus ITversity</blockquote>
        </div>     
        </form> 
    </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div class="container">
    <div class="left-side"></div>
    <div class="right-side">
        <div class="login-box">
            <h1>Login</h1>
            <form action="LoginServlet" method="post">
                <input type="text" name="employee_id" placeholder="Employee ID" required><br>
                <input type="password" name="password" placeholder="Password" required><br>
                <input type="submit" value="Login">
            </form>
            <p class="error">
                <% 
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null) {
                %>
                    <span style="color: red;"><%= errorMessage %></span>
                <% } %>
            </p>
        </div>
    </div>
</div>
</body>
</html>



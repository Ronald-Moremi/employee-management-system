<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="css/register.css"/>
</head>
<body>
    <div class="register-wrapper">
        <div class="register-card">
            <h1>Create Account</h1>
            <form method="post" action="RegisterServlet">
                <label for="employee_id">Employee ID</label>
                <input type="text" id="employee_id" name="employee_id" placeholder="Enter Employee ID" required />

                <label for="name">Full Name</label>
                <input type="text" id="name" name="name" placeholder="Enter Full Name" required />

                <label for="department">Department</label>
                <input type="text" id="department" name="department" placeholder="Enter Department" required />

                <label for="role">Role</label>
                <input type="text" id="role" name="role" placeholder="Enter Role" required />

                <label for="phone">Phone Number</label>
                <input type="tel" id="phone" name="phone" placeholder="Enter Phone Number" required />

                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Enter Email" required />

                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Create Password" required />

                <div class="form-buttons">
                    <button type="submit" class="btn-submit">Register</button>
                    <button type="reset" class="btn-clear">Clear</button>
                </div>
            </form>

            <% String registerError = (String) request.getAttribute("errorMessage");
               if (registerError != null) {
            %>
                <p style="color: red;"><%= registerError %></p>
            <% } %>

            <p><a href="login.jsp">Already have an account? Login here</a></p>
        </div>
    </div>
</body>
</html>


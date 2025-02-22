<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Set Password</title>
    <script>
        function validateAndSubmit() {
            var password = document.getElementById("password").value.trim();
            var confirmPassword = document.getElementById("confirmPassword").value.trim();
            var errorMsg = document.getElementById("errorMsg");

            // Clear previous errors
            errorMsg.innerHTML = "";

            // Password matching validation
            if (password !== confirmPassword) {
                errorMsg.innerHTML = "Passwords do not match!";
                return false;
            }

            // Send password to backend API
            fetch("/auth/reset-password", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ password: password })
            })
                .then(response => response.json())
                .then(data => {
                    alert("Password set successfully!");
                    window.location.href = "/"; // Redirect after success
                })
                .catch(error => {
                    errorMsg.innerHTML = "Error setting password!";
                    console.error("Error:", error);
                });

            return false; // Prevent default form submission
        }
    </script>
</head>
<body>
<h2>Set Your Password</h2>
<form onsubmit="return validateAndSubmit();">
    <label for="password">Password:</label>
    <input type="password" id="password" required /><br><br>

    <label for="confirmPassword">Confirm Password:</label>
    <input type="password" id="confirmPassword" required /><br><br>

    <span id="errorMsg" style="color: red;"></span><br><br>

    <button type="submit">Submit</button>
</form>
</body>
</html>

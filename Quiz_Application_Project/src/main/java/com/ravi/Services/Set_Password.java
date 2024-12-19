package com.ravi.Services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ravi.Database.DatabaseHandlings;

/**
 * Servlet implementation class Set_Password
 */
@WebServlet("/Set_Password")
public class Set_Password extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String newPassword = request.getParameter("new_password");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Establish database connection
            conn = DatabaseHandlings.getConnection();
            if (conn == null) {
                throw new SQLException("Unable to establish database connection.");
            }

            // Check if the user exists
            String sql_user = "SELECT * FROM User WHERE email = ?";
            stmt = conn.prepareStatement(sql_user);
            stmt.setString(1, email);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                // User exists, update the password
                String sql_update = "UPDATE User SET password = ? WHERE email = ?";
                stmt = conn.prepareStatement(sql_update);
                stmt.setString(1, newPassword);
                stmt.setString(2, email);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    // Password update successful
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<h2>Password updated successfully. <a href='LoginForm.html'>Login</a></h2>");
                    out.println("<script>");
                    out.println("alert('Password updated successfully. You may login in now');");
                    out.println("window.location.href = 'LoginForm.html';</script>");
                } else {
                    throw new Exception("Failed to update the password. Please try again.");
                }
            } else {
                // Email does not exist
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('Email does not exist. Register if you are a new user.');window.location.href = 'ForgotPassword.html';</script>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error: Unable to set password due to a database issue.</h2>");
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

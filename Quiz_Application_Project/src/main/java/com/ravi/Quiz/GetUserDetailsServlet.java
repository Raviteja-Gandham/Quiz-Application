package com.ravi.Quiz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserDetailsServlet
 */
@WebServlet("/getUserDetails")
public class GetUserDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // Retrieve the session
        if (session != null && session.getAttribute("userId") != null) {
            int userId = (int) session.getAttribute("userId");

            // Fetch user details from the database
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_db", "username", "password")) {
                String query = "SELECT name, email FROM users WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");

                    // Respond with JSON data
                    response.setContentType("application/json");
                    response.getWriter().write("{\"name\":\"" + name + "\",\"email\":\"" + email + "\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}

package com.ravi.Quiz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ravi.Database.DatabaseHandlings;

@WebServlet("/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String feedback = request.getParameter("feedback");
        int userId = Integer.parseInt(request.getParameter("userId"));
        String quizName = request.getParameter("quizName");

        Connection conn = null;
        PreparedStatement psInsert = null;

        try {
            conn = DatabaseHandlings.getConnection();

            // Insert feedback into feedback table
            String insertQuery = "INSERT INTO quiz_feedback (user_id, quiz_name, feedback, feedback_date) VALUES (?, ?, ?, NOW())";
            psInsert = conn.prepareStatement(insertQuery);
            psInsert.setInt(1, userId);
            psInsert.setString(2, quizName);
            psInsert.setString(3, feedback);
            psInsert.executeUpdate();

            response.setContentType("text/html");
            response.getWriter().println("<h2>Thank you for your feedback!</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            try { if (psInsert != null) psInsert.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}

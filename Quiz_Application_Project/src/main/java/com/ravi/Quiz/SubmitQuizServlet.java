package com.ravi.Quiz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ravi.Database.DatabaseHandlings;

@WebServlet("/SubmitQuizServlet")
public class SubmitQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Validate session and user login
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("LoginForm.html");
            return;
        }

        int userId = (int) session.getAttribute("userId"); // Retrieve userId from session
        String quizName = request.getParameter("quizName");
        
        int marksObtained = Integer.parseInt(request.getParameter("marks_Obtained"));

        Connection conn = null;
        PreparedStatement psSelect = null;
        PreparedStatement psUpdateOrInsert = null;
        ResultSet rs = null;

        try {
            conn = DatabaseHandlings.getConnection();

            // Check if the user already attempted the quiz
            String selectQuery = "SELECT attempts FROM quiz_results1 WHERE user_id = ? AND quiz_name = ?";
            psSelect = conn.prepareStatement(selectQuery);
            psSelect.setInt(1, userId);
            psSelect.setString(2, quizName);
            rs = psSelect.executeQuery();
            int attempts = 1;

            if (rs.next()) {
                attempts = rs.getInt("attempts") + 1;
                String updateQuery = "UPDATE quiz_results1 SET marks_obtained = ?, attempts = ?, last_attempt = NOW() WHERE user_id = ? AND quiz_name = ?";
                psUpdateOrInsert = conn.prepareStatement(updateQuery);
                psUpdateOrInsert.setInt(1, marksObtained);
                psUpdateOrInsert.setInt(2, attempts);	// Record the current attempt or last attempt made
                psUpdateOrInsert.setInt(3, userId);
                psUpdateOrInsert.setString(4, quizName);
                
                
             // Insert a record into past quiz results
                String insertPastQuery = "INSERT INTO past_quiz_results (user_id, quiz_name, marks_obtained, attempts, last_attempt) VALUES (?, ?, ?, ?, NOW())";
                PreparedStatement psInsertPast = conn.prepareStatement(insertPastQuery);
                psInsertPast.setInt(1, userId);
                psInsertPast.setString(2, quizName);
                psInsertPast.setInt(3, marksObtained);
                psInsertPast.setInt(4, attempts); // Record the previous & current attempt
                psInsertPast.executeUpdate(); // Execute insert query
                //psInsertPast.close(); // Close the PreparedStatement
     
            } else {
            	// Insert a new record if no prior attempts
                String insertQuery = "INSERT INTO quiz_results1 (user_id, quiz_name, marks_obtained, attempts, last_attempt) VALUES (?, ?, ?, ?, NOW())";
                
                psUpdateOrInsert = conn.prepareStatement(insertQuery);
                
                psUpdateOrInsert.setInt(1, userId);
                psUpdateOrInsert.setString(2, quizName);
                psUpdateOrInsert.setInt(3, marksObtained);
                psUpdateOrInsert.setInt(4, attempts);
                psUpdateOrInsert.executeUpdate();
            }
            
            response.setContentType("text/html");
            response.getWriter().println("<h2>Quiz submitted successfully...</h2>");
            response.getWriter().println("<h2 style='color: green;'>You scored: " + marksObtained + " marks.</h2>");
            response.getWriter().println("<h2>This was your attempt number: " + attempts + "</h2>");

            //Feedback
            response.getWriter().println("<form action='FeedbackServlet' method='POST'>");
            response.getWriter().println("<label for='feedback'>We value your feedback! Please share your thoughts:</label><br>");
            response.getWriter().println("<textarea name='feedback' rows='5' cols='50' placeholder='Enter your feedback here'></textarea><br>");
            response.getWriter().println("<input type='hidden' name='userId' value='" + userId + "'>");
            response.getWriter().println("<input type='hidden' name='quizName' value='" + quizName + "'>");
            response.getWriter().println("<button type='submit'>Submit Feedback</button>");
            response.getWriter().println("</form>");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (psSelect != null) psSelect.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (psUpdateOrInsert != null) psUpdateOrInsert.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}


package com.ravi.Quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ravi.Database.DatabaseHandlings;

@WebServlet("/feedback")
public class UserFeedback extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Database connection
        
        String query = "SELECT * FROM quiz_feedback"; // Replace "feedback" with your actual table name
        
        try (Connection connection = DatabaseHandlings.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            // HTML structure for feedback table
            out.println("<table cellspacing='10' cellpadding='10' class='table table-hover'>");
            out.println("<hr>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Feedback_ID</th>");
            out.println("<th>User_Id</th>");
            out.println("<th>Quiz_Name</th>");
            out.println("<th>Feedback</th>");
            out.println("<th>Feedback_Date</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            
            // Dynamically generate table rows
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("feedback_id") + "</td>");
                out.println("<td>" + rs.getString("user_id") + "</td>");
                out.println("<td>" + rs.getString("quiz_name") + "</td>");
                out.println("<td>" + rs.getString("feedback") + "</td>");
                out.println("<td>" + rs.getString("feedback_date") + "</td>");
                out.println("</tr>");
            }
            
            out.println("</tbody>");
            out.println("</table>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error fetching feedback. Please try again later.</p>");
        }
    }
}

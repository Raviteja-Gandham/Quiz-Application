package com.ravi.dao;

/*
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

@WebServlet("/user")
public class UserCRUDServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try (Connection connection = DatabaseHandlings.getConnection()) {
            switch (action) {
                case "createAccount":
                    createAccount(request, response, connection);
                    break;
                case "readData":
                    readData(request, response, connection);
                    break;
                case "updateDetails":
                    updateDetails(request, response, connection);
                    break;
                case "deleteAccount":
                    deleteAccount(request, response, connection);
                    break;
                default:
                    response.getWriter().println("Invalid action!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createAccount(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String sql = "INSERT INTO Users (name, email, password_hash) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            int rowsInserted = statement.executeUpdate();
            response.getWriter().println(rowsInserted > 0 ? "Account created successfully!" : "Account creation failed!");
        }
    }

    private void readData(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        int userId = Integer.parseInt(request.getParameter("userId"));

        String sql = "SELECT * FROM Users WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            PrintWriter out = response.getWriter();
            if (resultSet.next()) {
                out.println("ID: " + resultSet.getInt("user_id"));
                out.println("Name: " + resultSet.getString("name"));
                out.println("Email: " + resultSet.getString("email"));
            } else {
                out.println("No user found!");
            }
        }
    }

    private void updateDetails(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String newName = request.getParameter("newName");
        String newEmail = request.getParameter("newEmail");

        String sql = "UPDATE Users SET name = ?, email = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newName);
            statement.setString(2, newEmail);
            statement.setInt(3, userId);
            int rowsUpdated = statement.executeUpdate();
            response.getWriter().println(rowsUpdated > 0 ? "Details updated successfully!" : "Update failed!");
        }
    }

    private void deleteAccount(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        int userId = Integer.parseInt(request.getParameter("userId"));

        String sql = "DELETE FROM Users WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            int rowsDeleted = statement.executeUpdate();
            response.getWriter().println(rowsDeleted > 0 ? "Account deleted successfully!" : "Deletion failed!");
        }
    }
}
*/

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

@WebServlet("/userTable")
public class UserCRUDServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try (Connection connection = DatabaseHandlings.getConnection()) {
        	response.setContentType("text/html"); // Set response content type to HTML
        	PrintWriter out = response.getWriter();
        	response.getWriter().write("<h2>");
        	
            switch (action) {
            
                case "createAccount":
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    String insertSQL = "INSERT INTO Users (name, email, password_hash) VALUES (?, ?, ?)";
                    try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                        ps.setString(1, name);
                        ps.setString(2, email);
                        ps.setString(3, password);
                        ps.executeUpdate();
                    }
                    response.getWriter().write("Account created successfully!");
                    break;

                case "readData":
                    int userId = Integer.parseInt(request.getParameter("userId"));
                    String selectSQL = "SELECT * FROM Users WHERE user_id = ?";
                    try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
                        ps.setInt(1, userId);
                        ResultSet rs = ps.executeQuery();
                        
                        // To print data in tabular format
                        out.println("<table cellspacing='10' cellpadding='10' class='table table-hover'>");
                        out.println("</thead>");
                        out.println("<tr>");
                        out.println("<th>User_ID</th>");
                        out.println("<th>Name</th>");
                        out.println("<th>email</th>");
                        out.println("<th>password</th>");
                        out.println("<tr>");
                        out.println("<tbody>");
                        
                        if (rs.next()) {
                        	out.println("<tr>");
                        	out.println("<td>" + rs.getInt("user_id") + "</td>");
                            out.println("<td>" + rs.getString("name") + "</td>");
                            out.println("<td>" + rs.getString("email") + "</td>");
                            out.println("<td>" + rs.getString("password_hash") + "</td>");
                            out.println("</tr>");
                        	
                        	//response.getWriter().write("Name: " + rs.getString("name") + ", Email: " + rs.getString("email"));
                        } else {
                            response.getWriter().write("User not found.");
                        }
                        out.println("</tbody>");
                        out.println("</table>");
                    }
                    break;

                case "updateDetails":
                    userId = Integer.parseInt(request.getParameter("userId"));
                    String newName = request.getParameter("newName");
                    String newEmail = request.getParameter("newEmail");
                    String updateSQL = "UPDATE Users SET name = ?, email = ? WHERE user_id = ?";
                    try (PreparedStatement ps = connection.prepareStatement(updateSQL)) {
                        ps.setString(1, newName);
                        ps.setString(2, newEmail);
                        ps.setInt(3, userId);
                        ps.executeUpdate();
                    }
                    response.getWriter().write("Details updated successfully!");
                    break;

                case "deleteAccount":
                    userId = Integer.parseInt(request.getParameter("userId"));
                    String deleteSQL = "DELETE FROM Users WHERE user_id = ?";
                    try (PreparedStatement ps = connection.prepareStatement(deleteSQL)) {
                        ps.setInt(1, userId);
                        ps.executeUpdate();
                    }
                    response.getWriter().write("Account deleted successfully!");
                    break;
                    
                case "resultData":
                    int userId1 = Integer.parseInt(request.getParameter("userId"));
                    String selectSQL1 = "SELECT * FROM quiz_results1 WHERE user_id = ?";
                    try (PreparedStatement ps = connection.prepareStatement(selectSQL1)) {
                        ps.setInt(1, userId1);
                        ResultSet rs = ps.executeQuery();
                        
                     // To print data in tabular format
                        out.println("<table cellspacing='10' cellpadding='10' class='table table-hover'>");
                        out.println("</thead>");
                        out.println("<tr>");
                        out.println("<th>Quiz_ID</th>");
                        out.println("<th>User_ID</th>");
                        out.println("<th>Quiz_Name</th>");
                        out.println("<th>Marks_Obtained</th>");
                        out.println("<th>Total_Attempt</th>");
                        out.println("<th>Last_Attempted</th>");
                        out.println("<tr>");
                        out.println("<tbody>");
                        
                        if (rs.next()) {
                            out.println("<tr>");
                        	out.println("<td>" + rs.getInt("id") + "</td>");
                        	out.println("<td>" + rs.getInt("user_id") + "</td>");
                            out.println("<td>" + rs.getString("quiz_name") + "</td>");
                            out.println("<td>" + rs.getString("marks_obtained") + "</td>");
                            out.println("<td>" + rs.getString("attempts") + "</td>");
                            out.println("<td>" + rs.getString("last_attempt") + "</td>");
                            out.println("</tr>");
                        	//response.getWriter().write("Quiz_Name: " + rs.getString("quiz_name") + ", Marks Obtained: " + rs.getString("marks_Obtained")+ ", Total No.of Attempts: " + rs.getString("attempts") + ", Last Attempt: " + rs.getString("last_attempt"));
                        } else {
                            response.getWriter().write("User not found.");
                        }
                        out.println("</tbody>");
                        out.println("</table>");
                    }
                    break;
            
                case "resultsData":
                    int userId2 = Integer.parseInt(request.getParameter("userId"));
                    String selectSQL2 = "SELECT * FROM past_quiz_results WHERE user_id = ?";
                    try (PreparedStatement ps = connection.prepareStatement(selectSQL2)) {
                        ps.setInt(1, userId2);
                        ResultSet rs = ps.executeQuery();
                        
                        boolean userFound = false;
                        
                        // To print in tabular format
                        out.println("<table cellspacing='10' cellpadding='10' class='table table-hover'>");
                        out.println("</thead>");
                        out.println("<tr>");
                        out.println("<th>Quiz_ID</th>");
                        out.println("<th>User_ID</th>");
                        out.println("<th>Quiz_Name</th>");
                        out.println("<th>Marks_Obtained</th>");
                        out.println("<th>Attempt</th>");
                        out.println("<th>Last_Attempted</th>");
                        out.println("<tr>");
                        out.println("<tbody>");
                        
                        while (rs.next()) {
                        	userFound = true;
                        	
                        	out.println("<tr>");
                        	out.println("<td>" + rs.getInt("id") + "</td>");
                        	out.println("<td>" + rs.getInt("user_id") + "</td>");
                            out.println("<td>" + rs.getString("quiz_name") + "</td>");
                            out.println("<td>" + rs.getString("marks_obtained") + "</td>");
                            out.println("<td>" + rs.getString("attempts") + "</td>");
                            out.println("<td>" + rs.getString("last_attempt") + "</td>");
                            out.println("</tr>");
                        	//response.getWriter().write("Quiz_Name: " + rs.getString("quiz_name") + ", Marks Obtained: " + rs.getString("marks_Obtained")+ ", No.of Attempts: " + rs.getString("attempts") + ", Last Attempts: " + rs.getString("last_attempt") +"\n");
                        	
                        } 
                        if(userFound) {
                        	response.getWriter();
                        } else {
                            response.getWriter().write("User not found.");
                    }
                     out.println("</tbody>");
                     out.println("</table>");
                }
                 break;    
                 
                case "resultUpdate":
                    userId = Integer.parseInt(request.getParameter("userId"));
                    int newMarks = Integer.parseInt(request.getParameter("newMarks"));
                    String quizName = request.getParameter("quizName");
                    String updateSQL1 = "UPDATE quiz_results1 SET marks_obtained = ?, quiz_name = ? WHERE user_id = ?";
                    try (PreparedStatement ps = connection.prepareStatement(updateSQL1)) {
                        ps.setInt(1, newMarks);
                        ps.setString(2, quizName);
                        ps.setInt(3, userId);
                        ps.executeUpdate();
                    }
                    response.getWriter().write("Details updated successfully!");
                    break;
                    
                case "deleteResult":
                    userId = Integer.parseInt(request.getParameter("userId"));
                    quizName = request.getParameter("quizName");
                    String deleteSQL1 = "DELETE FROM quiz_results1 WHERE user_id = ? AND quiz_name = ?";
                    try (PreparedStatement ps = connection.prepareStatement(deleteSQL1)) {
                        ps.setInt(1, userId);
                        ps.setString(2, quizName);
                        ps.executeUpdate();
                    }
                    response.getWriter().write("Result deleted successfully!");
                    break;
             }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred: " + e.getMessage());
        }
        response.getWriter().write("</h2>");
    }
}


package com.ravi.dao;

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

@WebServlet("/adminTable")
public class AdminCRUDServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try (Connection connection = DatabaseHandlings.getConnection()) {
            switch (action) {
               case "createAccount":
                    String name = request.getParameter("username");
                    String password = request.getParameter("password");
                    String insertSQL = "INSERT INTO Admin (username, password) VALUES (?, ?)";
                    try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                        ps.setString(1, name);
                        ps.setString(2, password);
                        ps.executeUpdate();
                    }
                    response.getWriter().write("Account created successfully!");
                    break;
               
                case "readData":
                    int userId = Integer.parseInt(request.getParameter("adminId"));
                    String selectSQL = "SELECT * FROM Admin WHERE admin_id = ?";
                    try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
                        ps.setInt(1, userId);
                        ResultSet rs = ps.executeQuery();
                        out.println("<table cellspacing='10' cellpadding='10' class='table table-hover'>");
                        out.println("</thead>");
                        out.println("<tr>");
                        out.println("<th>Admin_ID</th>");
                        out.println("<th>username</th>");
                        out.println("<th>password</th>");
                        out.println("<tr>");
                        out.println("<tbody>");
                        if (rs.next()) {
                        	out.println("<tr>");
                        	out.println("<td>" + rs.getInt("admin_id") + "</td>");
                            out.println("<td>" + rs.getString("username") + "</td>");
                            out.println("<td>" + rs.getString("password") + "</td>");
                            out.println("</tr>");
                        	//response.getWriter().write("Name: " + rs.getString("username") + ", Admin Id: " + rs.getString("admin_id"));
                        } else {
                            response.getWriter().write("Admin not found.");
                        }
                        out.println("</tbody>");
                        out.println("</table>");
                    }
                    break;

                case "updateDetails":
                    userId = Integer.parseInt(request.getParameter("adminId"));
                    String newName = request.getParameter("newName");
                    String updateSQL = "UPDATE Admin SET username = ? WHERE admin_id = ?";
                    try (PreparedStatement ps = connection.prepareStatement(updateSQL)) {
                        ps.setString(1, newName);
                        ps.setInt(2, userId);
                        ps.executeUpdate();
                    }
                    response.getWriter().write("Details updated successfully!");
                    break;

                case "deleteAccount":
                    userId = Integer.parseInt(request.getParameter("adminId"));
                    String deleteSQL = "DELETE FROM Admin WHERE admin_id = ?";
                    try (PreparedStatement ps = connection.prepareStatement(deleteSQL)) {
                        ps.setInt(1, userId);
                        ps.executeUpdate();
                    }
                    response.getWriter().write("Account deleted successfully!");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred: " + e.getMessage());
        }
    }
}


package com.ravi.Services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ravi.Database.DatabaseHandlings;

@WebServlet("/user_register")
public class Register extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("username");
		String password = request.getParameter("password");
		
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseHandlings.getConnection();
			if(conn == null) {
				throw new SQLException("Unable to establish database conncetion.");
			}
			
			//Prepae SQL statements
			stmt = conn.prepareStatement("Insert into Users(email, password_hash) values(?, ?)");
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			// Execute the insert statement
            int rowsInserted = stmt.executeUpdate();

            // Response to client
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            if (rowsInserted > 0) {
            	// out.println("<h2>Registration successful!</h2>");
                response.sendRedirect("LoginForm.html"); // To navigate to the login page
            } else {
                out.println("<h2>Registration failed, please try again.</h2>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error: Unable to register due to a database issue.</h2>");
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

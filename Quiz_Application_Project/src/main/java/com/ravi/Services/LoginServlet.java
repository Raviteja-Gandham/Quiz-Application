package com.ravi.Services;

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
import javax.servlet.http.HttpSession;

import com.ravi.Database.DatabaseHandlings;

@WebServlet("/admin_login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	String id = request.getParameter("admin_id");
    String password = request.getParameter("admin_password");
    
    String email = request.getParameter("email");
    String u_password = request.getParameter("user_password");
   
    try (Connection connection = DatabaseHandlings.getConnection()) {
        String sql_admin = "SELECT * FROM Admin WHERE admin_id = ? AND password = ?";
        String sql_user = "SELECT user_id,name FROM Users WHERE email = ? AND password_hash = ?";
        PreparedStatement statement1 = connection.prepareStatement(sql_admin);
        PreparedStatement statement2 = connection.prepareStatement(sql_user);
        statement1.setString(1, id);
        statement1.setString(2, password);
        
        statement2.setString(1, email);
        statement2.setString(2, u_password);
        
        ResultSet resultSet1 = statement1.executeQuery();
        ResultSet resultSet2 = statement2.executeQuery();
        
       if (resultSet1.next()) {
    	    request.getSession().setAttribute("admin", id);
    	   // response.sendRedirect("AdminCRUD.html"); // Redirect to admin dashboard
    	    response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Redirecting...</title></head>");
            out.println("<body>");
            out.println("<script type='text/javascript'>");
            out.println("alert('Admin login successful! Redirecting to home page...');");
            out.println("window.location.href = 'AdminHomepage.html';");
            out.println("</script>");
            out.println("</body>");
            out.println("</html>");
            out.close();
    	}

       else if (resultSet2.next()) {
    	    int userId = resultSet2.getInt("user_id");
    	    String name = resultSet2.getString("name"); // Fetch additional details if required
    	    HttpSession session = request.getSession();
    	    session.setAttribute("userId", userId); // Store in session
    	    session.setAttribute("name", name);    // Optional: Store name

    	    // Redirect to home page with an alert message
    	    response.setContentType("text/html");
    	    PrintWriter out = response.getWriter();
    	    out.println("<html>");
    	    out.println("<head><title>Redirecting...</title></head>");
    	    out.println("<body style='font-weight:bold;'>");
    	    out.println("<script type='text/javascript'>");
    	    out.println("alert('Login successful! Welcome, " + name + " ,Note Your ID: " + userId +"');");
    	    out.println("window.location.href = 'HomePage.html';");
    	    out.println("</script>");
    	    out.println("</body>");
    	    out.println("</html>");
    	    out.close();
    	} else {
    	    // Invalid credentials
    	    response.setContentType("text/html");
    	    PrintWriter out = response.getWriter();
    	    out.println("<script>");
    	    out.println("alert('Invalid credentials!');");
    	    out.println("window.location.href = 'LoginForm.html';");
    	    out.println("</script>");
    	}

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
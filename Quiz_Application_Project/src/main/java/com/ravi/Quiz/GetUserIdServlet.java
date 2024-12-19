package com.ravi.Quiz;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GetUserIdServlet
 */
@WebServlet("/GetUserIdServlet")
public class GetUserIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        HttpSession session = request.getSession(false);
	        
	        if (session == null || session.getAttribute("userId") == null) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            response.getWriter().write("{\"error\": \"User not logged in\"}");
	        } else {
	            int userId = (int) session.getAttribute("userId");
	            response.setContentType("application/json");
	            response.getWriter().write("{\"userId\": " + userId + "}");
	        }
	    }
	}

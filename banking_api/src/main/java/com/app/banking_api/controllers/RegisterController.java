package com.app.banking_api.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.Role;
import com.app.banking_api.models.User;
import com.app.banking_api.service.RegisterService;
import com.app.banking_api.service.impl.RegisterServiceImpl;

/**
 * Servlet implementation class RegisterController
 */
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		User newUser = new User();
		RequestDispatcher requestDispatcher=null;
		
		try {
			
			if ((int) session.getAttribute("role") != 1) {
				throw new BusinessException("Insufficient permissions");
			}
			
			newUser.setUsername(request.getParameter("userName"));
			newUser.setPassword(request.getParameter("password"));
			newUser.setFirstName(request.getParameter("fname"));
			newUser.setLastName(request.getParameter("lname"));
			newUser.setEmail(request.getParameter("email"));
			
			Role role = new Role();
			role.setRoleId(Integer.parseInt(request.getParameter("role").substring(0, 1)));
			role.setRole(request.getParameter("role").substring(2));
			newUser.setRole(role);
			
			RegisterService reg = new RegisterServiceImpl();
			
			if (reg.isValidUserCredentials(newUser)) {
				out.print("<center><span style='color:green;'>User created successfully</span></center>");
				out.print("<center><a href='admin.html'>Back to admin</a></center>");

				//requestDispatcher=request.getRequestDispatcher("login");
				//requestDispatcher.forward(request, response);
			}
		} catch (BusinessException e) {
			requestDispatcher=request.getRequestDispatcher("regform.html");
			requestDispatcher.include(request, response);
			out.print("<center><span style='color:red;'>"+e.getMessage()+"</span></center>");
		}

	}
	

}

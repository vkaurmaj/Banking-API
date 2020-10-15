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
import com.app.banking_api.models.User;
import com.app.banking_api.service.LoginService;
import com.app.banking_api.service.impl.LoginServiceImpl;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		User user=new User();
		user.setUsername(request.getParameter("userName"));
		user.setPassword(request.getParameter("password"));
		
		LoginService service = new LoginServiceImpl();
		RequestDispatcher requestDispatcher=null;
		HttpSession session = request.getSession();
;    //set the session at login.

		PrintWriter out=response.getWriter();
		try {
			int success = service.isValidUserCredentials(user);
			if(success != -1) {
				//success
				switch(success) {
					case 1:
						session.setAttribute("role", 1);
						requestDispatcher=request.getRequestDispatcher("admin.html");
						requestDispatcher.forward(request, response);
						break;
					case 2:
						session.setAttribute("role", 2);
						requestDispatcher=request.getRequestDispatcher("empl.html");
						requestDispatcher.forward(request, response);
						break;
					case 3:
						session.setAttribute("role", 3);
						requestDispatcher=request.getRequestDispatcher("premium.html");
						requestDispatcher.forward(request, response);
						break;
					case 4:
						session.setAttribute("role", 4);
						requestDispatcher=request.getRequestDispatcher("standard.html");
						requestDispatcher.forward(request, response);
						break;	
				}
			}
		} catch (BusinessException e) {
			//failure
			requestDispatcher=request.getRequestDispatcher("login.html");
			requestDispatcher.include(request, response);
			out.print("<center><span style='color:red;'>"+e.getMessage()+"</span></center>");
		}
		
	}

}

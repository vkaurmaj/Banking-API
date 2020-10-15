package com.app.banking_api.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.User;
import com.app.banking_api.service.UserService;
import com.app.banking_api.service.impl.UserServiceImpl;

/**
 * Servlet implementation class AdminEmplController
 */
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		RequestDispatcher dispatcher = null;
		
		String criteria = request.getParameter("searchAction");
		String param = request.getParameter("param");
		UserService service = new UserServiceImpl();
		switch(criteria) {
			case "all":
				try {
					dispatcher=request.getRequestDispatcher("admin.html");
					dispatcher.include(request, response);
					ArrayList<User> result = service.getAllUsers();
					out.print("<br><center><h3>Results</h3></center");
					for (User u : result) {
						out.print("<center><label>"+ u.toString() + "</label></center><br>");
					}
					
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					dispatcher=request.getRequestDispatcher("admin.html");
					dispatcher.include(request, response);
					out.print(e.getMessage());
				}
				break;
			
			case "id":
				try {
					dispatcher=request.getRequestDispatcher("admin.html");
					dispatcher.include(request, response);
					User result = service.getUserByID(Integer.parseInt(param));
					out.print("<br><center><h3>Result</h3></center");
					out.print("<center><label>"+ result.toString() + "</label></center><br>");
				} catch (NumberFormatException e) {
					dispatcher=request.getRequestDispatcher("admin.html");
					dispatcher.include(request, response);
					out.print("<center><label color='red'>Invalid input for field (must be an integer)</label></center>");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					dispatcher=request.getRequestDispatcher("admin.html");
					dispatcher.include(request, response);
					out.print(e.getMessage());			
				}
				break;
			default:
				dispatcher=request.getRequestDispatcher("admin.html");
				dispatcher.include(request, response);
				out.print("<center><label color='red'>Invalid field entry</label></center>");
		}
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

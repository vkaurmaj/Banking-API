package com.app.banking_api.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TransactionController
 */
public class TransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		//HttpSession session = request.getSession();
		RequestDispatcher requestDispatcher = null;
		String selection = request.getParameter("accountAction");
		switch(selection) {
			case "withdraw":
				requestDispatcher = request.getRequestDispatcher("wform.html");
				requestDispatcher.forward(request, response);
				break;
			case "deposit":
				requestDispatcher = request.getRequestDispatcher("dform.html");
				requestDispatcher.forward(request, response);
				break;
			case "transfer":
				requestDispatcher = request.getRequestDispatcher("tform.html");
				requestDispatcher.forward(request, response);
				break;
		}

		
	}

}

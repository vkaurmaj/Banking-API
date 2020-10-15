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
import com.app.banking_api.models.Account;
import com.app.banking_api.service.AccountService;
import com.app.banking_api.service.impl.AccountServiceImpl;

/**
 * Servlet implementation class AccountController
 */
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountController() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		RequestDispatcher dispatcher = null;
		
		String criteria = request.getParameter("searchAction");
		String param = request.getParameter("param");
		AccountService service = new AccountServiceImpl();
		switch(criteria) {
			case "all":
				try {
					dispatcher=request.getRequestDispatcher("admin.html");
					dispatcher.include(request, response);
					ArrayList<Account> result = service.getAccounts();
					out.print("<br><center><h3>Results</h3></center");
					for (Account u : result) {
						out.print("<center><label>"+u+"</label></center><br>");
					}
					
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					dispatcher.include(request, response);
					out.print("<center><label color='red'>"+e.getMessage()+"</label></center>");
				}
				break;
			
			case "id":
				try {
					dispatcher=request.getRequestDispatcher("admin.html");
					dispatcher.include(request, response);
					Account result = service.getAccountById(Integer.parseInt(param));
					System.out.println(result);
					out.print("<br><center><h3>Result</h3></center");
					out.print("<center><label>"+ result+ "</label></center><br>");
				} catch (NumberFormatException e) {
					out.print("<center><label color='red'>Invalid input for field (must be an integer)</label></center>");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					out.print("<center><label color='red'>"+e.getMessage()+"</label></center>");
				}
				break;
			
			case "user":
				try {
					dispatcher=request.getRequestDispatcher("admin.html");
					dispatcher.include(request, response);
					Account result = service.getAccountByUser(param);
					out.print("<br><center><h3>Result</h3></center");
					out.print("<center><label>"+ result + "</label></center><br>");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					out.print("<center><label color='red'>"+e.getMessage()+"</label></center>");
				}
				break;
			case "status":
				try {
					dispatcher=request.getRequestDispatcher("admin.html");
					dispatcher.include(request, response);
					ArrayList<Account> result = service.getAccountsByStatus(param);
					out.print("<br><center><h3>Results</h3></center");
					for (Account u : result) {
						out.print("<center><label>"+u+ "</label></center><br>");
					}
					
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					out.print("<center><label color='red'>"+e.getMessage()+"</label></center>");
				}
				break;
			
			default:
				dispatcher=request.getRequestDispatcher("admin.html");
				dispatcher.include(request, response);
				out.print("<center><label color='red'>Invalid field entry</label></center>");
				break;

		}	
	}
	
}

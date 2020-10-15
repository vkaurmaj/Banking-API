package com.app.banking_api.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.Account;
import com.app.banking_api.service.BankService;
import com.app.banking_api.service.impl.BankServiceImpl;

/**
 * Servlet implementation class TransferController
 */
public class TransferController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransferController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int to = Integer.parseInt(request.getParameter("to"));
		int from = Integer.parseInt(request.getParameter("from"));
		double amount = Double.parseDouble(request.getParameter("amount"));
		
		BankService service = new BankServiceImpl();
		RequestDispatcher requestDispatcher=null;
		try {
			Account success = service.performTransfer(to, from, amount);
			if (success != null) {
				requestDispatcher=request.getRequestDispatcher("tform.html");
				requestDispatcher.include(request, response);
				out.print("<center><span style='color:green;'>Successful transfer</span></center>");
				out.print("<center><span style='color:green;'>Sending account balance: $" + success.getBalance() +"</span></center>");
			} else {
				throw new BusinessException("Transaction failed; try again");
			}
		} catch (BusinessException e) {
			//failure
			requestDispatcher=request.getRequestDispatcher("tform.html");
			requestDispatcher.include(request, response);
			out.print("<center><span style='color:red;'>"+e.getMessage()+"</span></center>");
		}
	}

}

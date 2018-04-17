package com.github.Alexa;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Step1_1SendaccessCode
 */
public class Step1_1SendaccessCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Step1_1SendaccessCode.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Step1_1SendaccessCode() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
		
		String state = request.getParameter("state");
		if (null == state ){
			state = "1";
		}
		log.info("Step1_1SendaccessCode ");
		log.info("state "+state);
		log.info("code "+request.getParameter("code"));
	
		String urlRedirect = "https://pitangui.amazon.com/spa/skill/account-linking-status.html?vendorId=MYFQ2S2E4F1Y&state="+state+"&code="+request.getParameter("code") ;
		res.sendRedirect(urlRedirect);
		//res.getWriter().println(urlRedirect);
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

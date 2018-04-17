package com.github.Alexa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
/**
 * Servlet implementation class Step1GetAuthCode
 */
public class Step1GetAuthCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Step1GetAuthCode.class.getName());
    
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Step1GetAuthCode() {
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
		String redirect_uri =  request.getParameter("redirect_uri");
		
		String client_id = request.getParameter("client_id");
		log.info("Step1GetAuthCode ");
		log.info("state "+state);
		log.info("client_id "+client_id);
		String urlRedirect = "https://github.com/login/oauth/authorize?client_id="+client_id+"&scope=user&response_type=token&state="+state ;
		res.sendRedirect(urlRedirect);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

package com.github;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetUserViaToken
 */
public class GetUserViaToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserViaToken() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
		String access_token = request.getParameter("access_token");  
		 StringBuffer json = new StringBuffer();
		try {
			URL url = new URL("https://api.github.com/user?access_token="+access_token );
			 BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			 String line;

			 while ((line = reader.readLine()) != null) {
			   json.append(line);
			 }
			 reader.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} 
		
		res.setContentType("application/json");
		 res.getWriter().println(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

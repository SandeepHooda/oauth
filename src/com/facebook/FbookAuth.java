package com.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FbookAuth
 */
public class FbookAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FbookAuth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		URL url = new URL("https://graph.facebook.com/v2.8/me?fields=name%2Cpicture%2Cemail&access_token="+req.getParameter("access_token") );
		 BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		 StringBuffer json = new StringBuffer();
		 String line;

		 while ((line = reader.readLine()) != null) {
		   json.append(line);
		 }
		 reader.close();
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

package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;

/**
 * Servlet implementation class GAuth
 */
public class GAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GAuth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Method 1
		VerificationResult response = Googleverifier.getVerificationResult(req.getParameter("id_token"));
		Gson gson = new Gson();
		String json = gson.toJson(response);
		//End method 1
		
	
		//Method 2
		/* URL url = new URL("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token="+req.getParameter("id_token") );
		 BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		 StringBuffer json = new StringBuffer();
		 String line;

		 while ((line = reader.readLine()) != null) {
		   json.append(line);
		 }
		 reader.close();*/
		//End of method 2 
		 
		resp.setContentType("application/json");
		resp.getWriter().println(json.toString());
		    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

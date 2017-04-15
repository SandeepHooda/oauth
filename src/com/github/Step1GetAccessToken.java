package com.github;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Step1TempAuthCode
 */
public class Step1GetAccessToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Step1GetAccessToken() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
		String message = "";
		
		/*
		 * Use the code from previous step to exchange access token
		 */
		String urlParameters  = "client_id=ca9ff4fb657deb4f4f4c&client_secret=2f98478deaf1c491c6a189250c6f2d1f9258d93a&code="+request.getParameter("code")+"&redirect_uri=https://oauth-sandeep.appspot.com/GitHub/Step2ParseUserInfo&state=1";
		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length;
		
		
	    URL url = new URL("https://github.com/login/oauth/access_token" );
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setDoOutput(true);
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type",  "application/x-www-form-urlencoded");
	    conn.setRequestProperty( "charset", "utf-8");
	    conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
	    conn.setUseCaches( false );
	    conn.setDoOutput(true);
	    conn.setDoInput(true);
	       
	    DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
	    wr.write( postData );
	        	
	  
	    /*
	     * Read response 
	     */
	    int respCode = conn.getResponseCode();  // New items get NOT_FOUND on PUT
	    if (respCode == HttpURLConnection.HTTP_OK || respCode == HttpURLConnection.HTTP_NOT_FOUND) {
	    	request.setAttribute("error", "");
	      StringBuffer response = new StringBuffer();
	      String line;

	      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      while ((line = reader.readLine()) != null) {
	        response.append(line);
	      }
	      reader.close();
	     
	      URL accessUrl = new URL("https://oauth-sandeep.appspot.com/GitHub/Step2ParseUserInfo?"+response.toString()  );
	      Map<String, String> accessTokanMap =splitQuery(accessUrl);
	      /*
	       * Use the access tojen to get user details
	       */
	      message = getUserDetails(accessTokanMap);
	      Cookie cookie = new Cookie("userdetails",message);
	      cookie.setMaxAge(60*60*24); 
	      res.addCookie(cookie);
	     // request.getSession().setAttribute("userdetails", message);
	      
	    } else {
	    	StringBuffer response = new StringBuffer();
		      String line;
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		      while ((line = reader.readLine()) != null) {
		        response.append(line);
		      }
		      reader.close();
	    	message += conn.getResponseCode() +" "+conn.getResponseMessage()+" "+response.toString();
	      
	    }
	    
		 res.setContentType("application/json");
		 //res.getWriter().println(message);
		 res.sendRedirect("/github.html");
	
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private  Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = url.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
	
	private String getUserDetails(Map<String, String> accessTokanMap){
		
		try {
			URL url = new URL("https://api.github.com/user?access_token="+accessTokanMap.get("access_token") );
			 BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			 StringBuffer json = new StringBuffer();
			 String line;

			 while ((line = reader.readLine()) != null) {
			   json.append(line);
			 }
			 reader.close();
			 return json.toString();
		} catch (Exception e) {
			
			e.printStackTrace();
			return e.getMessage();
		} 
		
	}
	
}

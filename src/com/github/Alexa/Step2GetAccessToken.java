package com.github.Alexa;

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
import java.util.logging.Logger;

/**
 * Servlet implementation class Step2GetAccessToken
 */
public class Step2GetAccessToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Step2GetAccessToken.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Step2GetAccessToken() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
		String message = "";
		try{
			
			 Map<String, String> accessTokanMap = null;
				String state = request.getParameter("state");
				if (null == state ){
					state = "1";
				}
				String token = request.getParameter("token");
				if (null == token) {
					token = request.getParameter("access_token"); 
				}
				 log.info("Token received in request"+token);
				if (null != token){
					//https://pitangui.amazon.com/spa/skill/account-linking-status.html?vendorId=AAAAAAAAAAAAAA#state=xyz&access_token=2YotnFZFEjr1zCsicMWpAA&token_type=Bearer
					res.sendRedirect("https://pitangui.amazon.com/spa/skill/account-linking-status.html?vendorId=MYFQ2S2E4F1Y#state="+state+"&access_token="+token+"&token_type=Bearer");
				}else {
					String client_id = request.getParameter("client_id");
					if (null == client_id){
						client_id = "bf9bcc9b55e8d9487747";
					}
					log.info("client_id in step 2 "+client_id);
					String urlParameters  = "client_id="+client_id+"&client_secret=8917682474f35a9c2894fbc3a1df0336eea156d0&code="+request.getParameter("code")+"&state="+state;
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
				     
				      URL accessUrl = new URL("https://oauth-sandeep.appspot.com/someDummyUrl?"+response.toString()  );
				      System.out.println(" dummy accessUrl  "+accessUrl);
				      accessTokanMap =splitQuery(accessUrl);
				      /*
				       * Use the access tojen to get user details
				       */
				      message = response.toString();
				      
				     
				      
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
				    
					 //res.setContentType("application/json");
					//res.getWriter().println("state="+state+"&access_token="+accessTokanMap.get("access_token")+"&token_type=Bearer");
				    //message += "  client_secret = "+request.getParameter("client_secret") +"  client_id = "+request.getParameter("client_id")
				    log.info("Message from git hub ."+message);
				    log.info("Alexa accepts"+request.getHeader("Accept"));
				    log.info("Referer"+request.getHeader("Referer"));
				    log.info("User-Agent"+request.getHeader("User-Agent"));
				    log.info("Response Content-Type "+res.getContentType());
				    token = accessTokanMap.get("access_token");
					//res.getWriter().println(message+"&state="+state);
				    log.info("Token received from git hub"+token);
					//res.sendRedirect("https://pitangui.amazon.com/spa/skill/account-linking-status.html?vendorId=MYFQ2S2E4F1Y#state="+state+"&access_token="+accessTokanMap.get("access_token")+"&token_type=bearer");
				    res.sendRedirect("https://pitangui.amazon.com/spa/skill/account-linking-status.html?vendorId=MYFQ2S2E4F1Y#state="+state+"&access_token="+token+"&token_type=Bearer");
					
				}
				
					
				
				
				//https://pitangui.amazon.com/spa/skill/account-linking-status.html?vendorId=AAAAAAAAAAAAAA&state=xyz&code=SplxlOBeZQQYbYS6WxSbIA
		}catch (Exception e){
			e.printStackTrace();
		}
		
	
		
			
			
			
		 
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


package com;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

public class Googleverifier {

	public static VerificationResult getVerificationResult(String idTokenString){
		VerificationResult response = new VerificationResult();
		try {
			String CLIENT_ID ="140214647064-qoagn8ns08r6454hsp4cnndp0qtb1uv1.apps.googleusercontent.com";
			HttpTransport transport;
			transport = GoogleNetHttpTransport.newTrustedTransport();
		
		JsonFactory jsonFactory = new GsonFactory();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
			    .setAudience(Collections.singletonList(CLIENT_ID))
			    // Or, if multiple clients access the backend:
			    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
			    .build();

			// (Receive idTokenString by HTTPS POST)
		

			GoogleIdToken idToken = verifier.verify(idTokenString);
			if (idToken != null) {
			  Payload payload = idToken.getPayload();

			  // Print user identifier
			  String userId = payload.getSubject();
			  System.out.println("User ID: " + userId);

			  // Get profile information from payload
			  String email = payload.getEmail();
			  boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
			  String name = (String) payload.get("name");
			  String pictureUrl = (String) payload.get("picture");
			  String locale = (String) payload.get("locale");
			  String familyName = (String) payload.get("family_name");
			  String givenName = (String) payload.get("given_name");

			  System.out.println("bname "+name);
			  System.out.println("family name "+familyName);
			  System.out.println("audience "+payload.getAudience());
			  System.out.println("issuer "+payload.getIssuer());
			  
			  System.out.println("email "+payload.getEmail());
			 
			  System.out.println("nounce "+payload.getNonce());
			  System.out.println("subject "+payload.getSubject());
			  response.setEmail(payload.getEmail());
			  response.setFamily_name(familyName);
			  response.setGiven_name(givenName);;
			  response.setPicture(pictureUrl);

			} else {
			  System.out.println("Invalid ID token.");
			}
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}

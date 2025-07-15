/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author admin
 */
public class GoogleLogin {

    public static final String CLIENT_ID = "644373995243-26el7jvl6h26m305a19fjsqt0nojk7al.apps.googleusercontent.com";
    public static final String CLIENT_SECRET = "";  // Your pass
    public static final String REDIRECT_URI = "http://localhost:8080/lighthouse/login?method=google";
    public static final String TOKEN_URL = "https://accounts.google.com/o/oauth2/token";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    // public static final String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";

    public String getToken(String code) throws IOException {
        // Send a POST request to get the OAuth2 token from Google
        String response = Request.Post(TOKEN_URL)
                .bodyForm(
                        Form.form()
                                .add("client_id", CLIENT_ID)
                                .add("client_secret", CLIENT_SECRET)
                                .add("redirect_uri", REDIRECT_URI)
                                .add("code", code)
                                .add("grant_type", GRANT_TYPE)
                                .build()
                ) 
                // Execute request and receive response content as String
                .execute().returnContent().asString(); 

        // Parse JSON from Google response
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        // Get access_token from JSON and remove quotes
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");

        return accessToken;
    }

    public User getInfoUser(String accessToken) throws IOException {
        // Create a path to query user information from Google API
        String link = USER_INFO_URL + accessToken;
        
        // Send a GET request to get user information
        String response = Request.Get(link).execute().returnContent().asString();
        // Parse the response JSON and create a GoogleAccount object from the returned data
        User ggAccount = new Gson().fromJson(response, User.class);
        return ggAccount;
    }

}

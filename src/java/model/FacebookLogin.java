/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author admin
 */
public class FacebookLogin {

    public static final String FACEBOOK_CLIENT_ID = "567657996245410";
    public static final String FACEBOOK_CLIENT_SECRET =  "";  //your pass
    public static final String FACEBOOK_REDIRECT_URI = "http://localhost:8080/lighthouse/login?method=facebook";
    public static final String FACEBOOK_LINK_GET_TOKEN = "https://graph.facebook.com/v19.0/oauth/access_token";
    public static final String FACEBOOK_LINK_GET_USER_INFO = "https://graph.facebook.com/me?fields=id,name,email,picture.width(200).height(200)&access_token=";

    public String getToken(String code) throws ClientProtocolException, IOException {
        String response = Request.Post(FACEBOOK_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form()
                                .add("client_id", FACEBOOK_CLIENT_ID)
                                .add("client_secret", FACEBOOK_CLIENT_SECRET)
                                .add("redirect_uri", FACEBOOK_REDIRECT_URI)
                                .add("code", code)
                                .build()
                )
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);

        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public User getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = FACEBOOK_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        //FacebookAccount fbAccount= new Gson().fromJson(response, FacebookAccount.class);
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        System.out.println("obj: " + jobj);
        // Get info user
        String id = jobj.has("id") ? jobj.get("id").getAsString() : "";
        String name = jobj.has("name") ? jobj.get("name").getAsString() : "";
        String email = jobj.has("email") ? jobj.get("email").getAsString() : "";
        String pictureUrl = jobj.has("picture") && jobj.getAsJsonObject("picture").has("data")
                ? jobj.getAsJsonObject("picture").getAsJsonObject("data").get("url").getAsString() : "";
        
        // return FacebookAccount
        return new User(id, name, email, pictureUrl);
    }
}

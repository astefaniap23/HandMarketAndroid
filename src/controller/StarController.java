package controller;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import config.AplicationConfig;
import config.CustomHttpClient;
import dto.WishListDTO;

public class StarController {
	
	
	public boolean starUser(WishListDTO starList) {
		String respuesta = null;
		try {
			   ArrayList<NameValuePair> postValores = new ArrayList<NameValuePair>(); // para el php
			   
				postValores.add(new BasicNameValuePair("usuario", null));			
				
        	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLStarSelected, postValores);
        	
        	String res = respuesta.toString();
        	res = res.replaceAll("\\s","");
        	System.out.println("respuesta star : " + res.toString());
        	/*
        	if (res.equals("1")) {
        		return true;
        	}*/
        	
        } catch (Exception e) {
            e.printStackTrace();    
        }     
		
        // PARSE JSON
        try {
			// crea un nuevo JSON array de nuestro string -> resultado 
        	JSONArray jArray = new JSONArray(respuesta.toString());
        	//ClientDTO clientProfile = new ClientDTO();
        	Log.e("JSON","Entro array");
        	
        	// por cada objeto en el JSON array
        	for(int i=0;i<jArray.length();i++){
        		JSONObject json_data = jArray.getJSONObject(i);
        		Log.e("JSON","Entro for");
        		
        		starList.setId_product(json_data.getString("id_product"));
        		
           	}
        	return true;
		} catch (JSONException e) {
			// TODO: handle exception			
			Log.e("JSON","Error parsing en "+ e.toString());
		}
		return false;
        
	}
	/////////////////////

}

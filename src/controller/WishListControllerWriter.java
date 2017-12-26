package controller;


import java.util.ArrayList;


import org.apache.http.NameValuePair;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import config.AplicationConfig;
import config.CustomHttpClient;

import dto.WishListWriterDTO;
import android.app.Activity;
import android.util.Log;

public class WishListControllerWriter extends Activity {

	
	public static ArrayList<WishListWriterDTO> searchProducts()
	{
		String respuesta = null;
		try{
			//inicializa las variables			
			ArrayList<NameValuePair> par = new ArrayList<NameValuePair>(); // para el php
			par.add(new BasicNameValuePair("usuario", null));
			respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLWishToList, par);
		}catch (Exception e) {
			 e.printStackTrace();   
			// TODO: handle exception
		}
			ArrayList<WishListWriterDTO> wishlistwriter = new ArrayList<WishListWriterDTO>();			

			try 
			{
				
				JSONArray jArray = new JSONArray(respuesta.toString());
				Log.e("PUTO ERROR JARRAY", jArray.toString());
				
				// por cada objeto en el JSON array
				for(int i = 0 ; i < jArray.length() ; i++){
					Log.e("PUTO ERROR", "ENTRO FOR");
					JSONObject json_data = jArray.getJSONObject(i);
				
					// creando nuevo producto
					
					
					// atributos	  	        		
					WishListWriterDTO wishlistwriterDTO = new WishListWriterDTO(
							json_data.getInt("id_code"),
							json_data.getInt("id_product"),
					    	json_data.getInt("id_user"),
							json_data.getString("nombre"),
							json_data.getString("descripcion"),
							json_data.getDouble("precio"),
							json_data.getString("imagen"),
							json_data.getString("imagen_max")
					);
					// resultRow son los objetos de array , y se agregan al objeto producto
					wishlistwriter.add(wishlistwriterDTO);
				}
				return wishlistwriter;
			}  catch (JSONException e) {
				Log.e("searchProducts - JSONException",  e.getMessage());
			}
			
			return null;	
	}
	
	
	
	
	
	
	
	
	
}

package controller;


import java.util.ArrayList;


import org.apache.http.NameValuePair;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import config.AplicationConfig;
import config.CustomHttpClient;
import dto.CategoryDTO;
import dto.ShoppingCarWriterDTO;

public class ShoppingCarControllerWriter extends Activity{
	
	
	public ArrayList<ShoppingCarWriterDTO> getProducts(CategoryDTO category) {
		return null;
	}
	
	public ShoppingCarWriterDTO getShopById(ShoppingCarWriterDTO shopping) {
		return null;
	}
	
	public static ArrayList<ShoppingCarWriterDTO> searchProducts()
	{
		String respuesta = null;
		try{
			//inicializa las variables			
			ArrayList<NameValuePair> par = new ArrayList<NameValuePair>(); // para el php
			par.add(new BasicNameValuePair("usuario", null));
			respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLShoppingToCar, par);
		}catch (Exception e) {
			 e.printStackTrace();   
			// TODO: handle exception
		}
			ArrayList<ShoppingCarWriterDTO> shoppingcarwriter = new ArrayList<ShoppingCarWriterDTO>();
			
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
					ShoppingCarWriterDTO shoppingcarwriterDTO = new ShoppingCarWriterDTO(
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
					shoppingcarwriter.add(shoppingcarwriterDTO);
				}
				return shoppingcarwriter;
			}  catch (JSONException e) {
				Log.e("searchProducts - JSONException",  e.getMessage());
			}
			
			return null;	
	}

}

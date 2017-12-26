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
import dto.RecordBillDTO;


public class RecordBillController {

	
	
	
	public static ArrayList<RecordBillDTO> searchRecord()
	{
		String respuesta = null;
		try{
			//inicializa las variables			
			ArrayList<NameValuePair> par = new ArrayList<NameValuePair>(); // para el php
			par.add(new BasicNameValuePair("usuario", null));
			respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLRecordBill, par);
		}catch (Exception e) {
			 e.printStackTrace();   
			// TODO: handle exception
		}
			ArrayList<RecordBillDTO> recordbill = new ArrayList<RecordBillDTO>();			

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
					RecordBillDTO recordBillDTO = new RecordBillDTO(
							json_data.getString("id"),
							json_data.getString("fecha")
					    
					);
					// resultRow son los objetos de array , y se agregan al objeto producto
					recordbill.add(recordBillDTO);
				}
				return recordbill;
			}  catch (JSONException e) {
				Log.e("searchProducts - JSONException",  e.getMessage());
			}
			
			return null;	
	}
	
	
	
	
	
	
	
}

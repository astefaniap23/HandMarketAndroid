package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import config.AplicationConfig;
import dto.RecordBillDetailsDTO;

public class RecordBillDetailsController extends Activity { 
	
	
	public static ArrayList<RecordBillDetailsDTO> searchRecord(String id)
	{
		//System.out.println(id.toString());
		//inicializa las variables
			HttpClient client = new DefaultHttpClient();	
			HttpParams params = client.getParams();
			String parameters = "";
			if(id!= null)
			{
				parameters = "?id=" + id;
				System.out.println("ID-----:" +id);
				System.out.println("parameter-----:" +parameters);
			}
			HttpGet post = new HttpGet(AplicationConfig.URLConection +AplicationConfig.URLRecordBillDetails + parameters );
		
			params.setParameter(id, id);
			post.setParams(params);
			HttpResponse respuesta = null;
			BufferedReader reader = null;
			InputStream webs = null;
			String resultado = "";
			ArrayList<RecordBillDetailsDTO> recordbilldetails = new ArrayList<RecordBillDetailsDTO>();
	
	

			try 
			{
				
				respuesta = client.execute(post);
				HttpEntity ent = respuesta.getEntity(); // obtiene el mensaje de la respues (decodifica)
				System.out.println("respuesta--------:"+respuesta);
				webs = ent.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(webs, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while((line = br.readLine()) != null)
				{ 	Log.e("PUTO ERROR JSON DATA w","WHILE ");
					sb.append(line + "\n");
					
				}
		/*
		String respuesta = null;
		try{
			//inicializa las variables			
			ArrayList<NameValuePair> par = new ArrayList<NameValuePair>(); // para el php
			par.add(new BasicNameValuePair("id", id));
			respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLRecordBillDetails, par);
		}catch (Exception e) {
			 e.printStackTrace();   
			// TODO: handle exception
		}
			ArrayList<RecordBillDetailsDTO> recordbilldetails = new ArrayList<RecordBillDetailsDTO>();*/
				
				JSONArray jArray = new JSONArray(sb.toString());
				webs.close();//Cierre memoria
				System.out.println("jArray:------"+jArray);
				// por cada objeto en el JSON array
				for(int i = 0 ; i < jArray.length() ; i++){
					JSONObject json_data = jArray.getJSONObject(i);
					Log.e("PUTO ERROR JSON DATA n", "FOR");
					// creando nuevo producto
					
					
					// atributos	
					RecordBillDetailsDTO recordBillDetailsDTO = new RecordBillDetailsDTO(
						
							json_data.getString("nombre"),
							json_data.getDouble("precio")
							
							);
					
					System.out.println("DTO   FOR:------"+json_data.getString("nombre")+json_data.getDouble("precio"));
					// resultRow son los objetos de array , y se agregan al objeto producto
					recordbilldetails.add(recordBillDetailsDTO);
					
					//Log.e("PUTO ERROR JSON DATA", json_data.toString());
				//	Log.e("PUTO ERROR recordbilldetails ", recordbilldetails.toString());
					//Log.e("PUTO ERROR recordBillDetailsDTO", recordBillDetailsDTO.toString());
					
				}
				return recordbilldetails;
			} catch (ClientProtocolException e) {				
				Log.e("searchProducts - ClientProtocolException", e.getMessage());
			} catch (IOException e) {
				Log.e("searchProducts - IOException",  e.getMessage());
			} catch (IllegalStateException e) {
				Log.e("searchProducts - IllegalStateException",  e.getMessage());
			} catch (JSONException e) {
				Log.e("searchProducts - JSONException",  e.getMessage());
			}
			
			
			return null;	
	}
	
	
	
}
	
	


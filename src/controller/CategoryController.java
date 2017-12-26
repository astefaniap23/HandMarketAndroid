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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import config.AplicationConfig;
import dto.CategoryDTO;

public class CategoryController {
	
	
	
	public static ArrayList<CategoryDTO> searchProducts()
	{
		//inicializa las variables
		HttpClient client = new DefaultHttpClient();				
		HttpPost post = new HttpPost(AplicationConfig.URLConection + AplicationConfig.URLCategoryService);
		HttpResponse respuesta = null;
		BufferedReader reader = null;
		InputStream webs = null;
		String resultado = "";
		ArrayList<CategoryDTO> category = new ArrayList<CategoryDTO>();
		
		try 
		{
			
			respuesta = client.execute(post);
			HttpEntity ent = respuesta.getEntity(); // obtiene el mensaje de la respues (decodifica)
			webs = ent.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(webs, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = br.readLine()) != null)
			{
				sb.append(line + "\n");
				
			}
			
			JSONArray jArray = new JSONArray(sb.toString());
			webs.close();//Cierre memoria
			// por cada objeto en el JSON array
			for(int i = 0 ; i < jArray.length() ; i++){
				JSONObject json_data = jArray.getJSONObject(i);
			
				// creando nuevo producto
				
				
				// atributos	  	        		
				CategoryDTO categoryDTO = new CategoryDTO(
				    json_data.getString("id_category"),
					json_data.getString("name")
				);
				
				// resultRow son los objetos de array , y se agregan al objeto producto
				category.add(categoryDTO);
			}
			return category;
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

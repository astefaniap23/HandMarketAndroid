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
import dto.CategoryDTO;
import dto.ProductDTO;

public class ProductController extends Activity {
	

	/*final Spinner cmbOpciones = (Spinner)findViewById(R.id.CmbOpciones);
	Spinner a = (Spinner)findViewById(R.id.CmbOpciones);
	CategoryDTO f = (CategoryDTO)a.getSelectedItem();*/
	
		
	public ArrayList<ProductDTO> getProducts(CategoryDTO category) {
		return null;
	}
	
	public ProductDTO getProductById(ProductDTO product) {
		return null;
	}
	
	//public  static ArrayList<ProductDTO> searchProducts(io$id_category)
	public  static ArrayList<ProductDTO> searchProducts(String id_category)
	{
	
			//inicializa las variables
			HttpClient client = new DefaultHttpClient();	
			HttpParams params = client.getParams();
			String parameters = "";
			if(id_category != null)
			{
				parameters = "?id_category=" + id_category;
			}
			HttpGet post = new HttpGet(AplicationConfig.URLConection +AplicationConfig.URLServiceProduct + parameters );
	
			params.setParameter(id_category, id_category);
			post.setParams(params);
			HttpResponse respuesta = null;
			BufferedReader reader = null;
			InputStream webs = null;
			String resultado = "";
			ArrayList<ProductDTO> productos = new ArrayList<ProductDTO>();
			
			try 
			{
				
				respuesta = client.execute(post);
				HttpEntity ent = respuesta.getEntity(); // obtiene el mensaje de la respues (decodifica)
				webs = ent.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(webs, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while((line = br.readLine()) != null)
				{ 	Log.e("PUTO ERROR JSON DATA w","WHILE ");
					sb.append(line + "\n");
					
				}
				
				JSONArray jArray = new JSONArray(sb.toString());
				webs.close();//Cierre memoria
				// por cada objeto en el JSON array
				for(int i = 0 ; i < jArray.length() ; i++){
					JSONObject json_data = jArray.getJSONObject(i);
					Log.e("PUTO ERROR JSON DATA n", "FOR");
					// creando nuevo producto
					
					
					// atributos	
					ProductDTO productDTO = new ProductDTO(
						json_data.getInt("id"),
						json_data.getString("id_category"),
						json_data.getString("nombre"),
						json_data.getString("descripcion"),
						json_data.getDouble("precio"),
						json_data.getString("imagen"),
						json_data.getString("imagen_max")
						
					);
					// resultRow son los objetos de array , y se agregan al objeto producto
					productos.add(productDTO);
					Log.e("PUTO ERROR JSON DATA", json_data.toString());
					Log.e("PUTO ERROR productos ", productos.toString());
					Log.e("PUTO ERROR productDTO", productDTO.toString());
				}
				return productos;
				
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

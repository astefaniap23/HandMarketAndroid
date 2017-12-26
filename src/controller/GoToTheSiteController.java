package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import config.AplicationConfig;
import config.CustomHttpClient;

import dto.SitesDTO;
import android.app.Activity;
import android.util.Log;

public class GoToTheSiteController extends Activity { 
	
	public static ArrayList<SitesDTO> searchSites()
	{
		//inicializa las variables
		HttpClient client = new DefaultHttpClient();				
		HttpPost post = new HttpPost(AplicationConfig.URLConection + AplicationConfig.URLGoToTheSite);
		HttpResponse respuesta = null;
		BufferedReader reader = null;
		InputStream webs = null;
		String resultado = "";
		ArrayList<SitesDTO> sites = new ArrayList<SitesDTO>();
		
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
				SitesDTO sitesDTO = new SitesDTO(
				    json_data.getString("id_place"),
					json_data.getString("name"),
					json_data.getString ("address")
				);
				
				// resultRow son los objetos de array , y se agregan al objeto producto
				sites.add(sitesDTO);
			}
			return sites;
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
	
	//// enviar factura email con direccion de envio del sucursal
	

public boolean enviarFacturaRetiroTienda(SitesDTO site){  // envio solo del total de productos sin delivery
	String respuesta = null;
	try {
		
	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			
	params.add(new BasicNameValuePair("idSucursal", site.getId_place() ));

    
	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleSendInvoice, params);
	
	String res = respuesta.toString();
	res = res.replaceAll("\\s","");
	Log.e("VERIFICA EMAIL ************", res);
	if(res.equals("1")){
		Log.e("ENTROO ************", res);
	return true;
	}
	
    } catch (Exception e) {
    	Log.e("ERROR MAIL ************", e.toString());
        e.printStackTrace();    
    } 
	return false;
	
}
	
	
	
	

}

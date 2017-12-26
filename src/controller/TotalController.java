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
import dto.TotalDTO;




public class TotalController{
	
public boolean totalShopping(TotalDTO total) {
		
		
		String respuesta = null;
        
        try {
        	ArrayList<NameValuePair> postValores = new ArrayList<NameValuePair>(); // para el php
			postValores.add(new BasicNameValuePair("precioT", null));
        	
			respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLTotalShoppingPrice, postValores);
        	
			String res = respuesta.toString();
        	res = res.replaceAll("\\s","");
        	
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
        		
        		total.setTotal(json_data.getDouble("SUM(p.precio)"));      		
				System.out.println(json_data.getDouble("SUM(p.precio)"));
				
           	}
        	return true;
		} catch (JSONException e) {
			// TODO: handle exception			
			Log.e("JSON","Error parsing en "+ e.toString());
		}
		return false;
        
	}
	
public boolean enviarFacturaDomicilio(TotalDTO total){ // envio con total delivery
	String respuesta = null;
	try {
		
	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			
	params.add(new BasicNameValuePair("totalCorreoDomicilio", Double.toString(total.getTotal()) ));
	params.add(new BasicNameValuePair("totalFacturaRetiro", Double.toString(total.getTotal2()) )); // total sin domicilio

    
	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleSendInvoiceDom, params);
	
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
	
public boolean TotalRetiro(TotalDTO total){  /// guarda total sin delivery en la bd
	String respuesta = null;
	try {
		
	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
	
	//params.add(new BasicNameValuePair("totalCorreoDomicilio", total.getTotal() )); // total domicilio
	params.add(new BasicNameValuePair("totalFacturaRetiro", Double.toString(total.getTotal2()) ));
	

    
	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleSendTotalRetiro, params);
	
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

public boolean TotalDelivery(TotalDTO total){  /// guarda total sin delivery en la bd
	String respuesta = null;
	try {
		
	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
	
	params.add(new BasicNameValuePair("totalFacturaDomicilio", Double.toString(total.getTotal()) )); // total domicilio
	params.add(new BasicNameValuePair("totalFacturaRetiro", Double.toString(total.getTotal2()) ));
	

    
	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleSendTotalDelivery, params);
	
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


public boolean cancelaCompraBackButton (){  /// guarda total sin delivery en la bd
	String respuesta = null;
	try {
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("nulo", null ));
		
    
	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleCancelBuyBackButton, params);
	
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

//////// TOTAL para el historial
public boolean totalHistorial(TotalDTO total,String id) {
	
	
	String respuesta = null;
    
    try {
    	ArrayList<NameValuePair> postValores = new ArrayList<NameValuePair>(); // para el php
		postValores.add(new BasicNameValuePair("id", id));
    	
		respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLRecordTotalBill, postValores);
    	
		String res = respuesta.toString();
    	res = res.replaceAll("\\s","");
    	
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
    		
    		total.setTotalHistorial(json_data.getDouble("total_productos")); 
    		total.setTotalHistorialDeli(json_data.getDouble("total")); 
    		
			System.out.println("TOTAL HISTORIAL PRODUCTOS: "+json_data.getString("total_productos"));
			System.out.println("TOTAL HISTORIAL:"+json_data.getString("total")+"..");
			
       	}
    	return true;
	} catch (JSONException e) {
		// TODO: handle exception			
		Log.e("JSON","Error parsing en "+ e.toString());
	}
	return false;
    
}

	

}

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
import dto.ClientDTO;

public class ClientController {
	
	///////////////////
	public String createClient(ClientDTO client) {
		String respuesta = null;
		try {
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			
	        params.add(new BasicNameValuePair("nuevoNombre", client.getName()));
	        params.add(new BasicNameValuePair("nuevoApellido", client.getLastName()));
	        params.add(new BasicNameValuePair("nuevoSexo", client.getGender()));
	        params.add(new BasicNameValuePair("nuevoUsuario", client.getUser()));
	        params.add(new BasicNameValuePair("nuevoClave", client.getPassword()));
	        params.add(new BasicNameValuePair("nuevoDireccion", client.getAddress()));
	        params.add(new BasicNameValuePair("nuevoCorreo", client.getEmail()));
	        params.add(new BasicNameValuePair("nuevoTelefono", client.getPhone()));
	
        	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleCreateClient, params);
        	
        	String res = respuesta.toString();
        	res = res.replaceAll("\\s","");
        	Log.i("Nuevo ->", res);
        	
        	if (res.indexOf("z")!= -1){   // entra
        		return "z";
        	}
        	else if(res.indexOf("b")!= -1){  
        		return "b"; // nombre con numero
        	}
        	
        	
        	
        	else if(res.indexOf("c")!= -1){  
        		return "c"; // apellido con numero
        	}
        	else if(res.indexOf("a")!= -1){  
        		return "a"; // apellido con numero
        	}      	
        	
        	
        	else if(res.indexOf("d")!= -1){  
        		return "d"; // usuario
        	}
        	
        	else if(res.indexOf("m")!= -1){  
        		return "m"; // correo existente
        	}   
        	
        	else if(res.indexOf("u")!= -1){  
        		return "u"; // usuario existente
        	}
        	
        	
        	
        } catch (Exception e) {
            e.printStackTrace();    
        }     
		
		return "e";
	}
	
	/////////////////////
	
	public int loginClient(ClientDTO client) {
		String respuesta = null;
		try {
			   ArrayList<NameValuePair> postValores = new ArrayList<NameValuePair>(); // para el php
			   
				postValores.add(new BasicNameValuePair("usuario", client.getUser()));
				postValores.add(new BasicNameValuePair("clave", client.getPassword()));
				postValores.add(new BasicNameValuePair("id", client.getId()));
				
				
        	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleLoginClient, postValores);
        	
        	String res = respuesta.toString();
        	res = res.replaceAll("\\s","");
        	
        	if (res.equals("0")){ // clave incorrecta
        		return 1;
        	}
        	
        	if (res.equals("1")) { // entra
        		return 2;
        	}
        	
        } catch (Exception e) {
            e.printStackTrace();    
        }     
		
		return 0;  // usuario no registrado
	}
	
	/////////////////////
	
	public boolean closeClient(){
		
		String respuesta = null;
		try {
			 ArrayList<NameValuePair> postValores = new ArrayList<NameValuePair>(); // para el php
				postValores.add(new BasicNameValuePair("usuario", " "));				
				
        	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleCloseClient, postValores);
        	
        	String res = respuesta.toString();
			res = res.replaceAll("\\s","");
			if (res.equals("2")) {
				return true;
			}
	 
		 }catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
			}	
		return false;
	}		
	
	/////////////////////
	
	public boolean profileClient(ClientDTO client) {
		
		
		String respuesta = null;
        
        try {
        	ArrayList<NameValuePair> postValores = new ArrayList<NameValuePair>(); // para el php
			postValores.add(new BasicNameValuePair("usuario", null));
        	
			respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleProfileClient, postValores);
        	
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
        		
        		client.setName(json_data.getString("nombre"));
        		client.setLastName(json_data.getString("apellido"));
        		client.setAddress(json_data.getString("direccion"));
        		client.setEmail(json_data.getString("correo")); 
        		client.setPhone(json_data.getString("telefono"));
				System.out.println(json_data.getString("nombre"));
				System.out.println(json_data.getString("apellido"));
				System.out.println(json_data.getString("direccion"));
				System.out.println(json_data.getString("correo"));
           	}
        	return true;
		} catch (JSONException e) {
			// TODO: handle exception			
			Log.e("JSON","Error parsing en "+ e.toString());
		}
		return false;
        
	}
	
	/////////////////////
	
	public boolean verifyClient(ClientDTO client){
		String respuesta = null;
		try {
			
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("usuario", null));
		
		params.add(new BasicNameValuePair("checkClave", client.getCheckPassword()));

        
    	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleVerifyClient, params);
    	
    	String res = respuesta.toString();
    	res = res.replaceAll("\\s","");
    	Log.e("VERIFICA ************", res);
    	if(res.equals("1")){
    	return true;
    	}
    	
	    } catch (Exception e) {
	        e.printStackTrace();    
	    }     
		
		return false;
		
	}
	
	
	/////////////////////

	
	public String modifyClient(ClientDTO client){
		
		String respuesta = null;
		try {
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("usuario", null));
			
			params.add(new BasicNameValuePair("checkClave", client.getCheckPassword()));
	        params.add(new BasicNameValuePair("modificarClave", client.getPassword()));
	        params.add(new BasicNameValuePair("modificarDireccion", client.getAddress()));
	        params.add(new BasicNameValuePair("modificarCorreo", client.getEmail()));
	        params.add(new BasicNameValuePair("modificarNombre", client.getName()));
	        params.add(new BasicNameValuePair("modificarApellido", client.getLastName()));
	        params.add(new BasicNameValuePair("modificarTelefono", client.getPhone()));
	        
        	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleModifyClient, params);
        	
        	String res = respuesta.toString();
        	res = res.replaceAll("\\s","");
        	Log.i("Modificado ->", res);
        
        	if(res.indexOf("b")!= -1){  
        		return "b"; // nombre con numero
        	}
        	else if(res.indexOf("c")!= -1){  
        		return "c"; // apellido con numero
        	}
        	else if(res.indexOf("a")!= -1){  
        		return "a"; // clave con espacios
        	}
        	else if(res.indexOf("d")!= -1){  
        		return "d"; // correo existente
        	}
        	
        	
        	
        	if (res.equals("1") || res.equals("2") || res.equals("3") || res.equals("4") ||
            		res.equals("12") || res.equals("13") || res.equals("14") || 
            		res.equals("23") ||	res.equals("24") || res.equals("34") ||
            		res.equals("123") ||
        			res.equals("1234")  	) {
            		return "z";
            	}
        	
        	
        	else if(res.indexOf("0")!= -1){  // si algun error como numeros en nombre o apellido, espacios en blancos en android,
        									//el php imprime 0 junto a las demas respuestas, este mismo 
        										//recorre la respuesta, si encuentra el 0 entonces es false
        		return "h";
        		}
        	
        } catch (Exception e) {
            e.printStackTrace();    
        }     
		
		return "e";
		
	}
	
	
	/////////////////////
	
	
	public boolean deleteClient(ClientDTO client){
		String respuesta = null;
		try {
			
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("usuario", null));
		
		params.add(new BasicNameValuePair("checkClave", client.getCheckPassword()));

        
    	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleDeleteClient, params);
    	
    	String res = respuesta.toString();
    	res = res.replaceAll("\\s","");
    	Log.e("Cuenta eliminada ************", res);
    	
    	if(res.equals("1")){

    	return true;
    	}
    	
	    } catch (Exception e) {
	        e.printStackTrace();    
	    }     
		
		return false;
		
	}
	
	
	//////////////////////
	
	public boolean sendPasswordEmail(ClientDTO client){
		String respuesta = null;
		try {
			
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				
		params.add(new BasicNameValuePair("checkEmail", client.getEmail()));

        
    	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleVerifyEmailPassword, params);
    	
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
	
	
	////////////////////
	
	public boolean sendUserEmail(ClientDTO client){
		String respuesta = null;
		try {
			
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				
		params.add(new BasicNameValuePair("checkEmail", client.getEmail()));

        
    	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLModuleVerifyEmailUser, params);
    	
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
	
	//////////////////////////
	
	
	
}

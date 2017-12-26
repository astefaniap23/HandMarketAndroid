package controller;


import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import config.AplicationConfig;
import config.CustomHttpClient;
import dto.ShoppingCarDTO;

public class ShoppingCarControllerReader {
	
	
	public boolean productToShoppingCar (ShoppingCarDTO shopping) {
		String respuesta = null;
		try {
			
			ArrayList<NameValuePair> par = new ArrayList<NameValuePair>();
			
	        //par.add(new BasicNameValuePair("id_code", shopping.getId_shop()));
	        par.add(new BasicNameValuePair("id_product", shopping.getId_product()));
	        //par.add(new BasicNameValuePair("id_user", shopping.getId_user()));
	        par.add(new BasicNameValuePair("name", shopping.getName()));
	        par.add(new BasicNameValuePair("price", Double.toString(shopping.getPrice())));
	        par.add(new BasicNameValuePair("image", shopping.getImage()));

	        
        	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLProductToShoppingCar, par);
        	
        	String res = respuesta.toString();
        	res = res.replaceAll("\\s","");
        	
        	if (res.equals("3")) {
        		
        		return true;
        	}else{
        		
        	}
        } catch (Exception e) {
            e.printStackTrace();    
        }     
		
		return false;
	}
		
	
}

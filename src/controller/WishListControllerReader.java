package controller;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import config.AplicationConfig;
import config.CustomHttpClient;
import dto.WishListDTO;

public class WishListControllerReader extends Activity{

	
	public boolean productToWishList (WishListDTO wishlist) {
		String respuesta = null;
		try {
			
			ArrayList<NameValuePair> par = new ArrayList<NameValuePair>();
			
	        //par.add(new BasicNameValuePair("id_code", wishlist.getId_shop()));
	        par.add(new BasicNameValuePair("id_product", wishlist.getId_product()));
	        //par.add(new BasicNameValuePair("id_user", wishlist.getId_user()));
	        par.add(new BasicNameValuePair("name", wishlist.getName()));
	        par.add(new BasicNameValuePair("price",  Double.toString(wishlist.getPrice())));
	        par.add(new BasicNameValuePair("image", wishlist.getImage()));

	        
        	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLProductToWishList, par);
        	
        	String res = respuesta.toString();
        	res = res.replaceAll("\\s","");
        	
        	if (res.equals("3")) {
        		
        		return true;
        	}else{
        		//Toast.makeText(WishListControllerReader.this, "Ya existe", Toast.LENGTH_SHORT).show();	
        		
        	}
        } catch (Exception e) {
            e.printStackTrace();    
        }     
		
		return false;
	
	
	
	}
	
	public boolean productDeleteWishList (WishListDTO wishlist) {
		
		String respuesta = null;
		try {
			
			ArrayList<NameValuePair> par = new ArrayList<NameValuePair>();
			
	       
	        par.add(new BasicNameValuePair("id_product", wishlist.getId_product()));
	       
	        
        	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLProductDeleteWishList, par);
        	
        	String res = respuesta.toString();
        	res = res.replaceAll("\\s","");
        	
        	if (res.equals("3")) {
        		
        		return true;
        	}else{
        		//Toast.makeText(WishListControllerReader.this, "Ya existe", Toast.LENGTH_SHORT).show();	
        		
        	}
        } catch (Exception e) {
            e.printStackTrace();    
        }     
		
		return false;
	}
	
}

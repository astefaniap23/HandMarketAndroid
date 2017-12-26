package interfaces.client;

import interfaces.product.GoToDelivery;
import interfaces.product.ShoppingCar;
import principal.interfaces.MainActivity;
import uno.menu.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import controller.ClientController;
import controller.TotalController;
import dto.ClientDTO;

public class VerifyClient extends Activity{
	private MainActivity enVerifyClient = new MainActivity();
	
	EditText campoClave;
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
			//enNuevoRegistro.finishRegistro.finish(); // Terminar el apilado de Registro.java
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.verify_client);
/*
	        if (checkConnectivity()==false) {
				
	       	 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
	       	 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	        	toast.show();
	        	
	       	        	this.finish();
	       	        	MainActivity.finishInicioPrincipal.finish();
	       	        	Intent intent = new Intent(getApplicationContext(),Registro.class);
	       				startActivity(intent);	
	       	        }else{	}*/
	        campoClave = (EditText) findViewById(R.id.campoCheckPasword);
	       	        
	    }
		
		public void botonCheckClient (View v){
			
			
				
		ClientDTO client = new ClientDTO();
		
		client.setCheckPassword(campoClave.getText().toString());
		
		 if (campoClave.length()==0 ){
			 
			  Toast toast = Toast.makeText(getApplicationContext(), "Introduzca la contraseña", Toast.LENGTH_SHORT);
   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
         	toast.show();
		 }else{
		try {				
			if (checkConnectivity()==false) {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			 	toast.show();
				        	
				        }else{	
			ClientController clientController = new ClientController();
			
			if(clientController.verifyClient(client)) {
				
				final ProgressDialog pd = ProgressDialog.show(this, null,"Cargando ..",	true, false);
				new Thread(new Runnable(){
				public void run(){
				entra();
				pd.dismiss();
				}
				
				private void entra() {
				finish();
				
				Intent intent2 = new Intent(getApplicationContext(),ModifyClient.class);
				startActivity(intent2);	 
					//Toast.makeText(getApplicationContext(), "Contraseña correcta", Toast.LENGTH_SHORT).show();
					
				}}).start();
	            } else{
	            	
	            	 Toast toast = Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT);
	         		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	               	toast.show();
				}  
				        }
				} catch (Exception e) {
					// TODO: handle exception
					
					 Toast toast = Toast.makeText(getApplicationContext(), "A ocurrido un error", Toast.LENGTH_SHORT);
			   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			         	toast.show();
				}
		}
		 
			
		}
		
		
		// boton fisico back
		public boolean onKeyDown(int keyCode, KeyEvent event)
		{
		    if(keyCode == KeyEvent.KEYCODE_BACK) // boton fisico BACK del telefono
		    {
		    	 if (checkConnectivity()==false) {
						
					 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		       	toast.show();
		       	this.finish();
			        	
			        }else{
		    	final ProgressDialog pd = ProgressDialog.show(this, null,"Cargando ..",	true, false);
				new Thread(new Runnable(){
				public void run(){
				entra();
				pd.dismiss();
				}
				
				private void entra() {
		    	
 			  VerifyClient.this.finish();
 			 Intent intent = new Intent(getApplicationContext(),UsuarioPerfil.class);
				startActivity(intent);	
 			  
				}}).start();
			        }
	         	return true;
	         	
		    }
		    return false;
		}
		
		
		
		
		
		
		// BOTON FISICO HOME 
				/////
				
				 @SuppressWarnings("static-access")
				public void onUserLeaveHint() { // esto es solo para el boton HOME

				        super.onUserLeaveHint();
				        System.out.println("HOMEEEEEEEEE");
				        
				        ClientController clientCloseController = new ClientController();
						   
						   if(clientCloseController.closeClient()){
							  finish();
							  enVerifyClient.finishInicioPrincipal.finish();
							   System.out.println("SESION CERRADA");
						   }else{
							   Toast.makeText(this, "A ocurrido un error", Toast.LENGTH_SHORT).show();
						   }
				    }
				
				
				
				////////////////////////////
				 
				 public void retornar (View v){
					 if (checkConnectivity()==false) {
							
						 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
						 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			       	toast.show();
			       	this.finish();
				        	
				        }else{
				        	
				        	final ProgressDialog pd = ProgressDialog.show(this, null,"Cargando ..",	true, false);
							new Thread(new Runnable(){
							public void run(){
							entra();
							pd.dismiss();
							}

							private void entra() {
						VerifyClient.this.finish();
						
						
						Intent intent = new Intent(getApplicationContext(),UsuarioPerfil.class);
						startActivity(intent);	
							}}).start();
				        }
				  }
					
					//////////////////////////// conexion
					
				 private boolean checkConnectivity()
			     {
			         boolean enabled = true;
			  
			         ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
			         NetworkInfo info = connectivityManager.getActiveNetworkInfo();
			          
			         if ((info == null || !info.isConnected() || !info.isAvailable()))
			         {
			             enabled = false;
			         }
			         return enabled;         
			     }
}

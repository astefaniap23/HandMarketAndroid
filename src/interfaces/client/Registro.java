package interfaces.client;

import principal.interfaces.MainActivity;
import uno.menu.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import controller.ClientController;
import dto.ClientDTO;


public class Registro extends Activity{
	
	

	
	EditText campoUsu,campoCon;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registro);
		
		campoUsu = (EditText)findViewById(R.id.campoUsuario);
        campoCon = (EditText)findViewById(R.id.campoContrasena);     
        
        
    
	}
	
	
	 public void entrar(View v){
		
		   System.out.println("pwTEXT: " + campoCon.getText().toString());

		   final ClientDTO client = new ClientDTO();
		   client.setUser(campoUsu.getText().toString());
		   client.setPassword(campoCon.getText().toString());	   
		   System.out.println("pwCLIENT: " + client.getPassword().toString());

								   
		   if (campoUsu.length()==0 || campoCon.length()==0 ){
			   
			   Toast toast = Toast.makeText(getApplicationContext(), "Datos incompletos", Toast.LENGTH_SHORT);
			   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	        	toast.show();
			   
		   }else {
				if (checkConnectivity()==false) {
			   Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			 	toast.show();
				        	
				        }else{	 

					
					 ClientController clientLoginController = new ClientController();
					   

					   if(clientLoginController.loginClient(client)==2){
						  
						   final ProgressDialog pd = ProgressDialog.show(this, null, "Cargando ..",	true, false);
							new Thread(new Runnable(){
							public void run(){
							entra();
							pd.dismiss();
							}

							private void entra() {	
						   finish();
							Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
							startActivity(intent2);	
							}}).start();
							//Toast.makeText(this, "Bienvenido!!!", Toast.LENGTH_SHORT).show();
					   }else{
						   if(clientLoginController.loginClient(client)==1){
							   Toast toast = Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT);
							   toast.setGravity(Gravity.CENTER, Gravity.LEFT,Gravity.RIGHT);
					        	toast.show();
							   campoCon.setText("");
							   campoCon.requestFocus();
						   }else{						   
						  Toast toast = Toast.makeText(getApplicationContext(), "Cliente no registrado", Toast.LENGTH_SHORT);
						  toast.setGravity(Gravity.CENTER, Gravity.LEFT,Gravity.RIGHT);
				        	toast.show();
						   campoUsu.setText("");
						   campoCon.setText("");
						   campoUsu.requestFocus();
						   }
					   }					
	
				        }
				
			  
		}   
	}
	   	   
	   public void nuevoRegistro (View v){
		   
		   final ProgressDialog pd = ProgressDialog.show(this, "Cargando ..","Espere ..",	true, false);
			new Thread(new Runnable(){
			public void run(){
			entra();
			pd.dismiss();
			}

			private void entra() {	
			Intent intent = new Intent(getApplicationContext(),NuevoRegistro.class);
			startActivity(intent);	
			
			}}).start();
	   }
	   
	   public void forgotPassword (View v){
		   
		   final ProgressDialog pd = ProgressDialog.show(this, "Cargando ..","Espere ..",	true, false);
			new Thread(new Runnable(){
			public void run(){
			entra();
			pd.dismiss();
			}

			private void entra() {	
	   
		   Intent intent2 = new Intent(getApplicationContext(),ForgotPassword.class);
		   startActivity(intent2);
		   
			}}).start();
	   }
	   
	   public void forgotUser (View v){
		   
		   final ProgressDialog pd = ProgressDialog.show(this, "Cargando ..","Espere ..",	true, false);
			new Thread(new Runnable(){
			public void run(){
			entra();
			pd.dismiss();
			}

			private void entra() {	
	   
		   Intent intent3 = new Intent(getApplicationContext(),ForgotUser.class);
		   startActivity(intent3);
		   
			}}).start();
		   
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

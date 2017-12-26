package interfaces.client;

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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import controller.ClientController;
import dto.ClientDTO;

public class ForgotPassword extends Activity{
	
	
	EditText campoEnviarEmail;
	
	
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
			//enNuevoRegistro.finishRegistro.finish(); // Terminar el apilado de Registro.java
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.forgot_password);

	        campoEnviarEmail = (EditText) findViewById(R.id.campoSendEmail);
	        
	       
	        
	    }
		
		public void botonSendEmail (View v){
		
			
			
		 if (campoEnviarEmail.length()==0 ){
			 Toast toast = Toast.makeText(getApplicationContext(), "Introduzca el correo", Toast.LENGTH_SHORT);
	   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	         	toast.show();
		 }else{
		try {		
			if(campoEnviarEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")){
				
				if (checkConnectivity()==false) {
					
					 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
				 	toast.show();
					        	
					        }else{	
								
				//Toast.makeText(this, "email valido", Toast.LENGTH_SHORT).show();
			
				final ClientDTO client = new ClientDTO();
				
				client.setEmail(campoEnviarEmail.getText().toString());
				
			final ClientController clientController = new ClientController();
			Log.e("CLIENT ************", client.toString());
			
					
		

			if(clientController.sendPasswordEmail(client)) {
				
				//finish();
	//////////////Progress
				final ProgressDialog pd = ProgressDialog.show(this, null, "Cargando ..", true, false);
						new Thread(new Runnable(){
						public void run(){
						codigoEntra();
						pd.dismiss();
						}

						private void codigoEntra() {	
				finish();
				
						}}).start();
						
			}else{
				Log.e("ERROR SEND", "error al enviar");
				 Toast toast = Toast.makeText(getApplicationContext(), "Correo no registrado", Toast.LENGTH_SHORT);
		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		         	toast.show();
			}
			
				
					
					///////////////////////////////////////// PROGRESS

			
					        }
			
			}
			else{
				 Toast toast = Toast.makeText(getApplicationContext(), "Correo no valido", Toast.LENGTH_SHORT);
		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		         	toast.show();
	
			}
			
			
				} catch (Exception e) {
					// TODO: handle exception
					 Toast toast = Toast.makeText(getApplicationContext(), "A ocurrido un error", Toast.LENGTH_SHORT);
			   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			         	toast.show();
				}
		}
		 
				        		
		}
		
		// BOTON FISICO HOME 
					/////			
					 public void onUserLeaveHint() { // esto es solo para el boton HOME

					        super.onUserLeaveHint();
					        System.out.println("HOMEEEEEEEEE");	        
					        
								  finish();
								   System.out.println("Actividad cerrada");
							 
					    }		
					////////////////////////////
					 
					 public void retornar (View v){
							
							this.finish();
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

package interfaces.client;

import principal.interfaces.MainActivity;
import uno.menu.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class DeleteClient extends Activity {
	//private MainActivity enDeleteClient = new MainActivity();
	
	EditText campoClave;
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
			//enNuevoRegistro.finishRegistro.finish(); // Terminar el apilado de Registro.java
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.delete_client);

	        campoClave = (EditText) findViewById(R.id.campoCheckPasswordDelete);
			
	    }
		
		public void botonDeleteAccount (View v){
			

		 if (campoClave.length()==0 ){
			 Toast.makeText(this, "Introduzca la contraseña", Toast.LENGTH_SHORT).show();
		 }else{showDialogConfirmDelete();
		
		}
		}

		private void showDialogConfirmDelete() {
			// TODO Auto-generated method stub
			
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setMessage("¿Esta seguro que quiere eliminar su cuenta?")
		              .setCancelable(true)
		               .setPositiveButton("Si", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   
		                       dialog.cancel();
		                       if (checkConnectivity()==false) {
		           				
		                    		 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
		                    		 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		                    	 	toast.show();
		                    	 	/*
		                    		        	DeleteClient.this.finish();
		                    		        	MainActivity.finishInicioPrincipal.finish();
		                    		        	Intent intent = new Intent(getApplicationContext(),Registro.class);
		                    					startActivity(intent);*/	
		                    		        }else{	                 
		                       try {	
		                    	   
		                    	   ClientDTO client = new ClientDTO();
		                   		
		                   		client.setCheckPassword(campoClave.getText().toString());
		               			
		               			ClientController clientController = new ClientController();
		               			
		               			if(clientController.deleteClient(client)) {
		               				finish();
		               				Intent intent2 = new Intent(getApplicationContext(),Registro.class);
		               				startActivity(intent2);	 
		               					Toast.makeText(getApplicationContext(), "Cuenta eliminada", Toast.LENGTH_SHORT).show();
		               					
		               	            } else{
		               	            	Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
		               				}   
		               				} catch (Exception e) {
		               					// TODO: handle exception
		               					Toast.makeText(getApplicationContext(), "A ocurrido un error", Toast.LENGTH_SHORT).show();
		               				}	                       
		                       
		                    		        }
		                   }
		               })
		               .setNegativeButton("No", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                        dialog.cancel();                        
		                   }
		               });
		        AlertDialog alert = builder.create();
		        alert.setTitle("Hand Market");
		        alert.show();
		    
			
		}

		// BOTON FISICO HOME 
				/////
				
				 public void onUserLeaveHint() { // esto es solo para el boton HOME

				        super.onUserLeaveHint();
				        System.out.println("HOMEEEEEEEEE");
				        
				        ClientController clientCloseController = new ClientController();
						   
						   if(clientCloseController.closeClient()){
							  finish();
							  //enDeleteClient.finishInicioPrincipal.finish();
							   System.out.println("SESION CERRADA");
						   }else{
							   Toast.makeText(this, "A ocurrido un error", Toast.LENGTH_SHORT).show();
						   }
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


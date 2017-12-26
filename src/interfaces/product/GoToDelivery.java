package interfaces.product;

import java.text.DecimalFormat;

import interfaces.client.Registro;
import interfaces.client.UsuarioPerfil;
import principal.interfaces.MainActivity;
import controller.ClientController;
import controller.TotalController;
import dto.ClientDTO;
import dto.TotalDTO;
import uno.menu.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GoToDelivery extends Activity {
	 DecimalFormat df = new DecimalFormat("0.00");
	private MainActivity enGoToDelivery = new MainActivity();
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gotodelivery);
			
		if (checkConnectivity()==false) {
			
			 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
			 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		 	toast.show();
		 	this.finish();
			        	
			        }else{	
		TextView miDireccion = (TextView) findViewById(R.id.miDireccionDelivery);
		
		
		/// muestra la direccion de envio
		ClientController clientMostrarDireccion = new ClientController();
 	   ClientDTO client = new ClientDTO();
 	   if(clientMostrarDireccion.profileClient(client)){
 		  miDireccion.setText(client.getAddress());
 	   }
 	   
 	   /// muestra el total
 	  try {
	        TextView recuPrecio = (TextView) findViewById(R.id.recuPrecioDelivery);
	        TextView envio = (TextView) findViewById(R.id.costoEnvio);
	        TextView recuPrecioDelivery = (TextView) findViewById(R.id.recuTotalConDelivery);
	        
	        Bundle extras = getIntent().getExtras();
			
	        recuPrecio.setText("Sub-total: " + df.format(extras.getDouble("recupera_precio")) + " Bs.");
	        envio.setText("Costo de envío: 70,00 Bs.");
	        recuPrecioDelivery.setText("TOTAL: " + df.format(extras.getDouble("recupera_precio_delivery")) + " Bs.");
	        
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
			        }  
	}
	
	
	
	
	
	public void listoDelivery(View v){
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirmar compra")
              .setCancelable(true)
               .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {	                     
                	   if (checkConnectivity()==false) {
                		   dialog.cancel();
                			 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
                			 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                		 	toast.show();
                		 	
                		 	/*
                			        	GoToDelivery.this.finish();
                			        	MainActivity.finishInicioPrincipal.finish();
                			        	
                			        	Intent intent = new Intent(getApplicationContext(),Registro.class);
                						startActivity(intent);	*/
                			        }else{	
                			        	
                			        	
                	   TotalController clientEnviarFacturaDomicilio = new TotalController();
                	   TotalDTO total = new TotalDTO();
                	   
                	   if(clientEnviarFacturaDomicilio.enviarFacturaDomicilio(total)){
        				   
        			   }else{
        				   Log.e("ERROR", "Error al enviar");
        				   dialog.cancel();
        			   }
                	   dialog.cancel();
                	   finish();
                	   
                	   Toast toast = Toast.makeText(GoToDelivery.this, "Gracias por su compra", Toast.LENGTH_SHORT);
            		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                  	toast.show();


                              	  
                   }}
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
	
	
	
	// BOTONES FISICOS HOME Y BACK
			/////
			/////////////
			////////////////////////
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
			    	TotalController totalCloseController = new TotalController();
	 			   
	 			   if(totalCloseController.cancelaCompraBackButton()){
	 				  
	 			   }else{
	 				   Log.e("ERROR", "Error al cerrar sesion");
	 			   }
	 			  GoToDelivery.this.finish();
	 			 Intent intent = new Intent(getApplicationContext(),ShoppingCar.class);
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
			        TotalController totalCloseController = new TotalController();
			        
			        if(totalCloseController.cancelaCompraBackButton()){
						   System.out.println("FACTURA CERRADA ");
	 			   }
			        
					   if(clientCloseController.closeClient()){
						   
						   finish();
						   enGoToDelivery.finishInicioPrincipal.finish();
						  //enDeleteClient.finishInicioPrincipal.finish();
						   System.out.println("SESION CERRADA ");
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
				
				 TotalController totalCloseController = new TotalController();
	 			   
	 			   if(totalCloseController.cancelaCompraBackButton()){
	 				  
	 			   }else{
	 				   Log.e("ERROR", "Error al cerrar sesion");
	 			   }
	 			  GoToDelivery.this.finish();
	 			 Intent intent = new Intent(getApplicationContext(),ShoppingCar.class);
					startActivity(intent);	
					
					
					}}).start();
			  }}
				
				
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

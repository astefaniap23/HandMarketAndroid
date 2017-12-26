package interfaces.client;


import interfaces.product.Product;
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
import android.widget.TextView;
import android.widget.Toast;
import controller.ClientController;
import dto.ClientDTO;

public class UsuarioPerfil extends Activity {
	
	private MainActivity enUsuarioPerfil = new MainActivity();

	
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.usuario_perfil);	
	        
	if (checkConnectivity()==false) {
					
		 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
		 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	 	toast.show();
		        	this.finish();
		        	
		        }else{	        
	        TextView txtbienvenido = (TextView) findViewById(R.id.txtBienvenido);
	        TextView txtMiCorreo = (TextView) findViewById(R.id.txtMiCorreo);
	        TextView txtMiDireccion = (TextView) findViewById(R.id.txtMiDireccion);
	        TextView txtMiTelefono = (TextView) findViewById(R.id.txtMiTelefono);
	        
	        ClientDTO client = new ClientDTO();
	        	        
	   	 ClientController clientProfileController = new ClientController();
		   
		   if(clientProfileController.profileClient(client)){
			   
			   txtbienvenido.setText(client.getName() +" "+ client.getLastName());
			   txtMiCorreo.setText(client.getEmail());
			   txtMiDireccion.setText(client.getAddress());
			   txtMiTelefono.setText(client.getPhone());
		   }
	        }
		   
	 }

	 
	 public void cerrarSesion (View v) {
		 if (checkConnectivity()==false) {
				
			 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
			 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		 	toast.show();
			        	
			        }else{	
		 
		 
		 ClientController clientCloseController = new ClientController();
		   
		   if(clientCloseController.closeClient()){
			   enUsuarioPerfil.finishInicioPrincipal.finish();

			   finish();
				Intent intent = new Intent(getApplicationContext(),Registro.class);
				startActivity(intent);	
				
	
				 Toast toast = Toast.makeText(getApplicationContext(), "Sesion cerrada correctamente", Toast.LENGTH_SHORT);
				 toast.setGravity(Gravity.CENTER, Gravity.LEFT,Gravity.RIGHT);
			 	toast.show();
		   }else{

			   Toast toast = Toast.makeText(getApplicationContext(), "A ocurrido un error", Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.CENTER, Gravity.LEFT,Gravity.RIGHT);
			 	toast.show();
		   }
			        }
		   
	 
	 }
	 
	 public void botonModificarDatos (View v) {
		
		 
		 
		 final ProgressDialog pd = ProgressDialog.show(this, null,"Cargando ..",true, false);
			new Thread(new Runnable(){
			public void run(){
			entra();
			pd.dismiss();
			}

			private void entra() {
				
			
		 finish();
		 Intent intent2 = new Intent(getApplicationContext(),VerifyClient.class);
		startActivity(intent2);	 
		
			 }}).start();
			        
	 }
	 
	 
	 /*
	 public void botonDeleteClient (View v) {
		 finish();
		 Intent intent2 = new Intent(this,DeleteClient.class);
		startActivity(intent2);	 
	 }*/
	
	 
	// BOTON FISICO HOME 
		/////
		
		 @SuppressWarnings("static-access")
		public void onUserLeaveHint() { // esto es solo para el boton HOME
			 this.finish();
		        super.onUserLeaveHint();
		        System.out.println("HOMEEEEEEEEE");
		        
		        ClientController clientCloseController = new ClientController();
				   
				   if(clientCloseController.closeClient()){
					 
					  enUsuarioPerfil.finishInicioPrincipal.finish();
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

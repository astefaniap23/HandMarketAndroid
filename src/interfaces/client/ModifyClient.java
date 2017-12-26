package interfaces.client;


import interfaces.product.WishList;
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
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import controller.ClientController;
import dto.ClientDTO;

public class ModifyClient extends Activity{
	private MainActivity enModifyClient = new MainActivity();
	
	//public String LOG = "Radio Button";
	//public String LOG2 = "Radio Button 2";
	
	EditText contrasenaMod,direccionMod,correoMod,nombreMod,apellidoMod,telefonoMod;
	
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
			//enNuevoRegistro.finishRegistro.finish(); // Terminar el apilado de Registro.java
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.modificar_registro);
	        
	        if (checkConnectivity()==false) {
				
	       	 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
	       	 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	        	toast.show();
	       	        	this.finish();
	       	        
	       	        }else{	
	        /*
	        TextView nombreActual = (TextView) findViewById(R.id.nombreActual);
	        TextView apellidoActual = (TextView) findViewById(R.id.apellidoActual);
	        TextView direccionActual = (TextView) findViewById(R.id.direccionActual);
	        TextView telefonoActual = (TextView) findViewById(R.id.telefonoActual);
	        TextView correoActual = (TextView) findViewById(R.id.correoActual);
	        
	        ClientDTO client = new ClientDTO();
	        
		   	ClientController datoActualController = new ClientController();
		   	
		   	if(datoActualController.profileClient(client)){
				   
		   		nombreActual.setText(client.getName());
		   		apellidoActual.setText(client.getLastName());
		   		direccionActual.setText(client.getAddress());
		   		telefonoActual.setText(client.getPhone());
		   		correoActual.setText(client.getEmail());
			   }
	        */
	        
	        
	        contrasenaMod = (EditText)findViewById(R.id.campoContrasenaMod);
	        direccionMod = (EditText)findViewById(R.id.campoDireccionMod);
	        correoMod = (EditText)findViewById(R.id.campoCorreoMod);	        
	        nombreMod =(EditText)findViewById(R.id.campoNombreMod);
	        apellidoMod =(EditText)findViewById(R.id.campoApellidoMod);
	        telefonoMod = (EditText)findViewById(R.id.campoTelefonoMod);
	        
	        
	        ClientDTO client = new ClientDTO();
	        
		   	ClientController datoActualController = new ClientController();
		   	
		   	if(datoActualController.profileClient(client)){
				   
		   		nombreMod.setHint(client.getName());
		   		apellidoMod.setHint(client.getLastName());
		   		direccionMod.setHint(client.getAddress());
		   		telefonoMod.setHint(client.getPhone());
		   		correoMod.setHint(client.getEmail());
			   }
	       	        }
	    }
		
			
		
	public void botonGuardarModificacionUsuario (View v){
		
		
					
		ClientDTO client = new ClientDTO();
		

		client.setPassword(contrasenaMod.getText().toString());
		client.setAddress(direccionMod.getText().toString());
		client.setEmail(correoMod.getText().toString());
		client.setName(nombreMod.getText().toString());
		client.setLastName(apellidoMod.getText().toString());
		client.setPhone(telefonoMod.getText().toString());
		

		if (contrasenaMod.length()==0 && direccionMod.length()==0 && correoMod.length()==0 &&
				nombreMod.length()==0 && apellidoMod.length()==0 && telefonoMod.length()==0 ){
			
			 
			 Toast toast = Toast.makeText(getApplicationContext(), "Sin datos a cambiar", Toast.LENGTH_SHORT);
	   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	         	toast.show();
			 
		}else{
	
			try {
				if(correoMod.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+") || correoMod.length()==0){
					//Toast.makeText(this, "email valido", Toast.LENGTH_SHORT).show();
					if (checkConnectivity()==false) {
						
						 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
						 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
					 	toast.show();
						        	
						        }else{	
			ClientController clientController = new ClientController();
			
			if(clientController.modifyClient(client)=="z") {
				
				finish();
				
				Intent intent = new Intent(getApplicationContext(),UsuarioPerfil.class);
				startActivity(intent);	
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Datos modificados", Toast.LENGTH_SHORT);
		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		         	toast.show();
		         	
		         	
			}
			
			else if(clientController.modifyClient(client)=="b") {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Nombre solo con letras, reintente", Toast.LENGTH_SHORT);
		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		         	toast.show();
					
				
            } 
			else if(clientController.modifyClient(client)=="c") {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Apellido solo con letras, reintente", Toast.LENGTH_SHORT);
		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		         	toast.show();
					
				
           } 
			
			else if(clientController.modifyClient(client)=="a") {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Contraseña sin espacios vacios, reintente", Toast.LENGTH_SHORT);
		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		         	toast.show();
					
				
          } else if(clientController.modifyClient(client)=="d") {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Correo existente, reintente", Toast.LENGTH_SHORT);
		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		         	toast.show();
					
				
          } 

			else if(clientController.modifyClient(client)=="h") {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Datos incorrectos, reintente", Toast.LENGTH_SHORT);
		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		         	toast.show();
					
				
          } 
			
			else if(clientController.modifyClient(client)=="e" ) {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Intente nuevamente", Toast.LENGTH_SHORT);
		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		         	toast.show();
					
				
         } 
			
			
						        }
			
						        
			}else{
				
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

	
	//------------------------------Barra Top 
	public void retornar (View v){
		
		this.finish();
		
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
		Intent intent = new Intent(getApplicationContext(),UsuarioPerfil.class);
		startActivity(intent);	
		
		}}).start();
	        }
  }
  //---------------------------------------- 

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
	    	
			  ModifyClient.this.finish();
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
						  enModifyClient.finishInicioPrincipal.finish();
						   System.out.println("SESION CERRADA");
					   }else{
						   Toast.makeText(this, "A ocurrido un error", Toast.LENGTH_SHORT).show();
					   }
			    }
					
			////////////////////////////
			 
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

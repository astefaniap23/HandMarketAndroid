package interfaces.client;

import uno.menu.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import controller.ClientController;
import dto.ClientDTO;

public class NuevoRegistro extends Activity{
	
	private static final String sexoM = "masculino";
	private static final String sexoF = "femenino";
	private String sexo;
	
	EditText nombre,apellido,usuario,contrasena,direccion,correo,telefono;
	RadioButton radioM,radioF;
	
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
			//enNuevoRegistro.finishRegistro.finish(); // Terminar el apilado de Registro.java
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.nuevo_registro);
	        
	        nombre = (EditText)findViewById(R.id.campoNombreNuevo);
	        apellido = (EditText)findViewById(R.id.campoApellidoNuevo);
	        radioM = (RadioButton) findViewById(R.id.masculino);
	        radioF = (RadioButton) findViewById(R.id.femenino);
	        usuario = (EditText)findViewById(R.id.campoUsuarioNuevo);
	        contrasena = (EditText)findViewById(R.id.campoContrasenaNuevo);
	        direccion = (EditText)findViewById(R.id.campoDireccionNuevo);
	        correo = (EditText)findViewById(R.id.campoCorreoNuevo);	
	        telefono = (EditText)findViewById(R.id.campoTelefonoNuevo);

	    }
		
			
		
	public void enviarNuevoUsuario (View v){	
		
			
		if (radioM.isChecked()==true) {
			sexo = sexoM;
			//Toast.makeText(this, "Es masculino",Toast.LENGTH_SHORT).show();
		}
		
		if (radioF.isChecked()==true) {
			sexo= sexoF;
			//Toast.makeText(this, "Es femenino",Toast.LENGTH_SHORT).show();
			}	
		
		

		ClientDTO client = new ClientDTO();
		
		client.setName(nombre.getText().toString());
		client.setLastName(apellido.getText().toString());
		client.setGender(sexo);
		client.setUser(usuario.getText().toString());
		client.setPassword(contrasena.getText().toString());
		client.setAddress(direccion.getText().toString());
		client.setEmail(correo.getText().toString());
		client.setPhone(telefono.getText().toString());
		
		
        
		if (nombre.length()==0 || apellido.length()==0 || usuario.length()==0 || contrasena.length()==0 
				|| direccion.length()==0 || telefono.length()==0 || correo.length()==0 ){
			
			 
			 Toast toast = Toast.makeText(NuevoRegistro.this, "Datos incompletos", Toast.LENGTH_SHORT);
	   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	         	toast.show();
			 
		}else{
			if(correo.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")){
				//Toast.makeText(this, "email valido", Toast.LENGTH_SHORT).show();
				if (checkConnectivity()==false) {
					
					 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
				 	toast.show();
	
					        }else{	
				ClientController clientController = new ClientController();
				
				if(clientController.createClient(client)=="z") {
					this.finish();
					
					 Toast toast = Toast.makeText(NuevoRegistro.this, "Registrado correctamente", Toast.LENGTH_SHORT);
			   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			         	toast.show();
					
	            } 
	            	
				else if(clientController.createClient(client)=="b") {
	    				
	   				 Toast toast = Toast.makeText(getApplicationContext(), "Nombre solo con letras, reintente", Toast.LENGTH_SHORT);
	   		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	   		         	toast.show();
	   					
	   				
	               } else if(clientController.createClient(client)=="c") {
	   				
	   				 Toast toast = Toast.makeText(getApplicationContext(), "Apellido solo con letras, reintente", Toast.LENGTH_SHORT);
	   		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	   		         	toast.show();
	   					
	   				
	              } 
	   			
	               else if(clientController.createClient(client)=="a") {
	   				
	   				 Toast toast = Toast.makeText(getApplicationContext(), "Contraseña sin espacios vacios, reintente", Toast.LENGTH_SHORT);
	   		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	   		         	toast.show();
	   					
	   				
	             } 
	   			
	   			
	               else if(clientController.createClient(client)=="d") {
	   				
	   				 Toast toast = Toast.makeText(getApplicationContext(), "Usuario sin espacios vacios, reintente", Toast.LENGTH_SHORT);
	   		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	   		         	toast.show();
	   					
	   				
	             } 
	   			
	               else if(clientController.createClient(client)=="u") {
	   				
	   				 Toast toast = Toast.makeText(getApplicationContext(), "Usuario existente, reintente", Toast.LENGTH_SHORT);
	   		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	   		         	toast.show();
	   					
	   				
	              } 
				
	               else if(clientController.createClient(client)=="m") {
		   				
		   				 Toast toast = Toast.makeText(getApplicationContext(), "Correo existente, reintente", Toast.LENGTH_SHORT);
		   		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		   		         	toast.show();
		   					
		   				
		              } 
	   			
	               else if(clientController.createClient(client)=="h") {
	   				
	   				 Toast toast = Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT);
	   		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	   		         	toast.show();
	   					
	   				
	             } 
	   			
	               else if(clientController.createClient(client)=="e" ) {
	   				
	   				 Toast toast = Toast.makeText(getApplicationContext(), "Intente nuevamente", Toast.LENGTH_SHORT);
	   		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	   		         	toast.show();
	   					
	   				
	            } 
	  	         	
	  	         	
	  	         	
	  	         	
	  	         	
	  	         	
	  	         	
				      
					        }
			}else{
				
				
				 Toast toast = Toast.makeText(NuevoRegistro.this, "Correo invalido", Toast.LENGTH_SHORT);
		   		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		         	toast.show();
				
			}
			
			
        	/*	
        	//// PROGRESS BAR  ////////////////////////////////////////////////////////
            	////
            	
            	final ProgressDialog dialog = ProgressDialog.show(NuevoRegistro.this, "", "Creando usuario..", true);
                dialog.setCancelable(true);
                new Thread(new Runnable() {  
                      @Override
                      public void run() {
                            // TODO Auto-generated method stub
                            try
                            {
                            Thread.sleep(1400);
                            }catch(Exception e){}
                            dialog.dismiss();
                            
                      }
                }).start();
                
                /////
                //// FIN PROGRESS BAR  ////////////////////////////////////////////////////////////////
                */
                // successfully created product
                
		} 
			        
	}

	
	//------------------------------Barra Top 
	public void retornar (View v){
		
		this.finish();
  }
	
	
  //---------------------------------------- 
	
	// BOTON FISICO HOME 
			/////			
			 public void onUserLeaveHint() { // esto es solo para el boton HOME

			        super.onUserLeaveHint();
			        System.out.println("HOMEEEEEEEEE");	        
			        
						  finish();
						   System.out.println("Actividad cerrada");
					 
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

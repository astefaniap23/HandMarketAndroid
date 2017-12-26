package principal.interfaces;

import interfaces.client.Registro;
import interfaces.client.UsuarioPerfil;
import interfaces.product.Product;
import interfaces.product.RecordBill;
import interfaces.product.ShoppingCar;
import interfaces.product.WishList;
import uno.menu.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path.FillType;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.Toast;
import controller.ClientController;


public class MainActivity extends Activity {
	public static Activity finishInicioPrincipal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        finishInicioPrincipal=this; // APILADO
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (checkConnectivity()==false) {
			
       	 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
       	 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
        	toast.show();
        	/*
       	        	this.finish();
       	        	Intent intent = new Intent(getApplicationContext(),Registro.class);
       				startActivity(intent);	*/
       	        }
                
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        super.onCreateOptionsMenu(menu);        
        MenuInflater Inflater = getMenuInflater();  // llenar la opcion de menu todos los items declarados en el main.xml de la carpeta menu
        Inflater.inflate(R.menu.main, menu);
        
        return true;
        
        
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.favoritos:
			Intent a = new Intent("android.intent.action.FAVORITOS");
			startActivity(a);
			break;
		case R.id.productos:
			Intent productos = new Intent(getApplicationContext(),Product.class);
			startActivity(productos);
			break;
		case R.id.buscar:
			//Intent b = new Intent("android.intent.action.BUSCAR");
			//startActivity(b);
			break;
		case R.id.carrito:
			Intent c = new Intent(getApplicationContext(),ShoppingCar.class);
			startActivity(c);
			break;
		case R.id.usuario:
		Intent intent = new Intent(this,UsuarioPerfil.class);
			startActivity(intent);	
			break;	
		case R.id.salir:
			this.finish();
			break;
		default:
			break;
		}		
		return false;
	}*/
	
	public void favorito (View v){
		 if (checkConnectivity()==false) {
				
			 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
			 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
        	toast.show();
	        	
	        }else{
		final ProgressDialog pd = ProgressDialog.show(this, null,"Cargando favoritos ..",	true, false);
		new Thread(new Runnable(){
		public void run(){
		entra();
		pd.dismiss();
		}

		private void entra() {			

			Intent intent = new Intent(getApplicationContext(),WishList.class);
			startActivity(intent);	
			
		}}).start();
	        }
		
	}
	
	
	public void productos (View v){	
		 if (checkConnectivity()==false) {
				
	        	Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
	        	toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	        	toast.show();
	        	
	        }else{
		
		
		final ProgressDialog pd = ProgressDialog.show(this, null, "Cargando productos ..",	true, false);
										new Thread(new Runnable(){
										public void run(){
										entra();
										pd.dismiss();
										}

										private void entra() {			

											Intent intent = new Intent(getApplicationContext(),Product.class);
											startActivity(intent);	
											
										}}).start();
	        }
	}
	
	
	public void registro(View v) {	
		
		 if (checkConnectivity()==false) {
				
	        	Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
	        	toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	        	toast.show();
	        	
	        }else{
	final ProgressDialog pd = ProgressDialog.show(this, null, "Cargando perfil ..",	true, false);
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
	
	public void shoppingcar (View v){
		 if (checkConnectivity()==false) {
				
			 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
			 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	        	toast.show();
	        	
	        }else{
		
		final ProgressDialog pd = ProgressDialog.show(this, null, "Cargando carrito ..",	true, false);
		new Thread(new Runnable(){
		public void run(){
		entra();
		pd.dismiss();
		}

		private void entra() {			

			
			Intent intent = new Intent(getApplicationContext(),ShoppingCar.class);
			startActivity(intent);					

			
		}}).start();
	        }
		
	}
	
	public void out (View v){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea salir de la aplicacion?")
              .setCancelable(true)
               .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
 
                       dialog.cancel();
                       
                       if (checkConnectivity()==false) {

              	        	MainActivity.this.finish();

              	        }else{
              	        	

              	  		final ProgressDialog pd = ProgressDialog.show(MainActivity.this, null, "Saliendo ..",	true, false);
              	  		new Thread(new Runnable(){
              	  		public void run(){
              	  		entra();
              	  		pd.dismiss();
              	  		}

              	  		private void entra() {	
                       ClientController clientCloseController = new ClientController();
        			   
        			   if(clientCloseController.closeClient()){
        				   
        			   }else{
        				   Log.e("ERROR", "Error al cerrar sesion");
        			   }
                       finish();
              	        
              	  	}}).start();
                   }
               }})
               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();                        
                   }
               });
        AlertDialog alert = builder.create();
        alert.setTitle("Hand Market");
        alert.show();
	        
	}
	
	
	
	
	
	
	public void buttonRecord (View v)
	{
		 if (checkConnectivity()==false) {
				
			 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
			 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	        	toast.show();
	        	
	        }else{
		final ProgressDialog pd = ProgressDialog.show(this, null, "Cargando historial ..", true, false);
		new Thread(new Runnable(){
		public void run(){
		entra();
		pd.dismiss();
		}

		private void entra() {			

			Intent intent = new Intent(getApplicationContext(),RecordBill.class);
			startActivity(intent);	
			
		}}).start();
	        }
	}
	
	
	
	
	
	
	// BOTONES FISICOS HOME Y BACK
	/////
	/////////////
	////////////////////////
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if(keyCode == KeyEvent.KEYCODE_BACK) // boton fisico BACK del telefono
	    {
	    	showDialogBack();
         	return true;
	    }
	    
	    if(keyCode == KeyEvent.KEYCODE_HOME) // boton fisico BACK del telefono
	    {
	    	ClientController clientCloseController = new ClientController();        			   
			   if(clientCloseController.closeClient()){      				   
			   }else{
				   Log.e("ERROR", "Error al cerrar sesion");
			   }
            finish();   
         	return true;
	    }
	     
	    return false;
	}
	
		
	void showDialogBack(){ //boton back declarado en el keycode boolean
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea salir de la aplicacion?")
              .setCancelable(true)
               .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                       
                       if (checkConnectivity()==false) {

             	        	MainActivity.this.finish();

             	        }else{
             	        	
             	        	final ProgressDialog pd = ProgressDialog.show(MainActivity.this, null, "Saliendo ..",	true, false);
                  	  		new Thread(new Runnable(){
                  	  		public void run(){
                  	  		entra();
                  	  		pd.dismiss();
                  	  		}

                  	  		private void entra() {	
                       ClientController clientCloseController = new ClientController();
        			   
        			   if(clientCloseController.closeClient()){
        				   
        			   }else{
        				   Log.e("ERROR", "Error al cerrar sesion");
        			   }
                       finish();
                   }}).start();
                   }
               }})
               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();                        
                   }
               });
        AlertDialog alert = builder.create();
        alert.setTitle("Hand Market");
        alert.show();
    }
	
	 public void onAttachedToWindow() {
	        super.onAttachedToWindow();
	        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);           
	    }
	     
	 /*
	    public void onUserLeaveHint() { // solo para home
	        // do stuff
	        super.onUserLeaveHint();
	        System.out.println("HOMEEEEEEEEE");
	    }*/
	
	
	
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

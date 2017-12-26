package interfaces.product;


import interfaces.client.Registro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import controller.ClientController;

import principal.interfaces.MainActivity;
import uno.menu.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ProductDetails extends  Activity {
	private MainActivity enProducts = new MainActivity();
	int request_code = 1;
	DecimalFormat df = new DecimalFormat("0.00");
	
  	 
	 public void onCreate(Bundle bundle) {
	        super.onCreate(bundle);
	        setContentView(R.layout.details_product);
	        
	        if (checkConnectivity()==false) {
				
	       	 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
	       	 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	        	toast.show();
	        	
	       	        	this.finish();
	       	        	/*MainActivity.finishInicioPrincipal.finish();
	       	        	Intent intent = new Intent(getApplicationContext(),Registro.class);
	       				startActivity(intent);	*/
	       	        }else{	
	        Bundle extras = getIntent().getExtras();


		
	        if(extras==null){
	        	return;
	        }

	        
	        final TextView nombreu = (TextView) findViewById(R.id.nombreProductoDetalle);
	        final TextView preciou = (TextView) findViewById(R.id.precioProductoDetalle);
	        final TextView detalleu = (TextView) findViewById(R.id.detalleProductoTexto);
	    	final ImageView imagenProductoDetalle = (ImageView)findViewById(R.id.imagenProductoDetalle);
	        
	        String nombre =extras.getString("nombre");
	        Double precio =extras.getDouble("precio");
	        String rutaImagen = extras.getString("rutaImagen");
	        String detalle =extras.getString("detalle");
	        
	        if (nombre!= null && precio!= null){
	        	
	        	//Toast.makeText(ProductDetails.this, nombre  + " a " , Toast.LENGTH_SHORT).show();	
	        	nombreu.setText(nombre);
	        	
	         	preciou.setText("Precio: "+ String.valueOf(df.format(precio)) + " Bs.");
	         	detalleu.setText(detalle);
	         	
	         	Bitmap bmp = null;
		        try {
		            URL url = new URL(rutaImagen);
		            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		        } catch (MalformedURLException e) {

		        }catch (IOException e) {

		        }
		       //imgView.setImageBitmap(bmp); 
		        imagenProductoDetalle.setImageBitmap(bmp);
	         	
	        }
	        
	        
	       	        }
  }		
	 
	 
	 public void finish() {
		 Intent data= new Intent();
		// data.putExtra("returnKey1","Lo hemos logrado");
		// data.putExtra("returnKey2","Este es otro valor de retorno");
		 setResult(RESULT_OK, data);
		 super.finish();		 
		 
	}
	 
		 
	    
	 //------------------------------Barra Top 
		public void retornar (View v){
			
			this.finish();
	  }
		
	
	//-------------------------------------------	
/*
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
				Intent b = new Intent("android.intent.action.BUSCAR");
				startActivity(b);
				break;
			case R.id.carrito:
				Intent c = new Intent("android.intent.action.carrito");
				startActivity(c);
				break;
				
			case R.id.usuario:
			Intent intent = new Intent(this,Registro.class);
				startActivity(intent);	
				break;	
				
			case R.id.salir:
				this.finish();
				break;
			default:
				break;
			}	
			return false;
		}
		
		public void carrito (View v){
			Intent intent = new Intent(this,ShoppingCar.class);
			startActivity(intent);
		}*/
		
		// BOTON FISICO HOME 
		/////
		
		 @SuppressWarnings("static-access")
		public void onUserLeaveHint() { // esto es solo para el boton HOME

		        super.onUserLeaveHint();
		        System.out.println("HOMEEEEEEEEE");
		        
		        ClientController clientCloseController = new ClientController();
				   
				   if(clientCloseController.closeClient()){
					  finish();
					  enProducts.finishInicioPrincipal.finish();
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

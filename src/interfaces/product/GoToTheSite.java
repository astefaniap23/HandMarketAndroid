package interfaces.product;

import interfaces.client.Registro;

import java.text.DecimalFormat;
import java.util.ArrayList;

import principal.interfaces.MainActivity;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import controller.ClientController;
import controller.GoToTheSiteController;
import controller.TotalController;
import dto.SitesDTO;

public class GoToTheSite extends Activity  {
	 DecimalFormat df = new DecimalFormat("0.00");
	
	private MainActivity enGoToTheSites = new MainActivity();
	

	private RadioButton radioButton_pulsado;
	
	ArrayList<SitesDTO> arrayOfWebDataSites = new ArrayList<SitesDTO>();
	public String idSucu;
	

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gotothesite);

		if (checkConnectivity()==false) {
			
			 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
			 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		 	toast.show();
			        	this.finish();
			        	MainActivity.finishInicioPrincipal.finish();
			        	Intent intent = new Intent(getApplicationContext(),Registro.class);
						startActivity(intent);	
			        }else{	
		arrayOfWebDataSites=GoToTheSiteController.searchSites(); 

		
		
       // final TextView lblMensaje = (TextView)findViewById(R.id.LblMensaje);
        final ListView CmbOpcionesSites = (ListView)findViewById(R.id.CmbOpcionesSites);
        
        ArrayAdapter<SitesDTO> adapter = new Adaptador();
        CmbOpcionesSites.setAdapter(adapter); 

		
		
		try {
	        TextView recuPrecio = (TextView) findViewById(R.id.recuPrecio);
	        Bundle extras = getIntent().getExtras();
			
	        recuPrecio.setText("Total: " +  df.format(extras.getDouble("recupera_precio")) +" Bs.");
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	        //////// radio sucursal
	        
			        }			
      
}
	
	
	
	public 	class Adaptador extends ArrayAdapter<SitesDTO>{
		Adaptador()
		{
			super(GoToTheSite.this, R.layout.gotothesiteitem_layout, arrayOfWebDataSites);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Se tiene que tener un View para que esto funcione
			
		    
			
			ViewHolder holder;
			
			if(convertView == null){
				
		
				LayoutInflater inflater= getLayoutInflater();
				convertView = inflater.inflate(R.layout.gotothesiteitem_layout, null);
				
				holder = new ViewHolder (convertView);
				convertView.setTag(holder);
				
	
				
				
				
				   }
				   else {
				holder=(ViewHolder)convertView.getTag();
			}
			
			
			
			holder.populateFrom(arrayOfWebDataSites.get(position));
			return (convertView);
			
		}
		
	}
		
	class ViewHolder {
	

		public TextView nombreText = null;
		public TextView textView1 = null;
		public RadioButton rb=null;
		public TextView addressText=null;
	
		ViewHolder(View gotothesiteitem_layout) {
			
			 nombreText = (TextView) gotothesiteitem_layout.findViewById(R.id.item_nombreMake);
			 addressText = (TextView) gotothesiteitem_layout.findViewById(R.id.addressText);
			 rb = (RadioButton)gotothesiteitem_layout.findViewById(R.id.rb);
			 textView1 = (TextView) gotothesiteitem_layout.findViewById(R.id.textView1);
			
			
			
		}
		
		
	
		void populateFrom(SitesDTO r){
			
		
			textView1.setText(r.getId_place().toString());
		   
			
			
			rb.setOnClickListener(new OnClickListener() {
			   
			   	 	
			    	 public void onClick(View v) {
			    		 
			    		 idSucu="";

			    		 if (radioButton_pulsado!=null)

			    		 radioButton_pulsado.setChecked(false);
			    		 
			    		 radioButton_pulsado = (RadioButton) v;
			    		
			    		System.out.println( "nombre: " + nombreText.getText().toString());
			    		
			    		idSucu=textView1.getText().toString();
			    		
			    		
				      
			     }
			    } 
			    );
			
			
			
			nombreText.setText(r.getName());
			addressText.setText(r.getAddress());
			
			//System.out.println(r.isSelected());

		}

	}
	
	

		//////////// fin de spinner y seleccion
			
			
			public void listo(View v){
				
				
				if(radioButton_pulsado==null || radioButton_pulsado.isChecked()==false){

					Toast toast = Toast.makeText(GoToTheSite.this, "Seleccione una opcion", Toast.LENGTH_SHORT);
					   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			        	toast.show();
				}
	       		else{
				 AlertDialog.Builder builder = new AlertDialog.Builder(this);
			        builder.setMessage("Confirmar compra")
			              .setCancelable(true)
			               .setPositiveButton("Si", new DialogInterface.OnClickListener() {
			                   public void onClick(final DialogInterface dialog, int id) {
			                	   
			                	   if (checkConnectivity()==false) {
			                		   dialog.cancel();
			                			 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
			                			 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			                		 	toast.show();
			                			        	
			                			        }else{	
			                			        	
			                	   GoToTheSiteController clientSucursal = new GoToTheSiteController();	
			                	   SitesDTO site = new SitesDTO(idSucu, null, null);
			                	   site.setId_place(site.getId_place());
			                	   
			                	   if (clientSucursal.enviarFacturaRetiroTienda(site)){
		                		   
			                		   
			                	   }
			                	   
			        			   else{
			        				   Log.e("ERROR", "Error al enviar");
			        				   dialog.cancel();
			        			   }
			                	   dialog.cancel();
			                	   finish();
			                	  
			                	   Toast toast = Toast.makeText(GoToTheSite.this, "Gracias por su compra", Toast.LENGTH_SHORT);
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
				
	       		//}
				}
			}
			
			
	public void onNothingSelected(AdapterView<?> arg0) {
		
	
		
		 Toast toast = Toast.makeText(GoToTheSite.this, "Debe seleccionar una opción", Toast.LENGTH_SHORT);
		   toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
      	toast.show();
		 
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
 			 
 				GoToTheSite.this.finish();
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
					   enGoToTheSites.finishInicioPrincipal.finish();
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
 			  GoToTheSite.this.finish();
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

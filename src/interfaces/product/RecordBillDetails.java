package interfaces.product;


import interfaces.client.Registro;

import java.text.DecimalFormat;
import java.util.ArrayList;

import principal.interfaces.MainActivity;
import controller.ClientController;
import controller.RecordBillDetailsController;
import controller.TotalController;
import uno.menu.R;
import dto.RecordBillDetailsDTO;
import dto.TotalDTO;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RecordBillDetails extends Activity {
	private MainActivity enProducts = new MainActivity();
	int request_code = 1;
	DecimalFormat df = new DecimalFormat("0.00");
	
	ArrayList<RecordBillDetailsDTO> arrayOfWebDataDetail = new ArrayList<RecordBillDetailsDTO>();			


	public void onCreate(Bundle bunble) {

		
		super.onCreate(bunble);
		setContentView(R.layout.recordbilldetails);
		
		if (checkConnectivity()==false) {
			
			 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
			 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		 	toast.show();
		 	
			        	this.finish();
			        /*	MainActivity.finishInicioPrincipal.finish();
			        	Intent intent = new Intent(getApplicationContext(),Registro.class);
						startActivity(intent);	*/
			        }else{	
        Bundle extras = getIntent().getExtras();		
      // arrayOfWebDataDetail = RecordBillDetailsController.searchRecord(null);	
        
        

	        if(extras==null){
	        	return;
	        	
	        	
	        }
	
	
	        
		    ListView listView = (ListView)findViewById(R.id.wishlistlistView);
		  //  TextView textView3 = (TextView) findViewById(R.id.textView3);
		 //   TextView nombreText = (TextView) findViewById(R.id.item_nombreMake);
		  //  TextView precioText = (TextView) findViewById(R.id.item_precioMake);
		    //TextView textView1 = (TextView) findViewById(R.id.textView1);
		   // CheckBox checkBox1= (CheckBox) findViewById(R.id.checkBox1);


	        String id =extras.getString("id");
	        System.out.println("ID:" +id);
	        
	       arrayOfWebDataDetail= RecordBillDetailsController.searchRecord(id);
	       
			 
			ArrayAdapter<RecordBillDetailsDTO> adapter = new Adaptador();
			listView.setAdapter(adapter);
			
	////////TOTAL 
	    	
			
	    	TextView textTotalProductos = (TextView) findViewById(R.id.totalProductosHistorial);
		       TextView textEnvio = (TextView) findViewById(R.id.deliveryHistorial);
		       TextView textTotal = (TextView) findViewById(R.id.totalHistorial);
		       
	    	TotalDTO total = new TotalDTO();
			
			TotalController totalController = new TotalController();
			
				if(totalController.totalHistorial(total, id)){
					

					
					if(total.getTotalHistorialDeli()>0){
						
						double envio=70.00;		
						textTotalProductos.setText("Sub-total: "+ df.format(total.getTotalHistorial())+ " Bs.");
						textEnvio.setText("Costo de envio: " +df.format(envio)+ " Bs.");
						
						textTotal.setText("TOTAL: " + df.format(total.getTotalHistorialDeli())+ " Bs.");
					}else{
						
						textTotalProductos.setText("TOTAL: "+ df.format(total.getTotalHistorial())+ " Bs.");
						
						textEnvio.setVisibility(View.GONE);
						textTotal.setVisibility(View.GONE);
						
					}
					
				}
				//////////////// FIN TOTAL
		
			        }
		}
	
	
				 public void finish() {
					 Intent data= new Intent();
					 data.putExtra("returnKey1","Lo hemos logrado");
					 data.putExtra("returnKey2","Este es otro valor de retorno");
					 setResult(RESULT_OK, data);
					 super.finish();		 
					 
				}
	 
	
				public 	class Adaptador extends ArrayAdapter<RecordBillDetailsDTO>{
					Adaptador()
					{
						super(RecordBillDetails.this, R.layout.detailrecordbilllist_item_layout, arrayOfWebDataDetail);
					}
			
					@Override
					public View getView(int position, View convertView, ViewGroup parent) {
						// Se tiene que tener un View para que esto funcione
						
					    
						
						ViewHolder holder;
						
						if(convertView == null){
							
					
							LayoutInflater inflater= getLayoutInflater();
							convertView = inflater.inflate(R.layout.detailrecordbilllist_item_layout, null);
							
							holder = new ViewHolder (convertView);
							convertView.setTag(holder);
														
							   }
							   else {
							holder=(ViewHolder)convertView.getTag();
						}
						
						
						
						holder.populateFrom(arrayOfWebDataDetail.get(position));
						return (convertView);
						
					}
					
				}
					
				class ViewHolder {
				
					//public ImageView imagen =null; 
					public TextView nombreText = null;
					public TextView precioText = null;
					//public TextView textView1 = null;
					//public TextView textView2 = null;
					//public CheckBox checkBox1 = null;			
					
					
					ViewHolder(View detailrecordbilllist_item_layout) {
						
					
						 nombreText = (TextView) detailrecordbilllist_item_layout.findViewById(R.id.item_nombreMake);
						 precioText = (TextView) detailrecordbilllist_item_layout.findViewById(R.id.item_precioMake);
						// textView1 = (TextView) detailrecordbilllist_item_layout.findViewById(R.id.textView1);
						
					//	textView1.setVisibility(0);
						//textView2.setVisibility(0);
					
						
					}
					
					void populateFrom(RecordBillDetailsDTO r){
						
						//textView1.setText(String.valueOf(r.getId()));
						//textView1.setVisibility(TextView.INVISIBLE);
						//textView2.setText(String.valueOf(r.getFecha()));
						//textView2.setVisibility(TextView.INVISIBLE);
						
				       
						nombreText.setText(r.getNombre());
						precioText.setText(df.format(r.getPrecio()));
						//System.out.println(r.isSelected());
			
					}

		}
			
				
				public void retornar (View v){
					
					this.finish();
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

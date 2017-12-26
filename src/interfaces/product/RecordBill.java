package interfaces.product;


import interfaces.client.Registro;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import principal.interfaces.MainActivity;

import config.AplicationConfig;
import config.CustomHttpClient;

import controller.RecordBillController;

import dto.RecordBillDTO;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



public class RecordBill  extends Activity{
	

	private static final int REQUEST_CODE=10;
	
	ArrayList<RecordBillDTO> arrayOfWebData = new ArrayList<RecordBillDTO>();
	//ArrayList<RecordBillDetailsDTO> arrayOfWebDataDetail=new ArrayList<RecordBillDetailsDTO>();
	 ArrayList<String> valorCheck = new ArrayList<String>();

	public void onCreate(Bundle savedInstanceState) {

	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.record);
	
	if (checkConnectivity()==false) {
		
		 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
		 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
	 	toast.show();
		        	this.finish();
		        	
		        }else{	
	
	arrayOfWebData = RecordBillController.searchRecord();
//arrayOfWebDataDetail= RecordBillDetailsController.searchRecord(null);
	
	
	
    ListView listView = (ListView)findViewById(R.id.wishlistlistView);
    ImageView imagen =(ImageView) findViewById(R.id.item_icon);
    TextView nombreText = (TextView) findViewById(R.id.item_nombreMake);
    TextView precioText = (TextView) findViewById(R.id.item_precioMake);
    TextView textView1 = (TextView) findViewById(R.id.textView1);
    CheckBox checkBox1= (CheckBox) findViewById(R.id.checkBox1);


	       
 
		ArrayAdapter<RecordBillDTO> adapter = new Adaptador();
		listView.setAdapter(adapter);
		
		
		        }
		
		}
	
	
			public void detailbillbutton (View v){
				
				if (checkConnectivity()==false) {
					
					 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
				 	toast.show();
					        	
					        }else{	
		
				
				TextView item_nombreMake = (TextView) v;
				RelativeLayout relativeLayout =  (RelativeLayout) item_nombreMake.getParent();
				TextView idText = (TextView) relativeLayout.findViewById(R.id.textView1);
				
				//Log.e("BILL ", item_nombreMake.toString());
				
				//Toast.makeText(Productos.this, "a " + idText.getText().toString(), Toast.LENGTH_LONG).show();	
				
				Iterator<RecordBillDTO> i = arrayOfWebData.iterator();
			//	RecordBillDetailsDTO recordbilldetails = new RecordBillDetailsDTO();
				final Intent detalles = new Intent(getApplicationContext(),RecordBillDetails.class);
				 //CategorySelectedDTO c = null;
				
				//Intent c = new Intent(getApplicationContext(),RecordBillDetails.class);
		
				
				while(i.hasNext())
				{
					
					RecordBillDTO p =  i.next();
		
					if(p.getId() == idText.getText())
					{
						//Toast.makeText(RecordBill.this, "Usted ha seleccionado: "+p.getId(), Toast.LENGTH_SHORT).show();	
					//	RecordBillDetailsController.searchRecord(p.getId());
						
								detalles.putExtra("id",p.getId());
								
							
								break;
								
							}
						
				}
				

				final ProgressDialog pd = ProgressDialog.show(this, null, "Cargando ..",true, false);
				new Thread(new Runnable(){
				public void run(){
				entra();
				pd.dismiss();
				}
				
				private void entra() {	
				
			startActivityForResult(detalles, REQUEST_CODE);
				
				}}).start();
					        }
			
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
			public void quitarDelCarrito (View v){
				if (checkConnectivity()==false) {
					
					 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
				 	toast.show();
					        	
					        }else{	
				
				//	 ArrayList<String> valorCheck = new ArrayList< String>();
					//final ListView listView = (ListView)findViewById(R.id.wishlistlistView);
					//System.out.println("valorCheck:  "+valorCheck);	
		
		
						//Gson gson = new Gson();
				       // String jsonCheck = gson.toJson(valorCheck);
				       // System.out.println("jsonCheck = " + jsonCheck);
				       
				        
				        String respuesta = null;
			        	
				
				       // JSONArray arr = new JSONArray(Arrays.asList(valorCheck));
				        JSONArray arr = new JSONArray(valorCheck);
				        System.out.println("jsonCheck = " + arr);
				        try {
							
		        			ArrayList<NameValuePair> parc = new ArrayList<NameValuePair>();
		        			parc.add(new BasicNameValuePair("id_facturaCheck",arr.toString()));
		        			respuesta = CustomHttpClient.executeHttpPost(
		        					AplicationConfig.URLConection+AplicationConfig.URLRefreshRecordBill,parc); 
		        			String res = respuesta.toString();
		                	res = res.replaceAll("\\s","");
		                	System.out.println("Respuesta: "+res);
		     
		                	if (res.equals("3")) { 
		                      
		                		
		                		Log.e("Entro en RES ***","BORRO DE LA BD");
		                	  	
		                	
		                	
		                	
		                	}
				
				final ProgressDialog pd = ProgressDialog.show(this, null, "Cargando ..",true, false);
				new Thread(new Runnable(){
				public void run(){
				entra();
				pd.dismiss();
				}
				
				private void entra() {
					RecordBill.this.onRestart(); 
        			Intent next=new Intent(RecordBill.this, RecordBill.class); 
        			startActivity(next);
        			finish();
        			
        				}}).start();
				
		                } catch (Exception e) {
		                    e.printStackTrace(); 
		                }
		
				        
				
					        }
		
					
					
				}
			
	
	
	
	
	
	
		
	
				public 	class Adaptador extends ArrayAdapter<RecordBillDTO>{
					Adaptador()
					{
						super(RecordBill.this, R.layout.recordlist_item_layout, arrayOfWebData);
					}
			
					@Override
					public View getView(int position, View convertView, ViewGroup parent) {
						// Se tiene que tener un View para que esto funcione
						
					    
						
						ViewHolder holder;
						
						if(convertView == null){
							
					
							LayoutInflater inflater= getLayoutInflater();
							convertView = inflater.inflate(R.layout.recordlist_item_layout, null);
							
							holder = new ViewHolder (convertView);
							convertView.setTag(holder);
							
				
							
							
							
							   }
							   else {
							holder=(ViewHolder)convertView.getTag();
						}
						
						
						
						holder.populateFrom(arrayOfWebData.get(position));
						return (convertView);
						
					}
					
				}
					
				class ViewHolder {
				
					public ImageView imagen =null; 
					public TextView nombreText = null;
					public TextView precioText = null;
					public TextView textView1 = null;
					public TextView textView2 = null;
					public CheckBox checkBox1 = null;			
					
					
					ViewHolder(View recordlist_item_layout) {
						
					
						 nombreText = (TextView) recordlist_item_layout.findViewById(R.id.item_nombreMake);
						 precioText = (TextView) recordlist_item_layout.findViewById(R.id.item_precioMake);
						 textView1 = (TextView) recordlist_item_layout.findViewById(R.id.textView1);
						 checkBox1 = (CheckBox)recordlist_item_layout.findViewById(R.id.checkBox1);
						// textView2 = (TextView) recordlist_item_layout.findViewById(R.id.textView2);
						textView1.setVisibility(View.INVISIBLE);
						//textView2.setVisibility(0);
							
						
						
						
						
					}
					
					
				
					void populateFrom(RecordBillDTO r){
						
						textView1.setText(String.valueOf(r.getId()));
						//textView1.setVisibility(TextView.INVISIBLE);
						//textView2.setText(String.valueOf(r.getFecha()));
						//textView2.setVisibility(TextView.INVISIBLE);
						
						
						checkBox1.setOnClickListener( new View.OnClickListener() { 
						     public void onClick(View v) { 
						    	 
						     CheckBox cb = (CheckBox) v ; 
						     // if(cb != null){
							      RelativeLayout relativeLayout =  (RelativeLayout) v.getParent();
								  TextView idText = (TextView) relativeLayout.findViewById(R.id.textView1);
								 // Log.e("*********************", idText.getText().toString());
								  
								  if(cb.isChecked())
								  {	
									 // valorCheck.add(Integer.getInteger((String) idText.getText()).toString());
									 // valorCheck.add(Integer.getInteger(idText.getText().toString()), "");
									/////MIO abajo
									  valorCheck.add( idText.getText().toString());
									  Log.e("****ADD TO VALOR CHECK*********", idText.getText().toString());
								  }
								  else
								  {	
									  //valorCheck.remove(Integer.getInteger(idText.getText().toString()));
									  /////MIO abajo
									  
									  Log.e("****REMOVE TO VALOR CHECK*********", idText.getText().toString());
									  valorCheck.remove( idText.getText().toString());
									  
								  }
								  Iterator<String> it = valorCheck.iterator();
								  while( it.hasNext())
								  {
									  String s = it.next();
									  Log.e("**************3*******", s);
								  }
								  
									System.out.println("valorCheck:  "+valorCheck);	  
							      
						     }
						    } 
						    );
						 
						
				       
				       
						nombreText.setText(r.getId());
						precioText.setText(r.getFecha());
						//System.out.println(r.isSelected());
			
					}
			


		}
			
				
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

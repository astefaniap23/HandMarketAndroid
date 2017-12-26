package interfaces.product;



import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import uno.menu.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import config.AplicationConfig;
import config.CustomHttpClient;
import controller.ShoppingCarControllerWriter;
import controller.TotalController;
import dto.ShoppingCarWriterDTO;
import dto.TotalDTO;

public class ShoppingCar extends Activity  {
	
	
	
	private static final int REQUEST_CODE=10;
	//private boolean mShowCheckbox;   
	public double Total;
	public double TotalProductos;
	public double TotalDelivery;
	public int verifica;
	 DecimalFormat df = new DecimalFormat("0.00");
	
	ArrayList<ShoppingCarWriterDTO> arrayOfWebData = new ArrayList<ShoppingCarWriterDTO>();
				

	 ArrayList<String> valorCheck = new ArrayList<String>();
	

		class Producto{
			public String imagenProducto;
			public String nombreProducto;
			public String precioProducto;
		}
		
				
			
			public void onCreate(Bundle savedInstanceState) {
		
			
			super.onCreate(savedInstanceState);
			setContentView(R.layout.shoppingcar);
			
			if (checkConnectivity()==false) {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			 	toast.show();
				        	this.finish();
				        	
				        }else{	
				
			arrayOfWebData = ShoppingCarControllerWriter.searchProducts();
			
		
		
			
			
	      ListView listView = (ListView)findViewById(R.id.shoppingCarlistView);
	       //ImageView imagen =(ImageView) findViewById(R.id.item_icon);
	      // TextView nombreText = (TextView) findViewById(R.id.item_nombreMake);
	       //TextView precioText = (TextView) findViewById(R.id.item_precioMake);
	       //TextView textView1 = (TextView) findViewById(R.id.textView1);
	      // CheckBox checkBox1= (CheckBox) findViewById(R.id.checkBox1);
	       TextView textTotalProductos = (TextView) findViewById(R.id.totalProductos);
	       TextView textEnvio = (TextView) findViewById(R.id.totalDelivery);
	       TextView textTotal = (TextView) findViewById(R.id.total);
	      
		//	textView1.setVisibility(0);

			ArrayAdapter<ShoppingCarWriterDTO> adapter = new Adaptador();
			listView.setAdapter(adapter);
			
			
			TotalDTO total = new TotalDTO();
			
			TotalController totalController = new TotalController();
			
				if(totalController.totalShopping(total)){
					double envio=70.00;
					if (total.getTotal()!=0){
					
					textTotalProductos.setText("TOTAL: "+ df.format(total.getTotal())+ " Bs.");
					TotalProductos = total.getTotal();
					textEnvio.setText("Costo de envio: " +df.format(envio)+ " Bs.");
					Double totalProductos = total.getTotal();
					
					Total=totalProductos+envio;
					textTotal.setText("TOTAL: " +df.format(Total)+ " Bs.");
					TotalDelivery=Total;
					verifica=1;
					}
					else {
						
						textTotalProductos.setVisibility(View.INVISIBLE);
						textTotalProductos.setText("Total: 0 Bs.");
						
						textEnvio.setText("Envio a domicilio: 0 Bs.");
						textTotal.setText("TOTAL: 0 Bs.");
						verifica=0;
						
					}
				}
				
				
				System.out.println("Valor check oncreate: " + valorCheck.toString());
				        }
			}
	

			
			
			
			/*
		class MyImageView extends ImageView
		{
			public MyImageView(Context context) {
				super(context);
				// TODO Auto-generated constructor stub
			}
			public MyImageView(Context context, AttributeSet attrs) {
				super(context, attrs);
				// TODO Auto-generated constructor stub
			}
			public MyImageView(Context context, AttributeSet attrs, int defStyle) {
				super(context, attrs, defStyle);
				// TODO Auto-generated constructor stub
			}
			private int id_x;

			
			public int getIdX(){
				return id_x;
			}
			public void setIdX(int id_x){
				this.id_x = id_x;
			}
		}*/
		
		
		public void detailsButtonShoppingCar (View v) {
			if (checkConnectivity()==false) {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			 	toast.show();
				        		
				        }else{	
		 	
			ImageView image = (ImageView) v;
			RelativeLayout relativeLayout =  (RelativeLayout) image.getParent();
			TextView idText = (TextView) relativeLayout.findViewById(R.id.textView1);
			
		//Toast.makeText(Productos.this, "a " + idText.getText().toString(), Toast.LENGTH_LONG).show();	
			
			Iterator<ShoppingCarWriterDTO> i = arrayOfWebData.iterator();
			final Intent productodetallescompra = new Intent(getApplicationContext(),ProductDetailsShopping.class);
			 //CategorySelectedDTO c = null;
			
			while(i.hasNext())
			{
				ShoppingCarWriterDTO p =  i.next();
				if(p.getId_product() == Integer.valueOf(idText.getText().toString()))
				{
					/*Toast.makeText(ShoppingCar.this, p.getName() + " a " + idText.getText().toString()
							+ c.getId_categorySelected(), Toast.LENGTH_LONG).show();	*/
					productodetallescompra.putExtra("nombre",p.getName());
					productodetallescompra.putExtra("precio", p.getPrice());
					productodetallescompra.putExtra("detalle", p.getDescription());
					productodetallescompra.putExtra("rutaImagen", p.getImagemax());
				
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
			
		startActivityForResult(productodetallescompra, REQUEST_CODE);
			
			}}).start();
			
				        }
		}
		
	
		
	
		
		
		public void quitarDelCarrito (View v){
			if (verifica==0){
			 Toast toast = Toast.makeText(getApplicationContext(), "Carrito vacio", Toast.LENGTH_SHORT);
			 toast.setGravity(Gravity.CENTER, Gravity.LEFT,Gravity.RIGHT);
		 	toast.show();
			}else{
			
			if (checkConnectivity()==false) {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			 	toast.show();
				        	
				        }else{	
				
			
			final ListView listView = (ListView)findViewById(R.id.shoppingCarlistView);

		        String respuesta = null;
		        
		        JSONArray arr = new JSONArray(valorCheck);
		        System.out.println("jsonCheck = " + arr);
		        try {
					
		        	
        			ArrayList<NameValuePair> parc = new ArrayList<NameValuePair>();
        			parc.add(new BasicNameValuePair("id_productCheck",arr.toString()));
        			respuesta = CustomHttpClient.executeHttpPost(
        					AplicationConfig.URLConection+AplicationConfig.URLRefreshShoppingCarList,parc);
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
        			
        			
        			@SuppressWarnings("unused")
        			private void entra() {	         		

            	
            	ShoppingCar.this.onRestart(); 
    			Intent next=new Intent(ShoppingCar.this, ShoppingCar.class); 
    			startActivity(next);
    			finish();
    			
        			}}).start();
                } catch (Exception e) {
                    e.printStackTrace(); 
                }
	        	
				        }
			}
		}
		
		/*
		public void refresh (View v){			
			
			super.onRestart(); 
			Intent next=new Intent(ShoppingCar.this, ShoppingCar.class); 
			startActivity(next);
			finish();		
			
		}*/
		
		public void comprar (View v){
			
			if (verifica>0){
				if (checkConnectivity()==false) {
					
					 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
				 	toast.show();
					        	
					        }else{	
			
			final TotalController clientEnviarFactura = new TotalController();
			   
            final TotalDTO total = new TotalDTO();
            
			double totalFacturaDelivery = TotalDelivery;
            double totalFacturaRetiro = TotalProductos;
           
            
            total.setTotal(totalFacturaDelivery);		                       
     	   total.setTotal2(totalFacturaRetiro);
     	  
     	   
			 
			
			
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setMessage("Seleccione el tipo de compra")
		              .setCancelable(true)
		               .setPositiveButton("Envio a domicilio", new DialogInterface.OnClickListener() {
		                   public void onClick(final DialogInterface dialog, int id) {	                     
		                	  
		                	   if (checkConnectivity()==false) {
		                		   dialog.cancel();
		                			 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
		                			 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		                		 	toast.show();
		                			        	
		                			        }else{
		                			        	
		                			        	
		                			        	final ProgressDialog pd = ProgressDialog.show(ShoppingCar.this, null, "Cargando ..",true, false);
		                						new Thread(new Runnable(){
		                						public void run(){
		                						entra();
		                						pd.dismiss();
		                						}
		                						
		                						private void entra() {	
		                	   
		                	   double recu =  TotalProductos;
		                	   double recu2 =  TotalDelivery;
		                	   
		                	   
		                	   if(clientEnviarFactura.TotalDelivery(total)){
		                		   finish();
			                       Intent intent = new Intent(getApplicationContext(),GoToDelivery.class);
			                       intent.putExtra("recupera_precio", recu);
			                       intent.putExtra("recupera_precio_delivery", recu2);
									startActivity(intent);	
		        			   }else{
		        				   Log.e("ERROR", "Error al enviar");
		        			   }
		                	  
								dialog.cancel();
		                						}}).start();
		                	   
		                			        }            
		                   }
		               })
		               .setNegativeButton("Retirar en tienda", new DialogInterface.OnClickListener() {
		                   public void onClick(final DialogInterface dialog, int id) {
		                	   if (checkConnectivity()==false) {
		                		   dialog.cancel();

		                			 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
		                			 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		                		 	toast.show();
		                			        	
		                			        }else{	
		                			        	final ProgressDialog pd = ProgressDialog.show(ShoppingCar.this, null, "Cargando ..",true, false);
		                						new Thread(new Runnable(){
		                						public void run(){
		                						entra();
		                						pd.dismiss();
		                						}
		                						
		                						private void entra() {	  	
		                			        	
		                	   double recu =  TotalProductos;
		                	  
		                	 
		                	   if(clientEnviarFactura.TotalRetiro(total)){
		                		   finish();
			                       Intent intent = new Intent(getApplicationContext(),GoToTheSite.class);
			                       intent.putExtra("recupera_precio", recu);
									startActivity(intent);	
		        			   }else{
		        				   Log.e("ERROR", "Error al enviar");
		        			   }
		                	  
								dialog.cancel();         							
		                			        
		                			    	}}).start();
				
		                			        
		                			        
		                			        
		                			        }                      
		                   }
		               });
		        AlertDialog alert = builder.create();
		        alert.setTitle("Hand Market");
		        alert.show();
					        }  
		}else{
	
			 
			 Toast toast = Toast.makeText(getApplicationContext(), "Carrito vacio", Toast.LENGTH_SHORT);
			 toast.setGravity(Gravity.CENTER, Gravity.LEFT,Gravity.RIGHT);
		 	toast.show();
		}
		}
		

		
		
		
		
		
		
		
		
		
			
		public 	class Adaptador extends ArrayAdapter<ShoppingCarWriterDTO>{
			Adaptador()
			{
				super(ShoppingCar.this, R.layout.car_list_item_layout, arrayOfWebData);
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// Se tiene que tener un View para que esto funcione

				ViewHolder holder;
				
				if(convertView == null){
					
			
					LayoutInflater inflater= getLayoutInflater();
					convertView = inflater.inflate(R.layout.car_list_item_layout, null);
					
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
			
			
			ViewHolder(View car_list_item_layout) {
				
				imagen =(ImageView) car_list_item_layout.findViewById(R.id.item_icon);
				 nombreText = (TextView) car_list_item_layout.findViewById(R.id.item_nombreMake);
				 precioText = (TextView) car_list_item_layout.findViewById(R.id.item_precioMake);
				 textView1 = (TextView) car_list_item_layout.findViewById(R.id.textView1);
				 checkBox1 = (CheckBox)car_list_item_layout.findViewById(R.id.checkBox1);
				 textView2 = (TextView) car_list_item_layout.findViewById(R.id.textView2);
				textView1.setVisibility(0);
				textView2.setVisibility(0);
					
				
				
				
				
			}
			
			
		
			void populateFrom(ShoppingCarWriterDTO r){
				
				textView1.setText(String.valueOf(r.getId_product()));
				textView1.setVisibility(View.INVISIBLE);
				textView2.setText(String.valueOf(r.getId_code()));
				textView2.setVisibility(View.INVISIBLE);
				
				
				checkBox1.setOnClickListener( new View.OnClickListener() { 
				     public void onClick(View v) { 
				    	 
				     CheckBox cb = (CheckBox) v ; 
				     // if(cb != null){
					      RelativeLayout relativeLayout =  (RelativeLayout) v.getParent();
						  TextView idText2 = (TextView) relativeLayout.findViewById(R.id.textView2);
						 // Log.e("*********************", idText.getText().toString());
						  
						  if(cb.isChecked())
						  {	
							 // valorCheck.add(Integer.getInteger((String) idText.getText()).toString());
							 // valorCheck.add(Integer.getInteger(idText.getText().toString()), "");
							/////MIO abajo
							  valorCheck.add( idText2.getText().toString());
							  Log.e("****ADD TO VALOR CHECK*********", idText2.getText().toString());
						  }
						  else
						  {	
							  //valorCheck.remove(Integer.getInteger(idText.getText().toString()));
							  /////MIO abajo
							  
							  Log.e("****REMOVE TO VALOR CHECK*********", idText2.getText().toString());
							  valorCheck.remove( idText2.getText().toString());
							  
						  }
						  Iterator<String> it = valorCheck.iterator();
						  while( it.hasNext())
						  {
							  String s = it.next();
							  Log.e("**************3*******", s);
						  }
						  
							System.out.println("valorCheck:   "+valorCheck);	  
					      
				     }
				    } 
				    );
				 
				
				
				
				Bitmap bmp = null;
		        try {
		            URL url = new URL(r.getImagen());
		            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		        } catch (MalformedURLException e) {

		        }catch (IOException e) {

		        }
		        
		  
				/*
			       
				if (imagen != null) {
					imagen.setImageDrawable(imagen.getContext().getResources().getDrawable(R.drawable.list_placeholder));
					new ImageAsyncTask(imagen).execute(r.getImagen());
				}*/
				
		        imagen.setImageBitmap(bmp);
				nombreText.setText(r.getName());
				precioText.setText(String.valueOf(df.format(r.getPrice()))+ " Bs.");
				//System.out.println(r.isSelected());
	
			}



			
		
	}
		//-------------------Menu-------------------------------------
		
		/*
			@Override
		    public boolean onCreateOptionsMenu(Menu menu) {
		      
				super.onCreateOptionsMenu(menu);        
		        MenuInflater Inflater = getMenuInflater();  
		        Inflater.inflate(R.menu.main, menu);
		        
		        return true;
	  
			}

				public boolean onOptionsItemSelected(MenuItem item)
			 	{
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
				//	case R.id.carrito:
						//Intent c = new Intent("android.intent.action.carrito");
						//startActivity(c);
					///	break;
						
					case R.id.usuario:
						Intent intent = new Intent(this,UsuarioPerfil.class);
						startActivity(intent);		
						break;	
						
					case R.id.salir:
				
						finish();
						break;
					default:
						break;
					}
					
					return false;
				}*/
				
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

		
		
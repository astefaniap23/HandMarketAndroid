package interfaces.product;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uno.menu.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import config.AplicationConfig;
import config.CustomHttpClient;
import controller.CategoryController;

import controller.ProductController;
import controller.ShoppingCarControllerReader;

import controller.WishListControllerReader;
import dto.CategoryDTO;
import dto.ProductDTO;
import dto.ShoppingCarDTO;
import dto.WishListDTO;


	
public class Product extends Activity implements OnItemSelectedListener{
	
	int bandera;
	DecimalFormat df = new DecimalFormat("0.00"); // si se quiere poner mejor formato ejm: 1,254,587.85  ->  DecimalFormat("#,###,##0.00");
												  // con el 0.00 ya es predefinido para todas las longitudes xxx...x.xx	
	private static final int REQUEST_CODE=10;
	
	
	ArrayList<ProductDTO> arrayOfWebData = new ArrayList<ProductDTO>();
	ArrayList<CategoryDTO> arrayOfWebDataCategory = new ArrayList<CategoryDTO>();
	//WishListDTO starList = new WishListDTO();
	
	
	
	
	class Producto{
		
		public String imagenProducto;
		public String nombreProducto;
		public String precioProducto;
	}

	public void onCreate(Bundle savedInstanceState) {
		 
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productos);
		
		if (checkConnectivity()==false) {
			
			 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
			 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		 	toast.show();
		 			this.finish();
			        		
			        }else{	
		
				
			arrayOfWebDataCategory=CategoryController.searchProducts(); 
			arrayOfWebData = ProductController.searchProducts(null);
			
			
	        final TextView lblMensaje = (TextView)findViewById(R.id.LblMensaje);
	        final Spinner cmbOpciones = (Spinner)findViewById(R.id.CmbOpciones);
	        final ListView listView = (ListView)findViewById(R.id.productolistView);
	        
	    	

	         ArrayAdapter<CategoryDTO> adaptador = new ArrayAdapter<CategoryDTO>
	         	(this,R.layout.spinner_item, arrayOfWebDataCategory);
	 	        adaptador.setDropDownViewResource(
	 	        android.R.layout.simple_spinner_dropdown_item);
	 	        cmbOpciones.setAdapter(adaptador);

	         cmbOpciones.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
	        	 public void onItemSelected(AdapterView<?> parent,android.view.View v, int position, long id){
	 	                 // lblMensaje.setText("Seleccionado: " + datos[position]);

	        	 }
	 	         
	 	         public void onNothingSelected(AdapterView<?> parent){
	 	        	 listView.setVisibility(ListView.INVISIBLE);
	 	              lblMensaje.setText(" ");
	 	          }
	 	         
	 	      });
	 	        
	         
	 	       cmbOpciones.setOnItemSelectedListener(this);
	 	        
			        }			 
	        
	}
	

		
		public void detailsButton (View v) {
			if (checkConnectivity()==false) {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			 	toast.show();
				        	
				        }else{	
		
			ImageView image = (ImageView) v;
			RelativeLayout relativeLayout =  (RelativeLayout) image.getParent();
			TextView idText = (TextView) relativeLayout.findViewById(R.id.textView1);
			
			//Toast.makeText(Productos.this, "a " + idText.getText().toString(), Toast.LENGTH_LONG).show();	
			
			Iterator<ProductDTO> i = arrayOfWebData.iterator();
			final Intent detalles = new Intent(getApplicationContext(),ProductDetails.class);
			 //CategorySelectedDTO c = null;
			
			while(i.hasNext())
			{
				ProductDTO p =  i.next();
				if(p.getId() == Integer.valueOf(idText.getText().toString()))
				{
					/*Toast.makeText(Product.this, "Usted ha seleccionado: "+p.getName(), Toast.LENGTH_SHORT).show();	*/
					detalles.putExtra("nombre",p.getName());
					detalles.putExtra("precio", p.getPrice());
					detalles.putExtra("detalle", p.getDescription());
					detalles.putExtra("rutaImagen", p.getImagemax());
				
					break;
					
				}
			}
			
			
			final ProgressDialog pd = ProgressDialog.show(this, null, "Cargando ..", true, false);
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
		
		public void  carButton(View v){
			if (checkConnectivity()==false) {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			 	toast.show();
				        	
				        }else{	
			//Toast.makeText(this, "CARRITO CLIK", Toast.LENGTH_SHORT).show();
			
			ImageView image = (ImageView) v;
			RelativeLayout relativeLayout =  (RelativeLayout) image.getParent();
			TextView idText = (TextView) relativeLayout.findViewById(R.id.textView1);
			

			Iterator<ProductDTO> i = arrayOfWebData.iterator();
			final ShoppingCarDTO shopping = new ShoppingCarDTO();
			
			while(i.hasNext())
			{
				final ProductDTO p =  i.next();
				if(p.getId() == Integer.valueOf(idText.getText().toString()))
				{
					
					shopping.setId_product(p.getId().toString());
					shopping.setName(p.getName().toString());
					shopping.setPrice(p.getPrice());
					shopping.setImage(p.getImage().toString());
					
					//Toast.makeText(Product.this, "Enviando "+p.getName()+" al carrito", Toast.LENGTH_SHORT).show();	
				/*	Toast.makeText(Product.this, p.getName() + " a " + idText.getText().toString(), Toast.LENGTH_LONG).show();	*/

					/*if (p.getId()==0 || p.getName().length() ==0 || p.getPrice()==0 || p.getImage().length() ==0 ){
						 Toast.makeText(this, "Datos incompletos para pasar a la BD-ShoppingCar", Toast.LENGTH_SHORT).show();
					}else{*/

										
							
					
					ShoppingCarControllerReader shoppingCarControllerR = new ShoppingCarControllerReader();
					
			    	if (shoppingCarControllerR.productToShoppingCar(shopping)==true) {
			    
			    	
			    		 Toast toast = Toast.makeText(Product.this,p.getName()+" enviado al carrito", Toast.LENGTH_SHORT);
						 toast.setGravity(Gravity.CENTER, Gravity.LEFT,Gravity.RIGHT);
					 	toast.show();
			    	}
			    	
			    	else
			    		
			    	{
			    		
			    		 Toast toast = Toast.makeText(Product.this, "No se pudo enviar "+p.getName()+" al carrito", Toast.LENGTH_SHORT);
						 toast.setGravity(Gravity.CENTER, Gravity.LEFT,Gravity.RIGHT);
					 	toast.show();
			    	}
						
					
			break;
			}
				
		}	
				        }
}
		
		
		public void wishList(View v){
			
			if (checkConnectivity()==false) {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			 	toast.show();
				        	
				        }else{	
			
			ImageView image = (ImageView) v;
			RelativeLayout relativeLayout =  (RelativeLayout) image.getParent();
			TextView idText = (TextView) relativeLayout.findViewById(R.id.textView1);
			ImageButton star=(ImageButton) relativeLayout.findViewById(R.id.favoritosDetalle);

			Iterator<ProductDTO> i = arrayOfWebData.iterator();
			WishListDTO wishlist = new WishListDTO();
			
			while(i.hasNext())
			{
				ProductDTO p =  i.next();
				if(p.getId() == Integer.valueOf(idText.getText().toString()))
				{
					
					wishlist.setId_product(p.getId().toString());
					wishlist.setName(p.getName().toString());
					wishlist.setPrice(p.getPrice());
					wishlist.setImage(p.getImage().toString());
					
					//Toast.makeText(Product.this, "Enviando "+p.getName()+" a tus favoritos", Toast.LENGTH_SHORT).show();	
					
						
					WishListControllerReader wishListControllerR = new WishListControllerReader();
				//	Toast.makeText(Product.this, " "+ wishListControllerR.productToWishList(wishlist) , Toast.LENGTH_SHORT).show();
					
					
					if (wishListControllerR.productToWishList(wishlist)==true) 
									    		
						{
						star.setSelected(true);	
						
						 Toast toast = Toast.makeText(Product.this, p.getName()+" enviado a favoritos", Toast.LENGTH_SHORT);
						 toast.setGravity(Gravity.CENTER, Gravity.LEFT,Gravity.RIGHT);
					 	toast.show();
						}
								
						else
				    		
				    	{
						
						if (wishListControllerR.productDeleteWishList(wishlist)==true) 
										    		
							{
							star.setSelected(false);	
							
							 Toast toast = Toast.makeText(Product.this, p.getName()+" eliminado de favoritos", Toast.LENGTH_SHORT);
							 toast.setGravity(Gravity.CENTER, Gravity.LEFT,Gravity.RIGHT);
						 	toast.show();
							}
									
							else
					    		
					    	{
					    		/*
					    		 Toast toast = Toast.makeText(Product.this, p.getName()+" ya se encuentra en favoritos", Toast.LENGTH_SHORT);
								 toast.setGravity(Gravity.CENTER, Gravity.LEFT,Gravity.RIGHT);
							 	toast.show();*/
					    	}
						}
			
			
					break;
				}
					
			}		
			
				        }
			
}
		
				
	

		public void onNothingSelected(AdapterView<?> arg0) {
				final ListView listView = (ListView)findViewById(R.id.productolistView);
			  	listView.setVisibility(ListView.INVISIBLE);
		}
		
		
		protected void onActivityResult(int requestCode, int resultCode, Intent data){
			if (resultCode==RESULT_OK && requestCode==REQUEST_CODE){
				if (data.hasExtra("returnKey1")){
				//	Toast.makeText(this, data.getExtras().getString("returnKey1"), Toast.LENGTH_SHORT).show();
				}
			}
			
		}
		
	
	
		
		////1
			
		
			@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			
				
				//final TextView lblMensaje = (TextView)findViewById(R.id.LblMensaje);
				final ListView listView = (ListView)findViewById(R.id.productolistView);
				//final ImageButton carrito = (ImageButton)findViewById(R.id.carrito);
			
				
				
				if(arg0.getId() == findViewById(R.id.CmbOpciones).getId())
					{		
							
							Spinner a = (Spinner)findViewById(R.id.CmbOpciones);
			           		CategoryDTO f = (CategoryDTO)a.getSelectedItem();
			           		//System.out.println(f.getId_category());
			           		if (checkConnectivity()==false) {
								
								 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
								 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
							 	toast.show();
								        	
								        }else{	
			           		this.arrayOfWebData = ProductController.searchProducts(f.getId_category());
			           		
			           		
			           	
							ArrayAdapter<ProductDTO> adapter = new Adaptador();
					   		listView.setAdapter(adapter); 
							
												
						
					   		
			 
							
					}
				
					}
		}	
			
			/*
		class MyImageView extends ImageView
		{
			public MyImageView(Context context) {
				super(context);
				
			}
			public MyImageView(Context context, AttributeSet attrs) {
				super(context, attrs);
			
			}
			public MyImageView(Context context, AttributeSet attrs, int defStyle) {
				super(context, attrs, defStyle);

			}
			private int id_x;

			
			public int getIdX(){
				return id_x;
			}
			public void setIdX(int id_x){
				this.id_x = id_x;
			}
		}*/
		
			
		
		
	//3	

		public 	class Adaptador extends ArrayAdapter<ProductDTO>{
			Adaptador()
			{
				super(Product.this, R.layout.list_item_layout, arrayOfWebData);////-4
			}
		
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// Se tiene que tener un View para que esto funcione
				
				ViewHolder holder;
				
				
				if(convertView == null){
					LayoutInflater inflater= getLayoutInflater();
					convertView = inflater.inflate(R.layout.list_item_layout, null);
					
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
				public ImageView estrella =null;
				public TextView starStatus = null;
				
				 String respuesta = null;
				 String res=null;
			
			ViewHolder(View list_item_layout) {
				
				 imagen =(ImageView) list_item_layout.findViewById(R.id.item_icon);
				 nombreText = (TextView) list_item_layout.findViewById(R.id.item_nombreMake);
				 precioText = (TextView) list_item_layout.findViewById(R.id.item_precioMake);
				 textView1 = (TextView) list_item_layout.findViewById(R.id.textView1);
				 starStatus = (TextView) list_item_layout.findViewById(R.id.starStatus);
				 estrella = (ImageView) list_item_layout.findViewById(R.id.favoritosDetalle);
				 textView1.setVisibility(View.INVISIBLE);
				
				
				
					try {
						   ArrayList<NameValuePair> postValores = new ArrayList<NameValuePair>(); // para el php
						   
							postValores.add(new BasicNameValuePair("usuario", null));			
							
			        	respuesta = CustomHttpClient.executeHttpPost(AplicationConfig.URLConection + AplicationConfig.URLStarSelected, postValores);
			        	
			        	res = respuesta.toString();
			        	res = res.replaceAll("\\s","");
			        	System.out.println("respuesta star : " + res.toString());
			        	
			        	/*
			        	if (res.equals("1")) {
			        		estrella.setSelected(true);
			        	}*/
			        	
			        } catch (Exception e) {
			            e.printStackTrace();    
			        }    
					
						
				 
			}
			
			
			
			void populateFrom(ProductDTO r){
				
				
				textView1.setText(String.valueOf(r.getId()));
				textView1.setVisibility(TextView.INVISIBLE);
				
				
				
				
				
				Bitmap bmp = null;
		        try {
		        	
		        	
		            URL url = new URL(r.getImage());
		           bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		            
		          
		        } catch (MalformedURLException e) {

		        }catch (IOException e) {

		        }
				
				
				 /* imagen asincrona
				if (imagen != null) {
					imagen.setImageDrawable(imagen.getContext().getResources().getDrawable(R.drawable.list_placeholder));
					new ImageAsyncTask(imagen).execute(r.getImage());
				}*/
				
		       
				
		        	
		        	imagen.setImageBitmap(bmp);
					nombreText.setText(r.getName());	
					precioText.setText(String.valueOf(df.format(r.getPrice()))+ " Bs.");
					textView1.setText(String.valueOf(r.getId()));
					estrella.setSelected(false);
					
					 try {
							// crea un nuevo JSON array de nuestro string -> resultado 
				        	JSONArray jArray = new JSONArray(respuesta.toString());
				        	//ClientDTO clientProfile = new ClientDTO();
				        	Log.e("JSON","Entro array");
				        	
				        	// por cada objeto en el JSON array
				        	for(int i=0;i<jArray.length();i++){
				        		
				        		JSONObject json_data = jArray.getJSONObject(i);
				        		Log.e("JSON","Entro for");
				        		
				        		//starList.setId_product(json_data.getString("id_product"));
				        		
				        		
				        		if (textView1.getText().toString().equals(json_data.getString("id_product"))){
				        			starStatus.setText(String.valueOf(1));
									estrella.setSelected(true);
									System.out.println("Producto user: " + json_data.getString("id_product"));
									System.out.println("Producto: " + textView1.getText().toString());
									}//else{ estrella.setSelected(false); }
				           	}
				        	
						} catch (JSONException e) {
							// TODO: handle exception			
							Log.e("JSON","Error parsing en "+ e.toString());
						}
					
					//estrella wishlist
					/*
		        	if (textView1.getText().equals(starList.getId_product())){
						estrella.setSelected(true);
						System.out.println("Producto user " + starList.getId_product());
						System.out.println("Producto " + textView1.getText());
						}else{ estrella.setSelected(false); }
					
        			
					//StarController starController = new StarController();
					
					//starController.starUser(starList);
			   		
			   		*/
			   	
			   		
			   		
			   		
        			
		}
		}
				
	
		
		
	 	 //-------------------Menu-------------------------------------
		/*
		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        
	        super.onCreateOptionsMenu(menu);        
	        MenuInflater Inflater = getMenuInflater();  
	        Inflater.inflate(R.menu.main, menu);
	        
	        return true;
	  
	    }
	 
		
			 @Override
				public boolean onOptionsItemSelected(MenuItem item)
			 	{
					
					switch (item.getItemId()) {
					case R.id.favoritos:
						Intent a = new Intent(getApplicationContext(),WishList.class);
						startActivity(a);
						break;
					case R.id.productos:
						//Intent productos = new Intent(getApplicationContext(),Productos.class);
						//startActivity(productos);
						break;
					case R.id.buscar:
						Intent b = new Intent("android.intent.action.BUSCAR");
						startActivity(b);
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
		
		
		
		


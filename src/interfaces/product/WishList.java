
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

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;


import config.AplicationConfig;
import config.CustomHttpClient;

import controller.ProductController;
import controller.ShoppingCarControllerReader;
import controller.WishListControllerWriter;

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
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import dto.ProductDTO;
import dto.ShoppingCarDTO;
import dto.WishListWriterDTO;


public class WishList extends Activity {
	
	

	
	private static final int REQUEST_CODE=10;
	//private boolean mShowCheckbox;   
	DecimalFormat df = new DecimalFormat("0.00");
	
	ArrayList<WishListWriterDTO> arrayOfWebData = new ArrayList<WishListWriterDTO>();
	ArrayList<ProductDTO> arrayOfWebDataProduct = new ArrayList<ProductDTO>();			

	ArrayList<String> valorCheck = new ArrayList<String>();


		
		
				
			
			public void onCreate(Bundle savedInstanceState) {
		
			
			super.onCreate(savedInstanceState);
			setContentView(R.layout.wishlist);
			
			if (checkConnectivity()==false) {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			 	toast.show();
			 	
				        	this.finish();
				        	
				        }else{	
				
			arrayOfWebData = WishListControllerWriter.searchProducts();
			arrayOfWebDataProduct = ProductController.searchProducts(null);
		
			
			
			
	       ListView listView = (ListView)findViewById(R.id.wishlistlistView);
	       //ImageView imagen =(ImageView) findViewById(R.id.item_icon);
	       //TextView nombreText = (TextView) findViewById(R.id.item_nombreMake);
	      // TextView precioText = (TextView) findViewById(R.id.item_precioMake);
	      // TextView textView1 = (TextView) findViewById(R.id.textView1);
	      // CheckBox checkBox1= (CheckBox) findViewById(R.id.checkBox1);
	      

		      
        
			ArrayAdapter<WishListWriterDTO> adapter = new Adaptador();
			listView.setAdapter(adapter);
			
			
			System.out.println("Valor check oncreate: " + valorCheck.toString());
			
				        }
			}
	

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
		}
		
		
		public void detailsButtonWishList (View v) {
			if (checkConnectivity()==false) {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			 	toast.show();
				        	
				        }else{	
			ImageView image = (ImageView) v;
			RelativeLayout relativeLayout =  (RelativeLayout) image.getParent();
			TextView idText = (TextView) relativeLayout.findViewById(R.id.textView1);
			
		//Toast.makeText(Productos.this, "a " + idText.getText().toString(), Toast.LENGTH_LONG).show();	
			
			Iterator<WishListWriterDTO> i = arrayOfWebData.iterator();
			final Intent productdetailswishlist = new Intent(getApplicationContext(),ProductDetailsWishList.class);
			 //CategorySelectedDTO c = null;
			
			while(i.hasNext())
			{
				WishListWriterDTO p =  i.next();
				if(p.getId_product() == Integer.valueOf(idText.getText().toString()))
				{
					//Toast.makeText(WishList.this, p.getName() + " a " + idText.getText().toString(), Toast.LENGTH_LONG).show();	
					productdetailswishlist.putExtra("nombre",p.getName());
					productdetailswishlist.putExtra("precio", p.getPrice());
					productdetailswishlist.putExtra("detalle", p.getDescription());
					productdetailswishlist.putExtra("rutaImagen", p.getImagemax());
				
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
			
			
		startActivityForResult(productdetailswishlist, REQUEST_CODE);
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
			

			Iterator<WishListWriterDTO> i = arrayOfWebData.iterator();
			ShoppingCarDTO shopping = new ShoppingCarDTO();
			
			while(i.hasNext())
			{
				WishListWriterDTO p =  i.next();
				if(p.getId_product() == Integer.valueOf(idText.getText().toString()))
				{
					
					shopping.setId_product(p.getId_product().toString());
					shopping.setName(p.getName().toString());
					shopping.setPrice(p.getPrice());
					shopping.setImage(p.getImagen().toString());
					
					//Toast.makeText(WishList.this, "Enviando "+p.getName()+" al carrito", Toast.LENGTH_SHORT).show();	
					
					ShoppingCarControllerReader shoppingCarControllerR = new ShoppingCarControllerReader();
					//Toast.makeText(WishList.this, " "+ shoppingCarControllerR.productToShoppingCar(shopping) , Toast.LENGTH_LONG).show();
					if (shoppingCarControllerR.productToShoppingCar(shopping)==true) 
			    		
			    	{

			    		  Toast toast = Toast.makeText(WishList.this, p.getName()+" enviado al carrito", Toast.LENGTH_SHORT);
							 toast.setGravity(Gravity.CENTER, Gravity.LEFT,Gravity.RIGHT);
						 	toast.show();
			    	}
			    	
			    	else
			    		
			    	{

			    		  Toast toast = Toast.makeText(WishList.this, "No se pudo enviar "+p.getName()+" al carrito", Toast.LENGTH_SHORT);
							 toast.setGravity(Gravity.CENTER, Gravity.LEFT,Gravity.RIGHT);
						 	toast.show();
			    	}	
						
			
			break;
			}
			}
		}			
}
		
		
		
		
		
		
		
		
		
		
		
		
		
	
		
	
		
		
		public void quitarDelCarrito (View v){
			if (checkConnectivity()==false) {
				
				 Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a internet, reintente", Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
			 	toast.show();
				        	
				        }else{	
		//	 ArrayList<String> valorCheck = new ArrayList< String>();
		//	final ListView listView = (ListView)findViewById(R.id.wishlistlistView);
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
        			parc.add(new BasicNameValuePair("id_productCheck",arr.toString()));
        			
        				
    					
    				
        			respuesta = CustomHttpClient.executeHttpPost(
        					AplicationConfig.URLConection+AplicationConfig.URLRefreshWishList,parc);
    				
        			
        			
        			
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
            	WishList.this.onRestart(); 
    			Intent next=new Intent(WishList.this, WishList.class); 
    			startActivity(next);
    			finish();
    			
        			}}).start();
                	
                } catch (Exception e) {
                    e.printStackTrace(); 
                }
		        
				        }
		
				        
			
		}
		
		/*
		public void refresh (View v){			
			
			super.onRestart(); 
			Intent next=new Intent(WishList.this, WishList.class); 
			startActivity(next);
			finish();		
			
		}*/
		
		
			
			 
			
			
		
		
	
		
			
		public 	class Adaptador extends ArrayAdapter<WishListWriterDTO>{
			
			
			Adaptador()
			{
				super(WishList.this, R.layout.wishlist_item_layout, arrayOfWebData);
			}

			
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// Se tiene que tener un View para que esto funcione
				
			    
				
				ViewHolder holder;
				
				if(convertView == null){
					
			
					LayoutInflater inflater= getLayoutInflater();
					convertView = inflater.inflate(R.layout.wishlist_item_layout, null);
					
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
		
			public ImageView imagen; 
			public TextView nombreText = null;
			public TextView precioText = null;
			public TextView textView1 = null;
			public TextView textView2 = null;
			public CheckBox checkBox1 = null;			
			
			
			
			ViewHolder(View wishlist_item_layout) {
				
				
				imagen =(ImageView) wishlist_item_layout.findViewById(R.id.item_icon);
				 nombreText = (TextView) wishlist_item_layout.findViewById(R.id.item_nombreMake);
				 precioText = (TextView) wishlist_item_layout.findViewById(R.id.item_precioMake);
				 textView1 = (TextView) wishlist_item_layout.findViewById(R.id.textView1);
				 checkBox1 = (CheckBox)wishlist_item_layout.findViewById(R.id.checkBox1);
				 textView2 = (TextView) wishlist_item_layout.findViewById(R.id.textView2);
				textView1.setVisibility(View.INVISIBLE);
				textView2.setVisibility(View.INVISIBLE);
				
				
				
			}
		
				
			
			
			
		
			void populateFrom(WishListWriterDTO r){
				
				textView1.setText(String.valueOf(r.getId_product()));
				//textView1.setVisibility(TextView.INVISIBLE);
				textView2.setText(String.valueOf(r.getId_code()));
				//textView2.setVisibility(TextView.INVISIBLE);

			  
			    	/*
				System.out.println("textView2:  "+textView2.getText().toString());
				if(textView2.getText().toString().equals("162")){
				if(checkBox1.isChecked()==false){
					checkBox1.setChecked(true);
				}else{
					checkBox1.setChecked(false);
				}
				}*/
			    
				  
				checkBox1.setOnClickListener( new View.OnClickListener() { 
				     public void onClick(View v) { 
				    	 
				     CheckBox cb = (CheckBox) v ; 
				     // if(cb != null){
					      RelativeLayout relativeLayout =  (RelativeLayout) v.getParent();
					      TextView idText2 = (TextView) relativeLayout.findViewById(R.id.textView2);
						 // Log.e("*********************", idText.getText().toString());
					System.out.println();
						  
						  
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
						  
							System.out.println("valorCheck:  "+valorCheck);	  
					      
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
		       
		       //imgView.setImageBitmap(bmp); 
		        
		        /*
		        checkBox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    public void onCheckedChanged(CompoundButton v,
                            boolean isChecked) {
                    	 CheckBox cb = (CheckBox) v ; 
                    	 
                    	 RelativeLayout relativeLayout =  (RelativeLayout) v.getParent();
					      TextView idText2 = (TextView) relativeLayout.findViewById(R.id.textView2);
					      System.out.println("valorCheck antes:  "+valorCheck + " - " +idText2.getText().toString());	  
                        
                         if (cb.isChecked()==true) {
                        	 System.out.println("valorCheck checked:  "+valorCheck.toString() + " - " +idText2.getText().toString());	  
                        	 cb.setChecked(true);
                          }
                         else
                        {if (cb.isChecked()==false) {
                        	 System.out.println("valorCheck unchecked:  "+valorCheck.toString() + " - " +idText2.getText().toString());	  
                        
                        	 cb.setChecked(false);
                        }
                        }

                    }
                  });*/
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
						Intent a = new Intent(getApplicationContext(),WishList.class);
						startActivity(a);
						break;
					case R.id.productos:
						Intent productos = new Intent(getApplicationContext(),Product.class);
						startActivity(productos);
						break;
				//	case R.id.buscar:
					//	Intent b = new Intent("android.intent.action.BUSCAR");
						//startActivity(b);
						//break;
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
		
		







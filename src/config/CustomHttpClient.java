package config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class CustomHttpClient {

	public static final int HTTP_TIMEOUT = 30 * 1000; // por si no hay respuesta
	private static HttpClient mHttpClient; // sirve para ejecutar todos los requerimientos que tiene el HTTP

	private static HttpClient getHttpClient(){ //objeto de http
		
		//verificar la conexion de la aplicacion al servidor
		if(mHttpClient == null){
			mHttpClient = new DefaultHttpClient();
			final HttpParams par = mHttpClient.getParams();
			
			//tiempo de espera de conexion
			HttpConnectionParams.setConnectionTimeout(par, HTTP_TIMEOUT);
			
			//tiempo de solicitud de datos
			HttpConnectionParams.setSoTimeout(par, HTTP_TIMEOUT);
			
			ConnManagerParams.setTimeout(par, HTTP_TIMEOUT); // hace q funcione los 2 tiempos anteriores
		}
		
		return mHttpClient; 
	}
	
	
	public static String executeHttpPost(String url, ArrayList postValores) throws Exception {
		// echo 1 en ingresar.php ||| El array que se declaró en el main activity
		
		BufferedReader in = null; // RECIBE LA INFORMACION
			
		try{
		//
		// MANDA LA INFORMACION
		//
		HttpClient cliente = getHttpClient(); // llamar a la conexion que hicimos

		HttpPost post = new HttpPost(url); // metodo que se estara mandando la informacion (url)
		
		// el httppost mandara una informacion y se debe decodificar diciendo ->
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postValores); // valores o la informacion que se mandaran (post) al servidor ( nombre y clave )
		post.setEntity(formEntity);
		
		// respuesta de como se ejecutara
		HttpResponse respuesta = cliente.execute(post);
		
		// recibe informacion 
		in = new BufferedReader(new InputStreamReader(respuesta.getEntity().getContent())); // leera la respuesta obteniendo la entidad y el contenido
		StringBuffer sb = new StringBuffer("");
		String linea = "";
		String NL = System.getProperty("line.separator"); //evitar espacios, lineas y saltos, lo compacta
		
		while((linea = in.readLine()) != null){
			sb.append(linea + NL);
		}
		in.close();
		
		String resultado = sb.toString();
		return resultado;	
		
		}finally{
			if(in !=null){
				try{
				in.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}	
		}
	}

	
	public static String executeHttpPost2(String url) throws Exception {
		// echo 1 en ingresar.php ||| El array que se declaró en el main activity

		BufferedReader in = null; // RECIBE LA INFORMACION
			
		try{
		//
		// MANDA LA INFORMACION
		//
		HttpClient cliente = getHttpClient(); // llamar a la conexion que hicimos

		HttpPost post = new HttpPost(url); // metodo que se estara mandando la informacion (url)
		
		// el httppost mandara una informacion y se debe decodificar diciendo ->
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(null); // valores o la informacion que se mandaran (post) al servidor ( nombre y clave )
		post.setEntity(formEntity);
		
		// respuesta de como se ejecutara
		HttpResponse respuesta = cliente.execute(post);
		
		// recibe informacion 
		in = new BufferedReader(new InputStreamReader(respuesta.getEntity().getContent())); // leera la respuesta obteniendo la entidad y el contenido
		StringBuffer sb = new StringBuffer("");
		String linea = "";
		String NL = System.getProperty("line.separator"); //evitar espacios, lineas y saltos, lo compacta
		
		while((linea = in.readLine()) != null){
			sb.append(linea + NL);
		}
		in.close();
		
		String resultado = sb.toString();
		return resultado;	
		
		}finally{
			if(in !=null){
				try{
				in.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}	
		}
	}

	/*public static String getData(String url) {
					
		try{
		//
		// MANDA LA INFORMACION
		//
		HttpClient cliente = new DefaultHttpClient(); // llamar a la conexion que hicimos

		HttpPost post = new HttpPost(url); // metodo que se estara mandando la informacion (url)
					
		// respuesta de como se ejecutara
		HttpResponse respuesta = cliente.execute(post);
		
		// el httppost mandara una informacion y se debe decodificar diciendo ->
		HttpEntity ent = respuesta.getEntity();
		String texto = EntityUtils.toString(ent);
		
		//return texto;
		
				}catch(Exception e){
					return "error";
				}
		
		
		// parse JSON data
		try {
			String s = "";
			JSONArray jArray = new JSONArray()
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
			}	*/
	
	
}
	
	




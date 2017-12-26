package dto;

public class ShoppingCarDTO {
	
	//private ArrayList<ProductBuyDTO> products;
	//private ClientDTO client;
	private String id_code;
	private String id_product;
	private String id_user;
	private String name;
	private double price;
	private String image;
	public boolean selected;
	
	
	public ShoppingCarDTO (){
	//	products = null;
	//	client =null;
		id_code=null;
		id_product =null;
		id_user = null;
		name =null;
		price=0;
		image=null;
	}
	
	public ShoppingCarDTO (/*ArrayList<ProductBuyDTO> products,ClientDTO client,*/
			String id_code, String id_product, String id_user, String name, Double price, String image){
		//this.products = products;
	//	this.client = client;
		this.id_code=id_code;
		this.id_product = id_product;
		this.id_user = id_user;
		this.name= name;
		this.price=price;
		this.image=image;
	}
	
	
/*	public ArrayList<ProductBuyDTO> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<ProductBuyDTO> products) {
		this.products = products;
	}
	public ClientDTO getClient() {
		return client;
	}
	public void setClient(ClientDTO client) {
		this.client = client;
	}*/
	
	public String getId_code(){
		return id_code;
	}
	
	public void setId_code(String id_code){
		this.id_code=id_code;
	}
	
	public String getId_product() {
		return id_product;
	}
	public void setId_product(String id_product) {
		this.id_product = id_product;
	}
	public String getId_user() {
		return id_user;
	}
	public void setId_user(String id_user) {
		this.id_user = id_user;
	}
	public String getName(){
		return name;
	} 
	
	public void setName(String name){
		this.name =name;
	}
	
	public double getPrice (){
		return price;
	}
	
	public void setPrice (double price){
		this.price= price;
	}
	
	public String getImage(){
		return image;
	}
	
	public void setImage (String image){
		this.image= image;
	}


}

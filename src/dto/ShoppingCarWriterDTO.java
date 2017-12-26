package dto;

public class ShoppingCarWriterDTO {
	
		//private ArrayList<ProductBuyDTO> products;
		//private ClientDTO client;
		private Integer id_code;
		private Integer id_product;
		private Integer id_user;
		private String name;
		private Double price;
		private String imagen;
		private String description;
		private String imagen_max;
		//boolean selected = false;
	   
		
		
		public ShoppingCarWriterDTO (){
		//	products = null;
		//	client =null;
			id_code=null;
			id_product =null;
			id_user = null;
			name =null;
			price=null;
			imagen=null;
			description = null;
			imagen_max=null;
			//selected = false;
		}
		
		public ShoppingCarWriterDTO (/*ArrayList<ProductBuyDTO> products,ClientDTO client,*/
				Integer id_code, Integer id_product, Integer id_user, String name, String description,Double price, String imagen, String imagen_max/*,boolean selected*/){
			
			//this.products = products;
		//	this.client = client;
			this.id_code=id_code;
			this.id_product = id_product;
			this.id_user = id_user;
			this.name= name;
			this.description = description;
			this.price=price;
			this.imagen=imagen;
			this.imagen_max=imagen_max;
			//this.selected = selected;
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
		
		public Integer getId_code(){
			return id_code;
		}
		
		public void setId_code(Integer id_code){
			this.id_code=id_code;
		}
		
		public Integer getId_product() {
			return id_product;
		}
		public void setId_product(Integer id_product) {
			this.id_product = id_product;
		}
		public Integer getId_user() {
			return id_user;
		}
		public void setId_user(Integer id_user) {
			this.id_user = id_user;
		}
		public String getName(){
			return name;
		} 
		
		public void setName(String name){
			this.name =name;
		}
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
		public Double getPrice (){
			return price;
		}
		
		public void setPrice (Double price){
			this.price= price;
		}
		
		public String getImagen(){
			return imagen;
		}
		
		public void setImage (String imagen){
			this.imagen= imagen;
		}
		
		
		public String getImagemax() {
			return imagen_max;
		}
		public void setImagemax (String imagen_max) {
			this.imagen_max = imagen_max;
		}
	
		


	/*

		public boolean isSelected() {
			// TODO Auto-generated method stub
			return isSelected();
		}

		public void setSelected(boolean selected) {
			 this.selected = selected;
			
		}*/

		
		

	}



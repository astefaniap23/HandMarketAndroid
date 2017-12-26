package dto;

public class WishListDTO {

	private String id_code;
	private String id_product;
	private String id_user;
	private String name;
	private Double price;
	private String image;
	public boolean selected;
	
	public  WishListDTO(){
		id_code=null;
		id_product =null;
		id_user = null;
		name =null;
		price=null;
		image=null;
	}
	
	
	public  WishListDTO(String id_code, String id_product, String id_user, String name, Double price, String image)
	{
		this.id_code=id_code;
		this.id_product = id_product;
		this.id_user = id_user;
		this.name= name;
		this.price=price;
		this.image=image;
	}
	
	
	
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
	
	public Double getPrice (){
		return price;
	}
	
	public void setPrice (Double price){
		this.price= price;
	}
	
	public String getImage(){
		return image;
	}
	
	public void setImage (String image){
		this.image= image;
	}

	
	
	
}

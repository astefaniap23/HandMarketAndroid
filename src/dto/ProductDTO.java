package dto;

/**
 * 
 * @author AStefaniaP
 *
 */
public class ProductDTO {
	
	/**
	 * 
	 */
	public Integer id;
	public String id_category;
	public String name;
	public String description;
	public Double price;
	public String image;
	public String imagen_max;
	/**
	 * los inicializa en null 
	 * 
	 */
	public ProductDTO() {
		id = null;
		id_category=null;
		name = null;
		description = null;
		price = null;
		image = null;
		imagen_max = null;
	}
	
	/**
	 * llena el producto con los atributos
	 * 
	 * @param id
	 * @param id_category
	 * @param name
	 * @param description
	 * @param price
	 * @param image
	 * @param imagen_max
	 */
	public ProductDTO(Integer id,String id_category,String name, String description, Double price, String image, String imagen_max) {
		this.id = id;
		this.id_category=id_category;
		this.name = name;
		this.description = description;
		this.price = price;
		this.image = image;
		this.imagen_max=imagen_max;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getId_category() {
		return id_category;
	}
	public void setId_category(String id_category) {
		this.id_category = id_category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getImagemax() {
		return imagen_max;
	}
	public void setImagemax (String imagen_max) {
		this.imagen_max = imagen_max;
	}
}

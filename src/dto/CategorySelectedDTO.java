package dto;


public class CategorySelectedDTO {
	public String id_category;
	
	public CategorySelectedDTO(String id_category){
		this.id_category=id_category;
	}
	
	public CategorySelectedDTO() {
		id_category=null;
		
	}

	public String getId_category() {
		return id_category;
	}
	public void setId_category(String id_category) {
		this.id_category = id_category;
	}
}

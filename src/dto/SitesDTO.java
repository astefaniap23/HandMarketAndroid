package dto;

public class SitesDTO {
	 public String id_place;
	 public String name;
	 public String address;
	
	
	public SitesDTO(String id_place, String name, String address){
		this.id_place=id_place;
		this.name=name;
		this.address=address;
	}
	
	public String getId_place() {
		return id_place;
	}
	public void setId_place(String id_place) {
		this.id_place = id_place;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress (){
		return address;
		
	}
	
	public void setAddress (String address){
		this.address=address;
	}
	
	public String toString() {
		
		return this.name;
	}

}


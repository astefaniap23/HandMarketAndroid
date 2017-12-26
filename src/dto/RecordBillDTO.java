package dto;



public class RecordBillDTO {

	
	 public String id;
	 public String fecha;
	
	 public RecordBillDTO() {
			id = null;
			fecha=null;
		
		}
		
	 	 
	public RecordBillDTO(String id, String fecha){
		this.id=id;
		this.fecha=fecha;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	


}
	
	


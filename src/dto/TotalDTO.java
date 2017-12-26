package dto;

public class TotalDTO {
	
	private double total;
	private double total2;
	
	
	private double totalHistorial;
	private double totalHistorialDeli;
	




	public TotalDTO (){
		total=0;
		total2=0;
		totalHistorial=0;
		totalHistorialDeli=0;
		
	}
	
	public TotalDTO (Double price)
	{
		this.total=total;
		this.total2=total2;
		//this.totalHistorial=totalHistorial;
		//this.totalHistorialDeli=totalHistorialDeli;
		
		
		
		}
	
	public double getTotal(){
		return total;
	}
	
	public void setTotal (double total){
		this.total= total;
	}
	public double getTotal2(){
		return total2;
	}
	
	public void setTotal2 (double totalFacturaRetiro){
		this.total2= totalFacturaRetiro;
	}
	
	
	public double getTotalHistorial(){
		return totalHistorial;
	}
	
	public void setTotalHistorial (double d){
		this.totalHistorial= d;
	}
	public double getTotalHistorialDeli(){
		return totalHistorialDeli;
	}
	
	public void setTotalHistorialDeli (double totalHistorialDeli){
		this.totalHistorialDeli= totalHistorialDeli;
	}
	
	
	
	
	
	

}

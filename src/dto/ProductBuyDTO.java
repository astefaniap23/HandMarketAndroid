package dto;

import java.util.Date;


public class ProductBuyDTO {
	private ProductDTO product;
	private Integer countity;
	private Date create;
	
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	public Integer getCountity() {
		return countity;
	}
	public void setCountity(Integer countity) {
		this.countity = countity;
	}
	public Date getCreate() {
		return create;
	}
	public void setCreate(Date create) {
		this.create = create;
	}
}

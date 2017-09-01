package com.hylexus.test.model;


public class TOrder{

	private Integer id;

	private Integer userId;

	private Integer productId;

	public TOrder(){
		
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	
	public void setProductId(Integer productId){
		this.productId = productId;
	}
	
	public Integer getId(){
		return this.id;
	}
	
	public Integer getUserId(){
		return this.userId;
	}
	
	public Integer getProductId(){
		return this.productId;
	}
		
	public TOrder id(Integer id){
		this.id = id;
		return this;
	}
		
	public TOrder userId(Integer userId){
		this.userId = userId;
		return this;
	}
		
	public TOrder productId(Integer productId){
		this.productId = productId;
		return this;
	}
}
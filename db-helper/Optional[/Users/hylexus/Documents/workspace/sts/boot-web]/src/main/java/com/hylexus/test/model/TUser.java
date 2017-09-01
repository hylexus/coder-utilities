package com.hylexus.test.model;

import java.util.Date;
import java.math.BigDecimal;

public class TUser{

	private Integer id;

	// 注释email
	private String email;

	private String name;

	private String password;

	private String nickname;

	private Date createdAt;

	private BigDecimal decimalCol;

	public TUser(){
		
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	
	public void setCreatedAt(Date createdAt){
		this.createdAt = createdAt;
	}
	
	public void setDecimalCol(BigDecimal decimalCol){
		this.decimalCol = decimalCol;
	}
	
	public Integer getId(){
		return this.id;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public String getNickname(){
		return this.nickname;
	}
	
	public Date getCreatedAt(){
		return this.createdAt;
	}
	
	public BigDecimal getDecimalCol(){
		return this.decimalCol;
	}
		
	public TUser id(Integer id){
		this.id = id;
		return this;
	}
		
	public TUser email(String email){
		this.email = email;
		return this;
	}
		
	public TUser name(String name){
		this.name = name;
		return this;
	}
		
	public TUser password(String password){
		this.password = password;
		return this;
	}
		
	public TUser nickname(String nickname){
		this.nickname = nickname;
		return this;
	}
		
	public TUser createdAt(Date createdAt){
		this.createdAt = createdAt;
		return this;
	}
		
	public TUser decimalCol(BigDecimal decimalCol){
		this.decimalCol = decimalCol;
		return this;
	}
}
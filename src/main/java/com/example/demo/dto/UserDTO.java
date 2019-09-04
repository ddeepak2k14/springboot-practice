package com.example.demo.dto;



public class UserDTO {
	private String name;
	private long id;
	private AddressDTO address;
	
	public UserDTO(){
		
	}
	
	public UserDTO(String name, long id, AddressDTO address) {
		super();
		this.name = name;
		this.id = id;
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	
	

}

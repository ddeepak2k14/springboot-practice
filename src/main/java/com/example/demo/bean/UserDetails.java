package com.example.demo.bean;

import lombok.Data;

@Data
public class UserDetails {
	private String name;
	private String id;
	private String address;
	public UserDetails(String name, String id, String address) {
		super();
		this.name = name;
		this.id = id;
		this.address = address;
	}
	

	
}

package com.wildcodeschool.myStuff.mme.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int amount;
	private String location;
	private String description;
	private Date lastUsed;
	
	
	public Item (String name, int amount, String location, String description, Date lastUsed) {
		super();
		this.name = name;
		this.amount = amount;
		this.location = location;
		this.description = description;
		this.lastUsed = lastUsed;
	}
	
	
	public Item () {
		
	}
	
	
	

}

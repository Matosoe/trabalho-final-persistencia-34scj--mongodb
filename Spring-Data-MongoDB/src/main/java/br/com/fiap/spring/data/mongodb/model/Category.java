package br.com.fiap.spring.data.mongodb.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Category {

	@Id
	private String id;
	
	private String name;

	public Category(String name) {
		this.name = name;
	}

	public Category(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Category() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

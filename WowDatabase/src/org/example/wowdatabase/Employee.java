package org.example.wowdatabase;

public class Employee {

	private long id;
	private String name;
	private String email;
	
	public Employee(String name){
		this.setName(name);
	}
	public Employee(){
		
	}
	
	public long getId(){
		return id;
	}
	public void setId(long id){
		this.id = id;
	}
	public String getName(){
		return name;
	}
	public String getEmail(){
		return email;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
	@Override
	public String toString(){
		return "Employee id: " + id + ", name: " + name + ", e-mail: " + email;
	}
	
		
}


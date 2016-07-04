package model.db;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class User {
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("password")
	@JsonIgnore
	private String pwd;
	
	@JsonProperty("role")
	private int role;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("age")
	private int age;
	
	@JsonProperty("time")
	private Timestamp time;

	public String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email=email;	
	}
	
	public int getAge(){
		return age;	
	}
	
	public void setAge(int age){
		this.age=age;
	}
	
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
}


package model.db;

import java.sql.Date;
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
	private String pwd;
	
	@JsonProperty("role")
	private Integer role;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("age")
	private int age;
	
	@JsonProperty("time")
	private Timestamp time;
	
	
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
	@JsonProperty("birth")
	private Date birth;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonProperty
	private String content;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
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


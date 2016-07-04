package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailUser {
	@JsonProperty
	private String username;
	@JsonProperty
	private String email;
	@JsonProperty
	private String age;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
}

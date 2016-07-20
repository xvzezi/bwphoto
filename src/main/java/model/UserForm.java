package model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class UserForm {
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("password")
	private String pwd;
	
	@JsonProperty("repassword")
	private String repwd;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("birth")
	private Date birth;
	
	@JsonCreator
	public UserForm(@JsonProperty(value = "name") String name, 
					  @JsonProperty(value = "password") String pwd,
					     @JsonProperty(value="repassword")String repwd,
					         @JsonProperty(value="email")String email,
	                             @JsonProperty(value="birth")Date birth)
	{
		this.name = name;
		this.pwd = pwd;
		this.repwd=repwd;
		this.email=email;
		this.birth=birth;
	}
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}


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
	
	public String getEmail(){
		return email;
	}
	public void setEmail(String email)
	{
		this.email=email;	
	}
	


	
}


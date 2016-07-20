package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SimpleUser
 * @author Xu Zezi
 * @category Model Helper Class For Controller
 * @version
 * 		0.Initial Design
 * @since 2016.6.28
 * @Description:
 *   A Single Structure To Store Login Message
 */
public class SimpleUser {
	@JsonProperty("name")
	private String username;
	
	@JsonProperty("password")
	private String pwd;

	@JsonCreator
	public SimpleUser(@JsonProperty(value = "name") String name, 
					  @JsonProperty(value = "password") String pwd)
	{
		this.username = name;
		this.pwd = pwd;
	}
	
	public SimpleUser(){};
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}

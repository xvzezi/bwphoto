package model.db;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Friend{
	
	public Friend(String myName,String frName){
		this.myName=myName;
		this.frName=frName;
	}
	
	@JsonProperty("myName")
	private String myName;
	
	@JsonProperty("frName")
	private String frName;

	public String getMyName() {
		return myName;
	}
	public void setMyName(String myName) {
		this.myName = myName;
	}
	
	public String getFrName() {
		return frName;
	}
	public void setFrName(String frName) {
		this.frName = frName;
	}

}


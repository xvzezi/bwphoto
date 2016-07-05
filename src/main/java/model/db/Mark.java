package model.db;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Mark{
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("item")
	private Item item;
	
	@JsonProperty("thisUser")
	private User thisUser;
	
	@JsonProperty("markUser")
	private User markUser;
	
	@JsonProperty("content")
	private String content;
	
	@JsonProperty("time")
	private Timestamp time;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item= item;
	}
	public User getThisUser() {
		return thisUser;
	}
	public void setThisUser(User thisUser) {
		this.thisUser =thisUser;
	}
	public User getMarkUser() {
		return markUser;
	}
	public void setMarkUser(User markUser) {
		this.markUser = markUser;
	}	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time; 
	}
}


package model.db;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Mark
{
	
	@JsonProperty("id")
	private int id;

	@JsonProperty("nested")
	private int parent;

	@JsonProperty("item_id")
	private Integer itemId;
	
	@JsonProperty("thisUser")
	private String thisname;
	
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
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId= itemId;
	}
	public String getThisname() {
		return thisname;
	}
	public void setThisname(String thisname) {
		this.thisname =thisname;
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

	public int getParent()
	{
		return parent;
	}

	public void setParent(int parent)
	{
		this.parent = parent;
	}
}


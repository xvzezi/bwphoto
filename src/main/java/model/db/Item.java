package model.db;

import java.util.Set;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Item{
	
	@JsonProperty("id")
	private Integer id;
	
	@JsonProperty("image_id")
	private Integer imageId;
	
	@JsonProperty("isbn")
	private String isbn;
	
	@JsonProperty("music_id")
	private String musicId;
	
	@JsonProperty("memory_id")
	private Integer memoryId;

	@JsonProperty("username")
	private String userName;

	@JsonProperty
	private char status;

	@JsonProperty
	private Timestamp time;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getImageId() {
		return imageId;
	}
	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getMusicId() {
		return musicId;
	}
	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}
	
	
	public Integer getMemoryId() {
		return memoryId;
	}
	public void setMemoryId(Integer memoryId) {
		this.memoryId = memoryId;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName=userName;
	}

	public char getStatus()
	{
		return status;
	}

	public void setStatus(char status)
	{
		this.status = status;
	}

	public Timestamp getTime()
	{
		return time;
	}

	public void setTime(Timestamp time)
	{
		this.time = time;
	}
}



package model.db;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Music{
	private int id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("author")
	private String author;
	
	@JsonProperty("music")
	private Byte[] music;

	public int getId(){
		return id;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Byte[] getMusic() {
		return music;
	}
	public void setMusic(Byte[] music) {
		this.music = music;
	}
}


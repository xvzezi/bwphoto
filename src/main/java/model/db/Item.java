package model.db;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Item{
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("image")
	private Image image;
	
	@JsonProperty("book")
	private Book book;
	
	@JsonProperty("music")
	private Music music;
	
	@JsonProperty("memory")
	private Memory memory;
	
	@JsonProperty("mark")
	private Mark mark;


	public int getId() {
		return id;
	}
	private void setId(int id) {
		this.id = id;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Music getMusic() {
		return music;
	}
	public void setMusic(Music music) {
		this.music = music;
	}
	
	
	public Memory getMemory() {
		return memory;
	}
	public void setMemory(Memory memory) {
		this.memory = memory;
	}
}


package model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hasee on 2016/7/11.
 */
public class Message
{


	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	@JsonProperty
	private String content;

}

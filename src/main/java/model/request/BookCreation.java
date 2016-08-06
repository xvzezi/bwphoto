package model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Book Request Body
 * @since  2016/8/6.
 */
public class BookCreation
{
	@JsonProperty
	private String url;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}

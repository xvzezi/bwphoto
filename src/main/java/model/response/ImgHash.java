package model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hasee on 2016/9/12.
 */
public class ImgHash
{
	@JsonProperty
	private int resource_id;
	@JsonProperty
	private String hash;

	public ImgHash(int resource_id, String hash)
	{
		this.resource_id = resource_id;
		this.hash = hash;
	}

	public int getResource_id()
	{
		return resource_id;
	}

	public void setResource_id(int resource_id)
	{
		this.resource_id = resource_id;
	}

	public String getHash()
	{
		return hash;
	}

	public void setHash(String hash)
	{
		this.hash = hash;
	}
}

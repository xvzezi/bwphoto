package model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Improved Use body
 * @since 2016/9/1.
 */
public class ResourceCreation
{
	@JsonProperty
	private Integer emotion;
	@JsonProperty
	private Integer type;

	public Integer getEmotion()
	{
		return emotion;
	}

	public void setEmotion(Integer emotion)
	{
		this.emotion = emotion;
	}

	public Integer getType()
	{
		return type;
	}

	public void setType(Integer type)
	{
		this.type = type;
	}
}

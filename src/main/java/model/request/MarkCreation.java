package model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

/**
 * Mark Creation Message
 * @author Xu Zezi
 * @since 2016/7/19.
 */
public class MarkCreation
{
	@JsonProperty
	private String content;
	@JsonProperty
	private Timestamp timestamp;

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public Timestamp getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp)
	{
		this.timestamp = timestamp;
	}

	@Override
	public String toString()
	{
		return "MarkCreation{" +
				"content='" + content + '\'' +
				", timestamp=" + timestamp +
				'}';
	}
}

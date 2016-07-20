package model.db;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 交友请求
 * @author Xu Zezi
 * @since 2016/7/13
 * @version
 *      0   basic info
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendRequest implements Serializable
{
	@JsonProperty
	private String applyer;
	@JsonProperty
	private String applyee;
	@JsonProperty
	private char status;

	public String getApplyer()
	{
		return applyer;
	}

	public void setApplyer(String applyer)
	{
		this.applyer = applyer;
	}

	public char getStatus()
	{
		return status;
	}

	public void setStatus(char status)
	{
		this.status = status;
	}

	public String getApplyee()
	{
		return applyee;
	}

	public void setApplyee(String applyee)
	{
		this.applyee = applyee;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FriendRequest that = (FriendRequest) o;

		if (applyer != null ? !applyer.equals(that.applyer) : that.applyer != null) return false;
		return applyee != null ? applyee.equals(that.applyee) : that.applyee == null;

	}

	@Override
	public int hashCode()
	{
		int result = applyer != null ? applyer.hashCode() : 0;
		result = 31 * result + (applyee != null ? applyee.hashCode() : 0);
		return result;
	}
}

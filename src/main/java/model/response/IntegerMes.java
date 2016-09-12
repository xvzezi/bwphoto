package model.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hasee on 2016/7/12.
 */
public class IntegerMes
{
	@JsonProperty
	private Integer amount;

	@JsonCreator
	public IntegerMes(@JsonProperty("amount") int amount)
	{
		this.amount = amount;
	}

	public Integer getAmount()
	{
		return amount;
	}

	public void setAmount(Integer amount)
	{
		this.amount = amount;
	}

}

package model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

/**
 * Class Rec Data
 * @since 2016/8/1
 * @author Xu Zezi
 * @version
 *      0   basic impl.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecData
{
	@JsonProperty
	private String name;

	@JsonProperty
	private double emotion;

	@JsonProperty
	private double type;

	@JsonProperty("friend_emotion")
	private double f_emotion;

	@JsonProperty("friend_type")
	private double f_type;

	@JsonProperty
	private Timestamp checktime;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getEmotion()
	{
		return emotion;
	}

	public void setEmotion(double emotion)
	{
		this.emotion = emotion;
	}

	public double getType()
	{
		return type;
	}

	public void setType(double type)
	{
		this.type = type;
	}

	public double getF_emotion()
	{
		return f_emotion;
	}

	public void setF_emotion(double f_emotion)
	{
		this.f_emotion = f_emotion;
	}

	public double getF_type()
	{
		return f_type;
	}

	public void setF_type(double f_type)
	{
		this.f_type = f_type;
	}

	public Timestamp getChecktime()
	{
		return checktime;
	}

	public void setChecktime(Timestamp checktime)
	{
		this.checktime = checktime;
	}
}

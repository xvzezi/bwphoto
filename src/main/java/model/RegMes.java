package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 返回信息 success or failed
 * @author Sturmfy
 * @since 2016.6.30
 */
@JsonInclude(Include.NON_NULL)
public class RegMes {
	@JsonProperty("success")
	private String success;
	@JsonProperty("fail")
	private String fail;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getFail() {
		return fail;
	}
	public void setFail(String fail) {
		this.fail = fail;
	}

	public static RegMes SUCCESS(String mes)
	{
		RegMes rm = new RegMes();
		rm.setSuccess(mes);
		return rm;
	}

	public static RegMes FAIL(String mes)
	{
		RegMes rm = new RegMes();
		rm.setFail(mes);
		return rm;
	}
}

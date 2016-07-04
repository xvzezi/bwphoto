package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 注册返回信息
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
}

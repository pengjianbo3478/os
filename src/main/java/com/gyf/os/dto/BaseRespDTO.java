package com.gyf.os.dto;
import java.io.Serializable;
import com.gyf.os.util.GsonUtils;
public class BaseRespDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String respCode;
	private String respMsg;

	public BaseRespDTO(String respCode, String respMsg) {
		this.respCode = respCode;
		this.respMsg = respMsg;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public BaseRespDTO() {
	}
	
	@Override
	public String toString() {
		return GsonUtils.toJson(this);
	}
}

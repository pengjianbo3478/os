package com.gyf.os.dto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.gyf.os.util.GsonUtils;
public class PoSplitRespDTO extends BaseRespDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	List<Double> body = new ArrayList<Double>();

	public List<Double> getBody() {
		return body;
	}

	public void setBody(List<Double> body) {
		this.body = body;
	}

	public PoSplitRespDTO() {
		super();
	}

	public PoSplitRespDTO(String respCode, String respMsg) {
		super(respCode, respMsg);
	}

	@Override
	public String toString() {
		return GsonUtils.toJson(this);
	}
}

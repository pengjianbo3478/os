package com.gyf.os.dto;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gyf.os.util.GsonUtils;
public class PoSplitReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private double poAmt;
	private String sign;
	private String orgCode;
	private String orderNo;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime orderTime;

	public double getPoAmt() {
		return poAmt;
	}

	public void setPoAmt(double poAmt) {
		this.poAmt = poAmt;
	}

	@Override
	public String toString() {
		return GsonUtils.toJson(this);
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}
}

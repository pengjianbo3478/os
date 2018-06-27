package com.gyf.os.enums;
public enum ResultCode {
	SUCCESS("00", "交易成功"), 
	FAIL("01", "交易失败"), 
	OS_02("02", "金额无效"),
	OS_03("03", "金额超限"),
	OS_04("04", "验签失败"),
	OS_05("05", "机构代码无效");
	private final String code;
	private final String desc;

	private ResultCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public String getCode() {
		return code;
	}
}

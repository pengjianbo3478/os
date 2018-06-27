package com.gyf.os.exception;
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String code;
	private String desc;
	private String info;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String code, String desc, Throwable cause) {
		super("code=" + code + "," + "desc=" + desc, cause);
		this.code = code;
		this.desc = desc;
	}

	public ServiceException(String code, String desc, String info, Throwable cause) {
		super("code=" + code + "," + "desc=" + desc, cause);
		this.code = code;
		this.desc = desc;
		this.info = info;
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String code, String desc) {
		super("code=" + code + "," + "desc=" + desc);
		this.code = code;
		this.desc = desc;
	}

	public ServiceException(String code, String desc, String remark) {
		super("code=" + code + "," + "desc=" + desc + "," + "remark=" + remark);
		this.code = code;
		this.desc = desc;
		this.info = remark;
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}

package com.cognixia.jump.exception;

import java.util.Date;

public class ErrorDetailsMoreInfo extends ErrorDetails {
	private Integer status;
	public ErrorDetailsMoreInfo(Date timestamp, String message, String details, Integer status) {
		super(timestamp, message, details);
		this.status = status;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return super.toString() + "ErrorDetailsMoreInfo [status=" + status + "]";
	}

}

package com.tao.jimmy.util;

import java.util.Map;

public class AjaxMsg {
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String REDIRECT = "redirect";
//	public static final String LOGIN = "login";

	private String status;
	private String resHtml;
	private String resUrl;
	private Object resObj;
	private Map<String, String> info;

	public AjaxMsg() {
		super();
	}

	public Object getResObj() {
		return resObj;
	}

	public void setResObj(Object resObj) {
		this.resObj = resObj;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResHtml() {
		return resHtml;
	}

	public void setResHtml(String resHtml) {
		this.resHtml = resHtml;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public Map<String, String> getInfo() {
		return info;
	}

	public void setInfo(Map<String, String> info) {
		this.info = info;
	}

}

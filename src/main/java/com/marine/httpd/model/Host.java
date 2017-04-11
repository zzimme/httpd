package com.marine.httpd.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Host implements Serializable {

	private static final long serialVersionUID = -89207497789985481L;

	private String serverName;
	private String documentRoot;
	private String forbidden;
	private String notFound;
	private String serverError;
	
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getDocumentRoot() {
		return documentRoot;
	}
	public void setDocumentRoot(String documentRoot) {
		this.documentRoot = documentRoot;
	}
	public String getForbidden() {
		return forbidden;
	}
	public void setForbidden(String forbidden) {
		this.forbidden = forbidden;
	}
	public String getNotFound() {
		return notFound;
	}
	public void setNotFound(String notFound) {
		this.notFound = notFound;
	}
	public String getServerError() {
		return serverError;
	}
	public void setServerError(String serverError) {
		this.serverError = serverError;
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	
}

package com.endava.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Ionuț Păduraru
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

	private String message;
	private String detail;

	public ErrorResponse(String message) {
        this.message = message;
    }

	public ErrorResponse(String message, String detail) {
		this.message = message;
		this.detail = detail;
	}

    public String getMessage() {
        return message;
    }

	public String getDetail() {
		return detail;
	}
}
package com.multitenant.app.common.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * ErrorResponseDto
 *
 * Standard API error response model used to return
 * validation, business, and system error details
 * in a consistent format.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDto {

	/* Timestamp when the error occurred */
	private LocalDateTime timestamp;

	/* HTTP status code of the response */
	private int status;

	/* HTTP error type (BAD_REQUEST, NOT_FOUND, etc.) */
	private String error;

	/* User-friendly error message */
	private String message;

	/* API endpoint path where the error occurred */
	private String path;

	/* Field validation errors (field name -> error message) */
	private Map<String, String> fieldErrors;

}
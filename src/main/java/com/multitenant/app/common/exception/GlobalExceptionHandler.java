package com.multitenant.app.common.exception;

import com.multitenant.app.common.response.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler
 *
 * Handles all application-wide exceptions and converts them
 * into standardized REST API error responses.
 *
 * Ensures consistent error handling, proper HTTP status mapping,
 * and secure exception management across the application.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Builds standardized error response format.
	 */
	private ResponseEntity<ErrorResponseDto> buildErrorResponse(String message, HttpStatus status, HttpServletRequest request) {
		ErrorResponseDto response = ErrorResponseDto.builder()
			.timestamp(LocalDateTime.now())
			.status(status.value())
			.error(status.name())
			.message(message)
			.path(request.getRequestURI())
			.build();
		return ResponseEntity.status(status).body(response);
	}

	/**
	 * Handles validation errors triggered by @Valid.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDto> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
		Map<String, String> fieldErrors = new HashMap<>();

		ex.getBindingResult()
			.getFieldErrors()
			.forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

		ErrorResponseDto response = ErrorResponseDto.builder()
			.timestamp(LocalDateTime.now())
			.status(HttpStatus.BAD_REQUEST.value())
			.error(HttpStatus.BAD_REQUEST.name())
			.message("Validation failed")
			.path(request.getRequestURI())
			.fieldErrors(fieldErrors)
			.build();
		return ResponseEntity.badRequest().body(response);
	}

	/**
	 * Handles duplicate resource errors.
	 */
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponseDto> handleDuplicateResource(DuplicateResourceException ex, HttpServletRequest request) {
		Map<String, String> fieldErrors = null;
		if (ex.getFieldName() != null) {
			fieldErrors = new HashMap<>();
			fieldErrors.put(ex.getFieldName(), ex.getMessage());
		}

		ErrorResponseDto response = ErrorResponseDto.builder()
			.timestamp(LocalDateTime.now())
			.status(HttpStatus.BAD_REQUEST.value())
			.error(HttpStatus.BAD_REQUEST.name())
			.message(ex.getMessage())
			.path(request.getRequestURI())
			.fieldErrors(fieldErrors)
			.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	/**
	 * Handles resource not found errors.
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
	}

	/**
	 * Handles bad request errors.
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponseDto> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * Handles all unhandled application exceptions.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex, HttpServletRequest request) {
		return buildErrorResponse("Internal Server Error..", HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	/**
	 * Handles authentication failures.
	 */
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponseDto> handleBadCredentials(BadCredentialsException ex,
			HttpServletRequest request) {
		return buildErrorResponse("Invalid username or password", HttpStatus.UNAUTHORIZED, request);
	}

}
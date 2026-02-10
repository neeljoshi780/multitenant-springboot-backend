package com.multitenant.app.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * PageResponseDto
 *
 * Generic response DTO used to return paginated API results.
 * Contains pagination metadata along with the actual data content.
 *
 * @param <T> type of elements in the paginated response
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageResponseDto<T> {

	/* Data records for the current page */
	private List<T> content;

	/* Current page index (zero-based) */
	private int pageNumber;

	/* Number of records per page */
	private int pageSize;

	/* Total number of records available */
	private long totalElements;

	/* Total number of pages */
	private int totalPages;

	/* Indicates if next page exists */
	private boolean hasNext;

	/* Indicates if previous page exists */
	private boolean hasPrevious;

}
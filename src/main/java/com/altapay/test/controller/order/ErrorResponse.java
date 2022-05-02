package com.altapay.test.controller.order;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ErrorResponse {
	@Builder.Default
	private final Instant timestamp = Instant.now();
	private final int status;
	private final String error;
	private final String message;
	private final String path;
}

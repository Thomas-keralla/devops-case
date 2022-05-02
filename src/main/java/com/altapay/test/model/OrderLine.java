package com.altapay.test.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(toBuilder = true)
public class OrderLine {
	@Builder.Default
	private String id = UUID.randomUUID().toString();
	private String code;
	private String description;
	private int quantity;
	private double price;
}

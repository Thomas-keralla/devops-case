package com.altapay.test.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionReference {
	private String id;
	private TransactionType type;
	private double amount;
}

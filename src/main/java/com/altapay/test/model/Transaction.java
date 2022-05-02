package com.altapay.test.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.springframework.ui.Model;

import javax.validation.ValidationException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
@Getter
public abstract class Transaction {
	protected String id = UUID.randomUUID().toString();
	protected Instant date = Instant.now();
	protected double amount;
	@JsonIgnore
	protected ModelFactory modelFactory;

	public Transaction(ModelFactory modelFactory, String id, double amount) {
		this.modelFactory = modelFactory;
		if(id != null) {
			this.id = id;
		}
		this.amount = amount;
	}

	public abstract TransactionType getType();

	public double getSettledAmount() {
		return 0;
	}

	public Transaction release() {
		throw new ValidationException("release is not supported for "+getType());
	}

	public Transaction capture(double amount) {
		throw new ValidationException("release is not supported for "+getType());
	}

	public Transaction refund(double amount) {
		throw new ValidationException("refund is not supported for "+getType());
	}
}

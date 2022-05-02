package com.altapay.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import javax.validation.ValidationException;
import java.beans.Transient;
import java.util.*;

@Getter
public class ShopOrder {
	private String id = UUID.randomUUID().toString();
	private List<OrderLine> orderLines = new ArrayList<>();
	private List<Transaction> transactions = new ArrayList<>();
	@JsonIgnore
	private transient ModelFactory modelFactory;

	@Builder(toBuilder = true)
	public ShopOrder(ModelFactory modelFactory, String id, @Singular List<OrderLine> orderLines, @Singular List<Transaction> transactions) {
		this.modelFactory = modelFactory;
		if(id != null) {
			this.id = id;
		}
		if(orderLines != null) {
			this.orderLines.addAll(orderLines);
		}
		if(transactions != null) {
			this.transactions.addAll(transactions);
		}
	}

	public Transaction reserve() throws ValidationException {
		if(!getLastTransaction().isPresent()) {
			Transaction reserve = modelFactory.getReserveTransaction().toBuilder()
					.amount(getOrderAmount())
					.build();
			transactions.add(reserve);
			return reserve;
		}
		throw new ValidationException("The shop order has already been reserved");
	}

	public Transaction release() throws ValidationException {
		Transaction release = getLastTransaction()
				.orElseThrow(() -> new ValidationException("The order is not reserved"))
				.release();
		transactions.add(release);
		return release;
	}

	@JsonIgnore
	public Optional<Transaction> getLastTransaction() {
		if(!transactions.isEmpty()) {
			return Optional.of(transactions.get(transactions.size()-1));
		}
		return Optional.empty();
	}

	public double getOrderAmount() {
		return orderLines.stream()
				.map(elem -> elem.getPrice()*elem.getQuantity())
				.reduce((double)0, Double::sum);
	}

	public double getSettledAmount() {
		return transactions.stream()
				.map(Transaction::getSettledAmount)
				.reduce((double) 0, Double::sum);
	}

	public Transaction capture(double amount) throws ValidationException {
		if(getSettledAmount() + amount > getOrderAmount()) {
			throw new ValidationException("There is only "+(getOrderAmount() - getSettledAmount())+" available to be captured");
		}
		Transaction capture = getLastTransaction()
				.orElseThrow(() -> new ValidationException("You can't capture an order that has not been reserved"))
				.capture(amount);
		transactions.add(capture);
		return capture;
	}

	public Transaction refund(double amount) throws ValidationException {
		if(getSettledAmount() <= amount ) {
			throw new ValidationException("There is only "+(getSettledAmount())+" available to be refunded");
		}
		Transaction refund = getLastTransaction()
				.orElseThrow(() -> new ValidationException("You can't capture an order that has not been reserved"))
				.refund(amount);
		transactions.add(refund);
		return refund;
	}
}

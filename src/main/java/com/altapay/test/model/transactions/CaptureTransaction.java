package com.altapay.test.model.transactions;

import com.altapay.test.model.ModelFactory;
import com.altapay.test.model.Transaction;
import com.altapay.test.model.TransactionType;
import lombok.Builder;

public class CaptureTransaction extends Transaction {

	@Builder(toBuilder = true)
	public CaptureTransaction(ModelFactory modelFactory, String id, double amount) {
		super(modelFactory, id, amount);
	}

	@Override
	public TransactionType getType() {
		return TransactionType.CAPTURE;
	}

	@Override
	public Transaction capture(double amount) {
		return modelFactory.getCaptureTransaction().toBuilder().amount(amount).build();
	}

	@Override
	public Transaction refund(double amount) {
		return modelFactory.getRefundTransaction().toBuilder().amount(amount).build();
	}

	@Override
	public double getSettledAmount() {
		return amount;
	}
}

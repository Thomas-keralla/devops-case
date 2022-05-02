package com.altapay.test.model.transactions;

import com.altapay.test.model.ModelFactory;
import com.altapay.test.model.Transaction;
import com.altapay.test.model.TransactionType;
import lombok.Builder;

public class ReserveTransaction extends Transaction {

	@Builder(toBuilder = true)
	public ReserveTransaction(ModelFactory modelFactory, String id, double amount) {
		super(modelFactory, id, amount);
	}

	@Override
	public TransactionType getType() {
		return TransactionType.RESERVE;
	}

	@Override
	public Transaction release() {
		return modelFactory.getReleaseTransaction().toBuilder().amount(amount).build();
	}

	@Override
	public Transaction capture(double amount) {
		return modelFactory.getCaptureTransaction().toBuilder().amount(amount).build();
	}
}

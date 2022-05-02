package com.altapay.test.model.transactions;

import com.altapay.test.model.ModelFactory;
import com.altapay.test.model.Transaction;
import com.altapay.test.model.TransactionType;
import lombok.Builder;

public class ReleaseTransaction extends Transaction {
	@Builder(toBuilder = true)
	public ReleaseTransaction(ModelFactory modelFactory, String id, double amount) {
		super(modelFactory, id, amount);
	}

	@Override
	public TransactionType getType() {
		return TransactionType.RELEASE;
	}
}

package com.altapay.test.controller.transaction;

import com.altapay.test.model.Transaction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class TransactionExecutionResponse {
	private Transaction transaction;
}

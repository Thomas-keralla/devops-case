package com.altapay.test.controller.transaction;

import com.altapay.test.controller.order.ControllerOrderLine;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionExecutionRequest {
	private double amount;
}

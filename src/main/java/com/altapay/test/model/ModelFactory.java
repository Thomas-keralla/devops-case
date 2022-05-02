package com.altapay.test.model;

import com.altapay.test.model.transactions.CaptureTransaction;
import com.altapay.test.model.transactions.RefundTransaction;
import com.altapay.test.model.transactions.ReleaseTransaction;
import com.altapay.test.model.transactions.ReserveTransaction;

public class ModelFactory {
	public ShopOrder getShopOrder() {
		return ShopOrder.builder().modelFactory(this).build();
	}

	public OrderLine getOrderLine() {
		return OrderLine.builder().build();
	}

	public ReserveTransaction getReserveTransaction() {
		return ReserveTransaction.builder().modelFactory(this).build();
	}

	public ReleaseTransaction getReleaseTransaction() {
		return ReleaseTransaction.builder().modelFactory(this).build();
	}

	public CaptureTransaction getCaptureTransaction() {
		return CaptureTransaction.builder().modelFactory(this).build();
	}

	public RefundTransaction getRefundTransaction() {
		return RefundTransaction.builder().modelFactory(this).build();
	}
}

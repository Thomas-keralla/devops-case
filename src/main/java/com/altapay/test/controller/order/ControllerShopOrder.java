package com.altapay.test.controller.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ControllerShopOrder {
	private List<ControllerOrderLine> orderLines;
}

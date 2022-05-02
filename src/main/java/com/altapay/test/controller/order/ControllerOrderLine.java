package com.altapay.test.controller.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ControllerOrderLine {
	private String code;
	private String description;
	private int quantity;
	private double price;
}

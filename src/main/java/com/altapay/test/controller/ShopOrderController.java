package com.altapay.test.controller;

import com.altapay.test.controller.order.ControllerShopOrder;
import com.altapay.test.controller.order.ErrorResponse;
import com.altapay.test.controller.transaction.TransactionExecutionRequest;
import com.altapay.test.controller.transaction.TransactionExecutionResponse;
import com.altapay.test.model.ModelFactory;
import com.altapay.test.model.ShopOrder;
import com.altapay.test.persistance.IShopOrderRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ValidationException;
import java.util.List;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@Controller
public class ShopOrderController {
	private IShopOrderRepository repository;
	private ModelFactory modelFactory;

	@Autowired
	public ShopOrderController(IShopOrderRepository repository, ModelFactory modelFactory) {
		this.repository = repository;
		this.modelFactory = modelFactory;
	}

	@GetMapping("/shopOrders")
	@ResponseBody
	public List<ShopOrder> shopOrders() {
		return repository.retrieveAll();
	}

	@GetMapping("/shopOrders/{id}")
	@ResponseBody
	public ShopOrder shopOrder(@PathVariable(value="id", required = true) String id) {
		return repository.retrieve(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop order with id "+id+" not found"));
	}

	@PostMapping("/shopOrders")
	@ResponseBody
	public ShopOrder createShopOrder(@RequestBody ControllerShopOrder request) {
		ShopOrder.ShopOrderBuilder order = modelFactory.getShopOrder().toBuilder();
		request.getOrderLines().forEach(elem -> {
			order.orderLine(
					modelFactory.getOrderLine().toBuilder()
							.code(elem.getCode())
							.price(elem.getPrice())
							.quantity(elem.getQuantity())
							.description(elem.getDescription())
							.build());
		});
		return repository.save(order.build());
	}

	@PostMapping("/shopOrders/{id}/reserve")
	@ResponseBody
	public ResponseEntity<TransactionExecutionResponse> reserve(@PathVariable(value="id", required = true) String id)
			throws ValidationException {
		ShopOrder order = shopOrder(id);
		TransactionExecutionResponse response = TransactionExecutionResponse.builder().transaction(order.reserve()).build();
		repository.save(order);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/shopOrders/{id}/release")
	@ResponseBody
	public ResponseEntity<TransactionExecutionResponse> release(@PathVariable(value="id", required = true) String id)
			throws ValidationException {
		ShopOrder order = shopOrder(id);
		TransactionExecutionResponse response = TransactionExecutionResponse.builder().transaction(order.release()).build();
		repository.save(order);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/shopOrders/{id}/capture")
	@ResponseBody
	public ResponseEntity<TransactionExecutionResponse> capture(@PathVariable(value="id", required = true) String id, @RequestBody TransactionExecutionRequest request)
			throws ValidationException {
		ShopOrder order = shopOrder(id);
		TransactionExecutionResponse response = TransactionExecutionResponse.builder().transaction(order.capture(request.getAmount())).build();
		repository.save(order);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/shopOrders/{id}/refund")
	@ResponseBody
	public ResponseEntity<TransactionExecutionResponse> refund(@PathVariable(value="id", required = true) String id, @RequestBody TransactionExecutionRequest request)
		throws ValidationException {
		ShopOrder order = shopOrder(id);
		TransactionExecutionResponse response = TransactionExecutionResponse.builder().transaction(order.refund(request.getAmount())).build();
		repository.save(order);
		return ResponseEntity.ok(response);
	}

	@ExceptionHandler({ ValidationException.class})
	public ResponseEntity<ErrorResponse> handleException(ValidationException exception) {
		return ResponseEntity.badRequest()
				.body(
						ErrorResponse.builder()
								.status(400)
								.error("Bad request")
								.message(exception.getMessage())
								.build()
				);
	}
}

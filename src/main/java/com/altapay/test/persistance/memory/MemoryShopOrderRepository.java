package com.altapay.test.persistance.memory;

import com.altapay.test.model.ModelFactory;
import com.altapay.test.model.OrderLine;
import com.altapay.test.model.ShopOrder;
import com.altapay.test.persistance.IShopOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MemoryShopOrderRepository implements IShopOrderRepository {
	private List<ShopOrder> orders;
	private ModelFactory modelFactory;

	@Autowired
	public MemoryShopOrderRepository(ModelFactory modelFactory) {
		this.modelFactory = modelFactory;
	}

	@Override
	public List<ShopOrder> retrieveAll() {
		if(orders == null) {
			ShopOrder order1 = modelFactory.getShopOrder().toBuilder()
					.orderLine(OrderLine.builder().code("A102391203").description("Pretty shoes").price(123).quantity(1).build())
					.orderLine(OrderLine.builder().code("123989ads0").description("Pretty hat").price(123).quantity(1).build())
					.build();
			orders = Stream.of(order1).collect(Collectors.toList());
		}
		return orders;
	}

	@Override
	public ShopOrder save(ShopOrder entity) {
		retrieve(entity.getId())
				.ifPresent(e -> orders.remove(e));
		orders.add(entity);
		return retrieve(entity.getId()).get();
	}

	@Override
	public Optional<ShopOrder> retrieve(String id) {
		return retrieveAll().stream()
				.filter(e -> e.getId().equals(id))
				.findFirst();
	}
}

package com.altapay.test.ioc;

import com.altapay.test.model.ModelFactory;
import com.altapay.test.persistance.IShopOrderRepository;
import com.altapay.test.persistance.memory.MemoryShopOrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class FrontendTestConfiguration {
	@Bean
	public IShopOrderRepository repository() {
		return new MemoryShopOrderRepository(modelFactory());
	}

	@Bean
	public ModelFactory modelFactory() {
		return new ModelFactory();
	}
}

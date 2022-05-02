package com.altapay.test.persistance;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> {
	List<T> retrieveAll();
	T save(T entity);
	Optional<T> retrieve(String id);
}

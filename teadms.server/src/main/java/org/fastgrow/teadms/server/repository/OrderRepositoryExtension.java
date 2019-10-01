package org.fastgrow.teadms.server.repository;

import java.util.List;

import org.fastgrow.teadms.server.domain.Order;
import org.fastgrow.teadms.server.repository.GenericRepository.DateFilter;
import org.fastgrow.teadms.server.repository.GenericRepository.Pager;

public interface OrderRepositoryExtension {
	List<Order> findByTextFieldsLikeAndProductIdAndDates(String search, Long productId, DateFilter shippingDateFilter, Pager pager);
}

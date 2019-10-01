package org.fastgrow.teadms.server.repository;

import org.fastgrow.teadms.server.domain.Order;

public interface OrderRepository extends GenericRepository<Order, Long>, OrderRepositoryExtension {
	
}

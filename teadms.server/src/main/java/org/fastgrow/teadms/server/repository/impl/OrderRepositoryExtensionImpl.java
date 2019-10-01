package org.fastgrow.teadms.server.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.apache.commons.lang3.StringUtils;
import org.fastgrow.teadms.server.domain.Order;
import org.fastgrow.teadms.server.repository.OrderRepositoryExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class OrderRepositoryExtensionImpl extends GenericRepositoryImpl<Order, Long> implements OrderRepositoryExtension {

    @Autowired
    public OrderRepositoryExtensionImpl(EntityManager em) {
        super(Order.class, em);
    }
    
    public List<Order> findByTextFieldsLikeAndProductIdAndDates(String search, Long productId, DateFilter shippingDateFilter, Pager pager) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(getDomainClass());
		EntityType<Order> type = getEntityManager().getMetamodel().entity(Order.class);
		Root<Order> root = query.from(getDomainClass());
		// Construct a list of parameters
		List<Predicate> predicates = new ArrayList<>();
		
		// text fields like condition
		if (StringUtils.isNotBlank(search)) {
			String searchParam = ("%" + StringUtils.trimToEmpty(search) + "%").toLowerCase();
			Predicate searchPredicate = builder.or(
					builder.like(
							builder.lower(root.get(type.getDeclaredSingularAttribute("address", String.class))), 
							searchParam),
					builder.like(
							builder.lower(root.get(type.getDeclaredSingularAttribute("receiverName", String.class))), 
							searchParam)
					//TODO: 
//	              || JinqStream.from(order.getItems())
//                  .anyMatch(item -> JPQL.like(item.getProduct().getName().toLowerCase(), searchParam)));					
					);
			predicates.add(searchPredicate);			
		}
		
		// shippind date filter
		if (null != shippingDateFilter) {
			if (shippingDateFilter.hasStartDate()) {
				predicates.add(builder.greaterThanOrEqualTo(root.<Date>get("shippingDate"), shippingDateFilter.getStartDate()));
			}
			
			if (shippingDateFilter.hasEndDate()) {
				predicates.add(builder.lessThanOrEqualTo(root.<Date>get("shippingDate"), shippingDateFilter.getEndDate()));
			}
		}
		
		// product id condition
//  if (productId != null) {
//      all = all.where(order ->
//              JinqStream.from(order.getItems())
//              .where(item -> Objects.equals(productId, item.getProduct().getId()))
//              .count() > 0);
//  }
		
		// evaluate query and return result list
		query.select(root).where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Order> q = getEntityManager().createQuery(query);
		if (null != pager) {
			q.setFirstResult(pager.getOffset());
			q.setMaxResults(pager.getLimit());
		}
		return q.getResultList();
    }

//	JinqStream<Order> byFieldsProductIdAndDates(String search, Long productId, Date startDate, Date endDate);
//    public JinqStream<Order> byFieldsProductIdAndDates(String search, Long productId, Date startDate, Date endDate) {
//    	JinqStream<Order> all = this.all();
//        if (StringUtils.isNotBlank(search)) {
//            String searchParam = "%" + StringUtils.trimToEmpty(search) + "%";
//            all = all.where(order -> JPQL.like(order.getAddress().toLowerCase(), searchParam)
//                    || JPQL.like(order.getReceiverName().toLowerCase(), searchParam)
//                    || JinqStream.from(order.getItems())
//                            .anyMatch(item -> JPQL.like(item.getProduct().getName().toLowerCase(), searchParam)));
//        }
//        if (null != startDate) {
//            all = all.where(order -> !order.getShippingDate().before(startDate));
//        }
//        if (null != endDate) {
//            all = all.where(order -> order.getShippingDate().before(endDate));
//        }
//        if (productId != null) {
//            all = all.where(order ->
//                    JinqStream.from(order.getItems())
//                    .where(item -> Objects.equals(productId, item.getProduct().getId()))
//                    .count() > 0);
//        }
//        return all;
//    }
}

package org.fastgrow.teadms.server.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BeanParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.fastgrow.teadms.api.Constants;
import org.fastgrow.teadms.api.endpoint.OrderEndpoint;
import org.fastgrow.teadms.api.model.OrderDto;
import org.fastgrow.teadms.api.model.OrderEditDto;
import org.fastgrow.teadms.api.model.OrderQueryDto;
import org.fastgrow.teadms.api.model.QueryPageDto;
import org.fastgrow.teadms.server.domain.Order;
import org.fastgrow.teadms.server.mapper.OrderMapper;
import org.fastgrow.teadms.server.repository.GenericRepository.DateFilter;
import org.fastgrow.teadms.server.repository.GenericRepository.Pager;
import org.fastgrow.teadms.server.repository.OrderRepository;
import org.fastgrow.teadms.server.utils.DateUtils;

@Component
@Transactional
public class OrderController implements OrderEndpoint {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
    private OrderRepository repository;
	@Autowired
	private OrderMapper mapper;

	@Override
    public Long create(OrderEditDto data) {
        Order order = mapper.fromModel(data);
        repository.saveAndFlush(order);
        return repository.getId(order);
    }

    @Override
    public List<OrderDto> list(@BeanParam OrderQueryDto query, @BeanParam QueryPageDto page) {
    	return filtered(query, page)
    			.stream()
    			.map(mapper::toModel)
    			.collect(Collectors.toList());
    }

    @Override
    public int count(@BeanParam OrderQueryDto query) {
        return (int) filtered(query, null).size();
    }

    @Override
    public OrderDto get(long id) {
    	return mapper.toModel(repository.requireById(id));
    }

    @Override
    public void update(long id, OrderEditDto data) {
        Order order = repository.requireById(id);
        mapper.fromModel(data, order);
    }

    private List<Order> filtered(OrderQueryDto query, QueryPageDto page) {
        try {
        	Date startDate = DateUtils.parse(Constants.getDateFormat(), query.startDate);
        	Date endDate = DateUtils.parse(Constants.getDateFormat(), query.endDate);
        	DateFilter dateFilter = DateFilter.of(startDate, endDate);
        	Pager pager = (null != page) ? Pager.of(page.offset, page.limit) : null;
        	return repository.findByTextFieldsLikeAndProductIdAndDates(query.text, query.itemId, dateFilter, pager);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

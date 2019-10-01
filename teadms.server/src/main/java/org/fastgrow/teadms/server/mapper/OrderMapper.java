package org.fastgrow.teadms.server.mapper;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.fastgrow.teadms.api.model.OrderDto;
import org.fastgrow.teadms.api.model.OrderEditDto;
import org.fastgrow.teadms.api.model.OrderItemDto;
import org.fastgrow.teadms.api.model.OrderStatusDto;
import org.fastgrow.teadms.server.domain.Order;
import org.fastgrow.teadms.server.domain.OrderItem;
import org.fastgrow.teadms.server.domain.OrderStatus;
import org.fastgrow.teadms.server.repository.OrderRepository;
import org.fastgrow.teadms.server.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
	@Autowired
	private OrderRepository repository;
	@Autowired
    private ProductRepository productRepository;
	@Autowired
	private ProductMapper productMapper;

	public OrderDto fromEntity(Order order) {
        OrderDto dto = new OrderDto();
        dto.id = repository.getId(order);
        dto.status = fromEntity(order.getStatus());
        dto.dateCreated = order.getDateCreated();
        dto.receiverName = order.getReceiverName();
        dto.address = order.getAddress();
        dto.date = order.getShippingDate();
        List<OrderItem> items = order.getItems().stream()
                .sorted(Comparator.comparing(item -> item.getProduct().getName()))
                .collect(Collectors.toList());
        for (OrderItem item : items) {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.product = productMapper.toModel(item.getProduct());
            itemDto.amount = item.getAmount();
            dto.items.add(itemDto);
        }
        return dto;
	}

	public OrderDto toModel(Order order) {
		return fromEntity(order);
	}
	
    public Order fromModel(OrderEditDto dto) {
        Order order = new Order(dto.receiverName, dto.address, dto.date);
        mapItems(dto, order);
        return order;
    }
    
    public void fromModel(OrderEditDto data, Order order) {
        order.setAddress(data.address);
        order.setReceiverName(data.receiverName);
        order.setStatus(fromModel(data.status));
        order.setShippingDate(data.date);
        order.deleteAllItems();
        mapItems(data, order);
    }
    
    private void mapItems(OrderEditDto dto, Order order) {
    	dto.items.stream()
    		.filter(Objects::nonNull)
    		.forEach(itemDto -> {
    			productRepository.findById(itemDto.productId).ifPresent(product -> {
    	            OrderItem item = new OrderItem(product, itemDto.amount);
    	            order.addItem(item);    				
    			});
	    	});
    }

	public OrderStatusDto fromEntity(OrderStatus source) {
		return OrderStatusDto.valueOf(source.name());
	}
	
	public OrderStatus fromModel(OrderStatusDto src) {
		return OrderStatus.valueOf(src.name());
	}
}

package org.fastgrow.teadms.client.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.fastgrow.teadms.api.endpoint.OrderEndpoint;
import org.fastgrow.teadms.api.model.OrderDto;
import org.fastgrow.teadms.api.model.OrderEditDto;
import org.fastgrow.teadms.api.model.OrderEditItemDto;
import org.fastgrow.teadms.api.model.OrderItemDto;
import org.fastgrow.teadms.api.model.ProductDto;
import org.fastgrow.teadms.api.model.OrderStatusDto;
import org.teavm.flavour.templates.BindTemplate;
import org.teavm.flavour.widgets.BackgroundWorker;
import org.teavm.jso.browser.Window;

@BindTemplate("templates/order.html")
public class OrderView {
    private Long id;
    private OrderDto order;
    private OrderEndpoint facade;
    private ProductSelectionViewFactory productSelectionViewFactory;
    private List<OrderItem> items = new ArrayList<>();
    private BackgroundWorker background = new BackgroundWorker();

    public OrderView(OrderEndpoint facade, ProductSelectionViewFactory productSelectionViewFactory) {
        this.facade = facade;
        this.productSelectionViewFactory = productSelectionViewFactory;
        initOrder();
    }

    public OrderView(OrderEndpoint facade, ProductSelectionViewFactory productSelectionViewFactory, Long id) {
        this.facade = facade;
        this.productSelectionViewFactory = productSelectionViewFactory;
        this.id = id;
        initOrder();
        load();
    }

    private void initOrder() {
        order = new OrderDto();
        order.address = "";
        order.receiverName = "";
        order.status = OrderStatusDto.PLANNED;
    }

    public boolean isFresh() {
        return id == null;
    }

    public void load() {
        background.run(() -> {
            order = facade.get(id);
            items.clear();
            for (OrderItemDto itemData : order.items) {
                items.add(new OrderItem(itemData));
            }
        });
    }

    public OrderDto getOrder() {
        return order;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addProduct() {
        ProductDto product = ProductSelectionViewFactory.chooseProduct(productSelectionViewFactory);
        if (product != null) {
            OrderItemDto itemData = new OrderItemDto();
            itemData.product = product;
            itemData.amount = 1;
            OrderItem item = new OrderItem(itemData);
            items.add(item);
        }
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(item.data.getPrice());
        }
        return total;
    }

    public boolean isLoading() {
        return background.isBusy();
    }

    public void save() {
        OrderEditDto saveData = new OrderEditDto();
        saveData.date = order.date;
        saveData.address = order.address;
        saveData.receiverName = order.receiverName;
        saveData.status = order.status;
        saveData.items.clear();
        for (OrderItem item : items) {
            OrderEditItemDto saveItemData = new OrderEditItemDto();
            saveItemData.productId = item.data.product.id;
            saveItemData.amount = item.data.amount;
            saveData.items.add(saveItemData);
        }
        background.run(() -> {
        	try {
	            if (id == null) {
	                facade.create(saveData);
	            } else {
	                facade.update(id, saveData);
	            }
        	} catch (Throwable error) {
        		System.out.println(error);
        	}
            Window.current().getHistory().back();
        });
    }
}

package org.fastgrow.teadms.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "org.fastgrow.teadms.server.domain.OrderItem")
@SequenceGenerator(name = "OrderItemIdGen", sequenceName = "OrderItemIdGen", allocationSize = 1)
@Table(name="ORDER_ITEM")
public class OrderItem {
    @Id
    @Column(nullable = false)
    @GeneratedValue(generator = "OrderItemIdGen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int amount;

    OrderItem() {
    }

    public OrderItem(Product product, int amount) {
        if (product == null) {
            throw new IllegalArgumentException("Product must be non-null");
        }
        validateAmount(amount);
        this.product = product;
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }

    void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    private void validateAmount(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
    }

    public void delete() {
        if (order != null) {
            order.deleteItem(this);
        }
    }
}

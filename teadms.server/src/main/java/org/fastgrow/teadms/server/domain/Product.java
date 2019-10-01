package org.fastgrow.teadms.server.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "org.fastgrow.teadms.server.domain.Product")
@SequenceGenerator(name = "ProductIdGen", sequenceName = "ProductIdGen", allocationSize = 1)
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @Column(nullable = false)
    @GeneratedValue(generator = "ProductIdGen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 40, nullable = false)
    private String sku;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(scale = 2, precision = 10, nullable = false)
    private BigDecimal price;

    public Product() {
    }

    public Product(String sku, String name, BigDecimal price) {
        validateSku(sku);
        validateName(name);
        validatePrice(price);
        this.sku = sku;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        validateSku(sku);
        this.sku = sku;
    }

    private void validateSku(String sku) {
        Objects.requireNonNull(sku);
        if (sku.isEmpty()) {
            throw new IllegalArgumentException("SKU must be non-empty");
        } else if (sku.length() > 40) {
            throw new IllegalArgumentException("SKU length must not exceed 40 character");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        Objects.requireNonNull(name);
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name must be non-empty");
        } else if (name.length() > 100) {
            throw new IllegalArgumentException("Name lenght must not exceed 100 character");
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    private void validatePrice(BigDecimal price) {
        Objects.requireNonNull(price);
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
    }
}

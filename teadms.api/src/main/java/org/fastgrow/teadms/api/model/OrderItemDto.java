package org.fastgrow.teadms.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import org.teavm.flavour.json.JsonPersistable;

@JsonPersistable
public class OrderItemDto {
    public ProductDto product;
    public Integer amount;

    @JsonIgnore
    public BigDecimal getUnitPrice() {
        return BigDecimal.valueOf(product.unitPrice);
    }

    @JsonIgnore
    public BigDecimal getPrice() {
        return getUnitPrice().multiply(BigDecimal.valueOf(amount));
    }
}

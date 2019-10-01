package org.fastgrow.teadms.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.teavm.flavour.json.JsonPersistable;

@JsonPersistable
public class OrderDto {
    public Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss XX")
    public Date dateCreated;

    public OrderStatusDto status;
    public String receiverName;
    public String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    public Date date;

    public List<OrderItemDto> items = new ArrayList<>();

    @JsonIgnore
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemDto item : items) {
            total = total.add(item.getPrice());
        }
        return total;
    }
}

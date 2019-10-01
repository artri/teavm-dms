package org.fastgrow.teadms.api.model;

import org.teavm.flavour.json.JsonPersistable;

@JsonPersistable
public class OrderEditItemDto {
    public Long productId;
    public Integer amount;
}

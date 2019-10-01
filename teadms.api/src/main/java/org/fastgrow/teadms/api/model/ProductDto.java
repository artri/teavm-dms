package org.fastgrow.teadms.api.model;

import org.teavm.flavour.json.JsonPersistable;

@JsonPersistable
public class ProductDto {
    public Long id;
    public String sku;
    public String name;
    public double unitPrice;
}

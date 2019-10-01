package org.fastgrow.teadms.api.model;

import org.teavm.flavour.json.JsonPersistable;

@JsonPersistable
public enum OrderStatusDto {
    PLANNED,
    DELIVERED,
    INVALID
}

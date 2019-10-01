package org.fastgrow.teadms.api.model;

import javax.ws.rs.QueryParam;
import org.teavm.flavour.json.JsonPersistable;

@JsonPersistable
public class OrderQueryDto {
    @QueryParam("text")
    public String text;

    @QueryParam("item-id")
    public Long itemId;

    @QueryParam("start-date")
    public String startDate;

    @QueryParam("end-date")
    public String endDate;
}

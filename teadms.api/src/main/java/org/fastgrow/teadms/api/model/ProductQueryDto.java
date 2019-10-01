package org.fastgrow.teadms.api.model;

import javax.ws.rs.QueryParam;
import org.teavm.flavour.json.JsonPersistable;

@JsonPersistable
public class ProductQueryDto {
    @QueryParam("name")
    public String namePart;
}

package org.fastgrow.teadms.api.model;

import javax.ws.rs.QueryParam;
import org.teavm.flavour.json.JsonPersistable;

@JsonPersistable
public class QueryPageDto {
    @QueryParam("offset")
    public Integer offset;

    @QueryParam("limit")
    public Integer limit;
}

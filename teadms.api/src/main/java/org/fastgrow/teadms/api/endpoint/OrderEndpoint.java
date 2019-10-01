package org.fastgrow.teadms.api.endpoint;

import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fastgrow.teadms.api.model.OrderDto;
import org.fastgrow.teadms.api.model.OrderEditDto;
import org.fastgrow.teadms.api.model.OrderQueryDto;
import org.fastgrow.teadms.api.model.QueryPageDto;
import org.teavm.flavour.rest.Resource;

@Resource
@Path("orders")
public interface OrderEndpoint {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Long create(OrderEditDto data);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<OrderDto> list(@BeanParam OrderQueryDto query, @BeanParam QueryPageDto page);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("count")
    int count(@BeanParam OrderQueryDto query);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    OrderDto get(@PathParam("id") long id);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void update(@PathParam("id") long id, OrderEditDto data);
}

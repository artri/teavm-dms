package org.fastgrow.teadms.api.endpoint;

import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fastgrow.teadms.api.model.ProductDto;
import org.fastgrow.teadms.api.model.ProductQueryDto;
import org.fastgrow.teadms.api.model.QueryPageDto;
import org.teavm.flavour.rest.Resource;

@Resource
@Path("products")
public interface ProductEndpoint {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Long create(ProductDto data);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<ProductDto> list(@BeanParam ProductQueryDto query, @BeanParam QueryPageDto page);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("count")
    int count(@BeanParam ProductQueryDto query);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    ProductDto get(@PathParam("id") long id);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void update(@PathParam("id") long id, ProductDto data);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") long id);
}

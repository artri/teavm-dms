package org.fastgrow.teadms.client;

import org.teavm.flavour.routing.Path;
import org.teavm.flavour.routing.PathParameter;
import org.teavm.flavour.routing.PathSet;
import org.teavm.flavour.routing.Route;

@PathSet
public interface ApplicationRoute extends Route {
    @Path("orders")
    void orderList();

    @Path("orders/page-{pageNum}")
    void orderPage(@PathParameter("pageNum") int page);

    @Path("orders/new")
    void newOrder();

    @Path("orders/{id}")
    void order(@PathParameter("id") long id);

    
    
    @Path("products")
    void productList();

    @Path("products/page-{pageNum}")
    void productPage(@PathParameter("pageNum") int page);

    @Path("products/{id}")
    void product(@PathParameter("id") long id);

    @Path("products/new")
    void newProduct();
}

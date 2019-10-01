package org.fastgrow.teadms.client.view;

import java.util.function.Consumer;

import org.fastgrow.teadms.client.ApplicationRoute;
import org.fastgrow.teadms.client.datasource.ProductDataSource;
import org.fastgrow.teadms.api.model.ProductDto;
import org.teavm.flavour.routing.Routing;
import org.teavm.flavour.templates.BindTemplate;
import org.teavm.flavour.widgets.PagedCursor;

@BindTemplate("templates/product-list.html")
public class ProductListView {
    private ProductDataSource dataSource;
    private PagedCursor<ProductDto> cursor;

    public ProductListView(ProductDataSource dataSource) {
        this(dataSource, 0);
    }

    public ProductListView(ProductDataSource dataSource, int page) {
        this.dataSource = dataSource;
        cursor = new PagedCursor<>(dataSource);
        cursor.setCurrentPage(page);
    }

    public ProductDataSource getDataSource() {
        return dataSource;
    }

    public PagedCursor<ProductDto> getCursor() {
        return cursor;
    }

    public void edit(long id, Consumer<String> consumer) {
        Routing.build(ApplicationRoute.class, consumer).product(id);
    }

    public void add() {
        Routing.open(ApplicationRoute.class).newProduct();
    }

    public void selectPage(int page) {
        cursor.setCurrentPage(page);
    }

    public void pageLink(int page, Consumer<String> consumer) {
        Routing.build(ApplicationRoute.class, consumer).productPage(page);
    }

    public void setFilter(String filter) {
        dataSource.setNamePart(filter);
        cursor.refresh();
    }
}

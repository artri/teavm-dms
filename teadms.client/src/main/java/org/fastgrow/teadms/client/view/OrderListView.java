package org.fastgrow.teadms.client.view;

import java.text.DateFormat;
import java.util.function.Consumer;

import org.fastgrow.teadms.client.ApplicationRoute;
import org.fastgrow.teadms.client.datasource.OrderDataSource;
import org.fastgrow.teadms.api.endpoint.ProductEndpoint;
import org.fastgrow.teadms.api.model.OrderDto;
import org.teavm.flavour.routing.Routing;
import org.teavm.flavour.templates.BindTemplate;
import org.teavm.flavour.widgets.PagedCursor;
import org.teavm.flavour.widgets.Popup;

@BindTemplate("templates/order-list.html")
public class OrderListView {
    private OrderDataSource dataSource;
    private ProductEndpoint productFacade;
    private ProductSelectionViewFactory productSelectionViewFactory;
    private PagedCursor<OrderDto> cursor;

    public OrderListView(OrderDataSource dataSource, ProductEndpoint productFacade,
            ProductSelectionViewFactory productSelectionViewFactory) {
        this(dataSource, productFacade, productSelectionViewFactory, 0);
    }

    public OrderListView(OrderDataSource dataSource, ProductEndpoint productFacade,
            ProductSelectionViewFactory productSelectionViewFactory, int pageNumber) {
        this.dataSource = dataSource;
        this.productFacade = productFacade;
        this.productSelectionViewFactory = productSelectionViewFactory;
        cursor = new PagedCursor<>(dataSource);
        cursor.setCurrentPage(pageNumber);
    }

    public OrderDataSource getDataSource() {
        return dataSource;
    }

    public PagedCursor<OrderDto> getCursor() {
        return cursor;
    }

    public void edit(long id, Consumer<String> consumer) {
        Routing.build(ApplicationRoute.class, consumer).order(id);
    }

    public void add() {
        Routing.open(ApplicationRoute.class).newOrder();
    }

    public void selectPage(int page) {
        cursor.setCurrentPage(page);
    }

    public void pageLink(int page, Consumer<String> consumer) {
        Routing.build(ApplicationRoute.class, consumer).orderPage(page);
    }

    public void setFilter(String filter) {
        dataSource.setSearchString(filter);
        cursor.refresh();
    }

    public DateFormat getDateFormat() {
        return DateFormat.getDateInstance(DateFormat.MEDIUM);
    }

    public boolean isAdvancedFilterApplied() {
        return dataSource.getStartDateFilter() != null || dataSource.getEndDateFilter() != null
                || dataSource.getSearchProductId() != null;
    }

    public void filter() {
        OrderFilterView filterView = new OrderFilterView(dataSource, productFacade, productSelectionViewFactory);
        Popup.showModal(filterView);
        cursor.refresh();
    }
}

package org.fastgrow.teadms.client.datasource;

import java.util.Date;
import java.util.List;

import org.fastgrow.teadms.api.Constants;
import org.fastgrow.teadms.api.endpoint.OrderEndpoint;
import org.fastgrow.teadms.api.model.OrderDto;
import org.fastgrow.teadms.api.model.OrderQueryDto;
import org.fastgrow.teadms.api.model.QueryPageDto;
import org.teavm.flavour.widgets.DataSource;

public class OrderDataSource implements DataSource<OrderDto> {
    private OrderEndpoint facade;
    private String searchString;
    private Long searchProductId;
    private Date startDateFilter;
    private Date endDateFilter;

    public OrderDataSource(OrderEndpoint facade) {
        this.facade = facade;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public Long getSearchProductId() {
        return searchProductId;
    }

    public void setSearchProductId(Long searchProductId) {
        this.searchProductId = searchProductId;
    }

    public Date getStartDateFilter() {
        return startDateFilter;
    }

    public void setStartDateFilter(Date startDateFilter) {
        this.startDateFilter = startDateFilter;
    }

    public Date getEndDateFilter() {
        return endDateFilter;
    }

    public void setEndDateFilter(Date endDateFilter) {
        this.endDateFilter = endDateFilter;
    }

    @Override
    public List<OrderDto> fetch(int offset, int limit) {
        QueryPageDto page = new QueryPageDto();
        page.offset = offset;
        page.limit = limit;
        return facade.list(createQuery(), page);
    }

    @Override
    public int count() {
        return facade.count(createQuery());
    }

    private OrderQueryDto createQuery() {
        OrderQueryDto query = new OrderQueryDto();
        query.text = searchString;
        query.itemId = searchProductId;
        query.startDate = startDateFilter != null ? Constants.getDateFormat().format(startDateFilter) : null;
        query.endDate = endDateFilter != null ? Constants.getDateFormat().format(endDateFilter) : null;
        return query;
    }
}

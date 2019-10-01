package org.fastgrow.teadms.client.datasource;

import java.util.List;

import org.fastgrow.teadms.api.endpoint.ProductEndpoint;
import org.fastgrow.teadms.api.model.ProductDto;
import org.fastgrow.teadms.api.model.ProductQueryDto;
import org.fastgrow.teadms.api.model.QueryPageDto;
import org.teavm.flavour.widgets.DataSource;

public class ProductDataSource implements DataSource<ProductDto> {
    private ProductEndpoint facade;
    private String namePart;

    public ProductDataSource(ProductEndpoint facade) {
        this.facade = facade;
    }

    public String getNamePart() {
        return namePart;
    }

    public void setNamePart(String namePart) {
        this.namePart = namePart;
    }

    @Override
    public List<ProductDto> fetch(int offset, int limit) {
        ProductQueryDto query = new ProductQueryDto();
        QueryPageDto page = new QueryPageDto();
        query.namePart = namePart;
        page.offset = offset;
        page.limit = limit;
        return facade.list(query, page);
    }

    @Override
    public int count() {
        ProductQueryDto query = new ProductQueryDto();
        query.namePart = namePart;
        return facade.count(query);
    }
}

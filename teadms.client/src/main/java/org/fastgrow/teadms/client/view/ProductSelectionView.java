package org.fastgrow.teadms.client.view;

import org.fastgrow.teadms.api.model.ProductDto;
import org.fastgrow.teadms.client.datasource.ProductDataSource;
import org.teavm.flavour.templates.BindTemplate;
import org.teavm.flavour.widgets.PagedCursor;
import org.teavm.flavour.widgets.PopupContent;
import org.teavm.flavour.widgets.PopupDelegate;

@BindTemplate("templates/product-selection.html")
public class ProductSelectionView implements PopupContent {
    private ProductDataSource dataSource;
    private PagedCursor<ProductDto> cursor;
    private PopupDelegate delegate;
    private ProductDto chosenProduct;

    public ProductSelectionView(ProductDataSource dataSource) {
        this.dataSource = dataSource;
        cursor = new PagedCursor<>(dataSource);
        cursor.setCurrentPage(0);
    }

    public ProductDataSource getDataSource() {
        return dataSource;
    }

    public PagedCursor<ProductDto> getCursor() {
        return cursor;
    }

    public void setFilter(String filter) {
        dataSource.setNamePart(filter);
        cursor.refresh();
    }

    public void choose(ProductDto product) {
        chosenProduct = product;
        delegate.close();
    }

    public ProductDto getChosenProduct() {
        return chosenProduct;
    }

    @Override
    public void setDelegate(PopupDelegate delegate) {
        this.delegate = delegate;
    }
}

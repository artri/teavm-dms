package org.fastgrow.teadms.client.view;

import org.fastgrow.teadms.api.endpoint.ProductEndpoint;
import org.fastgrow.teadms.api.model.ProductDto;
import org.fastgrow.teadms.client.datasource.OrderDataSource;
import org.teavm.flavour.templates.BindTemplate;
import org.teavm.flavour.widgets.PopupContent;
import org.teavm.flavour.widgets.PopupDelegate;

@BindTemplate("templates/order-filter.html")
public class OrderFilterView implements PopupContent {
    private OrderDataSource dataSource;
    private ProductSelectionViewFactory productSelectionViewFactory;
    private ProductDto product;
    private PopupDelegate delegate;

    public OrderFilterView(OrderDataSource dataSource, ProductEndpoint productFacade,
            ProductSelectionViewFactory productSelectionViewFactory) {
        this.dataSource = dataSource;
        this.productSelectionViewFactory = productSelectionViewFactory;
        if (dataSource.getSearchProductId() != null) {
            product = productFacade.get(dataSource.getSearchProductId());
        }
    }

    public OrderDataSource getDataSource() {
        return dataSource;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void chooseProduct() {
        product = ProductSelectionViewFactory.chooseProduct(productSelectionViewFactory);
    }

    public void clearProduct() {
        product = null;
    }

    public void save() {
        dataSource.setSearchProductId(product != null ? product.id : null);
        delegate.close();
    }

    @Override
    public void setDelegate(PopupDelegate delegate) {
        this.delegate = delegate;
    }
}

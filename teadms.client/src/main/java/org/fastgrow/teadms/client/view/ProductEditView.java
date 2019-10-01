package org.fastgrow.teadms.client.view;

import org.fastgrow.teadms.api.endpoint.ProductEndpoint;
import org.fastgrow.teadms.api.model.ProductDto;
import org.teavm.flavour.templates.BindTemplate;
import org.teavm.flavour.widgets.BackgroundWorker;
import org.teavm.jso.browser.Window;

@BindTemplate("templates/product.html")
public class ProductEditView {
    private Long id;
    private ProductDto product;
    private ProductEndpoint facade;
    private BackgroundWorker background = new BackgroundWorker();

    public ProductEditView(ProductEndpoint facade) {
        this.facade = facade;
        initProduct();
    }

    public ProductEditView(ProductEndpoint facade, Long id) {
        this.facade = facade;
        this.id = id;
        initProduct();
        load();
    }

    private void initProduct() {
        product = new ProductDto();
        product.sku = "";
        product.name = "";
        product.unitPrice = 0;
    }

    private void load() {
        background.run(() -> product = facade.get(id));
    }

    public void save() {
        background.run(() -> {
            if (id == null) {
                facade.create(product);
            } else {
                facade.update(id, product);
            }
            Window.current().getHistory().back();
        });
    }

    public ProductDto getProduct() {
        return product;
    }

    public boolean isLoading() {
        return background.isBusy();
    }
}

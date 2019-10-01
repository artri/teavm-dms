package org.fastgrow.teadms.client.view;

import org.fastgrow.teadms.api.model.ProductDto;
import org.teavm.flavour.widgets.Popup;

public interface ProductSelectionViewFactory {
    ProductSelectionView create();

    static ProductDto chooseProduct(ProductSelectionViewFactory factory) {
        ProductSelectionView view = factory.create();
        Popup.showModal(view);
        return view.getChosenProduct();
    }
}

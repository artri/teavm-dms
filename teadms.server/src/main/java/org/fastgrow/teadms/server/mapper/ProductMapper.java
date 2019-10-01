package org.fastgrow.teadms.server.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import org.fastgrow.teadms.api.model.ProductDto;
import org.fastgrow.teadms.server.domain.Product;
import org.fastgrow.teadms.server.repository.ProductRepository;

@Component
public class ProductMapper {
	@Autowired
    private ProductRepository repository;

    public ProductDto fromEntity(Product product) {
        ProductDto dto = new ProductDto();
        dto.id = repository.getId(product);
        dto.name = product.getName();
        dto.sku = product.getSku();
        dto.unitPrice = product.getPrice().doubleValue();
        return dto;
    }
    
    public ProductDto toModel(Product product) {
    	return fromEntity(product);
    }
    
    public Product fromModel(ProductDto data) {
        return new Product(data.sku, data.name, new BigDecimal(data.unitPrice));
    }
    
    public void fromModel(ProductDto data, Product product) {
        product.setSku(data.sku);
        product.setName(data.name);
        product.setPrice(new BigDecimal(data.unitPrice));
    }

    public Product toEntity(ProductDto data) {
        return fromModel(data);
    }
}

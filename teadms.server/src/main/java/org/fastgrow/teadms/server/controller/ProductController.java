package org.fastgrow.teadms.server.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.fastgrow.teadms.api.endpoint.ProductEndpoint;
import org.fastgrow.teadms.api.model.ProductDto;
import org.fastgrow.teadms.api.model.ProductQueryDto;
import org.fastgrow.teadms.api.model.QueryPageDto;
import org.fastgrow.teadms.server.domain.Product;
import org.fastgrow.teadms.server.mapper.ProductMapper;
import org.fastgrow.teadms.server.repository.ProductRepository;
import org.fastgrow.teadms.server.repository.GenericRepository.Pager;

@Component
@Transactional
public class ProductController implements ProductEndpoint {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
    private ProductRepository repository;
	@Autowired
    private ProductMapper mapper;

    @Override
    public Long create(ProductDto data) {
        Product product = mapper.fromModel(data);
        assertUniqueProductSKU(product.getSku());
        repository.saveAndFlush(product);
        return repository.getId(product);
    }

    @Override
    public List<ProductDto> list(ProductQueryDto query, QueryPageDto page) {
    	return filtered(query, page)
    			.stream()
    			.map(mapper::toModel)
    			.collect(Collectors.toList());
    }

    @Override
    public int count(ProductQueryDto query) {
    	return (int) filtered(query, null).size();
    }

    @Override
    public ProductDto get(long id) {
        return mapper.toModel(repository.requireById(id));
    }

    @Override
    public void update(long id, ProductDto data) {
        Product product = repository.requireById(id);
        mapper.fromModel(data, product);
        assertNotUniqueProductSKU(product.getSku());
    }

    @Override
    public void delete(long id) {
    	repository.deleteById(id);
    }
    
    private List<Product> filtered(ProductQueryDto query, QueryPageDto page) {
    	Pager pager = (null != page) ? Pager.of(page.offset, page.limit) : null;
    	return repository.findByNameLike(query.namePart, pager);
    }

    private void assertUniqueProductSKU(String sku) {
        if (!repository.isUniqueSKU(sku)) {
            throw new IllegalArgumentException("Product with SKU " + sku + " already exists");
        }
    }
    
    private void assertNotUniqueProductSKU(String sku) {
        if (repository.isUniqueSKU(sku)) {
            throw new IllegalArgumentException("Product with SKU " + sku + " already exists");
        }
    }
}

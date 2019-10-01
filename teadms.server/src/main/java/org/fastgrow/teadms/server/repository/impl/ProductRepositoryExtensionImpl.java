package org.fastgrow.teadms.server.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.fastgrow.teadms.server.domain.Product;
import org.fastgrow.teadms.server.repository.ProductRepositoryExtension;

@Repository
@Transactional(readOnly = true)
public class ProductRepositoryExtensionImpl extends GenericRepositoryImpl<Product, Long> implements ProductRepositoryExtension {
    @Autowired
    public ProductRepositoryExtensionImpl(EntityManager em) {
        super(Product.class, em);
    }

    @Override
    public List<Product> findByNameLike(String name) {
    	return findByAttributeContainsText("name", name);
    }

    @Override
    public List<Product> findByNameLike(String name, Pager pager) {
    	return findByAttributeContainsText("name", name, pager);
    }
    
    @Override
    public boolean isUniqueSKU(String sku) {
    	return findByAttributeEqualsText("sku", sku).isEmpty();
    }
}

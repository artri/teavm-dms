package org.fastgrow.teadms.server.repository;

import java.util.List;

import org.fastgrow.teadms.server.domain.Product;
import org.fastgrow.teadms.server.repository.GenericRepository.Pager;

public interface ProductRepositoryExtension {
	List<Product> findByNameLike(String name);
	List<Product> findByNameLike(String name, Pager pager);
	
	/**
	 * 
	 * @param sku - the SKU of a product
	 * @return <b>true</b> if given SKU is unique, <b>false</b> otherwise
	 */
	boolean isUniqueSKU(String sku);
}

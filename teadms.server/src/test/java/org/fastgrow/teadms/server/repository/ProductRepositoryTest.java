package org.fastgrow.teadms.server.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.fastgrow.teadms.server.TestConfiguration;
import org.fastgrow.teadms.server.domain.Product;
import org.fastgrow.teadms.server.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {TestConfiguration.class})
@DirtiesContext
public class ProductRepositoryTest {
	private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1.0);
	
	@Resource
	private ProductRepository productRepository;
	
	@BeforeEach
	public void setUp() {
		productRepository.save(new Product("12345", "product #1", DEFAULT_PRICE));
		productRepository.save(new Product("12346", "product #2", DEFAULT_PRICE));
		productRepository.save(new Product("12347", "product 3", DEFAULT_PRICE));
	}
	
	@Test
	public void givenProducts_whenFindByName_thenGetOk() {
		List<Product> products = productRepository.findByNameLike("product #");
		Assertions.assertEquals(products.size(), 2);
	}
	
	@Test
	public void givenProducts_whenUniqueSKU_thenGetFalse() {
		Assertions.assertFalse(productRepository.isUniqueSKU("12345"));
		Assertions.assertFalse(productRepository.isUniqueSKU("12346"));
		Assertions.assertFalse(productRepository.isUniqueSKU("12347"));
	}

	@Test
	public void givenProducts_whenUniqueSKU_thenGetTrue() {
		Assertions.assertTrue(productRepository.isUniqueSKU("54321"));
		Assertions.assertTrue(productRepository.isUniqueSKU("64321"));
		Assertions.assertTrue(productRepository.isUniqueSKU("74321"));
	}

}

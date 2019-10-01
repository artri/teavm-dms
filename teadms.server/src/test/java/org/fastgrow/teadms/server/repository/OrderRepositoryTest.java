package org.fastgrow.teadms.server.repository;

import javax.annotation.Resource;

import org.fastgrow.teadms.server.TestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {TestConfiguration.class})
@DirtiesContext
public class OrderRepositoryTest {

	@Resource
	private OrderRepository orderRepository;
	
	@BeforeEach
	public void setUp() {
		
	}
	
	
}

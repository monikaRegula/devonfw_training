package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.devonfw.app.java.order.orderservice.dataaccess.api.CustomerEntity;
import com.devonfw.module.test.common.base.ComponentTest;

/**
 * TODO mregula This type ...
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CustomerRepositoryTest extends ComponentTest {

  @Inject
  private OrderRepository orderRepository;

  @Inject
  private CustomerRepository customerRepository;

  @Override
  protected void doTearDown() {

    super.doTearDown();
    this.orderRepository.deleteAll();
    this.customerRepository.deleteAll();
  }

  // Remove Customer by id.
  @Test
  public void shouldRemoveCustmoerById() {

    // given
    CustomerEntity customer = new CustomerEntity();
    String ownerFirstname = "Mickey";
    customer.setFirstname(ownerFirstname);
    customer.setLastname("Mouse");
    CustomerEntity savedCustomer = this.customerRepository.save(customer);
    assertThat(this.customerRepository.find(savedCustomer.getId())).isNotNull();

    // when
    this.customerRepository.deleteById(savedCustomer.getId());

    // then
    assertThat(this.customerRepository.findAll()).isEmpty();
  }

}

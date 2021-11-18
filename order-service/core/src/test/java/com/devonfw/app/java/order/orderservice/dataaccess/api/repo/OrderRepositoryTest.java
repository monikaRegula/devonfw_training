package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.dataaccess.api.CustomerEntity;
import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.dataaccess.api.OrderEntity;
import com.devonfw.module.test.common.base.ComponentTest;

/**
 * TODO mregula This type ...
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class OrderRepositoryTest extends ComponentTest {

  @Inject
  OrderRepository orderRepository;

  @Inject
  private CustomerRepository customerRepository;

  @Inject
  private ItemRepository itemRepository;

  protected void doTeardown() {

    super.doTearDown();
    this.orderRepository.deleteAll();
    this.customerRepository.deleteAll();
    this.itemRepository.deleteAll();
  }

  // Find orders from given day with specific status - create SpringData query
  @Ignore
  @Test
  public void shouldFindByGivenDayAndStatus() {

    // given
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setFirstname("Monia");
    customerEntity.setLastname("Regula");
    this.customerRepository.save(customerEntity);

    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setPrice(5.50);
    orderEntity.setStatus(OrderStatus.PREPARING);
    LocalDate date = LocalDate.of(2021, 11, 17);
    orderEntity.setCreationDate(date);
    orderEntity.setOwner(customerEntity);

    OrderEntity orderEntity2 = new OrderEntity();
    orderEntity2.setPrice(6.00);
    orderEntity2.setStatus(OrderStatus.NEW);
    LocalDate date2 = LocalDate.of(2021, 11, 16);
    orderEntity2.setCreationDate(date2);
    orderEntity2.setOwner(customerEntity);

    this.orderRepository.save(orderEntity);
    this.orderRepository.save(orderEntity2);

    // when

    Set<OrderEntity> foundOrders = this.orderRepository.findAllByCreationDateAndStatus(date, OrderStatus.PREPARING);

    // then
    assertThat(foundOrders).hasSize(1);
    assertThat(foundOrders).extracting("id").containsOnly(orderEntity.getId());
    assertThat(foundOrders).extracting("creationDate").containsOnly(date);
    assertThat(foundOrders).extracting("status").containsOnly(OrderStatus.PREPARING);
  }

  // Create Order with two order positions and owner set
  @Test
  public void shouldCreateOrderWithTwoOrderPositionsAndOwnerSet() {

    // given
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setFirstname("Monia");
    customerEntity.setLastname("Regula");

    ItemEntity itemEntity = new ItemEntity();
    itemEntity.setDescription("description");
    itemEntity.setName("name");
    itemEntity.setPrice(1.50);

    ItemEntity itemEntity2 = new ItemEntity();
    itemEntity2.setDescription("description2");
    itemEntity2.setName("name2");
    itemEntity2.setPrice(2.50);

    Set<ItemEntity> orderPositions = new HashSet<>(Arrays.asList(itemEntity, itemEntity2));

    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setPrice(5.50);
    orderEntity.setStatus(OrderStatus.PREPARING);
    LocalDate date = LocalDate.of(2021, 11, 17);
    orderEntity.setCreationDate(date);
    orderEntity.setOwner(customerEntity);
    orderEntity.setOrderPositions(orderPositions);

    // when
    this.customerRepository.save(customerEntity);
    this.itemRepository.save(itemEntity);
    this.itemRepository.save(itemEntity2); // albo itemRepository.saveAll(orderPositions);

    OrderEntity actual = this.orderRepository.save(orderEntity);

    // then
    assertThat(actual.getOrderPositions()).extracting("name").contains("name", "name2");
    assertThat(actual.getOwner()).extracting("firstname").contains("Monia");
  }

}

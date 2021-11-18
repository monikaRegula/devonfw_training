package com.devonfw.app.java.order.orderservice.logic.impl;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;

import com.devonfw.app.java.order.orderservice.logic.api.Orderservice;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;

/**
 * TODO mregula This type ...
 *
 */
@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class OrderserviceImplTest {

  @Inject
  private Orderservice orderService;

  /**
   * increase price of the item with specified name.
   */
  @Ignore
  @Test
  public void shouldIncreaseItemPriceBySpecifiedName() {

    // given
    String name = "banana";
    float price = 2.0f;

    // when
    this.orderService.increasePriceBySpecifiedName(name, price);
    // then
    Page<ItemEto> result = this.orderService.findItemsByNameAndOrderedByName("banana");
    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setName(name);
    Page<ItemEto> updatedItems = this.orderService.findItems(criteria);
    // assertThat(updatedItems).isNotEmpty();
    // assertThat(updatedItems.stream().map(ItemEto::getPrice)).containsOnly(6.50);
  }

}

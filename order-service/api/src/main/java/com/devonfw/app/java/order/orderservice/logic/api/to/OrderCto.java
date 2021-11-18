package com.devonfw.app.java.order.orderservice.logic.api.to;

import java.util.Set;

import com.devonfw.module.basic.common.api.to.AbstractCto;

public class OrderCto extends AbstractCto {

  private Set<ItemEto> items;

  private CustomerEto customerEto;

  private OrderEto order;

  /**
   * @return items
   */
  public Set<ItemEto> getItems() {

    return this.items;
  }

  /**
   * @param items new value of {@link #getitems}.
   */
  public void setItems(Set<ItemEto> items) {

    this.items = items;
  }

  /**
   * @return customerEto
   */
  public CustomerEto getCustomerEto() {

    return this.customerEto;
  }

  /**
   * @param customerEto new value of {@link #getcustomerEto}.
   */
  public void setCustomerEto(CustomerEto customerEto) {

    this.customerEto = customerEto;
  }

  /**
   * @return order
   */
  public OrderEto getOrder() {

    return this.order;
  }

  /**
   * @param order new value of {@link #getorder}.
   */
  public void setOrder(OrderEto order) {

    this.order = order;
  }

}
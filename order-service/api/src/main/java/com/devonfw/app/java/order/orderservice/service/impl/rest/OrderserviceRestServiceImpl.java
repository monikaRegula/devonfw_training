package com.devonfw.app.java.order.orderservice.service.impl.rest;

import java.time.LocalDate;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.domain.Page;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.logic.api.Orderservice;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderCto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderEto;
import com.devonfw.app.java.order.orderservice.service.api.rest.OrderserviceRestService;

/**
 * TODO mregula This type ...
 *
 */
@Named("OrderserviceRestService")
public class OrderserviceRestServiceImpl implements OrderserviceRestService {

  @Inject
  private Orderservice orderservice;

  @Override
  public OrderCto saveOrder(OrderCto order) {

    return this.orderservice.createOrderWithTwoPositionsAndSpecifiedOwner(order);
  }

  @Override
  public Page<ItemEto> findItemsWithNameLikeOrdered(String name) {

    return this.orderservice.findItemsByNameAndOrderedByName(name);
  }

  @Override
  public void raiseItemPrice(String name, Float price) {

    this.orderservice.increasePriceBySpecifiedName(name, price);

  }

  @Override
  public Set<OrderEto> findOrdersByCreationDateAndStatus(LocalDate date, OrderStatus status) {

    return this.orderservice.findOrdersByGivenDayAndStatus(date, status);
  }

  @Override
  public void deleteCustomer(long id) {

    this.orderservice.deleteCustomer(id);

  }

}

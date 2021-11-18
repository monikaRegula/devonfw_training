package com.devonfw.app.java.order.orderservice.common.api;

import java.time.LocalDate;

import com.devonfw.app.java.order.general.common.api.ApplicationEntity;

public interface Order extends ApplicationEntity {

  /**
   * @return priceId
   */

  public double getPrice();

  /**
   * @param price setter for price attribute
   */

  public void setPrice(double price);

  /**
   * getter for ownerId attribute
   *
   * @return ownerId
   */

  public Long getOwnerId();

  /**
   * @param owner setter for owner attribute
   */

  public void setOwnerId(Long ownerId);

  /**
   * @return creationDateId
   */

  public LocalDate getCreationDate();

  /**
   * @param creationDate setter for creationDate attribute
   */

  public void setCreationDate(LocalDate creationDate);

  /**
   * @param status
   */
  void setStatus(OrderStatus status);

  /**
   * @return
   */
  OrderStatus getStatus();

}

package com.devonfw.app.java.order.orderservice.logic.api.to;

import com.devonfw.app.java.order.orderservice.common.api.Item;
import com.devonfw.module.basic.common.api.to.AbstractEto;

/**
 * Entity transport object of Item
 */
public class ItemEto extends AbstractEto implements Item {

  private static final long serialVersionUID = 1L;

  private String name;

  private String description;

  private Double price;

  @Override
  public String getName() {

    return name;
  }

  @Override
  public void setName(String name) {

    this.name = name;
  }

  @Override
  public String getDescription() {

    return description;
  }

  @Override
  public void setDescription(String description) {

    this.description = description;
  }

  @Override
  public Double getPrice() {

    return price;
  }

  @Override
  public void setPrice(Double price) {

    this.price = price;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
    result = prime * result + ((this.price == null) ? 0 : this.price.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    // class check will be done by super type EntityTo!
    if (!super.equals(obj)) {
      return false;
    }
    ItemEto other = (ItemEto) obj;
    if (this.name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!this.name.equals(other.name)) {
      return false;
    }
    if (this.description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!this.description.equals(other.description)) {
      return false;
    }
    if (this.price == null) {
      if (other.price != null) {
        return false;
      }
    } else if (!this.price.equals(other.price)) {
      return false;
    }
    return true;
  }

}

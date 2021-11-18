package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import static com.querydsl.core.alias.Alias.$;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.dataaccess.api.OrderEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;
import com.querydsl.jpa.impl.JPAQuery;

public interface OrderRepository extends DefaultRepository<OrderEntity> {

  default Page<OrderEntity> findByCriteria(OrderSearchCriteriaTo criteria) {

    OrderEntity alias = newDslAlias();
    JPAQuery<OrderEntity> query = newDslQuery(alias);

    Double price = criteria.getPrice();
    if (price != null) {
      query.where($(alias.getPrice()).eq(price));
    }

    OrderStatus status = criteria.getStatus();
    if (status != null) {
      query.where($(alias.getStatus()).eq(status));
    }

    Long ownerId = criteria.getOwnerId();
    if (ownerId != null) {
      query.where($(alias.getOwner().getId()).eq(ownerId));
    }

    LocalDate creationDate = criteria.getCreationDate();
    if (creationDate != null) {
      query.where($(alias.getCreationDate()).eq(creationDate));
    }

    addOrderBy(query, alias, criteria.getPageable().getSort());

    return QueryUtil.get().findPaginated(criteria.getPageable(), query, true);

  }

  public default void addOrderBy(JPAQuery<OrderEntity> query, OrderEntity alias, Sort sort) {

    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch (next.getProperty()) {
          case "price":
            if (next.isAscending()) {
              query.orderBy($(alias.getPrice()).asc());
            } else {
              query.orderBy($(alias.getPrice()).desc());
            }
            break;
          case "status":
            if (next.isAscending()) {
              query.orderBy($(alias.getStatus()).asc());
            } else {
              query.orderBy($(alias.getStatus()).desc());
            }
            break;
          case "creationDate":
            if (next.isAscending()) {
              query.orderBy($(alias.getCreationDate()).asc());
            } else {
              query.orderBy($(alias.getCreationDate()).desc());
            }
          case "owner":
            if (next.isAscending()) {
              query.orderBy($(alias.getOwner().getId()).asc());
            } else {
              query.orderBy($(alias.getOwner().getId()).desc());
            }
            break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '" + next.getProperty() + "'");
        }
      }
    }
  }

  Set<OrderEntity> findAllByCreationDateAndStatus(LocalDate creationDate, OrderStatus status);

}

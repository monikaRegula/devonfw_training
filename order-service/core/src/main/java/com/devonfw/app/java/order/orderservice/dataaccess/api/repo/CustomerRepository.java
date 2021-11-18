package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import static com.querydsl.core.alias.Alias.$;

import java.util.Iterator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.devonfw.app.java.order.orderservice.dataaccess.api.CustomerEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;
import com.querydsl.jpa.impl.JPAQuery;

public interface CustomerRepository extends DefaultRepository<CustomerEntity> {

  default Page<CustomerEntity> findByCriteria(CustomerSearchCriteriaTo criteria) {

    CustomerEntity alias = newDslAlias();
    JPAQuery<CustomerEntity> query = newDslQuery(alias);

    String firstname = criteria.getFirstname();
    if (firstname != null && !firstname.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getFirstname()), firstname, criteria.getFirstnameOption());
    }
    String lastname = criteria.getLastname();
    if (lastname != null && !lastname.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getLastname()), lastname, criteria.getLastnameOption());
    }
    addOrderBy(query, alias, criteria.getPageable().getSort());

    return QueryUtil.get().findPaginated(criteria.getPageable(), query, true);
  }

  public default void addOrderBy(JPAQuery<CustomerEntity> query, CustomerEntity alias, Sort sort) {

    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch (next.getProperty()) {
          case "firstname":
            if (next.isAscending()) {
              query.orderBy($(alias.getFirstname()).asc());
            } else {
              query.orderBy($(alias.getFirstname()).desc());
            }
            break;
          case "lastname":
            if (next.isAscending()) {
              query.orderBy($(alias.getLastname()).asc());
            } else {
              query.orderBy($(alias.getLastname()).desc());
            }
            break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '" + next.getProperty() + "'");
        }
      }
    }
  }

}

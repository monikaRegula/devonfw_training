package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import java.util.Arrays;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;
import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;
import com.devonfw.module.basic.common.api.query.StringSearchOperator;
import com.devonfw.module.test.common.base.ComponentTest;

/**
 * TODO mregula This type ...
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ItemRepositoryTest extends ComponentTest {

  @Inject
  private ItemRepository itemRepository;

  @Override
  protected void doTearDown() {

    super.doTearDown();
    this.itemRepository.deleteAll();
  }

  // @Test
  // public void shouldFindAllItems() {
  //
  // // when
  // List<ItemEntity> foundItems = this.itemRepository.findAll();
  //
  // // then
  // assertThat(foundItems).hasSize(1);
  // }

  private void prepareItems() {

    ItemEntity item1 = new ItemEntity();
    String item1Name = "banana";
    item1.setName(item1Name);
    item1.setPrice(2.50);

    ItemEntity item2 = new ItemEntity();
    String item2Name = "orange";
    item2.setName(item2Name);
    item2.setPrice(3.00);

    ItemEntity item3 = new ItemEntity();
    String item3Name = "apple";
    item3.setName(item3Name);
    item3.setPrice(1.50);

    this.itemRepository.saveAll(Arrays.asList(item1, item2, item3));
  }

  /*
   * Find item entities where name is like given argument (case insensitive). Sort result by name ascending - use search
   * criteria query.
   */
  @Test
  public void shouldFindItemsOrderedByName() {

    // given
    prepareItems();
    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setName("NAN");
    StringSearchOperator syntax = StringSearchOperator.LIKE;
    StringSearchConfigTo nameOption = StringSearchConfigTo.of(syntax);
    nameOption.setIgnoreCase(true);
    nameOption.setMatchSubstring(true);
    criteria.setNameOption(nameOption);
    Sort sort = Sort.by("name");
    Pageable pageable = PageRequest.of(0, 20, sort);
    criteria.setPageable(pageable);
    // when
    Page<ItemEntity> foundItems = this.itemRepository.findByCriteria(criteria);

    // then
    assertThat(foundItems).hasSize(1);
    assertThat(foundItems).extracting("name").containsExactly("banana");
  }

  // Update item with given name changing it’s price.
  @Test
  public void shouldUpdateItemPrice() {

    // given
    prepareItems();

    // when
    Set<ItemEntity> itemsToUpdate = this.itemRepository.findByName("banana");
    itemsToUpdate.stream().forEach(item -> item.setPrice(item.getPrice() + 1));
    this.itemRepository.saveAll(itemsToUpdate);

    // then
    Set<ItemEntity> updatedItems = this.itemRepository.findByName("banana");
    assertThat(updatedItems).extracting("price").containsOnly(3.50);
  }

}

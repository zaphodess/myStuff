package com.wildcodeschool.myStuff.mme.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;
import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wildcodeschool.myStuff.mme.entities.Item;
import com.wildcodeschool.myStuff.mme.repositories.ItemRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ItemRestControllerTest {
	
	private static final String BASE_PATH = "/api/v1/items";

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ItemRepository repo;

	@BeforeEach
	void setupRepo() {
		repo.deleteAll();
	}

	@Test
	void shouldBeAbleToUploadAnItem() {
		
//      Given / Arrange
        Item lawnMower = buildLawnMower();   
//      When / Act
        ResponseEntity<Item> response = restTemplate.postForEntity(BASE_PATH, lawnMower, Item.class);
  //    Then / Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotNull();
    //    assertThat(response.getBody()).isEqualToComparingFieldByField(lawnMower);
	}
	
	@Test
	void shouldReadAllItems() {

	}

	@Test
	void shouldFindOneItem() {
		fail();
	}

	@Test
	void shouldFindNoItemForUnknownId() throws URISyntaxException {
		fail();
	}

	@Test
	void shouldBeAbleToDeleteAnItem() throws URISyntaxException {
		fail();
	}

	@Test
	void shouldNotBeAbleToDeleteAnItemWithUnknownId() throws URISyntaxException {
		fail();
	}

	@Test
	void shouldBeAbleToReplaceAnItem() throws URISyntaxException {
		fail();
	}

	@Test
	void shouldNotBeAbleToReplaceAnItemWithUnknownId() throws URISyntaxException {
		fail();
	}

	public void fail() {
		System.out.println("You made a mistake");
	}
	
	private ResponseEntity<Item> givenAnInsertedItem () {
		Item item = buildLawnMower();
		return restTemplate.postForEntity(BASE_PATH, item, Item.class);		
	}
	

	
	private Item buildLawnMower() {
		Item item1 = new Item();
        item1.setDescription("Description LawnMower");
        item1.setName("Lawn Mower");
        item1.setLastUsed(Date.valueOf("2019-05-01"));
        item1.setAmount(1);
        item1.setLocation("Basement");
        return item1;
	}
	
	private Item buildLawnTrimmer() {
		Item item2 = new Item ();
        item2.setDescription("Description LawnTrimmer");
        item2.setName("Lawn Trimmer");
        item2.setLastUsed(Date.valueOf("2020-02-02"));
        item2.setAmount(1);
        item2.setLocation("Basement");
        return item2;
       	
	}

}

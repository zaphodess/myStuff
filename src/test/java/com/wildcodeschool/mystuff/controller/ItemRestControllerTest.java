package com.wildcodeschool.mystuff.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;
import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wildcodeschool.mystuff.entities.Item;
import com.wildcodeschool.mystuff.repositories.ItemRepository;

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
        ResponseEntity<Item> response = restTemplate.postForEntity(BASE_PATH + "/", lawnMower, Item.class);
  //    Then / Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody()).isEqualToComparingOnlyGivenFields(lawnMower, "name","description","amount","location");
	}
	
//	@Test
//	void shouldReadAllItems() {
//		// Given / Arrange
//		Item lawnMower = buildLawnMower();
//		Item lawnTrimmer = buildLawnTrimmer();
//		this.repo.save(lawnMower);
//		this.repo.save(lawnTrimmer);
//		//When / Act
//		ResponseEntity<Item> response = restTemplate.postForEntity(BASE_PATH, this.repo, Item.class);
//				
//		//Then / Assert
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//	}

	@Test
	void shouldFindOneItem() {
		// Given / Arrange
		Item lawnMower = givenAnInsertedItem().getBody();
		// When / Act
		ResponseEntity<Item> response = restTemplate.getForEntity(BASE_PATH + lawnMower.getId(), Item.class);
		// Then / Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualToComparingFieldByField(lawnMower);	
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
		Item item = Item.builder().name("Lawn mower").amount(1).lastUsed(Date.valueOf("2019-05-01"))
				.location("Basement").build();
		return item;
	}

	private Item buildLawnTrimmer() {
		Item item = Item.builder().name("Lawn trimmer").amount(1).lastUsed(Date.valueOf("2018-05-01"))
				.location("Basement").build();
		return item;
	}
	
}

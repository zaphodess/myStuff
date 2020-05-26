package com.wildcodeschool.mystuff.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
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
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.wildcodeschool.mystuff.entities.Item;
import com.wildcodeschool.mystuff.repositories.ItemRepository;

import io.swagger.models.HttpMethod;

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
	
	@Test
	void shouldReadAllItems() {
		// Given / Arrange
		Item lawnMower = buildLawnMower();
		Item lawnTrimmer = buildLawnTrimmer();
		//When / Act
		ResponseEntity<Item[]> response = restTemplate.getForEntity(BASE_PATH, Item[].class);
				
		//Then / Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().length).isEqualTo(2);
		assertThat(response.getBody()[0]).isEqualToComparingFieldByField(lawnMower);
		assertThat(response.getBody()[1]).isEqualToComparingFieldByField(lawnTrimmer);

	}

	@Test
	void shouldFindOneItem() {
		// Given / Arrange
		Item lawnMower = givenAnInsertedItem().getBody();
		// When / Act
		ResponseEntity<Item> response = restTemplate.getForEntity(BASE_PATH + "/" + lawnMower.getId(), Item.class);
		// Then / Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualToComparingFieldByField(lawnMower);	
	}

	@Test
	void shouldFindNoItemForUnknownId() throws URISyntaxException {
		// Given / Arrange
		Long id = 1905L;
		// When / Act
		ResponseEntity<Item> response = restTemplate.getForEntity(BASE_PATH + "/" + id, Item.class);
		// Then | Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldBeAbleToDeleteAnItem() throws URISyntaxException {
		// Given / Arrange
		Item lawnMower = givenAnInsertedItem().getBody();
		// When / Act
		URI url = new URI(restTemplate.getRootUri() + BASE_PATH + "/" + lawnMower.getId());
		RequestEntity<String> request = new RequestEntity<>(HttpMethod.DELETE, url);
		ResponseEntity<String> response = restTemplate.exchange(request, String.class);
		// Then / Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	void shouldNotBeAbleToDeleteAnItemWithUnknownId() throws URISyntaxException {
		// Given / Arrange
		Long id = 20205L;
		// When / Act
		URI url = new URI(restTemplate.getRootUri() + BASE_PATH + "/" + id);
		RequestEntity<String> request = new RequestEntity<>(HttpMethod.DELETE, url);
		ResponseEntity<String> response = restTemplate.exchange(request, String.class);
		// Then / Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldBeAbleToReplaceAnItem() throws URISyntaxException {
		// Given / Arrange
		Item lawnMower = givenAnInsertedItem().getBody();
		Item lawnTrimmer = buildLawnTrimmer();
		// When / Act
		URI url = new URI(restTemplate.getRootUri() + BASE_PATH + "/" + lawnMower.getId());
		RequestEntity<String> request = new RequestEntity<>(HttpMethod.PUT,url);
		ResponseEntity<Item> response = restTemplate.exchange(request, Item.class);
		// Then / Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualToComparingFieldByField(lawnTrimmer);
	}

	@Test
	void shouldNotBeAbleToReplaceAnItemWithUnknownId() throws URISyntaxException {
		// Given / Arrange
		Long id = 6666L;
		// When / Act
		URI url = new URI(restTemplate.getRootUri() + BASE_PATH + "/" + id);
		RequestEntity<String> request = new RequestEntity<>(HttpMethod.PUT, url);
		ResponseEntity<Item> response = restTemplate.exchange(request, Item.class);
		// Then / Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	private ResponseEntity<Item> givenAnInsertedItem () {
		Item item = buildLawnMower();
		return restTemplate.postForEntity(BASE_PATH, item, Item.class);		
	}
	

	private Item buildLawnMower() {
		Item item = Item.builder().name("Lawn mower").amount(1).lastUsed(Date.valueOf("2020-01-01"))
				.location("Basement").build();
		return item;
	}

	private Item buildLawnTrimmer() {
		Item item = Item.builder().name("Lawn trimmer").amount(1).lastUsed(Date.valueOf("2019-05-07"))
				.location("Basement").build();
		return item;
	}
	
}

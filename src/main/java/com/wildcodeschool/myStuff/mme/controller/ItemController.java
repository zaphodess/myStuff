package com.wildcodeschool.myStuff.mme.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wildcodeschool.myStuff.mme.entities.Item;
import com.wildcodeschool.myStuff.mme.repositories.ItemRepository;



@RestController
// @RequestMapping("/api/v1/items")
public class ItemController {
	
	private final ItemRepository itemRepo;
	
	@Autowired
	public ItemController (ItemRepository itemRepo) {
		this.itemRepo = itemRepo;	
	}
	
	@GetMapping("/api/v1/items/all")
	public List<Item> getAllItems() {
		return this.itemRepo.findAll();
	}
	
	@GetMapping("/api/v1/items/{id}")
	public Optional<Item> getItemById(@RequestParam Long Id) {
		return this.itemRepo.findById(Id);
	}
	
	@PostMapping ("/api/v1/items/")
	public Item createItem(@RequestBody Item newItem) {
		return this.itemRepo.save(newItem);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/api/v1/items/{id}")
	ResponseEntity<?> deleteTopic(@PathVariable long id) {
		try {
			this.itemRepo.deleteById(id);
			return new ResponseEntity<String>("Data deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Resource not found", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("api/v1/items/{id}")
	public Item replace(@RequestBody Item newItem, @PathVariable Long id) {
		return itemRepo.findById(id).map(item -> {
			item.setAmount(newItem.getAmount());
			item.setDescription(newItem.getDescription());
			item.setLastUsed(newItem.getLastUsed());
			item.setLocation(newItem.getLocation());
			item.setName(newItem.getName());
			return itemRepo.save(item);
		}).orElseThrow();
	}
}

package com.wildcodeschool.mystuff.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wildcodeschool.mystuff.entities.Item;
import com.wildcodeschool.mystuff.repositories.ItemRepository;



@RestController
@RequestMapping("/api/v1/items")
public class ItemController {
	
	private final ItemRepository itemRepo;
	
	@Autowired
	public ItemController (ItemRepository itemRepo) {
		this.itemRepo = itemRepo;	
	}
	
	@GetMapping
	public List<Item> getAllItems() {
		return this.itemRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Item getItemById(@PathVariable Long id) {
		return this.itemRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Item createItem(@RequestBody Item newItem) {
		return this.itemRepo.save(newItem);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	ResponseEntity<?> deleteTopic(@PathVariable long id) {
		try {
			this.itemRepo.deleteById(id);
			return new ResponseEntity<String>("Data deleted successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<String>("Resource not found", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}")
	public Item replace(@RequestBody Item newItem, @PathVariable Long id) {
		return itemRepo.findById(id).map(item -> {
			item.setAmount(newItem.getAmount());
			item.setDescription(newItem.getDescription());
			item.setLastUsed(newItem.getLastUsed());
			item.setLocation(newItem.getLocation());
			item.setName(newItem.getName());
			return itemRepo.save(item);
		}).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}

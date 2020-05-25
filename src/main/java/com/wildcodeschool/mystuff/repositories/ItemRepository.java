package com.wildcodeschool.mystuff.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wildcodeschool.mystuff.entities.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
	

}

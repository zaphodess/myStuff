package com.wildcodeschool.myStuff.mme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wildcodeschool.myStuff.mme.entities.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
	

}

package com.wildcodeschool.myStuff.mme.config;



import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.wildcodeschool.myStuff.mme.entities.Item;
import com.wildcodeschool.myStuff.mme.repositories.ItemRepository;


@Component
public class ItemLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	    private ItemRepository itemRepository;
	   // private Logger log = Logger.getLogger(ItemLoader.class);
	    @Autowired
	    public void setItemRepository(ItemRepository itemRepository) {
	        this.itemRepository = itemRepository;
	    }
	    @Override
	    @DateTimeFormat (pattern="yyyy-MM-dd")
	    public void onApplicationEvent(ContextRefreshedEvent event) {
	        
	    	Item topf = new Item();
	        topf.setDescription("Haushaltsgegenstand");
	        topf.setName("Topf");
	        topf.setLastUsed(Date.valueOf("2020-02-02"));
	        topf.setAmount(3);
	        topf.setLocation("Küche");
	        itemRepository.save(topf);
	        
	        Item tisch = new Item();
	        tisch.setDescription("Möbel");
	        tisch.setName("Tisch");
	        tisch.setLastUsed(Date.valueOf("2020-03-03"));
	        tisch.setAmount(1);
	        tisch.setLocation("Wohnzimmer");
	        itemRepository.save(tisch);
	     
	    }

}

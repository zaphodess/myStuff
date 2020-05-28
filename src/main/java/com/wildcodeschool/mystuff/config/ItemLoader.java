package com.wildcodeschool.mystuff.config;



import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.wildcodeschool.mystuff.entities.Item;
import com.wildcodeschool.mystuff.repositories.ItemRepository;


@Component
public class ItemLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	    private ItemRepository itemRepository;
	   // private Logger log = Logger.getLogger(ItemLoader.class);
	    @Autowired
	    public void setItemRepository(ItemRepository itemRepository) {
	        this.itemRepository = itemRepository;
	    }
	    @Override
	    
	    public void onApplicationEvent(ContextRefreshedEvent event) {
	        
	    	for (int i = 0; i < 20; i++) {
	    		
	    		Item topf = new Item();
		        topf.setDescription("Haushaltsgegenstand");
		        topf.setName("Topf");
		        topf.setLastUsed(LocalDate.of(2020,02,02));
		        topf.setAmount(3);
		        topf.setLocation("Küche");
		        itemRepository.save(topf);
		        
		        Item tisch = new Item();
		        tisch.setDescription("Möbel");
		        tisch.setName("Tisch");
		        tisch.setLastUsed(LocalDate.of(2020,02,02));
		        tisch.setAmount(1);
		        tisch.setLocation("Wohnzimmer");
		        itemRepository.save(tisch);
		        
				
				//Das geht nicht, weil ich keinen speziellen Konstruktor gebaut habe. Die Reihenfolge stimmt nicht
		        //Item item1 = new Item("Computer", 2, "2020-05-22", "Büro", "Diesen Computer benutze ich im HomeOffice.");
		        //itemRepository.save(item1);
			}
	    	
	     
	    }

}
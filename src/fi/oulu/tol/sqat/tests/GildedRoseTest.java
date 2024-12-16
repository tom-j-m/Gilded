package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	@Test
	public void exampleTest() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();
		
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert quality has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
		
		//assert the quality goes to 0 and not beyond
		for (int i = 0; i < 20; i++) {
			inn.oneDay();
		}
		quality = items.get(0).getQuality();
		
		assertEquals(0, quality);
	}
	
	@Test
	public void testSulfuras() {
		//create an inn, add item of Sulfuras, and test the sell-in date and quality stays the same.
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 15, 80));
		inn.oneDay();
		
		//access a list of items, get the sell-in date of the one set
		List<Item> items = inn.getItems();
		int sellInSulf = items.get(0).getSellIn();
		int quality = items.get(0).getQuality();
		
		//assert sell-in date has not changed
		assertEquals("Failed sell-in date for Sulfuras", 15, sellInSulf);
		
		//assert quality has not changed
		assertEquals("Failed quality for Sulfuras", 80, quality);
		
	}
	
	@Test
	public void testAgedBrie() {
		//create an inn, add item of AgedBrie, test that the quality increases over time
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Aged Brie", 10, 30));
		inn.oneDay();
		
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		String name = items.get(0).getName();
		
		//assert item name matches
		boolean nameTest = "Aged Brie".equals(items.get(0).getName());
		assertEquals("Failed getName method", true, nameTest);
		
		//assert quality has added by one
		assertEquals("Failed quality for Aged Brie", 31, quality);
		
		//assert the quality increases after sell in date has passed
		while (items.get(0).getSellIn() > 0) {
			inn.oneDay();
		}
		quality = items.get(0).getQuality();
		inn.oneDay();
		boolean aromatic = (quality < items.get(0).getQuality());
		assertEquals("Failed quality increase after sell-in date passed for Aged Cheese", true, aromatic);
		

		
		//age the brie to max quality
		for (int i = 0; i < 20; i++) {
			inn.oneDay();
		}
		quality = items.get(0).getQuality();
		//assert quality does not exceed 50
		assertEquals("Failed max quality for Aged Brie", 50, quality);
	}
	
	@Test
	public void testBSPass() {
		//create an inn, add item of Backstage pass, test that the quality increases over time
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20));
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert quality has added by one
		assertEquals("Failed quality for backstage passes", 21, quality);
		
		//test that the quality increases by 2 when sell-in date is between 5-10
		while (items.get(0).getSellIn() > 5) {
			inn.oneDay();
		}
		quality = items.get(0).getQuality();
		assertEquals("Failed quality increase for BSPass while sell-in date 5-10", 31, quality);
		
		//test that the quality increases by 3 when sell-in date is between 0-5
		inn.oneDay();
		
		quality = items.get(0).getQuality();
		assertEquals("Failed quality increase for BSPass while sell-in date 5-10", 34, quality);
		
		//test that the quality drops to 0 when concert has passed
		while (items.get(0).getSellIn() > -2) {
			inn.oneDay();
		}
		
		quality = items.get(0).getQuality();
		assertEquals("Failed quality drop for BSPass when concert has passed", 0, quality);
	}
	
	@Test
	public void testQualityEdge() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Not Brie", 10, 60));
		
		inn.oneDay();
				
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
				
		//assert quality has decreased by one
		assertEquals("Failed quality edge test", 59, quality);

	}
	
	@Test
	public void testNegativeSellInValue() {
		//testing different items with negative Sell-in dates
		//regular item
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Bag of Chips", -1, 18));
		inn.oneDay();
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		assertEquals("Negative sell-in date failure", 16, quality);
		
		//Aged Brie
		inn.setItem(new Item("Aged Brie", -1, 0));
		inn.oneDay();
		items = inn.getItems();
		quality = items.get(1).getQuality();
		assertEquals("Negative sell-in date failure for Brie", 2, quality);
		
		//Backstage pass
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", -1, 20));
		inn.oneDay();
		items = inn.getItems();
		quality = items.get(2).getQuality();
		assertEquals(0, quality);
		
		//Sulfuras
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", -1, 80));
		inn.oneDay();
	    items = inn.getItems();
	    assertEquals(80, items.get(3).getQuality());
	    assertEquals(-1, items.get(3).getSellIn());
	}
	
}

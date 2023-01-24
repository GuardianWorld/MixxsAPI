package MixxsAPI;

import net.minecraft.src.Item;

public class Item_Searcher {

	//Any functions that involve searching inside ItemAPI will rest here.
	
    public static Item TryGettingItem(String itemName, Item defaultItem) {
    	if(!itemName.toLowerCase().contains("item") && !itemName.toLowerCase().contains("tile")) {
    		itemName = "item." + itemName;
    	}
    	
    	convertNomeclature(itemName);
    	
    	for(int x = 1; x < Item.itemsList.length; x++) {  			
    		if(Item.itemsList[x] != null && Item.itemsList[x].getItemName() != null && Item.itemsList[x].getItemName().equals(itemName)) {
    			return Item.itemsList[x];
    		}	
    	}  	
    	MLogger.print("Item_Searcher", "Invalid Return Item: " + itemName + ". Using default value ["+ defaultItem.getItemName() +"].", MLogger.ErrorType.ERROR);
    	return defaultItem;
    }
	
    public static Item TryGettingItem(String itemSearched) {
    	String itemName = itemSearched;
    	String blockName = itemSearched;
    	
    	if(itemSearched.toLowerCase().contains("block.")) {
    		blockName = itemSearched.replace("block.", "tile.");
    	} 
    	else if(!itemSearched.toLowerCase().contains("tile.")) {
    		blockName = "tile." + itemSearched;
    	}

    	if(!itemSearched.toLowerCase().contains("item.")) {
    		itemName = "item." + itemSearched;
    	}
    	
    	//System.err.println(blockName + "|" + itemName);
    	
    	blockName = convertNomeclature(blockName);
    	itemName = convertNomeclature(itemName);

    	for(int x = 1; x < Item.itemsList.length; x++) {  			
    		if(Item.itemsList[x] != null && Item.itemsList[x].getItemName() != null) {
    			String itemListItemName = Item.itemsList[x].getItemName();

    			if(itemListItemName.equals(itemName) || itemListItemName.equals(blockName)) {
    				return Item.itemsList[x];
    			}
    		}		
    	}
    	  
		return null;  	
    	
    }
    
    public static int TryGettingItemID(String itemSearched) {
    	String itemName = itemSearched;
    	String blockName = itemSearched;
    	
    	if(itemSearched.toLowerCase().contains("block.")) {
    		blockName = itemSearched.replace("block.", "tile.");
    	} 
    	else if(!itemSearched.toLowerCase().contains("tile.")) {
    		blockName = "tile." + itemSearched;
    	}

    	if(!itemSearched.toLowerCase().contains("item.")) {
    		itemName = "item." + itemSearched;
    	}
    	
    	//System.err.println(blockName + "|" + itemName);
    	
    	blockName = convertNomeclature(blockName);
    	itemName = convertNomeclature(itemName);

    	for(int x = 1; x < Item.itemsList.length; x++) {  			
    		if(Item.itemsList[x] != null && Item.itemsList[x].getItemName() != null) {
    			String itemListItemName = Item.itemsList[x].getItemName();

    			if(itemListItemName.equals(itemName) || itemListItemName.equals(blockName)) {
    				return x;
    			}
    		}		
    	}
    	  
		return 0;  	
    	
    }
    
    public static String convertNomeclature(String name) {
    	if(name.equals("tile.cobblestone")) {
    		return "tile.stonebrick";
    	}
    	if(name.equals("item.emptyBowl")) {
    		return Item.bowlEmpty.getItemName();
    	}
    	if(name.equals("item.soupBowl")) {
    		return Item.bowlSoup.getItemName();
    	}
    	if(name.equals("item.bucketMilk")) {
    		return Item.bucketMilk.getItemName();
    	}
    	if(name.equals("item.diamond")) {
    		return Item.diamond.getItemName();
    	}
    	
    	return name;
    }
    
}




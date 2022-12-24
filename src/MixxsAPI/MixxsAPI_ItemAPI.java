package net.minecraft.src.MixxsAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import hmi.TabUtils;
import hmi.Utils;
import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBow;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_MixxsAPI;
import net.minecraft.src.MixxsAPI.Items.*;
import net.minecraft.src.overrideapi.utils.tool.ToolMaterial;

public class MixxsAPI_ItemAPI {
	//public static Item monsterEgg = (new ItemEntityEgg(9000)).setItemName("item.entity.egg.name");
	//public static Item bow = (new CustomItemBow(555, Item.itemsList[1], 384)).setIconCoord(5, 1); //item Name, but can work for blocks as well.
	//public static Item customDye = (new CustomItemDye(9001)).setItemName("Custom Dye");
    private static int defaultTextureIndex = 0;
    private static int defaultID = 3200;
    private static int defaultSetID = 6600;
    private static int idIncrements = 0;
    private static String[] itemTypeArray = 
    	   {"item", //Items Done
    		"axe" ,	"pickaxe", "spade",	"shovel", "sword",	"hoe", //Equipments Done
    		"bow",
    		"food", // Food
    		"armor", // Armor
    		"bucket",
    		"door",
    		"seeds",
    		"soup" };
    public static Map<String,EnumToolMaterial> materialList = new HashMap<String,EnumToolMaterial>();
    public ArrayList<ItemStack> customItems;
    
    Properties inputFormatException;
     
    public MixxsAPI_ItemAPI(){
    	 String defaultTexturePath = "/textures/NoTexture.png"; 
    	 defaultTextureIndex = ModLoader.addOverride("/gui/items.png", defaultTexturePath);    	 
    	 inputFormatException = new Properties();
    	 
    	 MixxsAPI_ItemAPI.addEnumToMaterialMap("wood", EnumToolMaterial.WOOD);
    	 MixxsAPI_ItemAPI.addEnumToMaterialMap("stone", EnumToolMaterial.STONE);
    	 MixxsAPI_ItemAPI.addEnumToMaterialMap("iron", EnumToolMaterial.IRON);
    	 MixxsAPI_ItemAPI.addEnumToMaterialMap("diamond", EnumToolMaterial.EMERALD);
    	 MixxsAPI_ItemAPI.addEnumToMaterialMap("emerald", EnumToolMaterial.EMERALD);
    	 MixxsAPI_ItemAPI.addEnumToMaterialMap("gold", EnumToolMaterial.GOLD);
    	 
    	 //ModLoader.AddName(monsterEgg, "Default Egg");
    	 //ModLoader.AddLocalization("item.null.egg.name", "Default Egg");
    	 
    	 }
    //Map<String, Item> modItemTable
    public boolean ItemAPICalls(String modConfigPath, ArrayList<Item> modItemTable, Map<String,Integer> textureMap){    	    	
    	 
    	MLogger.print("Item API", "called.", MLogger.NORMAL);
    	
    	//HashMap to allow the same texturePath to be used by multiple Items.
    	if(textureMap == null) {
    		textureMap = new HashMap<String,Integer>();
    	}
        try{
            File file = new File(mod_MixxsAPI.configpath + modConfigPath);
            inputFormatException.load(new InputStreamReader(new FileInputStream(file)));
        }
        catch(IOException fileNotFoundException3){
        	MLogger.print("Item API", "Invalid File, ItemAPI is now quitting.", MLogger.ERROR);
            return false;
        }
        
        MLogger.print("ItemAPI", "making ItemList and Building Items...", MLogger.NORMAL);
        
        //## Get Properties.
        //Materials
        String materialList = inputFormatException.getProperty("MaterialList", "");
        //Normal Items
        String itemList     = inputFormatException.getProperty("ItemList", "");
        //Food Items
        String foodList     = inputFormatException.getProperty("FoodList", "");
        String soupList     = inputFormatException.getProperty("SoupList", "");
       
        //Equipament Items
        String equipSetList = inputFormatException.getProperty("FullEquipamentSetList", ""); //Equipament Set
        String swordList    = inputFormatException.getProperty("SwordList", "");
        String pickaxeList  = inputFormatException.getProperty("PickaxeList", "");
        String shovelList   = inputFormatException.getProperty("ShovelList", "");
        String axeList      = inputFormatException.getProperty("AxeList", "");
        String hoeList      = inputFormatException.getProperty("HoeList", "");
        //Bow
        String bowList = inputFormatException.getProperty("BowList", "");
        //Doors
        String doorList 	 = inputFormatException.getProperty("DoorList", "");
        
        //Armor Items
        String armorList 	 = inputFormatException.getProperty("ArmorList", "");
        String advArmorList = inputFormatException.getProperty("AdvancedArmorList", "");       
        
        //Advanced Option: Enum List.
        if(!materialList.isEmpty()) { addMaterialToEnumMaterialsList(materialList.split(",")); 			  		   		}
        if(!itemList.isEmpty())     { addDefaultItemToGame(itemList.split(","), modItemTable, textureMap);		   		}
        if(!foodList.isEmpty())     { addFoodItemToGame(foodList.split(","), modItemTable, textureMap);   		   		}
        if(!equipSetList.isEmpty()) { addEquipamentSetToGame(equipSetList.split(","), modItemTable, textureMap);  	 	}
        if(!swordList.isEmpty())    { addEquipamentToGame(swordList.split(","), "sword",modItemTable, textureMap); 		}
        if(!pickaxeList.isEmpty())  { addEquipamentToGame(pickaxeList.split(","), "pickaxe",modItemTable, textureMap);  }
        if(!shovelList.isEmpty())   { addEquipamentToGame(shovelList.split(","), "spade",modItemTable, textureMap);     }
        if(!axeList.isEmpty()) 		{ addEquipamentToGame(axeList.split(","), "axe",modItemTable, textureMap);          }
        if(!hoeList.isEmpty()) 		{ addEquipamentToGame(hoeList.split(","), "hoe",modItemTable, textureMap);	        }     
        if(!doorList.isEmpty()) 	{ String[] splitDoorList = doorList.split(",");								        }
        
        if(!armorList.isEmpty())	{ 	String[] splitArmorList = armorList.split(",");        							}
        if(!advArmorList.isEmpty()) {        	String[] splitAdvArmorList = advArmorList.split(",");         			}         
        //Items with special conditions, such as return type, will be placed last:
        if(!soupList.isEmpty()) 	{ 	addSoupItemToGame(soupList.split(","), modItemTable, textureMap);        		}  
        if(!bowList.isEmpty())      { addBowToGame(bowList.split(","), modItemTable, textureMap);                       }
        
        return true;
    }
    
    private void addMaterialToEnumMaterialsList(String[] splitMaterialList) {
    	if(splitMaterialList.length > 0) {
    		if(mod_MixxsAPI.additionalFunctionalityMods.get(0).equals("net.minecraft.src.overrideapi.OverrideAPI")) {
        		for(String materialName: splitMaterialList) {
        	    	MLogger.print("Item API", "Obtaining Tool Material " + materialName + ".", MLogger.NORMAL);
        			
            		MixxsAPI_ItemAPI.addEnumToMaterialMap(
            				materialName,
            				TryGettingValues(("MaterialSetHarverstLevel" + materialName), 0),
            				TryGettingValues(("MaterialSetMaxUses" + materialName), 69),
            				TryGettingValues(("MaterialSetEfficiencyOnProperMaterial" + materialName), 2.0F),
            				TryGettingValues(("MaterialSetDamageVsEntity" + materialName), 0)
            				);
        		}
        	}
    		else {
    			MLogger.print("Item API", "Could not find OverrideAPI. Will not be able to create custom ENUM items. ", MLogger.ERROR);
    		}
    	}
    }
    
    /**
     * Adds a Item into a Item Table.
     */
    private void addDefaultItemToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		//MLogger.print("Item API", "Obtaining Default Item " + baseName + ".", MLogger.NORMAL);

            Item auxItem = ItemAPIbuildDefaultItem(
            		baseName,
            		inputFormatException.getProperty("SetName" + baseName, baseName),
            		(TryGettingValues(("idItem" + baseName), defaultID) + idIncrements),
            		TryGettingTexture("Texture" + baseName),
            		TryGettingValues(("SetMaxStack" + baseName), 1),
            		textureMap
            		);	

            modItemTable.add(auxItem);
    	}
    }
    
    /**
     * Adds a food Item into a Item Table.
     */
    private void addFoodItemToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		//MLogger.print("Item API", "Obtaining Food Item " + baseName + ".", MLogger.NORMAL); 
 
        	Item auxItem = ItemAPIbuildFoodItem(
            		baseName,
            		inputFormatException.getProperty("SetName" + baseName, baseName),
            		TryGettingValues(("idItem" + baseName), defaultID) + idIncrements,
            		TryGettingTexture("Texture" + baseName),
            		TryGettingValues(("SetMaxStack" + baseName), 1),
            		TryGettingValues(("FoodHealAmount" + baseName), 1),
            		TryGettingFavoriteFoodWolf("FoodWolfFavorite" + baseName),
            		textureMap
            		);	
            modItemTable.add(auxItem);
    	}
    }
    
    private void addSoupItemToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		//MLogger.print("Item API", "Obtaining Soup Item " + baseName + ".", MLogger.NORMAL);
        	
        	Item auxItem = ItemAPIbuildSoupItem(
            		baseName,
            		inputFormatException.getProperty("SetName" + baseName, baseName),
            		TryGettingValues(("idItem" + baseName), defaultID) + idIncrements,
            		TryGettingTexture("Texture" + baseName),
            		TryGettingValues(("FoodHealAmount" + baseName), 1),
            		TryGettingFavoriteFoodWolf("FoodWolfFavorite" + baseName),
            		TryGettingReturnItem(inputFormatException.getProperty(("SetReturnItem" + baseName), ""), Item.bowlEmpty), 
            		textureMap
            		);	
        	
            modItemTable.add(auxItem);
    	}
    }
    
    private void addEquipamentSetToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String, Integer> textureMap) {
    	for(String baseName : splitList) {
    		//MLogger.print("Item API", "Obtaining Set " + baseName + ".", MLogger.NORMAL);
    		//Separates set into wanted resources;
    		int reservedID = TryGettingValues(("idReservedSet" + baseName), defaultSetID) + idIncrements;
    		EnumToolMaterial enumTypeMaterial = TryGettingEnumMaterial("SetEnumType" + baseName);
    		String textureSetBase = inputFormatException.getProperty("TextureSet" + baseName, "");
    		String[] SetBaseTypes = {
    				"Sword",
    				"Axe",
    				"Pickaxe",
    				"Hoe",
    				"Shovel"
    		};
    		String[] textureSet = {
    				TryGettingTextureRedux(textureSetBase.replace("#", "Sword"), baseName),
    				TryGettingTextureRedux(textureSetBase.replace("#", "Axe"), baseName),
    				TryGettingTextureRedux(textureSetBase.replace("#", "Pickaxe"), baseName),
    				TryGettingTextureRedux(textureSetBase.replace("#", "Hoe"), baseName),
    				TryGettingTextureRedux(textureSetBase.replace("#", "Shovel"), baseName)
    		};
    		String[] nameSet = {
    				inputFormatException.getProperty("SetName" + baseName + "Sword", baseName + "Sword"),
    				inputFormatException.getProperty("SetName" + baseName + "Axe", baseName + "Axe"),
    				inputFormatException.getProperty("SetName" + baseName + "Pickaxe", baseName + "Pickaxe"),
    				inputFormatException.getProperty("SetName" + baseName + "Hoe", baseName + "Hoe"),
    				inputFormatException.getProperty("SetName" + baseName + "Shovel", baseName + "Shovel")
    		};
    		
    		for(int x = 0; x < 5; x++) {
    			Item auxItem = ItemAPIBuildEquipament(
                		baseName + SetBaseTypes[x],
                		nameSet[x], //fullName
                		reservedID + x, //ID
                		textureSet[x], //Texture
                		SetBaseTypes[x],
                		enumTypeMaterial,
                		textureMap
                		);		
    			modItemTable.add(auxItem);
    		}
    	}
    }
    
    private void addEquipamentToGame(String[] splitList, String type, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		//MLogger.print("Item API", "Obtaining Equipament " + baseName + ".", MLogger.NORMAL);	
    		
            Item auxItem = ItemAPIBuildEquipament(
            		baseName,
            		inputFormatException.getProperty("SetName" + baseName, baseName),
            		TryGettingValues(("idItem" + baseName), defaultID) + idIncrements,
            		TryGettingTexture("Texture" + baseName),
            		type,
            		TryGettingEnumMaterial("ItemEnumType" + baseName),
            		textureMap
            		);	

            modItemTable.add(auxItem);
    	}
    }
  
    private void addBowToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		//MLogger.print("Item API", "Obtaining Equipament " + baseName + ".", MLogger.NORMAL);	
    		
            Item auxItem = ItemAPIbuildBow(
            		baseName,
            		inputFormatException.getProperty("SetName" + baseName, baseName),
            		TryGettingValues(("idItem" + baseName), defaultID) + idIncrements,
            		TryGettingTexture("Texture" + baseName),
            		TryGettingValues("SetDurability" + baseName, 0),
            		TryGettingReturnItem(inputFormatException.getProperty(("SetAmmo" + baseName), ""), Item.arrow),
            		textureMap
            		);	

            modItemTable.add(auxItem);
    	}
    }
    
    //Build items:
   
    //Public API Calls, Use them if you wish to build an item manually using this system.
    //It is still recommended to build trough ItemAPICalls, since the only difference between this and
    //Default Item making is the ID checker.
    public static Item ItemAPIbuildDefaultItem(String baseName, String fullName, int id, String texture, 
    		int stackSize, Map<String, Integer> textureHashMap) {
		MLogger.print("Item API", "Building Default Item " + baseName + ".", MLogger.NORMAL);	
    	
    	//Get an EmptyID;
    	id = getEmptyID(baseName, id); 
    	//Register Item
    	Item aux = (new ItemCustom(id)).setMaxStackSize(stackSize).setItemName(baseName);
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
        return aux;
    	
    }
    
    public static Item ItemAPIbuildFoodItem(String baseName, String fullName, int id, String texture, int stackSize, 
    		int foodHealAmount, boolean foodWolfFavorite, Map<String, Integer> textureHashMap) {
		MLogger.print("Item API", "Building Food Item " + baseName + ".", MLogger.NORMAL);	
    	
    	//Get an EmptyID;
    	id = getEmptyID(baseName, id);
    	//Register Item
    	Item aux = (new ItemCustomFood(id, foodHealAmount, foodWolfFavorite)).setMaxStackSize(stackSize).setItemName(baseName);
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
    	return aux;
    }
    
    public static Item ItemAPIbuildSoupItem(String baseName, String fullName, int id, String texture, int foodHealAmount, 
    		boolean foodWolfFavorite, Item returnItem, Map<String, Integer> textureHashMap) {
		MLogger.print("Item API", "Building Soup Item " + baseName + ".", MLogger.NORMAL);	
    	
    	//Get an EmptyID;
    	id = getEmptyID(baseName, id);
    	//Register Item
    	Item aux = (new ItemCustomSoup(id, foodHealAmount, foodWolfFavorite, returnItem).setItemName(baseName));  
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
    	return aux;
    }
    
    public static Item ItemAPIBuildEquipament(String baseName, String fullName, int id, String texture, 
    		String type, EnumToolMaterial enumTypeMaterial, Map<String, Integer> textureHashMap) {
		MLogger.print("Item API", "Building Equipament " + baseName + ".", MLogger.NORMAL);	
    	Item aux = null;
    	
    	//Get an EmptyID;
    	id = getEmptyID(baseName, id);
    	
    	if(type.toLowerCase().equals("axe")){ 
    		aux = (new ItemCustomAxe(id, enumTypeMaterial).setItemName(baseName));     
    	}
        else if(type.toLowerCase().equals("pickaxe")){ 
        	aux = (new ItemCustomPickaxe(id, enumTypeMaterial).setItemName(baseName)); 
        }
        else if(type.toLowerCase().equals("spade") || type.toLowerCase().equals("shovel")){ 
        	aux = (new ItemCustomSpade(id, enumTypeMaterial).setItemName(baseName));   
        }
        else if(type.toLowerCase().equals("sword")){ 
        	aux = (new ItemCustomSword(id, enumTypeMaterial).setItemName(baseName));   
        }
        else if(type.toLowerCase().equals("hoe")){ 
        	aux = (new ItemCustomHoe(id, enumTypeMaterial).setItemName(baseName));    
        }
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
        return aux;
	}

    public static Item ItemAPIbuildBow(String baseName, String fullName, int id, String texture, 
    		int durability, Item consumeItem, Map<String, Integer> textureHashMap) {
		MLogger.print("Item API", "Building Bow " + baseName + ".", MLogger.NORMAL);	
    	
    	//Get an EmptyID;
    	id = getEmptyID(baseName, id); 
    	//Register Item
    	Item aux = (new ItemCustomBow(id, consumeItem, durability)).setItemName(baseName);
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
        return aux;
    	
    }
    
    //Adds
    
    public static void addEnumToMaterialMap(String enumName, int harvestLevel, int maxUses, float efficiencyOnProperMaterial, int damageVsEntity) {
    	MLogger.print("Item API", "Building Material " + enumName + ".", MLogger.NORMAL);
    	if(!materialList.containsKey(enumName)) {
    		materialList.put(enumName.toLowerCase(), 
    				ToolMaterial.create(enumName.toUpperCase(), harvestLevel, maxUses, efficiencyOnProperMaterial, damageVsEntity));
    		return;
    	}   	
    	MLogger.print("Item API", "EnumMaterial ["+ enumName.toUpperCase() +"] already exists, not adding.", MLogger.WARNING);
    }
    
    public static void addEnumToMaterialMap(String enumName, EnumToolMaterial material) {
    	if(!materialList.containsKey(enumName)) {
    		materialList.put(enumName.toLowerCase(), material);
    		return;
    	}   	
    	MLogger.print("Item API", "EnumMaterial ["+ enumName.toUpperCase() +"] already exists, not adding.", MLogger.WARNING);
    }
      
    private static int getEmptyID(String baseName, int id) {
    	if(Item.itemsList[256+id] != null) {
    		MLogger.print("Item API", "Item ["+ baseName +"] with id " + id
    				+ " Conflicts with [" + Item.itemsList[256 + id].getItemName() + "] Trying to change ID", MLogger.ERROR);	
    	}
    	while(Item.itemsList[256 + id] != null) {	
    		idIncrements++;
    		id += idIncrements;
    	}   
    	return id;
    }
    
    private static int getTextureIndex(String texture,String name,Map<String, Integer> textureHash) {
        //If texture is null
    	int textureIndex;
    	if(texture == null) {
    		return defaultTextureIndex;
        }
    	//If texture exists, check Texture Hash
    	if(textureHash == null) {
    		textureIndex = ModLoader.addOverride("/gui/items.png", texture);
    	}
    	else {
    		if(textureHash.containsKey(texture)) {
        		textureIndex = textureHash.get(texture);
        		MLogger.print("Item API", name + " has the same texture as another item, reusing indexNumber.", MLogger.WARNING);
        	}
        	else {
            	textureIndex = ModLoader.addOverride("/gui/items.png", texture);
            	textureHash.put(texture, textureIndex);
        	}
    	}
    	return textureIndex;
    }
  
    //Tries.
    private int TryGettingValues(String idProperty, int valueDef){
        int value;
        try{
        	value = Integer.valueOf(inputFormatException.getProperty(idProperty)).intValue();
        	if(value < 0) {	throw new Exception(); }
        }
        catch(Exception numberFormatException5){
        	MLogger.print("Item API", "Invalid value for: " + idProperty + ". Using default value ["+valueDef+"].", MLogger.ERROR);
            return valueDef;
        }    
        
        return value;
    }
    
    private float TryGettingValues(String idProperty, float valueDef){
        float value;
        try{
        	value = Float.valueOf(inputFormatException.getProperty(idProperty)).floatValue();
        	if(value < 0) {	throw new Exception(); }
        }
        catch(Exception numberFormatException5){
        	MLogger.print("Item API", "Invalid value for: " + idProperty + ". Using default value ["+valueDef+"].", MLogger.ERROR);
            return valueDef;
        }    
        
        return value;
    }
    
    private String TryGettingTextureRedux(String texture, String baseNameProperty) {
    	if(getClass().getResourceAsStream(texture) != null) { 
    		return texture; 
    	}  	
    	MLogger.print("Item API", "Invalid texture for: " + texture + ". Using default texture", MLogger.ERROR);
		return null;
    }
    
    private String TryGettingTexture(String textureProperty) {
    	String image = inputFormatException.getProperty(textureProperty, null);
    	if(image == null) {
    		MLogger.print("Item API", "No texture for: " + textureProperty + ". Using default texture.", MLogger.ERROR);
    		return null;
    	}
    	if(getClass().getResourceAsStream(image) != null) {
    		return image;
    	}
    	MLogger.print("Item API", "Invalid texture for: " + textureProperty + ". Using default texture", MLogger.ERROR);
		return null;
    }
    
    private Boolean TryGettingFavoriteFoodWolf(String foodWolfFavoriteProperty) {
    	String aux = inputFormatException.getProperty(foodWolfFavoriteProperty, "NO").toUpperCase();
    	return (aux.equals("YES") || aux.equals("TRUE"));
    }
    
    private EnumToolMaterial TryGettingEnumMaterial(String materialProperty) {
    	
    	String aux = inputFormatException.getProperty(materialProperty, "N/A").toLowerCase();   	
    	if(materialList.containsKey(aux)){
    		return materialList.get(aux);
    	}
    	MLogger.print("Item API", "Invalid MaterialType for: " + materialProperty + ". Using default value [STONE].", MLogger.ERROR);
    	return EnumToolMaterial.STONE;
    }
    
    private Item TryGettingReturnItem(String itemName, Item defaultItem) {
    	if(!itemName.toLowerCase().contains("item") && !itemName.toLowerCase().contains("tile")) {
    		itemName = "item" + itemName;
    	}
    	
    	for(int x = 1; x < Item.itemsList.length; x++) {  			
    		if(Item.itemsList[x] != null && Item.itemsList[x].getItemName() != null && Item.itemsList[x].getItemName().equals(itemName)) {
    			return Item.itemsList[x];
    		}	
    	}  	
    	MLogger.print("Item API", "Invalid Return Item: " + itemName + ". Using default value ["+ defaultItem.getItemName() +"].", MLogger.ERROR);
    	return Item.itemsList[1];
    }
    
    
}
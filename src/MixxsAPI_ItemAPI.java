package net.minecraft.src;

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

import net.minecraft.src.MixxsAPI.*;

import net.minecraft.src.overrideapi.utils.tool.ToolMaterial;

public class MixxsAPI_ItemAPI {
    private static int defaultTextureIndex = 0;
    private static int defaultID = 3200;
    private static int defaultSetID = 6600;
    private static int idIncrements = 0;
    private static String[] itemTypeArray = 
    	   {"item", //Items Done
    		"axe" ,	"pickaxe", "spade",	"shovel", "sword",	"hoe", //Equipments Done
    		"food", // Food
    		"armor", // Armor
    		"bucket",
    		"door",
    		"seeds",
    		"soup" };
    public static Map<String,EnumToolMaterial> materialList = new HashMap<String,EnumToolMaterial>();
    
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
    	 
    }
    //Map<String, Item> modItemTable
    public boolean ItemAPICalls(String modConfigPath, ArrayList<Item> modItemTable, Map<String,Integer> textureMap){    	    	
    	 
    	System.out.println("> Mixxs [ItemAPI] called, making ItemList and Building Items...");
    	
    	String materialList; //Materials
    	String itemList; //Normal Items
    	String foodList, soupList; //Food Items
    	String swordList, pickaxeList, shovelList, axeList, hoeList; //Equipament Items
    	String equipSetList; //Equipament Set
    	String doorList;
    	String armorList, advArmorList; //Armor Items
    	
    	//HashMap to allow the same texturePath to be used by multiple Items.
    	if(textureMap == null) {
    		textureMap = new HashMap<String,Integer>();
    	}
        try{
            File file = new File(mod_MixxsAPI.configpath + modConfigPath);
            inputFormatException.load(new InputStreamReader(new FileInputStream(file)));
        }
        catch(IOException fileNotFoundException3){
            System.err.println("> [ItemAPI] Error: Invalid File, ItemAPI is now quitting.");
            return false;
        }
        //Get Properties.
        materialList = inputFormatException.getProperty("MaterialList", "");
        
        itemList     = inputFormatException.getProperty("ItemList", "");
        
        foodList     = inputFormatException.getProperty("FoodList", "");
        soupList     = inputFormatException.getProperty("SoupList", "");
        
        swordList    = inputFormatException.getProperty("SwordList", "");
        pickaxeList  = inputFormatException.getProperty("PickaxeList", "");
        shovelList   = inputFormatException.getProperty("ShovelList", "");
        axeList      = inputFormatException.getProperty("AxeList", "");
        hoeList      = inputFormatException.getProperty("HoeList", "");
        equipSetList = inputFormatException.getProperty("FullEquipamentSetList", "");
        
        doorList 	 = inputFormatException.getProperty("DoorList", "");
        
        armorList 	 = inputFormatException.getProperty("ArmorList", "");
        advArmorList = inputFormatException.getProperty("AdvancedArmorList", "");       
        
        //Advanced Option: Enum List.
        if(!materialList.isEmpty()){ 
        	String[] splitMaterialList = materialList.split(","); 
        	addMaterialToEnumMaterialsList(splitMaterialList);	
        }
        if(!itemList.isEmpty()){ 
        	String[] splitItemList = itemList.split(",");
        	addDefaultItemToGame(splitItemList, modItemTable, textureMap);  	
        }
        if(!foodList.isEmpty()) {
        	String[] splitFoodList = foodList.split(",");
        	addFoodItemToGame(splitFoodList, modItemTable, textureMap);
        }
        if(!equipSetList.isEmpty()) {
        	String[] splitSetList = equipSetList.split(",");
        	addEquipamentSetToGame(splitSetList, modItemTable, textureMap);
        }
        if(!swordList.isEmpty()) {
        	String[] splitSwordList = swordList.split(",");
        	addEquipamentToGame(splitSwordList, "sword",modItemTable, textureMap);
        }
        if(!pickaxeList.isEmpty()) {
        	String[] splitPickaxeList = pickaxeList.split(",");
        	addEquipamentToGame(splitPickaxeList, "pickaxe",modItemTable, textureMap);
        }
        if(!shovelList.isEmpty()) {
        	String[] splitShovelList = shovelList.split(",");
        	addEquipamentToGame(splitShovelList, "spade",modItemTable, textureMap);
        }
        if(!axeList.isEmpty()) {
        	String[] splitAxeList = axeList.split(",");
        	addEquipamentToGame(splitAxeList, "axe",modItemTable, textureMap);
        }
        if(!hoeList.isEmpty()) {
        	String[] splitHoeList = hoeList.split(",");
        	addEquipamentToGame(splitHoeList, "hoe",modItemTable, textureMap);
        }
        if(!doorList.isEmpty()) {
        	String[] splitDoorList = doorList.split(",");
        }
        
        if(armorList != null && !armorList.isEmpty()){ 
        	String[] splitArmorList = armorList.split(",");
        
        }
        if(advArmorList != null && !advArmorList.isEmpty()) {
        	String[] splitAdvArmorList = advArmorList.split(","); 
        
        }
        
        
        //Items with special conditions, such as return type, will be placed last:
        if(!soupList.isEmpty()) {
        	String[] splitSoupList = soupList.split(",");
        	addSoupItemToGame(splitSoupList, modItemTable, textureMap);
        }  
        
        return true;
    }
    
    private void addMaterialToEnumMaterialsList(String[] splitMaterialList) {
    	if(splitMaterialList.length > 0) {
    		if(mod_MixxsAPI.additionalFunctionalityMods.get(0).equals("net.minecraft.src.overrideapi.OverrideAPI")) {
        		for(String materialName: splitMaterialList) {
        			System.out.println("> [ItemAPI]: Obtaining Tool Material " + materialName + ".");
        			int materialHarvestLevel = TryGettingValues(("MaterialSetHarverstLevel" + materialName), 0);
        			int materialMaxUses = TryGettingValues(("MaterialSetMaxUses" + materialName), 69);
        			float materialEfficiencyOnProperMaterial = TryGettingValues(("MaterialSetEfficiencyOnProperMaterial" + materialName), 2.0F);
        			int materialDamageVsEntity = TryGettingValues(("MaterialSetDamageVsEntity" + materialName), 0);
            		MixxsAPI_ItemAPI.addEnumToMaterialMap(
            				materialName,
            				materialHarvestLevel,
            				materialMaxUses,
            				materialEfficiencyOnProperMaterial,
            				materialDamageVsEntity);
        		}
        	}
    		else {
    			System.out.println("> [ItemAPI]: Could not find OverrideAPI. Will not be able to create custom ENUM items.");
    		}
    	}
    }
    
    /**
     * Adds a Item into a Item Table.
     */
    private void addDefaultItemToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		System.out.println("> [ItemAPI]: Obtaining Default Item " + baseName + ".");
    		
    		//Get initial values, common to all of the items.
    		ArrayList<Object> defaultValues = TryGettingCommonItemValues(baseName, "");

            Item auxItem = ItemAPIbuildDefaultItem(
            		baseName,
            		(String)defaultValues.get(0), //fullName
            		(Integer)defaultValues.get(1), //ID
            		(String)defaultValues.get(2), //Texture
            		(Integer)defaultValues.get(3), //StackSize
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
    		System.out.println("> [ItemAPI]: Obtaining Food Item " + baseName + ".");
    		
    		//Get initial values, common to all of the items.
    		ArrayList<Object> defaultValues = TryGettingCommonItemValues(baseName, "");
    		int foodHealAmount = TryGettingValues(("FoodHealAmount" + baseName), 1);
        	boolean foodWolfFavorite = TryGettingFavoriteFoodWolf(("FoodWolfFavorite" + baseName));
 
        	Item auxItem = ItemAPIbuildFoodItem(
            		baseName,
            		(String)defaultValues.get(0), //fullName
            		(Integer)defaultValues.get(1), //ID
            		(String)defaultValues.get(2), //Texture
            		(Integer)defaultValues.get(3), //StackSize
            		foodHealAmount,
            		foodWolfFavorite,
            		textureMap
            		);	
            modItemTable.add(auxItem);
    	}
    }
    
    private void addSoupItemToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		System.out.println("> [ItemAPI]: Obtaining Soup Item " + baseName + ".");
    		
    		//Get initial values, common to all of the items.
    		ArrayList<Object> defaultValues = TryGettingCommonItemValues(baseName, "soup");
    		int foodHealAmount = TryGettingValues(("FoodHealAmount" + baseName), 1);
        	boolean foodWolfFavorite = TryGettingFavoriteFoodWolf(("FoodWolfFavorite" + baseName));
        	String itemReturn = inputFormatException.getProperty(("SetReturnItem" + baseName), "");
        	
        	if(itemReturn.contains("item." + baseName)) {
        		itemReturn = "none";
        	}
        	
        	Item returnItem = TryGettingReturnItem(itemReturn);
        	
        	Item auxItem = ItemAPIbuildSoupItem(
            		baseName,
            		(String)defaultValues.get(0), //fullName
            		(Integer)defaultValues.get(1), //ID
            		(String)defaultValues.get(2), //Texture
            		foodHealAmount,
            		foodWolfFavorite,
            		returnItem, 
            		textureMap
            		);	
        	
            modItemTable.add(auxItem);
    	}
    }
    
    private void addEquipamentSetToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String, Integer> textureMap) {
    	for(String baseName : splitList) {
    		System.out.println("> [ItemAPI]: Obtaining Set " + baseName + ".");
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
                		1, //StackSize
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
    		System.out.println("> [ItemAPI]: Obtaining Default Item " + baseName + ".");
    		
    		//Get initial values, common to all of the items.
    		ArrayList<Object> defaultValues = TryGettingCommonItemValues(baseName, "equipament");
    		EnumToolMaterial enumTypeMaterial = TryGettingEnumMaterial("ItemEnumType" + baseName);
    		
    		
            Item auxItem = ItemAPIBuildEquipament(
            		baseName,
            		(String)defaultValues.get(0), //fullName
            		(Integer)defaultValues.get(1), //ID
            		(String)defaultValues.get(2), //Texture
            		(Integer)defaultValues.get(3), //StackSize
            		type,
            		enumTypeMaterial,
            		textureMap
            		);	

            modItemTable.add(auxItem);
    	}
    }
  
   
    //Public API Calls, Use them if you wish to build an item manually using this system.
    //It is still recommended to build trough ItemAPICalls, since the only difference between this and
    //Default Item making is the ID checker.
    public static Item ItemAPIbuildDefaultItem(String baseName, String fullName, int id, String texture, 
    		int stackSize, Map<String, Integer> textureHashMap) {
    	System.out.println("> [ItemAPI]: Building Default Item " + baseName + ".");
    	Item aux = null;
    	
    	//Get an EmptyID;
       	while(Item.itemsList[256 + id] != null) {
    		System.err.println("> [ItemAPI] Warning: Item ["+ baseName +"] with id " + id
    				+ " Conflict with [" + Item.itemsList[256 + id].getItemName() + "] Trying to change ID");
    		idIncrements++;
    		id += idIncrements;
    	}   
    	
    	//Register Item
    	aux = (new Item(id)).setMaxStackSize(stackSize);
    	
        //Set the Name
        aux.setItemName(baseName);
        
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
        return aux;
    	
    }
    
    public static Item ItemAPIbuildFoodItem(String baseName, String fullName, int id, String texture, int stackSize, 
    		int foodHealAmount, boolean foodWolfFavorite, Map<String, Integer> textureHashMap) {
    	System.out.println("> [ItemAPI]: Building Food Item " + baseName + ".");
    	Item aux = null;
    	
    	//Get an EmptyID;
       	while(Item.itemsList[256 + id] != null) {
    		System.err.println("> [ItemAPI] Warning: Item ["+ baseName +"] with id " + id
    				+ " Conflict with [" + Item.itemsList[256 + id].getItemName() + "] Trying to change ID");
    		idIncrements++;
    		id += idIncrements;
    	}   
    	
    	//Register Item
    	aux = (new ItemFood(id, foodHealAmount, foodWolfFavorite)).setMaxStackSize(stackSize);
    	
        //Set the Name
        aux.setItemName(baseName);
        
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
    	return aux;
    }
    
    public static Item ItemAPIbuildSoupItem(String baseName, String fullName, int id, String texture, int foodHealAmount, 
    		boolean foodWolfFavorite, Item returnItem, Map<String, Integer> textureHashMap) {
    	System.out.println("> [ItemAPI]: Building Food Item " + baseName + ".");
    	Item aux = null;
    	
    	//Get an EmptyID;
       	while(Item.itemsList[256 + id] != null) {
    		System.err.println("> [ItemAPI] Warning: Item ["+ baseName +"] with id " + id
    				+ " Conflict with [" + Item.itemsList[256 + id].getItemName() + "] Trying to change ID");
    		idIncrements++;
    		id += idIncrements;
    	}   
    	
    	//Register Item
    	aux = (new ItemCustomSoup(id, foodHealAmount, foodWolfFavorite, returnItem));
    	
        //Set the Name
        aux.setItemName(baseName);
        
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
    	return aux;
    }
    
    public static Item ItemAPIBuildEquipament(String baseName, String fullName, int id, String texture, 
    		int stackSize, String type, EnumToolMaterial enumTypeMaterial, Map<String, Integer> textureHashMap) {
    	
    	System.out.println("> [ItemAPI]: Building Equipament " + baseName + ".");
    	
    	Item aux = null;
    	
    	//Get an EmptyID;
       	while(Item.itemsList[256 + id] != null) {
    		System.err.println("> [ItemAPI] Warning: Item ["+ baseName +"] with id " + id
    				+ " Conflict with [" + Item.itemsList[256 + id].getItemName() + "] Trying to change ID");
    		idIncrements++;
    		id += idIncrements;
    	}   
    	
    	if(type.toLowerCase().equals("axe")) {
        	aux = (new ItemAxe(id, enumTypeMaterial));
        }
        else if(type.toLowerCase().equals("pickaxe")){
        	aux = (new ItemPickaxe(id, enumTypeMaterial));
        }
        else if(type.toLowerCase().equals("spade") || type.toLowerCase().equals("shovel")){
        	aux = (new ItemSpade(id, enumTypeMaterial));
        }
        else if(type.toLowerCase().equals("sword")){
        	aux = (new ItemSword(id, enumTypeMaterial));
        }
        else if(type.toLowerCase().equals("hoe")){
        	aux = (new ItemHoe(id, enumTypeMaterial));
        }
    	
    	//Set the Name
        aux.setItemName(baseName);
        
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        
        ModLoader.AddName(aux, fullName);
        return aux;
	}

    //Adds
    
    public static void addEnumToMaterialMap(String enumName, int harvestLevel, int maxUses, float efficiencyOnProperMaterial, int damageVsEntity) {
    	System.out.println("> [ItemAPI]: Building Material " + enumName + ".");
    	if(!materialList.containsKey(enumName)) {
    		materialList.put(enumName.toLowerCase(), 
    				ToolMaterial.create(enumName.toUpperCase(), harvestLevel, maxUses, efficiencyOnProperMaterial, damageVsEntity));
    		return;
    	}   	
    	System.out.println("[Mixxs API] Warning: enumMaterial ["+ enumName.toUpperCase() +"] already exists, not adding.");
    }
    
    public static void addEnumToMaterialMap(String enumName, EnumToolMaterial material) {
    	if(!materialList.containsKey(enumName)) {
    		materialList.put(enumName.toLowerCase(), material);
    		return;
    	}   	
    	System.out.println("[Mixxs API] Warning: enumMaterial ["+ enumName.toUpperCase() +"] already exists, not adding.");
    }
    

    /*private String checkItemTypeRedux(String type) {
    	if(type.equals("spade") || type.equals("sword") || type.equals("hoe") || type.equals("pickaxe") || type.equals("axe")) {
    		return "Equipament";
    	}
    	if(type.equals("Food")) {
    		return "Food";
    	}
    	return "Item";
    }*/
      
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
        		System.out.println("> [ItemAPI] Warning: "+ name + " has the same texture as another item, reusing indexNumber.");
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
        	if(value < 0) {
        		throw new Exception();
        	}
        }
        catch(Exception numberFormatException5){
            System.err.println("> [ItemAPI] Error: Invalid value for: " + idProperty + ". Using default value ["+valueDef+"].");
            return valueDef;
        }    
        
        return value;
    }
    
    private float TryGettingValues(String idProperty, float valueDef){
        float value;
        try{
        	value = Float.valueOf(inputFormatException.getProperty(idProperty)).floatValue();
        	if(value < 0) {
        		throw new Exception();
        	}
        }
        catch(Exception numberFormatException5){
            System.err.println("> [ItemAPI] Error: Invalid value for: " + idProperty + ". Using default value ["+valueDef+"].");
            return valueDef;
        }    
        
        return value;
    }
    
    private ArrayList<Object> TryGettingCommonItemValues(String baseName, String type){
        ArrayList<Object> aux = new ArrayList<>();
        aux.add(inputFormatException.getProperty("SetName" + baseName, baseName));
        aux.add(TryGettingValues(("idItem" + baseName), defaultID) + idIncrements);
        aux.add(TryGettingTexture("Texture" + baseName));
        if(!type.equals("equipament") && !type.equals("soup")) {
        	aux.add(TryGettingValues(("SetMaxStack" + baseName), 1));
        }
        else {
        	aux.add(1);
        }
        aux.add(inputFormatException.getProperty("SetName" + baseName, baseName));
        aux.add(TryGettingValues(("idItem" + baseName), defaultID) + idIncrements);
        aux.add(TryGettingTexture("Texture" + baseName));
        return aux;//String fullName, int id, String texture, int stackSize,
    }
    
    private String TryGettingTextureRedux(String texture, String baseNameProperty) {
    	if(getClass().getResourceAsStream(texture) != null) {
    		return texture;
    	}
    	
    	System.err.println("> [ItemAPI] Error: Invalid texture for: " + texture + ". Using default texture");
		return null;
    }
    
    private String TryGettingTexture(String textureProperty) {
    	String image = inputFormatException.getProperty(textureProperty, null);
    	if(image == null) {
    		System.err.println("> [ItemAPI] Error: No texture for: " + textureProperty + ". Using default texture."); 
    		return null;
    	}
    	if(getClass().getResourceAsStream(image) != null) {
    		return image;
    	}
    	
    	System.err.println("> [ItemAPI] Error: Invalid texture for: " + textureProperty + ". Using default texture");
		return null;
    }
    
    /*private String TryGettingItemType(String typeProperty) {
    	String input = inputFormatException.getProperty(typeProperty, "Item");
    	for(String s: itemTypeArray) {
    		if(s.equals(input.toLowerCase())) {
    			if(input.toLowerCase().equals("shovel")) {
    				return "spade";
    			}
    			return input.toLowerCase();
    		}
    	}
    	System.err.println("> [ItemAPI] Error: Invalid type for: " + typeProperty + ". Using default value.");
    	return "item";
    }*/
    
    private Boolean TryGettingFavoriteFoodWolf(String foodWolfFavoriteProperty) {
    	String aux = inputFormatException.getProperty(foodWolfFavoriteProperty, "NO").toUpperCase();
    	if(aux.equals("YES") || aux.equals("TRUE")) {
    		return true;
    	}
    	return false;
    }
    
    private EnumToolMaterial TryGettingEnumMaterial(String materialProperty) {
    	
    	String aux = inputFormatException.getProperty(materialProperty, "N/A").toLowerCase();
    	
    	if(materialList.containsKey(aux)){
    		return materialList.get(aux);
    	}
    	
    	System.err.println("> [ItemAPI] Error: Invalid MaterialType for: " + materialProperty + ". Using default value [STONE].");
    	return EnumToolMaterial.STONE;
    
    }
    
    private Item TryGettingReturnItem(String itemName) {
    	if(itemName.equals("none")) {
    		return null;
    	}

    	if(!itemName.toLowerCase().contains("item") && !itemName.toLowerCase().contains("tile")) {
    		itemName = "item" + itemName;
    	}
    	
    	for(int x = 1; x < Item.itemsList.length; x++) {  			
    		if(Item.itemsList[x] != null && Item.itemsList[x].getItemName() != null && Item.itemsList[x].getItemName().equals(itemName)) {
    			return Item.itemsList[x];
    		}	
    	}
    	
    	System.err.println("> [ItemAPI] Error: Invalid Return Item: " + itemName + ". Using default value [tile.stone].");
    	
    	return Item.itemsList[1];
    }
    
    
    
    
}

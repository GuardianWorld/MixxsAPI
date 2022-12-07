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

import net.minecraft.src.overrideapi.utils.tool.ToolMaterial;

public class MixxsAPI_ItemAPI {
    private static int defaultTextureIndex = 0;
    private static int defaultID = 3200;
    private static int idIncrements = 0;
    private static String[] itemTypeArray = {"item", "axe" ,"pickaxe", "spade", "shovel", "sword", "spade", "hoe", "food", "bucket", "door", "seeds"};
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
    
    public boolean ItemAPICalls(String modConfigPath, ArrayList<Item> modItemTable, Map<String,Integer> textureMap){    	    	
    	
    	System.out.println("> Mixxs [ItemAPI] called, making ItemList and Building Items...");
    	
    	//HashMap to allow the same texturePath to be used by multiple Items.
    	if(textureMap == null) {
    		textureMap = new HashMap<String,Integer>();
    	}
    	
        String[] splitItemList = {};
        String[] splitEnumList = {};
        try{
            File file = new File(mod_MixxsAPI.configpath + modConfigPath);
            inputFormatException.load(new InputStreamReader(new FileInputStream(file)));
            String itemList = inputFormatException.getProperty("ItemList", null);
            String enumList = inputFormatException.getProperty("MaterialList", null);
            
            if(itemList != null) { splitItemList = itemList.split(","); }
            if(enumList != null) { splitEnumList = enumList.split(","); }
			//rarity = Integer.valueOf(numberformatexception.getProperty("rarity")).intValue();
        }
        catch(IOException fileNotFoundException3){
            System.err.println("> [ItemAPI] Error: Invalid File, ItemAPI is now quitting.");
            return false;
        }
        
        //Advanced Option: Enum List.
    	if(mod_MixxsAPI.additionalFunctionalityMods.get(0).equals("net.minecraft.src.overrideapi.OverrideAPI")) {
    		for(String materialName: splitEnumList) {
    			System.out.println("> [ItemAPI]: Obtaining Tool Material " + materialName + ".");
    			int materialHarvestLevel = TryGettingValues(("MaterialSetHLevel" + materialName), 0);
    			int materialMaxUses = TryGettingValues(("MaterialSetMUses" + materialName), 59);
    			float materialEfficiencyOnProperMaterial = TryGettingValues(("MaterialSetEOPM" + materialName), 2.0F);
    			int materialDamageVsEntity = TryGettingValues(("MaterialSetDVE" + materialName), 0);
        		MixxsAPI_ItemAPI.addEnumToMaterialMap(
        				materialName,
        				materialHarvestLevel,
        				materialMaxUses,
        				materialEfficiencyOnProperMaterial,
        				materialDamageVsEntity);
    		}
        }
        
        
        
        //If File with Strings is found.
        for(String s : splitItemList){
        	System.out.println("> [ItemAPI]: Obtaining Item " + s + ".");
            
            //Get initial values
            int id = TryGettingValues(("idItem" + s), defaultID) + idIncrements;
            String texture = TryGettingTexture("Texture" + s);
            String type = TryGettingItemType("Type" + s);
            String name = inputFormatException.getProperty("SetName" + s, s); 
            int stackSize = 1;
            
            EnumToolMaterial enumMaterial = EnumToolMaterial.STONE;
            if(checkItemTypeRedux(type).equals("Equipament")) {
            	enumMaterial = TryGettingEnumMaterial("ItemEnumType" + s);
            }
            else {
            	stackSize = TryGettingValues(("SetMaxStack" + s), 1);
            }
            
            int foodHealAmount = 1;
            boolean foodWolfFavorite = false;
            if(checkItemTypeRedux(type).equals("Food")){
            	foodHealAmount = TryGettingValues(("FoodHealAmount" + s), 1);
            	foodWolfFavorite = TryGettingFavoriteFoodWolf(("FoodWolfFavorite" + s));
            }
            
            
            //Call the building calls;
            Item auxItem = ItemAPIbuildItem(
            		s, 				  //base name
            		name,			  //full name
            		id, 			  //Item ID
            		texture,		  //Texture
            		type,			  //Item Type
            		stackSize,		  //Stack size
            		enumMaterial,	  //EnumMaterial Type.
            		foodHealAmount,   //Food heal amount
            		foodWolfFavorite, //Is it wolfy's favorite?
            		textureMap    //TextureHash map for repeated textures, not obligatory.
            		);
            
            modItemTable.add(auxItem);    
        }
        return true;
    }
    
    public Item ItemAPIbuildItem(String baseName, String fullName, int id, String texture, String type, int stackSize, 
    		EnumToolMaterial enumTypeMaterial, int healAmount, boolean wolfFavorite , Map<String, Integer> textureHash){
    	System.out.println("> [ItemAPI]: Building Item " + baseName + ".");
    	
        Item aux = null;        
       
        while(Item.itemsList[256 + id] != null) {
			System.err.println("> [ItemAPI] Warning: Item ["+ baseName +"] with id " + id
					+ " Conflict with [" + Item.itemsList[256 + id].getItemName() + "] Trying to change ID");
			idIncrements++;
			id += idIncrements;
		}     
        
        //Set individual characteristics.
        if(type.equals("item")){
            aux = (new Item(id)).setMaxStackSize(stackSize);   
        }
        else if(type.equals("axe")) {
        	aux = (new ItemAxe(id, enumTypeMaterial));
        }
        else if(type.equals("pickaxe")){
        	aux = (new ItemPickaxe(id, enumTypeMaterial));
        }
        else if(type.equals("spade")){
        	aux = (new ItemSpade(id, enumTypeMaterial));
        }
        else if(type.equals("sword")){
        	aux = (new ItemSword(id, enumTypeMaterial));
        }
        else if(type.equals("hoe")){
        	aux = (new ItemHoe(id, enumTypeMaterial));
        }
        else if(type.equals("food")){
        	aux = (new ItemFood(id, healAmount, wolfFavorite)).setMaxStackSize(stackSize);
        }
        else if(type.equals("bucket")){

        }
        else if(type.equals("door")){

        }
        
        //Set the Name
        aux.setItemName(baseName);
        
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHash));  
        
        ModLoader.AddName(aux, fullName);
        return aux;
    }  
    
    private String checkItemTypeRedux(String type) {
    	if(type.equals("spade") || type.equals("sword") || type.equals("hoe") || type.equals("pickaxe") || type.equals("axe")) {
    		return "Equipament";
    	}
    	if(type.equals("Food")) {
    		return "Food";
    	}
    	return "Item";
    }
    
    
    private int getTextureIndex(String texture,String name,Map<String, Integer> textureHash) {
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
    
    public static void addEnumToMaterialMap(String enumName, int harvestLevel, int maxUses, float efficiencyOnProperMaterial, int damageVsEntity) {
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
    
    //Tries.
    private int TryGettingValues(String idProperty, int valueDef){
        int value;
        try{
        	value = Integer.valueOf(inputFormatException.getProperty(idProperty)).intValue();
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
        }
        catch(Exception numberFormatException5){
            System.err.println("> [ItemAPI] Error: Invalid value for: " + idProperty + ". Using default value ["+valueDef+"].");
            return valueDef;
        }    
        
        return value;
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
    
    private String TryGettingItemType(String typeProperty) {
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
    }
    
    private Boolean TryGettingFavoriteFoodWolf(String foodWolfFavoriteProperty) {
    	String aux = inputFormatException.getProperty(foodWolfFavoriteProperty, "NO").toUpperCase();
    	if(aux.equals("YES")) {
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
}

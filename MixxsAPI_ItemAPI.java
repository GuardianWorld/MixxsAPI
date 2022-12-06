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

public class MixxsAPI_ItemAPI {
    private static int defaultTextureIndex = 0;
    private static int defaultID = 600;
    private static int idIncrements;
    private static String[] itemTypeArray = {"item", "axe" ,"pickaxe", "spade", "shovel", "sword", "spade", "hoe", "food", "bucket", "door", "seeds"};
    
    Properties inputFormatException;
    
    public MixxsAPI_ItemAPI(){
    	 String defaultTexturePath = "/textures/NoTexture.png";
    	 defaultTextureIndex = ModLoader.addOverride("/gui/items.png", defaultTexturePath);
    	 MixxsAPI_ItemAPI.idIncrements = 0;
    	 inputFormatException = new Properties();
    }
    
    public boolean ItemAPICalls(String modConfigPath, ArrayList<Item> modItemTable){    	    	
    	
    	System.out.println("> Mixxs [ItemAPI] called, making ItemList and Building Items...");
    	
    	//HashMap to allow the same texturePath to be used by multiple Items.
    	Map<String,Integer> tempTextureMap = new HashMap<String,Integer>();
    	
        String[] split;
        try{
            File file = new File(mod_MixxsAPI.configpath + modConfigPath);
            inputFormatException.load(new InputStreamReader(new FileInputStream(file)));
            String itemList = inputFormatException.getProperty("ItemList");
            split = itemList.split(",");
			//rarity = Integer.valueOf(numberformatexception.getProperty("rarity")).intValue();
        }
        catch(IOException fileNotFoundException3){
            System.err.println("> [ItemAPI] Error: Invalid File, ItemAPI is now quitting.");
            return false;
        }
        
        for(String s : split){
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
            		tempTextureMap    //TextureHash map for repeated textures, not obligatory.
            		);

            modItemTable.add(auxItem);      
            if(id == 600 + idIncrements) {
            	MixxsAPI_ItemAPI.idIncrements++;
            }
            
            
            
        }
        return true;
    }
    
    public Item ItemAPIbuildItem(String baseName, String fullName, int id, String texture, String type, int stackSize, 
    		EnumToolMaterial enumTypeMaterial, int healAmount, boolean wolfFavorite , Map<String, Integer> textureHash){
    	System.out.println("> [ItemAPI]: Building Item " + baseName + ".");
    	
        Item aux = null;        
       
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
    
    private String TryGettingTexture(String textureProperty) {
    	String image = inputFormatException.getProperty(textureProperty, null);
    	if(image == null) {
    		System.err.println("> [ItemAPI] Error: No texture for: " + textureProperty + ". Using default texture."); 
    		return null;
    	}
    	try{
    		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(image)));
    		return image;
    	} catch (Exception e) {
    		System.err.println("> [ItemAPI] Error: Invalid texture for: " + textureProperty + ". Using default texture");
    		return null;
		}
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
    	
    	if(aux.equals("wood")) {
    		return EnumToolMaterial.WOOD;
    	}
    	if(aux.equals("stone")) {
    		return EnumToolMaterial.STONE;
    	}
    	if(aux.equals("iron")) {
    		return EnumToolMaterial.IRON;
    	}
    	if(aux.equals("diamond") || aux.equals("emerald")) {
    		return EnumToolMaterial.EMERALD;
    	}
    	if(aux.equals("gold")) {
    		return EnumToolMaterial.GOLD;
    	}
    	
    	System.err.println("> [ItemAPI] Error: Invalid MaterialType for: " + materialProperty + ". Using default value [STONE].");
    	return EnumToolMaterial.STONE;
    
    }
}

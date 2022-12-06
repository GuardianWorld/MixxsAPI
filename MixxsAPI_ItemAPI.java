package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

public class MixxsAPI_ItemAPI {
    private static int defaultTextureIndex = 0;
    private static int defaultID = 600;
    private static int idIncrements;
    private static String[] itemTypeArray = {"Item", "Pickaxe", "Spade", "Sword", "Spade", "Hoe", "Food", "Bucket", "Door"};
    
    Properties inputFormatException;
    
    public MixxsAPI_ItemAPI(){
    	 String defaultTexturePath = "/textures/NoTexture.png";
    	 defaultTextureIndex = ModLoader.addOverride("/gui/items.png", defaultTexturePath);
    	 MixxsAPI_ItemAPI.idIncrements = 0;
    	 inputFormatException = new Properties();

    }
    
    public boolean ItemAPICalls(String modConfigPath, ArrayList<Item> modItemTable){    	    	
    	System.out.println("Mixxs ItemAPI called, making ItemList...");
        String[] split;
        try{
            File file = new File(mod_MixxsAPI.configpath + modConfigPath);
            inputFormatException.load(new InputStreamReader(new FileInputStream(file)));
            String itemList = inputFormatException.getProperty("ItemList");
            split = itemList.split(",");
			//rarity = Integer.valueOf(numberformatexception.getProperty("rarity")).intValue();
        }
        catch(IOException fileNotFoundException3){
            System.err.println("Invalid File, ItemAPI is now quitting.");
            return false;
        }

        for(String s : split){
            
        	String idProperty = "idItem" + s;
            String itemTextureProperty = "Texture" + s;  
            String itemTypeProperty = "Type" + s;
            String itemNameProperty = "SetName" + s;
            String itemStackSizeProperty = "SetMaxStack" + s;

            int id = TryGettingValues(idProperty, defaultID) + idIncrements;
            String texture = TryGettingTexture(itemTextureProperty);
            String type = TryGettingType(itemTypeProperty);
            String name = inputFormatException.getProperty(itemNameProperty, s);
            int stackSize = TryGettingValues(itemStackSizeProperty, 1);

            Item auxItem = buildItem(s, id, texture, type, stackSize);

            modItemTable.add(auxItem);      
            ModLoader.AddName(auxItem, name);
            if(id == 600 + idIncrements) {
            	MixxsAPI_ItemAPI.idIncrements++;
            }
        }

        return true;
    }
    
    private Item buildItem(String name, int id, String texture, String type, int stackSize){
        Item aux = null;

        if(type.equals("Item")){
            aux = (new Item(id)).setItemName(name).setMaxStackSize(stackSize);   
            if(texture == null) {
            	aux.setIconIndex(defaultTextureIndex);
            }
            else
            {
            	aux.setIconIndex(ModLoader.addOverride("/gui/items.png", texture));
            }
            //aux.setIconIndex(defaultTexture);
        }
        else if(type.equals("Pickaxe")){

        }
        else if(type.equals("Spade")){

        }
        else if(type.equals("Sword")){

        }
        else if(type.equals("Hoe")){

        }
        else if(type.equals("Food")){

        }
        else if(type.equals("Bucket")){

        }
        else if(type.equals("Door")){

        }

        return aux;
    }
    
    private int TryGettingValues(String idProperty, int valueDef){
        int value;
        try{
        	value = Integer.valueOf(inputFormatException.getProperty(idProperty)).intValue();
        }
        catch(Exception numberFormatException5){
            System.err.println("Invalid value for: " + idProperty + ". Using default value ["+valueDef+"].");
            return valueDef;
        }    
        
        return value;
    }
    
    private String TryGettingTexture(String textureProperty) {
    	String image = inputFormatException.getProperty(textureProperty, null);
    	if(image == null) {
    		System.err.println("No texture for: " + textureProperty + ". Using default texture."); 
    		return null;
    	}
    	try{
    		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(image)));
    		return image;
    	} catch (Exception e) {
    		System.err.println("Invalid texture for: " + textureProperty + ". Using default texture");
    		return null;
		}
    }
    
    private String TryGettingType(String typeProperty) {
    	String input = inputFormatException.getProperty(typeProperty, "Item");
    	for(String s: itemTypeArray) {
    		if(s.equals(input)) {
    			return input;
    		}
    	}
    	System.err.println("Invalid type for: " + typeProperty + ". Using default value.");
    	return "Item";
    }
    
}

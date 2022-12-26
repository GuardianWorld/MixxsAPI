package MixxsAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ModLoader;
import MixxsAPI.Items.*;
import net.minecraft.src.overrideapi.utils.tool.ToolMaterial;

public class Item_Builder {
	
	protected static int defaultTextureIndex = ModLoader.addOverride("/gui/items.png", "/textures/NoTexture.png");
	public static Map<String,EnumToolMaterial> materialList = new HashMap<String,EnumToolMaterial>();
	public static Map<String, Integer> armorLayerList = new HashMap<String, Integer>();
	
	//Build items:
	   
    //Public API Calls, Use them if you wish to build an item manually using this system.
    //It is still recommended to build trough ItemAPICalls, since the only difference between this and
    //Default Item making is the ID checker.
    public static Item ItemAPIBuildDefaultItem(String baseName, String fullName, int id, String texture, 
    	int stackSize, Map<String, Integer> textureHashMap) {
		MLogger.print("Item API", "Building Default Item " + baseName + ".", MLogger.NORMAL);	
    	
    	//Get an EmptyID;
    	id = getEmptyItemID(baseName, id); 
    	//Register Item
    	Item aux = (new ItemCustom(id)).setMaxStackSize(stackSize).setItemName(baseName);
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
        return aux;
    	
    }
    
    public static Item ItemAPIBuildFoodItem(String baseName, String fullName, int id, String texture, int stackSize, 
    		int foodHealAmount, boolean foodWolfFavorite, Map<String, Integer> textureHashMap) {
		MLogger.print("Item API", "Building Food Item " + baseName + ".", MLogger.NORMAL);	
    	
    	//Get an EmptyID;
    	id = getEmptyItemID(baseName, id);
    	//Register Item
    	Item aux = (new ItemCustomFood(id, foodHealAmount, foodWolfFavorite)).setMaxStackSize(stackSize).setItemName(baseName);
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
    	return aux;
    }
    
    public static Item ItemAPIBuildSoupItem(String baseName, String fullName, int id, String texture, int foodHealAmount, 
    		boolean foodWolfFavorite, Item returnItem, Map<String, Integer> textureHashMap) {
		MLogger.print("Item API", "Building Soup Item " + baseName + ".", MLogger.NORMAL);	
    	
    	//Get an EmptyID;
    	id = getEmptyItemID(baseName, id);
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
    	id = getEmptyItemID(baseName, id);
    	
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

    public static Item ItemAPIBuildBow(String baseName, String fullName, int id, String texture, 
    		int durability, Item consumeItem, Map<String, Integer> textureHashMap) {
		MLogger.print("Item API", "Building Bow " + baseName + ".", MLogger.NORMAL);	
    	
    	//Get an EmptyID;
    	id = getEmptyItemID(baseName, id); 
    	//Register Item
    	Item aux = (new ItemCustomBow(id, consumeItem, durability)).setItemName(baseName);
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
        return aux;
    	
    }
    
    public static Item ItemAPIBuildShears(String baseName, String fullName, int id, String texture, int durability,
    		ArrayList<Integer> blockIDList, Map<String,Integer> textureHashMap) {
    	MLogger.print("Item API", "Building Shears " + baseName + ".", MLogger.NORMAL);	
    	//Get an EmptyID;
    	id = getEmptyItemID(baseName, id); 
    	//Register Item
    	Item aux = (new ItemCustomShears(id, blockIDList ,durability)).setItemName(baseName);
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
        return aux;    	
    }
    
    public static Item ItemAPIBuildBlockPlacer(String baseName, String fullName, int id, String texture, int durability,
    		int blockID, String sound, Map<String,Integer> textureHashMap) {
    	MLogger.print("Item API", "Building BlockPlacer " + baseName + ".", MLogger.NORMAL);	
    	//Get an EmptyID;
    	id = getEmptyItemID(baseName, id); 
    	//Register Item
    	Item aux = (new ItemBlockPlacer(id, durability, blockID, sound)).setItemName(baseName);
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
        return aux;    	
    }
    
    public static Item ItemAPIBuildBlockReplacer(String baseName, String fullName, int id, String texture, int durability,
    		int blockID, ArrayList<Integer> replacerListForbidden, Map<String,Integer> textureHashMap) {
    	MLogger.print("Item API", "Building BlockReplacer " + baseName + ".", MLogger.NORMAL);	
    	//Get an EmptyID;
    	id = getEmptyItemID(baseName, id); 
    	//Register Item
    	Item aux = (new ItemBlockReplacer(id, durability, blockID, replacerListForbidden)).setItemName(baseName);
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        //Add Name via ModLoader
        ModLoader.AddName(aux, fullName);
        return aux;    	
    }
    
    public static Item ItemAPIBuildArmor(String baseName, String fullName, int id, String texture, int durability, int armorLevel,
    		int renderIndex, int armorType, int damageReduceAmount, Map<String,Integer> textureHashMap) {
    	MLogger.print("Item API", "Building Armor " + baseName + ".", MLogger.NORMAL);	
    	//Get an EmptyID;
    	id = getEmptyItemID(baseName, id); 
    	//Register Item
    	Item aux = (new ItemCustomArmor(id, armorLevel, renderIndex, armorType, damageReduceAmount, durability)).setItemName(baseName);
        //Set Texture
        aux.setIconIndex(getTextureIndex(texture,baseName, textureHashMap));  
        //Add Name via ModLoader
        ModLoader.AddArmor("");
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
    public static void addLayerIndexToLayerMap(String layerName, int index) {
    	if(!armorLayerList.containsKey(layerName)) {
    		armorLayerList.put(layerName, index);	
    	}
    	return;
    }
    private static int getEmptyItemID(String baseName, int id) {
    	if(Item.itemsList[256+id] != null) {
    		MLogger.print("Item API", "Item ["+ baseName +"] with id " + id
    				+ " Conflicts with [" + Item.itemsList[256 + id].getItemName() + "] Trying to change ID", MLogger.ERROR);	
    	}
    	while(Item.itemsList[256 + id] != null) {	
    		MixxsAPI_ItemAPI.idIncrements++;
    		id += MixxsAPI_ItemAPI.idIncrements;
    	}   
    	return id;
    }
    
    private static int getTextureIndex(String texture,String name,Map<String, Integer> textureHash) {
        //If texture is null
    	
    	int textureIndex;
    	if(texture == null || texture.equals("/textures/NoTexture.png")) {
    		//System.err.println(defaultTextureIndex);
    		return defaultTextureIndex;
        }
    	
    	//System.err.println(texture);
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
	
}

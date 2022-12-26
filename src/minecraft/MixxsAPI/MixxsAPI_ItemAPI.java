package MixxsAPI;

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
import MixxsAPI.Items.*;
import net.minecraft.src.overrideapi.utils.tool.ToolMaterial;

public class MixxsAPI_ItemAPI {
	//public static Item monsterEgg = (new ItemEntityEgg(9000)).setItemName("item.entity.egg.name");
	//public static Item bow = (new CustomItemBow(555, Item.itemsList[1], 384)).setIconCoord(5, 1); //item Name, but can work for blocks as well.
	//public static Item customDye = (new CustomItemDye(9001)).setItemName("Custom Dye");

    protected static int defaultID = 3200;
    private static int defaultSetID = 6600;
    protected static int idIncrements = 0;
    public ArrayList<ItemStack> customItems;
    
    Properties inputFormatException;
     
    private final String I_setName 				= "SetName_";
    private final String I_setID 				= "idItem_";
    private final String I_reservedToolSetID 	= "idReservedToolSet_";
    private final String I_reservedArmorSetID 	= "idReservedArmorSet_";
    private final String I_SetEnumType 			= "SetEnumType_";
    private final String I_Texture 				= "Texture_";
    private final String I_SetTextureTool 		= "TextureToolSet_";
    private final String I_SetTextureArmor     	= "TextureArmorSet_";
    private final String I_SetTextureLayer      = "TextureLayerSet_";
    private final String I_UseTextureLayer      = "UseTextureLayer_";
    private final String I_SetMaxStack 			= "SetMaxStack_";
    private final String I_FoodHealAmount 		= "FoodHealAmount_";
    private final String I_FoodWolfFavorite 	= "FoodWolfFavorite_";
    private final String I_ReturnItem 			= "SetReturnItem_";
    private final String I_ItemEnumType 		= "ItemEnumType_";
    private final String I_SetAmmo 				= "SetAmmo_";
    private final String I_SetDurability 		= "SetDurability_";
    private final String I_HarvestBlocks 		= "SetHarvestBlocks_";
    private final String I_SetBlock      		= "SetBlock_";
    private final String I_ForbiddenBlocks 		= "SetForbiddenToReplace_";
    private final String I_ArmorLevel           = "ArmorLevel_";
    private final String I_RenderIndex			= "RenderIndex_";
    private final String I_DamageReduceAmount   = "DamageReduceAmount_";
    
    
    public MixxsAPI_ItemAPI(){
    	  
    	 inputFormatException = new Properties();
    	 
    	 Item_Builder.addEnumToMaterialMap("wood", EnumToolMaterial.WOOD);
    	 Item_Builder.addEnumToMaterialMap("stone", EnumToolMaterial.STONE);
    	 Item_Builder.addEnumToMaterialMap("iron", EnumToolMaterial.IRON);
    	 Item_Builder.addEnumToMaterialMap("diamond", EnumToolMaterial.EMERALD);
    	 Item_Builder.addEnumToMaterialMap("emerald", EnumToolMaterial.EMERALD);
    	 Item_Builder.addEnumToMaterialMap("gold", EnumToolMaterial.GOLD);
    	 
    	 Item_Builder.addLayerIndexToLayerMap("cloth", 0);
    	 Item_Builder.addLayerIndexToLayerMap("chainmail", 1);
    	 Item_Builder.addLayerIndexToLayerMap("iron", 2);
    	 Item_Builder.addLayerIndexToLayerMap("diamond", 3);
    	 Item_Builder.addLayerIndexToLayerMap("emerald", 3);
    	 Item_Builder.addLayerIndexToLayerMap("gold", 4);
    	 
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
        String armorLayerList = inputFormatException.getProperty("ArmorLayerList", "");
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
        String bowList      = inputFormatException.getProperty("BowList", "");
        //Shears
        String shearList    = inputFormatException.getProperty("ShearList", "");
        
        String FSteelList   = inputFormatException.getProperty("FlintAndSteelList", "");
        String BPlacerList  = inputFormatException.getProperty("BlockPlacerList", "");
        String BReplacerList = inputFormatException.getProperty("BlockReplacerList", "");
        
        //Doors
        String doorList 	= inputFormatException.getProperty("DoorList", "");
        
        //Armor Items
        String armorSetList	= inputFormatException.getProperty("ArmorSetList", "");
        String helmetList 	= inputFormatException.getProperty("ArmorHelmetList", "");
        String chestList    = inputFormatException.getProperty("ArmorChestList", "");
        String legsList     = inputFormatException.getProperty("ArmorLegsList", "");
        String bootsList    = inputFormatException.getProperty("ArmorBootsList", "");
        
        //Advanced Option: Enum List.
        if(!materialList.isEmpty()) 	{ addMaterialToEnumMaterialsList(materialList.split(",")); 			  		   			  	}
        if(!armorLayerList.isEmpty()) 	{ addArmorLayerToList(armorLayerList.split(","));							 				}
        if(!itemList.isEmpty())     	{ addDefaultItemToGame(itemList.split(","), modItemTable, textureMap);		   			  	}
        if(!foodList.isEmpty())     	{ addFoodItemToGame(foodList.split(","), modItemTable, textureMap);   		   			  	}
        if(!equipSetList.isEmpty())		{ addEquipamentSetToGame(equipSetList.split(","), modItemTable, textureMap);  	 		  	}
        if(!swordList.isEmpty())    	{ addEquipamentToGame(swordList.split(","), "sword",modItemTable, textureMap); 			  	}
        if(!pickaxeList.isEmpty())  	{ addEquipamentToGame(pickaxeList.split(","), "pickaxe",modItemTable, textureMap);  	  	}
        if(!shovelList.isEmpty())   	{ addEquipamentToGame(shovelList.split(","), "spade",modItemTable, textureMap);    	      	}
        if(!axeList.isEmpty()) 			{ addEquipamentToGame(axeList.split(","), "axe",modItemTable, textureMap);          	  	}
        if(!hoeList.isEmpty()) 			{ addEquipamentToGame(hoeList.split(","), "hoe",modItemTable, textureMap);	        	  	}     
        if(!doorList.isEmpty()) 		{ String[] splitDoorList = doorList.split(",");								       	      	}
        if(!shearList.isEmpty())    	{ addShearsToGame(shearList.split(","), modItemTable, textureMap);						  	}
        if(!FSteelList.isEmpty())   	{ addBlockPlacerToGame(FSteelList.split(","), "flintAndSteel", modItemTable, textureMap); 	}
        if(!BPlacerList.isEmpty())  	{ addBlockPlacerToGame(BPlacerList.split(","), "blockPlacer", modItemTable, textureMap); 	}
        if(!BReplacerList.isEmpty())	{ addBlockReplacerToGame(BReplacerList.split(","), modItemTable, textureMap);               }
        
        if(!armorSetList.isEmpty())		{ addArmorSetToGame(armorSetList.split(","), modItemTable, textureMap);						}
        if(!helmetList.isEmpty()) 		{ addArmorToGame(helmetList.split(","), ItemCustomArmor.HELMET, modItemTable, textureMap);	}
        if(!chestList.isEmpty()) 		{ addArmorToGame(chestList.split(","), ItemCustomArmor.CHESTPLATE, modItemTable, textureMap);	}
        if(!legsList.isEmpty()) 		{ addArmorToGame(legsList.split(","), ItemCustomArmor.LEGGINGS, modItemTable, textureMap);	}
        if(!bootsList.isEmpty()) 		{ addArmorToGame(bootsList.split(","), ItemCustomArmor.BOOTS, modItemTable, textureMap);	}
        //Items with special conditions, such as return type, will be placed last:
        if(!soupList.isEmpty()) 		{ addSoupItemToGame(soupList.split(","), modItemTable, textureMap);        					}  
        if(!bowList.isEmpty())      	{ addBowToGame(bowList.split(","), modItemTable, textureMap);                       		}
        
        return true;
    }
    
    private void addMaterialToEnumMaterialsList(String[] splitMaterialList) {
    	if(splitMaterialList.length > 0) {
    		if(mod_MixxsAPI.additionalFunctionalityMods.get(0).equals("net.minecraft.src.overrideapi.OverrideAPI")) {
        		for(String materialName: splitMaterialList) {
        	    	MLogger.print("Item API", "Obtaining Tool Material " + materialName + ".", MLogger.NORMAL);
        			
        	    	Item_Builder.addEnumToMaterialMap(
            			materialName,
            			TryGettingValues(("MaterialSetHarverstLevel_" + materialName), 0),
            			TryGettingValues(("MaterialSetMaxUses_" + materialName), 69),
            			TryGettingValues(("MaterialSetEfficiencyOnProperMaterial_" + materialName), 2.0F),
            			TryGettingValues(("MaterialSetDamageVsEntity_" + materialName), 0)
            			);
        		}
        	}
    		else {
    			MLogger.print("Item API", "Could not find OverrideAPI. Will not be able to create custom ENUM items. ", MLogger.ERROR);
    		}
    	}
    }
    
    private void addArmorLayerToList(String[] splitArmorLayerList) {
    	for(String armorLayerName : splitArmorLayerList) {
    		Item_Builder.addLayerIndexToLayerMap(armorLayerName, ModLoader.AddArmor(armorLayerName));
    	}
    }
    
    /**
     * Adds a Item into a Item Table.
     */
    private void addDefaultItemToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		//MLogger.print("Item API", "Obtaining Default Item " + baseName + ".", MLogger.NORMAL);

            Item auxItem = Item_Builder.ItemAPIBuildDefaultItem(
            		baseName,
            		inputFormatException.getProperty(I_setName + baseName, baseName),
            		(TryGettingValues((I_setID + baseName), defaultID) + idIncrements),
            		TryGettingTexture(I_Texture + baseName),
            		TryGettingValues((I_SetMaxStack + baseName), 1),
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
 
        	Item auxItem = Item_Builder.ItemAPIBuildFoodItem(
            		baseName,
            		inputFormatException.getProperty(I_setName + baseName, baseName),
            		TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
            		TryGettingTexture(I_Texture + baseName),
            		TryGettingValues((I_SetMaxStack + baseName), 1),
            		TryGettingValues((I_FoodHealAmount + baseName), 1),
            		TryGettingFavoriteFoodWolf(I_FoodWolfFavorite + baseName),
            		textureMap
            		);	
            modItemTable.add(auxItem);
    	}
    }
    
    private void addSoupItemToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		//MLogger.print("Item API", "Obtaining Soup Item " + baseName + ".", MLogger.NORMAL);
        	
        	Item auxItem = Item_Builder.ItemAPIBuildSoupItem(
            		baseName,
            		inputFormatException.getProperty(I_setName + baseName, baseName),
            		TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
            		TryGettingTexture(I_Texture + baseName),
            		TryGettingValues((I_FoodHealAmount + baseName), 1),
            		TryGettingFavoriteFoodWolf(I_FoodWolfFavorite + baseName),
            		TryGettingReturnItem(inputFormatException.getProperty((I_ReturnItem + baseName), ""), Item.bowlEmpty), 
            		textureMap
            		);	
        	
            modItemTable.add(auxItem);
    	}
    }
    
    private void addEquipamentSetToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String, Integer> textureMap) {
    	for(String baseName : splitList) {
    		//MLogger.print("Item API", "Obtaining Set " + baseName + ".", MLogger.NORMAL);
    		//Separates set into wanted resources;
    		int reservedID = TryGettingValues((I_reservedToolSetID + baseName), defaultSetID) + idIncrements;
    		EnumToolMaterial enumTypeMaterial = TryGettingEnumMaterial(I_SetEnumType + baseName);
    		String textureSetBase = inputFormatException.getProperty(I_SetTextureTool + baseName, "");
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
    				inputFormatException.getProperty(I_setName + baseName + "Sword", baseName + "Sword"),
    				inputFormatException.getProperty(I_setName + baseName + "Axe", baseName + "Axe"),
    				inputFormatException.getProperty(I_setName + baseName + "Pickaxe", baseName + "Pickaxe"),
    				inputFormatException.getProperty(I_setName + baseName + "Hoe", baseName + "Hoe"),
    				inputFormatException.getProperty(I_setName + baseName + "Shovel", baseName + "Shovel")
    		};
    		
    		for(int x = 0; x < 5; x++) {
    			Item auxItem = Item_Builder.ItemAPIBuildEquipament(
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
    		
            Item auxItem = Item_Builder.ItemAPIBuildEquipament(
            		baseName,
            		inputFormatException.getProperty(I_setName + baseName, baseName),
            		TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
            		TryGettingTexture(I_Texture + baseName),
            		type,
            		TryGettingEnumMaterial(I_ItemEnumType + baseName),
            		textureMap
            		);	

            modItemTable.add(auxItem);
    	}
    }
  
    private void addBowToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		
            Item auxItem = Item_Builder.ItemAPIBuildBow(
            		baseName,
            		inputFormatException.getProperty(I_setName + baseName, baseName),
            		TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
            		TryGettingTexture(I_Texture + baseName),
            		TryGettingValues(I_SetDurability + baseName, 0),
            		TryGettingReturnItem(inputFormatException.getProperty((I_SetAmmo + baseName), ""), Item.arrow),
            		textureMap
            		);	

            modItemTable.add(auxItem);
    	}
    }
    
    private void addShearsToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		
    		String SetHarvestBlocks = inputFormatException.getProperty((I_HarvestBlocks + baseName), "");
    		String[] split = null;
    		if(!SetHarvestBlocks.isEmpty()) {
    			split = SetHarvestBlocks.split(",");
    		}
    		
            Item auxItem = Item_Builder.ItemAPIBuildShears(
            		baseName,
            		inputFormatException.getProperty(I_setName + baseName, baseName),
            		TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
            		TryGettingTexture(I_Texture + baseName),
            		TryGettingValues(I_SetDurability + baseName, 0),
            		TryGettingBlockIDs(baseName, split, Block.leaves.blockID),
            		textureMap
            		);	

            modItemTable.add(auxItem);
    	}
    }
    
    private void addBlockPlacerToGame(String[] splitList, String type, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	
    	for(String baseName : splitList) { 		
           
    		int BlockID;
    		String defaultSound = "fire.ignite";
    		if(type.equals("flintAndSteel")) {
    			defaultSound = "fire.ignite";
    			BlockID = GetBlockID(inputFormatException.getProperty(I_SetBlock + baseName, ""), Block.fire.blockID, false);
    		}
    		else {
    			defaultSound = "step.step";
    			BlockID = GetBlockID(inputFormatException.getProperty(I_SetBlock + baseName, ""), Block.fire.blockID, true);
    		}
    		
    		
    		
    		
    	   Item auxItem = Item_Builder.ItemAPIBuildBlockPlacer(
    			   baseName,
    			   inputFormatException.getProperty(I_setName + baseName, baseName),
    			   TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
    			   TryGettingTexture(I_Texture + baseName),
    			   TryGettingValues(I_SetDurability + baseName, 32),
    			   BlockID,//BlockID
    			   defaultSound,//inputFormatException.getProperty("SetSound" + baseName, defaultSound),//Sound
    			   textureMap
           );	
           modItemTable.add(auxItem);     	
    	}
    }
    
    private void addBlockReplacerToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	
    	for(String baseName : splitList) { 		
           
    		String SetForbiddenToReplaceBlocks = inputFormatException.getProperty((I_ForbiddenBlocks + baseName), "");
    		String[] split = null;
    		if(!SetForbiddenToReplaceBlocks.isEmpty()) {
    			split = SetForbiddenToReplaceBlocks.split(",");
    		}
    		
    	   Item auxItem = Item_Builder.ItemAPIBuildBlockReplacer(
    			   baseName,
    			   inputFormatException.getProperty(I_setName + baseName, baseName),
    			   TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
    			   TryGettingTexture(I_Texture + baseName),
    			   TryGettingValues(I_SetDurability + baseName, 32),
    			   GetBlockID(inputFormatException.getProperty(I_SetBlock + baseName, ""), Block.stone.blockID, false),//BlockID
    			   TryGettingBlockIDs(baseName, split, 0),
    			   textureMap
           );	
           modItemTable.add(auxItem);     	
    	}
    }

    private void addArmorSetToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String, Integer> textureMap) {
    	for(String baseName : splitList) {

    		//Separates set into wanted resources;
    		int reservedID = TryGettingValues((I_reservedArmorSetID + baseName), defaultSetID) + idIncrements;
    		String textureSetBase = inputFormatException.getProperty(I_SetTextureArmor + baseName, "");
    		String[] SetBaseTypes = {
    				"Helmet",
    				"Plate",
    				"Legs",
    				"Boots"
    		};
    		String[] textureSet = {
    				TryGettingTextureRedux(textureSetBase.replace("#", "Helmet"), baseName),
    				TryGettingTextureRedux(textureSetBase.replace("#", "Plate"), baseName),
    				TryGettingTextureRedux(textureSetBase.replace("#", "Legs"), baseName),
    				TryGettingTextureRedux(textureSetBase.replace("#", "Boots"), baseName),
    		};
    		String[] nameSet = {
    				inputFormatException.getProperty(I_setName + baseName + "Helmet", baseName + "Helmet"),
    				inputFormatException.getProperty(I_setName + baseName + "Plate", baseName + "Chestplate"),
    				inputFormatException.getProperty(I_setName + baseName + "Legs", baseName + "Legs"),
    				inputFormatException.getProperty(I_setName + baseName + "Boots", baseName + "Boots"),
    		};
    		int[] Durability = {
    				TryGettingValuesNoError(I_SetDurability + baseName + "Helmet", -1),
    				TryGettingValuesNoError(I_SetDurability + baseName + "Plate" ,-1),
    				TryGettingValuesNoError(I_SetDurability + baseName + "Legs" ,-1),
    				TryGettingValuesNoError(I_SetDurability + baseName + "Boots" ,-1)
    		};
    		
    		int[] DamageReduceAmount = {
    				TryGettingValuesNoError(I_DamageReduceAmount + baseName + "Helmet", -1),
    				TryGettingValuesNoError(I_DamageReduceAmount + baseName + "Plate" ,-1),
    				TryGettingValuesNoError(I_DamageReduceAmount + baseName + "Legs" ,-1),
    				TryGettingValuesNoError(I_DamageReduceAmount + baseName + "Boots" ,-1)
    		};
    		
    		int[] armorType = {
    				ItemCustomArmor.HELMET,
    				ItemCustomArmor.CHESTPLATE,
    				ItemCustomArmor.LEGGINGS,
    				ItemCustomArmor.BOOTS
    		};

    		for(int x = 0; x < 4; x++) {
    			Item auxItem = Item_Builder.ItemAPIBuildArmor(
                		baseName + SetBaseTypes[x],
                		nameSet[x], //fullName
                		reservedID + x, //ID
                		textureSet[x], //Texture
                		Durability[x], //Durability
                		TryGettingValues(I_ArmorLevel + baseName, 0), //armor level
                		TryGettingArmorRenderIndex(I_RenderIndex + baseName),
                		armorType[x],
                		DamageReduceAmount[x], //DamageReduceAmount
                		textureMap
                		);		
    			modItemTable.add(auxItem);
    		}
    	}
    }
    
    private void addArmorToGame(String[] splitList, int type, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		//MLogger.print("Item API", "Obtaining Equipament " + baseName + ".", MLogger.NORMAL);	
    		
    		inputFormatException.getProperty(I_RenderIndex + baseName, "cloth");
    		
			Item auxItem = Item_Builder.ItemAPIBuildArmor(
            		baseName,
            		inputFormatException.getProperty(I_setName + baseName, baseName), //fullName
            		TryGettingValues((I_setID + baseName), defaultID) + idIncrements, //ID
            		TryGettingTexture(I_Texture + baseName), //Texture
            		TryGettingValuesNoError(I_SetDurability + baseName, -1), //Durability
            		TryGettingValues(I_ArmorLevel, 0), //armor level
            		TryGettingArmorRenderIndex(I_RenderIndex + baseName),
            		type,
            		TryGettingValuesNoError(I_DamageReduceAmount + baseName ,-1), //DamageReduceAmount
            		textureMap
            		);		

            modItemTable.add(auxItem);
    	}
    	/*
    	 *          Item auxItem = Item_Builder.ItemAPIBuildEquipament(
            		baseName,
            		inputFormatException.getProperty(I_setName + baseName, baseName),
            		TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
            		TryGettingTexture(I_Texture + baseName),
            		type,
            		TryGettingEnumMaterial(I_ItemEnumType + baseName),
            		textureMap
            		);	

            modItemTable.add(auxItem);
    	 */
    }
    
    
    
    //Tries.
    private int TryGettingValues(String valueProperty, int valueDef){
        int value;
        try{
        	value = Integer.valueOf(inputFormatException.getProperty(valueProperty)).intValue();
        	if(value < 0) {	throw new Exception(); }
        }
        catch(Exception numberFormatException5){
        	MLogger.print("Item API", "Invalid value for: " + valueProperty + ". Using default value ["+valueDef+"].", MLogger.ERROR);
            return valueDef;
        }    
        
        return value;
    }
    
    private int TryGettingValuesNoError(String valueProperty, int valueDef) {
    	int value;
        try{
        	value = Integer.valueOf(inputFormatException.getProperty(valueProperty)).intValue();
        	if(value < 0) {	throw new Exception(); }
        }
        catch(Exception numberFormatException5){
            return valueDef;
        }    
        
        return value;
    }
    
    
    private float TryGettingValues(String valueProperty, float valueDef){
        float value;
        try{
        	value = Float.valueOf(inputFormatException.getProperty(valueProperty)).floatValue();
        	if(value < 0) {	throw new Exception(); }
        }
        catch(Exception numberFormatException5){
        	MLogger.print("Item API", "Invalid value for: " + valueProperty + ". Using default value ["+valueDef+"].", MLogger.ERROR);
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
    	if(Item_Builder.materialList.containsKey(aux)){
    		return Item_Builder.materialList.get(aux);
    	}
    	MLogger.print("Item API", "Invalid MaterialType for: " + materialProperty + ". Using default value [STONE].", MLogger.ERROR);
    	return EnumToolMaterial.STONE;
    }
    
    private int TryGettingArmorRenderIndex(String renderIndex) {
    	String aux = inputFormatException.getProperty(renderIndex, "N/A").toLowerCase();   
    	if(Item_Builder.armorLayerList.containsKey(aux)) {
    		return Item_Builder.armorLayerList.get(aux);
    	}
    	MLogger.print("Item API", "Invalid ArmorLayer for: " + renderIndex + ". Using default value [CHAINMAIL].", MLogger.ERROR);
    	return 1;
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
    
    //Send to BlockAPI
    
    private ArrayList<Integer> TryGettingBlockIDs(String itemName, String[] blockNames, int defaultItem) {
    	ArrayList<Integer> blockIDs = new ArrayList<>();
    	ArrayList<String> auxNames = new ArrayList<>();
    	if(blockNames == null) {
    		ArrayList<Integer> defaultBlockIDs = new ArrayList<>();
        	defaultBlockIDs.add(defaultItem);
    		return defaultBlockIDs;
    	}
    	for(String blockName : blockNames) {
    		if(!blockName.toLowerCase().contains("tile.")) {
    			auxNames.add("tile." + blockName.toLowerCase());
    		}
    		else if(!blockName.toLowerCase().contains("item.")){
    			auxNames.add(blockName.toLowerCase());
    		}
    	}
    	
    	if(auxNames.size() == 0) {
    		ArrayList<Integer> defaultBlockIDs = new ArrayList<>();
        	defaultBlockIDs.add(defaultItem);
        	MLogger.print("Item API", "No valid block IDs set for "+ itemName +". Using default value ["+ defaultItem +"].", MLogger.ERROR);
        	return defaultBlockIDs;
    	}
    	
    	for(int x = 1; x < Item.itemsList.length; x++) {
    		for(int y = 0; y < auxNames.size(); y++) {
        		//System.err.println(auxNames.get(y) + "|" + Item.itemsList[x].getItemName());
    			if(Item.itemsList[x] != null && Item.itemsList[x].getItemName() != null && Item.itemsList[x].getItemName().equals(auxNames.get(y))){
        			blockIDs.add(x);
        			break;
        		}
    		}	
    	}
    	//System.err.println(blockIDs);
    	return blockIDs;
    }
    
    private int GetBlockID(String blockName, int defaultBlock, boolean ShouldError) {
    	
    	if(!blockName.contains("tile.")) {
    		blockName = "tile." + blockName;
    	}
    	
    	//Convert to common nomeclature
    	if(blockName.equals("tile.cobblestone")) {
    		blockName = "tile.stonebrick";
    	}
    	
    	for(Block b : Block.blocksList) {	
    		if(b != null && b.getBlockName() != null && b.getBlockName().equals(blockName)) {
    			return b.blockID;
    		}
    	}
    	if(ShouldError) {
    		MLogger.print("[Block API]", "Could not find block + " + blockName + " using default block: [" + defaultBlock + "].", MLogger.ERROR);
    	}
    	return defaultBlock;
    }
    
}
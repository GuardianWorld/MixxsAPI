package MixxsAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_MixxsAPI;
import MixxsAPI.Items.*;

public class MixxsAPI_ItemAPI {
	//public static Item monsterEgg = (new ItemEntityEgg(9000)).setItemName("item.entity.egg.name");
	//public static Item bow = (new CustomItemBow(555, Item.itemsList[1], 384)).setIconCoord(5, 1); //item Name, but can work for blocks as well.
	//public static Item customDye = (new CustomItemDye(9001)).setItemName("Custom Dye");

    protected static int defaultID = 3200;
    private static int defaultSetID = 6600;
    protected static int idIncrements = 0;
    public ArrayList<ItemStack> customItems;
    
    static Properties inputFormatException;
     
    private final String I_setName 				= "SetName_";
    private final String I_setID 				= "idItem_";
    private final String I_reservedToolSetID 	= "idReservedToolSet_";
    private final String I_reservedArmorSetID 	= "idReservedArmorSet_";
    private final String I_SetEnumType 			= "SetEnumType_";
    private final String I_Texture 				= "Texture_";
    private final String I_SetTextureTool 		= "TextureToolSet_";
    private final String I_SetTextureArmor     	= "TextureArmorSet_";
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

    Auxiliary_Calls a_calls;

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
    	MLogger.print("Item API", "called.", MLogger.ErrorType.NORMAL);
    	
    	//HashMap to allow the same texturePath to be used by multiple Items.
    	if(textureMap == null) {
    		textureMap = new HashMap<>();
    	}

        try{
            File file = new File(mod_MixxsAPI.configpath + modConfigPath);
            inputFormatException.load(new InputStreamReader(new FileInputStream(file)));
        } catch(IOException fileNotFoundException3){
        	MLogger.print("Item API", "Invalid File, ItemAPI is now quitting.", MLogger.ErrorType.ERROR);
            return false;
        }
        
        a_calls = new Auxiliary_Calls(inputFormatException);
        
        MLogger.print("ItemAPI", "making ItemList and Building Items...", MLogger.ErrorType.NORMAL);
        
        //## Get Properties.
        //Materials
        String materialList = inputFormatException.getProperty("MaterialList", "");
        String armorLayerList = inputFormatException.getProperty("ArmorLayerList", "");
        //Normal Items
        String itemList     = inputFormatException.getProperty("ItemList", "");
        //Food Items
        String foodList     = inputFormatException.getProperty("FoodList", "");
        String soupList     = inputFormatException.getProperty("SoupList", "");
       
        //Equipment Items
        String equipSetList = inputFormatException.getProperty("FullEquipmentSetList", ""); //Equipment Set
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
        if(!materialList.isEmpty()) 	addMaterialToEnumMaterialsList(materialList.split(","));
        if(!armorLayerList.isEmpty()) 	addArmorLayerToList(armorLayerList.split(","));
        if(!itemList.isEmpty())     	addDefaultItemToGame(itemList.split(","), modItemTable, textureMap);
        if(!foodList.isEmpty())     	addFoodItemToGame(foodList.split(","), modItemTable, textureMap);
        if(!equipSetList.isEmpty())		addEquipmentSetToGame(equipSetList.split(","), modItemTable, textureMap);
        if(!swordList.isEmpty())    	addEquipmentToGame(swordList.split(","), "sword",modItemTable, textureMap);
        if(!pickaxeList.isEmpty())  	addEquipmentToGame(pickaxeList.split(","), "pickaxe",modItemTable, textureMap);
        if(!shovelList.isEmpty())   	addEquipmentToGame(shovelList.split(","), "spade",modItemTable, textureMap);
        if(!axeList.isEmpty()) 			addEquipmentToGame(axeList.split(","), "axe",modItemTable, textureMap);
        if(!hoeList.isEmpty()) 			addEquipmentToGame(hoeList.split(","), "hoe",modItemTable, textureMap);
        if(!doorList.isEmpty()) 		{ String[] splitDoorList = doorList.split(","); }
        if(!shearList.isEmpty())    	addShearsToGame(shearList.split(","), modItemTable, textureMap);
        if(!FSteelList.isEmpty())   	addBlockPlacerToGame(FSteelList.split(","), "flintAndSteel", modItemTable, textureMap);
        if(!BPlacerList.isEmpty())  	addBlockPlacerToGame(BPlacerList.split(","), "blockPlacer", modItemTable, textureMap);
        if(!BReplacerList.isEmpty())	addBlockReplacerToGame(BReplacerList.split(","), modItemTable, textureMap);
        
        if(!armorSetList.isEmpty())		addArmorSetToGame(armorSetList.split(","), modItemTable, textureMap);
        if(!helmetList.isEmpty()) 		addArmorToGame(helmetList.split(","), ItemCustomArmor.HELMET, modItemTable, textureMap);
        if(!chestList.isEmpty()) 		addArmorToGame(chestList.split(","), ItemCustomArmor.CHESTPLATE, modItemTable, textureMap);
        if(!legsList.isEmpty()) 		addArmorToGame(legsList.split(","), ItemCustomArmor.LEGGINGS, modItemTable, textureMap);
        if(!bootsList.isEmpty()) 		addArmorToGame(bootsList.split(","), ItemCustomArmor.BOOTS, modItemTable, textureMap);
        //Items with special conditions, such as return type, will be placed last:
        if(!soupList.isEmpty()) 		addSoupItemToGame(soupList.split(","), modItemTable, textureMap);
        if(!bowList.isEmpty())      	addBowToGame(bowList.split(","), modItemTable, textureMap);
        
        return true;
    }
    
    private void addMaterialToEnumMaterialsList(String[] splitMaterialList) {
    	if(splitMaterialList.length > 0) {
    		if(!mod_MixxsAPI.additionalFunctionalityMods.get(0).equals("NotFound")) {
        		for(String materialName: splitMaterialList) {
        	    	MLogger.print("Item API", "Obtaining Tool Material " + materialName + ".", MLogger.ErrorType.NORMAL);
        			
        	    	Item_Builder.addEnumToMaterialMap(
            			materialName,
            			a_calls.TryGettingValues(("MaterialSetHarvestLevel_" + materialName), 0),
            			a_calls.TryGettingValues(("MaterialSetMaxUses_" + materialName), 69),
            			a_calls.TryGettingValues(("MaterialSetEfficiencyOnProperMaterial_" + materialName), 2.0F),
            			a_calls.TryGettingValues(("MaterialSetDamageVsEntity_" + materialName), 0)
            			);
        		}
        	}
    		else {
    			MLogger.print("Item API", "Could not find OverrideAPI. Will not be able to create custom ENUM items. ", MLogger.ErrorType.ERROR);
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
    		//MLogger.ErrorType.print("Item API", "Obtaining Default Item " + baseName + ".", MLogger.ErrorType.NORMAL);

            Item auxItem = Item_Builder.ItemAPIBuildDefaultItem(
            		baseName,
            		inputFormatException.getProperty(I_setName + baseName, baseName),
            		(a_calls.TryGettingValues((I_setID + baseName), defaultID) + idIncrements),
            		a_calls.TryGettingTexture(I_Texture + baseName),
            		a_calls.TryGettingValues((I_SetMaxStack + baseName), 1),
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
    		//MLogger.ErrorType.print("Item API", "Obtaining Food Item " + baseName + ".", MLogger.ErrorType.NORMAL);
 
        	Item auxItem = Item_Builder.ItemAPIBuildFoodItem(
            		baseName,
            		inputFormatException.getProperty(I_setName + baseName, baseName),
            		a_calls.TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
            		a_calls.TryGettingTexture(I_Texture + baseName),
            		a_calls.TryGettingValues((I_SetMaxStack + baseName), 1),
            		a_calls.TryGettingValues((I_FoodHealAmount + baseName), 1),
            		a_calls.TryGettingValuesNoError(I_FoodWolfFavorite + baseName),
            		textureMap
            		);

            modItemTable.add(auxItem);
    	}
    }
    
    private void addSoupItemToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		//MLogger.ErrorType.print("Item API", "Obtaining Soup Item " + baseName + ".", MLogger.ErrorType.NORMAL);
        	
        	Item auxItem = Item_Builder.ItemAPIBuildSoupItem(
            		baseName,
            		inputFormatException.getProperty(I_setName + baseName, baseName),
            		a_calls.TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
            		a_calls.TryGettingTexture(I_Texture + baseName),
            		a_calls.TryGettingValues((I_FoodHealAmount + baseName), 1),
            		a_calls.TryGettingValuesNoError(I_FoodWolfFavorite + baseName),
            		Item_Searcher.TryGettingItem(inputFormatException.getProperty((I_ReturnItem + baseName), ""), Item.bowlEmpty), 
            		textureMap
            		);	
        	
            modItemTable.add(auxItem);
    	}
    }
    
    private void addEquipmentSetToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String, Integer> textureMap) {
    	for(String baseName : splitList) {
    		//MLogger.ErrorType.print("Item API", "Obtaining Set " + baseName + ".", MLogger.ErrorType.NORMAL);
    		//Separates set into wanted resources;
    		
    		int reservedID = a_calls.TryGettingValues((I_reservedToolSetID + baseName), defaultSetID) + idIncrements;
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
    				a_calls.TryGettingTextureRedux(textureSetBase.replace("#", "Sword"), baseName),
    				a_calls.TryGettingTextureRedux(textureSetBase.replace("#", "Axe"), baseName),
    				a_calls.TryGettingTextureRedux(textureSetBase.replace("#", "Pickaxe"), baseName),
    				a_calls.TryGettingTextureRedux(textureSetBase.replace("#", "Hoe"), baseName),
    				a_calls.TryGettingTextureRedux(textureSetBase.replace("#", "Shovel"), baseName)
    		};
    		String[] nameSet = {
    				inputFormatException.getProperty(I_setName + baseName + "Sword", baseName + "Sword"),
    				inputFormatException.getProperty(I_setName + baseName + "Axe", baseName + "Axe"),
    				inputFormatException.getProperty(I_setName + baseName + "Pickaxe", baseName + "Pickaxe"),
    				inputFormatException.getProperty(I_setName + baseName + "Hoe", baseName + "Hoe"),
    				inputFormatException.getProperty(I_setName + baseName + "Shovel", baseName + "Shovel")
    		};
    		
    		for(int x = 0; x < 5; x++) {
    			Item auxItem = Item_Builder.ItemAPIBuildEquipment(
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
    
    private void addEquipmentToGame(String[] splitList, String type, ArrayList<Item> modItemTable, Map<String,Integer> textureMap) {
    	for(String baseName : splitList) {
    		//MLogger.ErrorType.print("Item API", "Obtaining Equipment " + baseName + ".", MLogger.ErrorType.NORMAL);
    		
            Item auxItem = Item_Builder.ItemAPIBuildEquipment(
            		baseName,
            		inputFormatException.getProperty(I_setName + baseName, baseName),
            		a_calls.TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
            		a_calls.TryGettingTexture(I_Texture + baseName),
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
            		a_calls.TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
            		a_calls.TryGettingTexture(I_Texture + baseName),
            		a_calls.TryGettingValues(I_SetDurability + baseName, 0),
            		Item_Searcher.TryGettingItem(inputFormatException.getProperty((I_SetAmmo + baseName), ""), Item.arrow),
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
            		a_calls.TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
            		a_calls.TryGettingTexture(I_Texture + baseName),
            		a_calls.TryGettingValues(I_SetDurability + baseName, 0),
            		Block_Searcher.TryGettingBlockIDs(baseName, split, Block.leaves.blockID),
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
    			BlockID = Block_Searcher.GetBlockID(inputFormatException.getProperty(I_SetBlock + baseName, ""), Block.fire.blockID, false);
    		} else {
    			defaultSound = "step.step";
    			BlockID = Block_Searcher.GetBlockID(inputFormatException.getProperty(I_SetBlock + baseName, ""), Block.fire.blockID, true);
    		}
    		
    	   Item auxItem = Item_Builder.ItemAPIBuildBlockPlacer(
    			   baseName,
    			   inputFormatException.getProperty(I_setName + baseName, baseName),
    			   a_calls.TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
    			   a_calls.TryGettingTexture(I_Texture + baseName),
    			   a_calls.TryGettingValues(I_SetDurability + baseName, 32),
    			   BlockID, //BlockID
    			   defaultSound, //inputFormatException.getProperty("SetSound" + baseName, defaultSound),//Sound
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
    			   a_calls.TryGettingValues((I_setID + baseName), defaultID) + idIncrements,
    			   a_calls.TryGettingTexture(I_Texture + baseName),
    			   a_calls.TryGettingValues(I_SetDurability + baseName, 32),
    			   Block_Searcher.GetBlockID(inputFormatException.getProperty(I_SetBlock + baseName, ""), Block.stone.blockID, false),//BlockID
    			   Block_Searcher.TryGettingBlockIDs(baseName, split, 0),
    			   textureMap
           );	
           modItemTable.add(auxItem);     	
    	}
    }

    private void addArmorSetToGame(String[] splitList, ArrayList<Item> modItemTable, Map<String, Integer> textureMap) {
    	for(String baseName : splitList) {
    		//Separates set into wanted resources;
    		int reservedID = a_calls.TryGettingValues((I_reservedArmorSetID + baseName), defaultSetID) + idIncrements;
    		String textureSetBase = inputFormatException.getProperty(I_SetTextureArmor + baseName, "");
    		String[] SetBaseTypes = {
    				"Helmet",
    				"Plate",
    				"Legs",
    				"Boots"
    		};
    		String[] textureSet = {
    				a_calls.TryGettingTextureRedux(textureSetBase.replace("#", "Helmet"), baseName),
    				a_calls.TryGettingTextureRedux(textureSetBase.replace("#", "Plate"), baseName),
    				a_calls.TryGettingTextureRedux(textureSetBase.replace("#", "Legs"), baseName),
    				a_calls.TryGettingTextureRedux(textureSetBase.replace("#", "Boots"), baseName),
    		};
    		String[] nameSet = {
    				inputFormatException.getProperty(I_setName + baseName + "Helmet", baseName + "Helmet"),
    				inputFormatException.getProperty(I_setName + baseName + "Plate", baseName + "Chestplate"),
    				inputFormatException.getProperty(I_setName + baseName + "Legs", baseName + "Legs"),
    				inputFormatException.getProperty(I_setName + baseName + "Boots", baseName + "Boots"),
    		};
    		int[] Durability = {
    				a_calls.TryGettingValuesNoError(I_SetDurability + baseName + "Helmet", -1),
    				a_calls.TryGettingValuesNoError(I_SetDurability + baseName + "Plate" ,-1),
    				a_calls.TryGettingValuesNoError(I_SetDurability + baseName + "Legs" ,-1),
    				a_calls.TryGettingValuesNoError(I_SetDurability + baseName + "Boots" ,-1)
    		};
    		
    		int[] DamageReduceAmount = {
    				a_calls.TryGettingValuesNoError(I_DamageReduceAmount + baseName + "Helmet", -1),
    				a_calls.TryGettingValuesNoError(I_DamageReduceAmount + baseName + "Plate" ,-1),
    				a_calls.TryGettingValuesNoError(I_DamageReduceAmount + baseName + "Legs" ,-1),
    				a_calls.TryGettingValuesNoError(I_DamageReduceAmount + baseName + "Boots" ,-1)
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
                		a_calls.TryGettingValues(I_ArmorLevel + baseName, 0), //armor level
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
    		//MLogger.ErrorType.print("Item API", "Obtaining Equipment " + baseName + ".", MLogger.ErrorType.NORMAL);
    		
    		inputFormatException.getProperty(I_RenderIndex + baseName, "cloth");
    		
			Item auxItem = Item_Builder.ItemAPIBuildArmor(
            		baseName,
            		inputFormatException.getProperty(I_setName + baseName, baseName), //fullName
            		a_calls.TryGettingValues((I_setID + baseName), defaultID) + idIncrements, //ID
            		a_calls.TryGettingTexture(I_Texture + baseName), //Texture
            		a_calls.TryGettingValuesNoError(I_SetDurability + baseName, -1), //Durability
            		a_calls.TryGettingValues(I_ArmorLevel, 0), //armor level
            		TryGettingArmorRenderIndex(I_RenderIndex + baseName),
            		type,
            		a_calls.TryGettingValuesNoError(I_DamageReduceAmount + baseName ,-1), //DamageReduceAmount
            		textureMap
            		);		

            modItemTable.add(auxItem);
    	}
    	/*
    	 *          Item auxItem = Item_Builder.ItemAPIBuildEquipment(
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
    
    private EnumToolMaterial TryGettingEnumMaterial(String materialProperty) {
    	String aux = inputFormatException.getProperty(materialProperty, "N/A").toLowerCase();   	
    	if(Item_Builder.materialList.containsKey(aux)){
    		return Item_Builder.materialList.get(aux);
    	}
    	MLogger.print("Item API", "Invalid MaterialType for: " + materialProperty + ". Using default value [STONE].", MLogger.ErrorType.ERROR);
    	return EnumToolMaterial.STONE;
    }
    
    private int TryGettingArmorRenderIndex(String renderIndex) {
    	String aux = inputFormatException.getProperty(renderIndex, "N/A").toLowerCase();   
    	if(Item_Builder.armorLayerList.containsKey(aux)) {
    		return Item_Builder.armorLayerList.get(aux);
    	}
    	MLogger.print("Item API", "Invalid ArmorLayer for: " + renderIndex + ". Using default value [CHAINMAIL].", MLogger.ErrorType.ERROR);
    	return 1;
    }

    
    //Send to BlockAPI
    
}
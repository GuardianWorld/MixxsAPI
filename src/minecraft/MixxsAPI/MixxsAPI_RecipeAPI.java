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
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_MixxsAPI;


public class MixxsAPI_RecipeAPI {
	
    
    static Properties inputFormatException;

    private final String I_EquipamentSetCraftingMaterial	= "EquipmentSetCraftingMaterial_";
    private final String I_EquipamentSetSecondaryMaterial   = "EquipamentSetSecondaryMaterial_";
    
    private final String I_SetCraftingMaterial1             = "SetFirstCraftingMaterial_";
    private final String I_SetCraftingMaterial2             = "SetSecondCraftingMaterial_";
    
    private final String I_SetCraftingMaterials				= "SetCraftingMaterials_";  
    
    private final String I_SetRecipe					   = "SetRecipe_";
    private final String I_SetCraftingMaterial             = "SetCraftingMaterial_";
    private final String I_SetAmountCrafted				   = "SetAmountCrafted_";
    private final String I_SetItemDamage				   = "SetItemDamage_";
    private final String I_SetMaterialDamage			   = "SetMaterialDamage_";
    
    private final String I_ArmorSetCraftingMaterial         = "ArmorSetCraftingMaterial_";
    
    private final String I_SetSmeltingMaterial				= "SetSmeltingMaterial_";
    
    
    Auxiliary_Calls a_calls;
    
    public MixxsAPI_RecipeAPI(){
   	 inputFormatException = new Properties();
	}
    
    public boolean RecipeAPICalls(String modConfigPath){
    	MLogger.print("Recipe API", "called.", MLogger.ErrorType.NORMAL);
    	
    	try{
            File file = new File(mod_MixxsAPI.configpath + modConfigPath);
            inputFormatException.load(new InputStreamReader(new FileInputStream(file)));
        } catch(IOException fileNotFoundException3){
        	MLogger.print("Recipe API", "Invalid File, Recipe API is now quitting.", MLogger.ErrorType.ERROR);
            return false;
        }
    	a_calls = new Auxiliary_Calls(inputFormatException);
    	 //## Get Properties.
    	
    	String defaultRecipes 	= inputFormatException.getProperty("DefaultRecipes", "");
    	String shapelessRecipes = inputFormatException.getProperty("ShapelessRecipes", "");
    	
    	//Pre-made recipes
    	String equipmentSetRecipeList = inputFormatException.getProperty("EquipmentSetRecipeList", "");
    	String swordRecipeList = inputFormatException.getProperty("SwordRecipeList", "");
    	String pickaxeRecipeList = inputFormatException.getProperty("PickaxeRecipeList", "");
    	String shovelRecipeList = inputFormatException.getProperty("ShovelRecipeList", "");
    	String axeRecipeList = inputFormatException.getProperty("AxeRecipeList", "");
    	String hoeRecipeList = inputFormatException.getProperty("HoeRecipeList", "");
    	
    	String armorSetRecipeList = inputFormatException.getProperty("ArmorSetRecipeList", "");
    	String helmetRecipeList = inputFormatException.getProperty("HelmetRecipeList", "");
    	String chestRecipeList = inputFormatException.getProperty("ChestRecipeList", "");
    	String legsRecipeList = inputFormatException.getProperty("LegsRecipeList", "");
    	String bootsRecipeList = inputFormatException.getProperty("BootsRecipeList", "");
    	
    	String furnaceRecipeList = inputFormatException.getProperty("FurnaceRecipes", "");
    	
    	if(!defaultRecipes.isEmpty()) 			addDefaultRecipe(defaultRecipes.split(","));
    	if(!shapelessRecipes.isEmpty()) 		addShapelessRecipe(shapelessRecipes.split(","));
    	
    	if(!equipmentSetRecipeList.isEmpty()) 	addEquipmentSetRecipe(equipmentSetRecipeList.split(","));
    	if(!swordRecipeList.isEmpty()) 			addSwordRecipe(swordRecipeList.split(","));
    	if(!pickaxeRecipeList.isEmpty()) 		addPickaxeRecipe(pickaxeRecipeList.split(","));
    	if(!shovelRecipeList.isEmpty()) 		addShovelRecipe(shovelRecipeList.split(","));
    	if(!axeRecipeList.isEmpty()) 			addAxeRecipe(axeRecipeList.split(","));
    	if(!hoeRecipeList.isEmpty()) 			addHoeRecipe(hoeRecipeList.split(","));
    	
    	if(!armorSetRecipeList.isEmpty()) 		addArmorSetRecipe(armorSetRecipeList.split(","));
    	if(!helmetRecipeList.isEmpty()) 		addHelmetRecipe(helmetRecipeList.split(","));
    	if(!chestRecipeList.isEmpty()) 			addChestplateRecipe(chestRecipeList.split(","));
    	if(!legsRecipeList.isEmpty()) 			addLeggingsRecipe(legsRecipeList.split(","));
    	if(!bootsRecipeList.isEmpty()) 			addBootsRecipe(bootsRecipeList.split(","));
    	
    	if(!furnaceRecipeList.isEmpty())		addFurnaceRecipe(furnaceRecipeList.split(","));
    	
    	return true;
    }
    
    @SuppressWarnings("unused")
	private void addDefaultRecipe(String[] splitList) {
		outerloop:
    	for(String itemName : splitList) {
    		Item aux = Item_Searcher.TryGettingItem(itemName, null);
    		if(aux != null) {
    			String[] craftingGrid = inputFormatException.getProperty(I_SetRecipe + itemName, "").replaceAll("\"", "").split(",");
    			char[] lettersInRecipe = Recipe_Builder.lettersInRecipe(craftingGrid);
    			
    			Map<Character, ItemStack> connectionMap = new HashMap<Character, ItemStack>();
    			
    			int recipeDamage = a_calls.TryGettingValuesNoError(I_SetItemDamage + itemName, 0);
    			
    			for(char c : lettersInRecipe) {
    				String materialstring = inputFormatException.getProperty(I_SetCraftingMaterial + itemName + "_" + c, "");
    				if(!materialstring.isEmpty()) {
    					
    					Item auxMaterial = Item_Searcher.TryGettingItem(materialstring);
    					int materialDamage = a_calls.TryGettingValuesNoError(I_SetMaterialDamage + itemName + "_" + c, 0);
    					
    					if(auxMaterial != null) {
    						Recipe_Builder.makeConnectionMap(c, auxMaterial, materialDamage, connectionMap);
    					}
    					else {
    						MLogger.print("RecipeAPI", "Invalid auxMaterial" + materialstring + " On recipe "+ itemName, MLogger.ErrorType.ERROR);
    						continue outerloop;
    					}

    				}    				
    			}
    			
    			int craftedAmount = a_calls.TryGettingValuesNoError(I_SetAmountCrafted + itemName, 1);
    			
    			Recipe_Builder.buildNormalRecipe(aux, craftedAmount, recipeDamage, craftingGrid, lettersInRecipe, connectionMap);
    			
    			//public static void buildNormalRecipe(Item toBeCrafted, int amount, int itemDamage, String[] recipeFormat, char[] characterMap,
    			//Map<Character, ItemStack> connectionMap, boolean isShapeless)
    			//I_SetCraftingMaterial
    		}
    	}
    }
    
    private void addShapelessRecipe(String[] splitList) {
    	for(String itemName : splitList) {
    		Item aux = Item_Searcher.TryGettingItem(itemName, null);
    		if(aux != null) {
    			String[] craftingMaterials = inputFormatException.getProperty(I_SetCraftingMaterials + itemName, "").split(",");
    			
    			int recipeDamage = a_calls.TryGettingValuesNoError(I_SetItemDamage + itemName, 0);
    			int craftedAmount = a_calls.TryGettingValuesNoError(I_SetAmountCrafted + itemName, 1);
    		
    			ArrayList<Item> materials = new ArrayList<>();
    		
    		
    			for(String s : craftingMaterials) {
    				Item aux2 = Item_Searcher.TryGettingItem(s);
    				if(aux2 != null) {
    					materials.add(aux2);
    				}
    			}
    		
    			ItemStack[] itemArray = new ItemStack[materials.size()];
    		
    			for(int x = 0; x < materials.size(); x++) {	
    				itemArray[x] = new ItemStack(materials.get(x), 1, a_calls.TryGettingValuesNoError(I_SetMaterialDamage + itemName + "_" + x, 0));
    			}
    		
    			Recipe_Builder.buildShapelessRecipe(aux, craftedAmount, recipeDamage, itemArray);
    		}
    	}
    }
    
    private void addFurnaceRecipe(String[] splitList) {
    	for(String itemName : splitList) {
    		Item aux = Item_Searcher.TryGettingItem(itemName, null);
    		if(aux != null) {
    			String smeltingMaterials = inputFormatException.getProperty(I_SetSmeltingMaterial + itemName, null);
    			if(smeltingMaterials == null) {
    				continue;
    			}
    			
    			int aux2 = Item_Searcher.TryGettingItemID(smeltingMaterials);
    			if(aux2 == 0) {
    				continue;
    			}

    			int recipeDamage = a_calls.TryGettingValuesNoError(I_SetItemDamage + itemName, 0);
    			int craftedAmount = a_calls.TryGettingValuesNoError(I_SetAmountCrafted + itemName, 1);
    			
    			Recipe_Builder.buildFurnaceRecipe(aux2, aux, craftedAmount, recipeDamage);  		
    			
    		}
    	}
    }
    
    private void addEquipmentSetRecipe(String[] splitList) {
    	for(String itemName : splitList) {
    		String material = inputFormatException.getProperty(I_EquipamentSetCraftingMaterial + itemName, "");
    		String supportMaterial = inputFormatException.getProperty(I_EquipamentSetSecondaryMaterial + itemName, "item.stick");
    		
    		if(material.isEmpty()) {
    			MLogger.print("Recipe API", "Invalid Material for equipament Set ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}	
    		
    		Item primaryMaterial = Item_Searcher.TryGettingItem(material);
    		Item secondaryMaterial = Item_Searcher.TryGettingItem(supportMaterial);
    		
    		if(primaryMaterial == null) {
    			MLogger.print("Recipe API", "Item material not found for ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}		
    		
    		addSwordRecipe(itemName + "Sword", primaryMaterial, secondaryMaterial);
    		addPickaxeRecipe(itemName + "Pickaxe", primaryMaterial, secondaryMaterial);
    		addShovelRecipe(itemName + "Shovel", primaryMaterial, secondaryMaterial);
    		addAxeRecipe(itemName + "Axe", primaryMaterial, secondaryMaterial);
    		addHoeRecipe(itemName + "Hoe", primaryMaterial, secondaryMaterial);	
    	}
    }
    
    private void addSwordRecipe(String[] splitList) {
    	for(String itemName : splitList) {
    		String material = inputFormatException.getProperty(I_SetCraftingMaterial1 + itemName, "");
    		String supportMaterial = inputFormatException.getProperty(I_SetCraftingMaterial2 + itemName, "item.stick");
    		
    		if(material.isEmpty()) {
    			MLogger.print("Recipe API", "Invalid Material for equipament Set ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}	
    		
    		Item primaryMaterial = Item_Searcher.TryGettingItem(material);
    		Item secondaryMaterial = Item_Searcher.TryGettingItem(supportMaterial);
    		
    		if(primaryMaterial == null) {
    			MLogger.print("Recipe API", "Item material not found for ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}		
    		
    		addSwordRecipe(itemName, primaryMaterial, secondaryMaterial);
    	}
    }
    
    private void addSwordRecipe(String itemName, Item material, Item secondaryMaterial) {
    	
    	Item wantedItem = Item_Searcher.TryGettingItem(itemName);
    	if(wantedItem == null) {
			MLogger.print("Recipe API", "Item not found for ["+ itemName +"], Skipping", MLogger.ErrorType.ERROR);
			return;
		}	
    	String[] recipeFormat = {"x", "x", "y"};
    	char[] characterMap = {'x', 'y'};
    	Map<Character,ItemStack> connectionMap = new HashMap<Character, ItemStack>();
    	connectionMap.put(characterMap[0], new ItemStack(material, 1, 0));
    	connectionMap.put(characterMap[1], new ItemStack(secondaryMaterial, 1, 0));
    	Recipe_Builder.buildNormalRecipe(wantedItem, 1, 0, recipeFormat, characterMap, connectionMap);
    }
    
    private void addPickaxeRecipe(String[] splitList) {
    	for(String itemName : splitList) {
    		String material = inputFormatException.getProperty(I_SetCraftingMaterial1 + itemName, "");
    		String supportMaterial = inputFormatException.getProperty(I_SetCraftingMaterial2 + itemName, "item.stick");
    		
    		if(material.isEmpty()) {
    			MLogger.print("Recipe API", "Invalid Material for equipament Set ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}	
    		
    		Item primaryMaterial = Item_Searcher.TryGettingItem(material);
    		Item secondaryMaterial = Item_Searcher.TryGettingItem(supportMaterial);
    		
    		if(primaryMaterial == null) {
    			MLogger.print("Recipe API", "Item material not found for ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}		
    		
    		addPickaxeRecipe(itemName, primaryMaterial, secondaryMaterial);
    	}
    }
    
    private void addPickaxeRecipe(String itemName, Item material, Item secondaryMaterial) {
    	Item wantedItem = Item_Searcher.TryGettingItem(itemName);
    	if(wantedItem == null) {
			MLogger.print("Recipe API", "Item not found for ["+ itemName +"], Skipping", MLogger.ErrorType.ERROR);
			return;
		}	
    	String[] recipeFormat = {"xxx", " y ", " y "};
    	char[] characterMap = {'x', 'y'};
    	Map<Character,ItemStack> connectionMap = new HashMap<Character, ItemStack>();
    	connectionMap.put(characterMap[0], new ItemStack(material, 1, 0));
    	connectionMap.put(characterMap[1], new ItemStack(secondaryMaterial, 1, 0));
    	Recipe_Builder.buildNormalRecipe(wantedItem, 1, 0, recipeFormat, characterMap, connectionMap);
    }
    
    private void addShovelRecipe(String[] splitList) {
    	for(String itemName : splitList) {
    		String material = inputFormatException.getProperty(I_SetCraftingMaterial1 + itemName, "");
    		String supportMaterial = inputFormatException.getProperty(I_SetCraftingMaterial2 + itemName, "item.stick");
    		
    		if(material.isEmpty()) {
    			MLogger.print("Recipe API", "Invalid Material for equipament Set ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}	
    		
    		Item primaryMaterial = Item_Searcher.TryGettingItem(material);
    		Item secondaryMaterial = Item_Searcher.TryGettingItem(supportMaterial);
    		
    		if(primaryMaterial == null) {
    			MLogger.print("Recipe API", "Item material not found for ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}		
    		
    		addShovelRecipe(itemName, primaryMaterial, secondaryMaterial);
    	}
    }
    
    private void addShovelRecipe(String itemName, Item material, Item secondaryMaterial) {
    	Item wantedItem = Item_Searcher.TryGettingItem(itemName);
    	if(wantedItem == null) {
			MLogger.print("Recipe API", "Item not found for ["+ itemName +"], Skipping", MLogger.ErrorType.ERROR);
			return;
		}	
    	String[] recipeFormat = {"x", "y", "y"};
    	char[] characterMap = {'x', 'y'};
    	Map<Character,ItemStack> connectionMap = new HashMap<Character, ItemStack>();
    	connectionMap.put(characterMap[0], new ItemStack(material, 1, 0));
    	connectionMap.put(characterMap[1], new ItemStack(secondaryMaterial, 1, 0));
    	Recipe_Builder.buildNormalRecipe(wantedItem, 1, 0, recipeFormat, characterMap, connectionMap);
    }
    
    private void addAxeRecipe(String[] splitList) {
    	for(String itemName : splitList) {
    		String material = inputFormatException.getProperty(I_SetCraftingMaterial1 + itemName, "");
    		String supportMaterial = inputFormatException.getProperty(I_SetCraftingMaterial2 + itemName, "item.stick");
    		
    		if(material.isEmpty()) {
    			MLogger.print("Recipe API", "Invalid Material for equipament Set ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}	
    		
    		Item primaryMaterial = Item_Searcher.TryGettingItem(material);
    		Item secondaryMaterial = Item_Searcher.TryGettingItem(supportMaterial);
    		
    		if(primaryMaterial == null) {
    			MLogger.print("Recipe API", "Item material not found for ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}		
    		
    		addAxeRecipe(itemName, primaryMaterial, secondaryMaterial);
    	}
    }
    
    private void addAxeRecipe(String itemName, Item material, Item secondaryMaterial) {
    	Item wantedItem = Item_Searcher.TryGettingItem(itemName);
    	if(wantedItem == null) {
			MLogger.print("Recipe API", "Item not found for ["+ itemName +"], Skipping", MLogger.ErrorType.ERROR);
			return;
		}	
    	String[] recipeFormat = {" xx", " yx", " y "};
    	char[] characterMap = {'x', 'y'};
    	Map<Character,ItemStack> connectionMap = new HashMap<Character, ItemStack>();
    	connectionMap.put(characterMap[0], new ItemStack(material, 1, 0));
    	connectionMap.put(characterMap[1], new ItemStack(secondaryMaterial, 1, 0));
    	Recipe_Builder.buildNormalRecipe(wantedItem, 1, 0, recipeFormat, characterMap, connectionMap);
    }
    
    private void addHoeRecipe(String[] splitList) {
    	for(String itemName : splitList) {
    		String material = inputFormatException.getProperty(I_SetCraftingMaterial1 + itemName, "");
    		String supportMaterial = inputFormatException.getProperty(I_SetCraftingMaterial2 + itemName, "item.stick");
    		
    		if(material.isEmpty()) {
    			MLogger.print("Recipe API", "Invalid Material for equipament Set ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}	
    		
    		Item primaryMaterial = Item_Searcher.TryGettingItem(material);
    		Item secondaryMaterial = Item_Searcher.TryGettingItem(supportMaterial);
    		
    		if(primaryMaterial == null) {
    			MLogger.print("Recipe API", "Item material not found for ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}		
    		
    		addHoeRecipe(itemName, primaryMaterial, secondaryMaterial);
    	}
    }
    
    private void addHoeRecipe(String itemName, Item material, Item secondaryMaterial) {
    	Item wantedItem = Item_Searcher.TryGettingItem(itemName);
    	if(wantedItem == null) {
			MLogger.print("Recipe API", "Item not found for ["+ itemName +"], Skipping", MLogger.ErrorType.ERROR);
			return;
		}	
    	String[] recipeFormat = {"xx ", " y ", " y "};
    	char[] characterMap = {'x', 'y'};
    	Map<Character,ItemStack> connectionMap = new HashMap<Character, ItemStack>();
    	connectionMap.put(characterMap[0], new ItemStack(material, 1, 0));
    	connectionMap.put(characterMap[1], new ItemStack(secondaryMaterial, 1, 0));
    	Recipe_Builder.buildNormalRecipe(wantedItem, 1, 0, recipeFormat, characterMap, connectionMap);
    }
    
    private void addArmorSetRecipe(String[] splitList) {
    	for(String itemName : splitList) {
    		String material = inputFormatException.getProperty(I_ArmorSetCraftingMaterial + itemName, "");
    		
    		if(material.isEmpty()) {
    			MLogger.print("Recipe API", "Invalid Material for equipament Set ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}	
    		
    		Item primaryMaterial = Item_Searcher.TryGettingItem(material);
    		
    		if(primaryMaterial == null) {
    			MLogger.print("Recipe API", "Item material not found for ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}		
    		
    		addHelmetRecipe(itemName + "Helmet", primaryMaterial);
    		addChestplateRecipe(itemName + "Plate", primaryMaterial);
    		addLeggingsRecipe(itemName + "Legs", primaryMaterial);
    		addBootsRecipe(itemName + "Boots", primaryMaterial);
    	}
    }
    
    private void addHelmetRecipe(String[] splitList) {
    	for(String itemName : splitList) {
    		String material = inputFormatException.getProperty(I_SetCraftingMaterial1 + itemName, "");
    		
    		if(material.isEmpty()) {
    			MLogger.print("Recipe API", "Invalid Material for equipament Set ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}	
    		
    		Item primaryMaterial = Item_Searcher.TryGettingItem(material);
    		
    		if(primaryMaterial == null) {
    			MLogger.print("Recipe API", "Item material not found for ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}		
    		
    		addHelmetRecipe(itemName, primaryMaterial);
    	}
    }
    
    private void addHelmetRecipe(String itemName, Item material) {
    	Item wantedItem = Item_Searcher.TryGettingItem(itemName);
    	if(wantedItem == null) {
			MLogger.print("Recipe API", "Item not found for ["+ itemName +"], Skipping", MLogger.ErrorType.ERROR);
			return;
		}	
    	String[] recipeFormat = {"xxx", "x x"};
    	char[] characterMap = {'x'};
    	Map<Character,ItemStack> connectionMap = new HashMap<Character, ItemStack>();
    	connectionMap.put(characterMap[0], new ItemStack(material, 1, 0));
    	Recipe_Builder.buildNormalRecipe(wantedItem, 1, 0, recipeFormat, characterMap, connectionMap);
    }
       
    private void addChestplateRecipe(String[] splitList) {
    	for(String itemName : splitList) {
    		String material = inputFormatException.getProperty(I_SetCraftingMaterial1 + itemName, "");
    		
    		if(material.isEmpty()) {
    			MLogger.print("Recipe API", "Invalid Material for equipament Set ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}	
    		
    		Item primaryMaterial = Item_Searcher.TryGettingItem(material);
    		
    		if(primaryMaterial == null) {
    			MLogger.print("Recipe API", "Item material not found for ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}		
    		
    		addChestplateRecipe(itemName, primaryMaterial);
    	}
    }
    
    private void addChestplateRecipe(String itemName, Item material) {
    	Item wantedItem = Item_Searcher.TryGettingItem(itemName);
    	if(wantedItem == null) {
			MLogger.print("Recipe API", "Item not found for ["+ itemName +"], Skipping", MLogger.ErrorType.ERROR);
			return;
		}	
    	String[] recipeFormat = {"x x", "xxx", "xxx"};
    	char[] characterMap = {'x'};
    	Map<Character,ItemStack> connectionMap = new HashMap<Character, ItemStack>();
    	connectionMap.put(characterMap[0], new ItemStack(material, 1, 0));
    	Recipe_Builder.buildNormalRecipe(wantedItem, 1, 0, recipeFormat, characterMap, connectionMap);
    }
    
    private void addLeggingsRecipe(String[] splitList) {
    	for(String itemName : splitList) {
    		String material = inputFormatException.getProperty(I_SetCraftingMaterial1 + itemName, "");
    		
    		if(material.isEmpty()) {
    			MLogger.print("Recipe API", "Invalid Material for equipament Set ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}	
    		
    		Item primaryMaterial = Item_Searcher.TryGettingItem(material);
    		
    		if(primaryMaterial == null) {
    			MLogger.print("Recipe API", "Item material not found for ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}		
    		
    		addLeggingsRecipe(itemName, primaryMaterial);
    	}
    }
    
    private void addLeggingsRecipe(String itemName, Item material) {
    	Item wantedItem = Item_Searcher.TryGettingItem(itemName);
    	if(wantedItem == null) {
			MLogger.print("Recipe API", "Item not found for ["+ itemName +"], Skipping", MLogger.ErrorType.ERROR);
			return;
		}	
    	String[] recipeFormat = {"xxx", "x x", "x x"};
    	char[] characterMap = {'x'};
    	Map<Character,ItemStack> connectionMap = new HashMap<Character, ItemStack>();
    	connectionMap.put(characterMap[0], new ItemStack(material, 1, 0));
    	Recipe_Builder.buildNormalRecipe(wantedItem, 1, 0, recipeFormat, characterMap, connectionMap);
    }
    
    private void addBootsRecipe(String[] splitList) {
    	for(String itemName : splitList) {
    		String material = inputFormatException.getProperty(I_SetCraftingMaterial1 + itemName, "");
    		
    		if(material.isEmpty()) {
    			MLogger.print("Recipe API", "Invalid Material for equipament Set ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}	
    		
    		Item primaryMaterial = Item_Searcher.TryGettingItem(material);
    		
    		if(primaryMaterial == null) {
    			MLogger.print("Recipe API", "Item material not found for ["+itemName+"], Skipping", MLogger.ErrorType.ERROR);
    			continue;
    		}		
    		
    		addBootsRecipe(itemName, primaryMaterial);
    	}
    }
    
    private void addBootsRecipe(String itemName, Item material) {
    	Item wantedItem = Item_Searcher.TryGettingItem(itemName);
    	if(wantedItem == null) {
			MLogger.print("Recipe API", "Item not found for ["+ itemName +"], Skipping", MLogger.ErrorType.ERROR);
			return;
		}	
    	String[] recipeFormat = {"x x", "x x"};
    	char[] characterMap = {'x'};
    	Map<Character,ItemStack> connectionMap = new HashMap<Character, ItemStack>();
    	connectionMap.put(characterMap[0], new ItemStack(material, 1, 0));
    	Recipe_Builder.buildNormalRecipe(wantedItem, 1, 0, recipeFormat, characterMap, connectionMap);
    }
    
}

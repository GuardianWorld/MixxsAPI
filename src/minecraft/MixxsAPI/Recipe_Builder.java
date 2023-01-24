package MixxsAPI;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;

public class Recipe_Builder {

	
	/**
	 * Builds an Recipe, not to be used if you are coding, since this is quite inneficient compared to SIMPLY BUILDING by hand.
	 * Works well with what i want tho.
	 * @param toBeCrafted Item that will be crafted
	 * @param amount The amount received after the crafting
	 * @param itemDamage The item SubType (ItemDamage)
	 * @param recipeFormat The shape of the recipe, in 1x1 up to 3x3
	 * @param characterMap The characters of the recipe (xyz, etc).
	 * @param connectionMap The connection that is formed between an character (x) -> ItemStack (Dye for example)
	 * @param isShapeless If the recipe is shapeless or not
	 */
	public static void buildNormalRecipe(Item toBeCrafted, int amount, int itemDamage, String[] recipeFormat, char[] characterMap, 
		Map<Character, ItemStack> connectionMap) {
				
		if(recipeFormat == null) {
			return;
		}
		
		int recipeSizeColumn = recipeFormat.length;
		int recipeSizeLine = 0;
		
		for(String s: recipeFormat) {
			if(recipeSizeLine < s.toCharArray().length) {
				recipeSizeLine = s.toCharArray().length;
			}		
		}
		
		MLogger.print("Recipe API", "Building Recipe for Item " + toBeCrafted.getItemName() + ".", MLogger.ErrorType.NORMAL);
		//System.err.println(recipeSizeColumn + "|" + recipeSizeLine);
		
		
		if(recipeSizeColumn == 1 && recipeSizeLine == 1) add1x1RecipeDefault(toBeCrafted, amount, itemDamage, recipeFormat, characterMap, connectionMap);
		else if(recipeSizeColumn == 1 && recipeSizeLine == 2) add1x2RecipeDefault(toBeCrafted, amount, itemDamage, recipeFormat, characterMap, connectionMap);
		else if(recipeSizeColumn == 1 && recipeSizeLine == 3) add1x3RecipeDefault(toBeCrafted, amount, itemDamage, recipeFormat, characterMap, connectionMap);
		else if(recipeSizeColumn == 2 && recipeSizeLine == 1) add2x1RecipeDefault(toBeCrafted, amount, itemDamage, recipeFormat, characterMap, connectionMap);
		else if(recipeSizeColumn == 2 && recipeSizeLine == 2) add2x2RecipeDefault(toBeCrafted, amount, itemDamage, recipeFormat, characterMap, connectionMap);
		else if(recipeSizeColumn == 2 && recipeSizeLine == 3) add2x3RecipeDefault(toBeCrafted, amount, itemDamage, recipeFormat, characterMap, connectionMap);
		else if(recipeSizeColumn == 3 && recipeSizeLine == 1) add3x1RecipeDefault(toBeCrafted, amount, itemDamage, recipeFormat, characterMap, connectionMap);
		else if(recipeSizeColumn == 3 && recipeSizeLine == 2) add3x2RecipeDefault(toBeCrafted, amount, itemDamage, recipeFormat, characterMap, connectionMap);
		else if(recipeSizeColumn == 3 && recipeSizeLine == 3) add3x3RecipeDefault(toBeCrafted, amount, itemDamage, recipeFormat, characterMap, connectionMap);	
		
	}
	
	public static void buildShapelessRecipe(Item toBeCrafted, int amount, int itemDamage, ItemStack[] materialsMap) {
		
		MLogger.print("Recipe API", "Building Shapeless Recipe for Item " + toBeCrafted.getItemName() + ".", MLogger.ErrorType.NORMAL);
		if(materialsMap == null || materialsMap.length == 0) {
			System.err.println("Cannot build " + toBeCrafted.getItemName() + " Invalid Materials");
			return;
		}		
		addShapelessRecipe(toBeCrafted, amount, itemDamage, materialsMap);	
	}
	
	public static void buildFurnaceRecipe(int resourceID, Item toBeCrafted, int amount, int itemDamage) {
		MLogger.print("Recipe API", "Building Furnace Recipe for Item " + toBeCrafted.getItemName() + ".", MLogger.ErrorType.NORMAL);
		if(resourceID != 0 && toBeCrafted != null) {
			addFurnaceRecipe(resourceID, toBeCrafted, amount, itemDamage);
		}
	}
	
	private static void addFurnaceRecipe(int resource, Item toBeCrafted, int amount, int subItem) {
		ModLoader.AddSmelting(resource, new ItemStack(toBeCrafted, amount, subItem));
	}
	
	
	//1x1
	
	
	
	private static void addShapelessRecipe(Item toBeCrafted , int amount, int subItem, Object[] materialsMap) {	
		
		ModLoader.AddShapelessRecipe(
				new ItemStack(toBeCrafted, amount, subItem),
				materialsMap);
				
	}
	
	//2x2

	private static void add1x1RecipeDefault(Item toBeCrafted , int amount, int subItem, String[] recipeFormat, 
			char[] charactersInRecipe, Map<Character, ItemStack> connectionMap) {
		ModLoader.AddRecipe(
				new ItemStack(toBeCrafted, amount, subItem),
				recipeFormat[0],
				charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]));
	}
	
	private static void add1x2RecipeDefault(Item toBeCrafted , int amount, int subItem, String[] recipeFormat, 
			char[] charactersInRecipe, Map<Character, ItemStack> connectionMap) {
		
		switch(charactersInRecipe.length) {
		case 1:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]));
			break;
		case 2:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1])
					);
			break;
		}
	}
	
	private static void add2x1RecipeDefault(Item toBeCrafted , int amount, int subItem, String[] recipeFormat, 
			char[] charactersInRecipe, Map<Character, ItemStack> connectionMap) {
		
		switch(charactersInRecipe.length) {
		case 1:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]));
			break;
		case 2:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1])
					);
			break;
		}
	}
	
	private static void add2x2RecipeDefault(Item toBeCrafted , int amount, int subItem, String[] recipeFormat, 
			char[] charactersInRecipe, Map<Character, ItemStack> connectionMap) {	
		switch(charactersInRecipe.length) {
			case 1:
				ModLoader.AddRecipe(
						new ItemStack(toBeCrafted, amount, subItem),
						recipeFormat[0],
						recipeFormat[1],
						charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]));
				break;
			case 2:
				ModLoader.AddRecipe(
						new ItemStack(toBeCrafted, amount, subItem),
						recipeFormat[0],
						recipeFormat[1],
						charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
						charactersInRecipe[1], connectionMap.get(charactersInRecipe[1])
						);
				break;
			case 3:
				ModLoader.AddRecipe(
						new ItemStack(toBeCrafted, amount, subItem),
						recipeFormat[0],
						recipeFormat[1],
						charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
						charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
						charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]));
				break;
			case 4:
				ModLoader.AddRecipe(
						new ItemStack(toBeCrafted, amount, subItem),
						recipeFormat[0],
						recipeFormat[1],
						charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
						charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),	
						charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]),
						charactersInRecipe[3], connectionMap.get(charactersInRecipe[3]));
				break;
		}	
		

	}
	
	//3x3
	
	//3 Materials
	private static void add1x3RecipeDefault(Item toBeCrafted , int amount, int subItem, String[] recipeFormat, 
			char[] charactersInRecipe, Map<Character, ItemStack> connectionMap) {	
		
		switch(charactersInRecipe.length) {
		case 1:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]));
			break;
		case 2:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]));
			break;
		case 3:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
					charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]));
			break;
		}
		
	}
	//6 Materials
	private static void add2x3RecipeDefault(Item toBeCrafted , int amount, int subItem, String[] recipeFormat, 
			char[] charactersInRecipe, Map<Character, ItemStack> connectionMap) {	
		
		switch(charactersInRecipe.length) {
		case 1:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]));
			break;
		case 2:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]));
			break;
		case 3:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
					charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]));
			break;
		case 4:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
					charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]),
					charactersInRecipe[3], connectionMap.get(charactersInRecipe[3]));
			break;
		case 5:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
					charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]),
					charactersInRecipe[3], connectionMap.get(charactersInRecipe[3]),
					charactersInRecipe[4], connectionMap.get(charactersInRecipe[4]));
			break;
		case 6:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
					charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]),
					charactersInRecipe[3], connectionMap.get(charactersInRecipe[3]),
					charactersInRecipe[4], connectionMap.get(charactersInRecipe[4]),
					charactersInRecipe[5], connectionMap.get(charactersInRecipe[5]));
			break;
	
		}
		
	}
	
	
	//3 Materials
	private static void add3x1RecipeDefault(Item toBeCrafted , int amount, int subItem, String[] recipeFormat, 
			char[] charactersInRecipe, Map<Character, ItemStack> connectionMap) {	
		switch(charactersInRecipe.length) {
		case 1:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					recipeFormat[2],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]));
			break;
		case 2:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					recipeFormat[2],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]));
			break;
		case 3:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					recipeFormat[2],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
					charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]));
			break;
	
		}
	}
	
	//6 Materials
	private static void add3x2RecipeDefault(Item toBeCrafted , int amount, int subItem, String[] recipeFormat, 
			char[] charactersInRecipe, Map<Character, ItemStack> connectionMap) {	
		switch(charactersInRecipe.length) {
		case 1:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					recipeFormat[2],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]));
			break;
		case 2:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					recipeFormat[2],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]));
			break;
		case 3:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					recipeFormat[2],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
					charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]));
			break;
		case 4:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					recipeFormat[2],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
					charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]),
					charactersInRecipe[3], connectionMap.get(charactersInRecipe[3]));
			break;
		case 5:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					recipeFormat[2],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
					charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]),
					charactersInRecipe[3], connectionMap.get(charactersInRecipe[3]),
					charactersInRecipe[4], connectionMap.get(charactersInRecipe[4]));
			break;
		case 6:
			ModLoader.AddRecipe(
					new ItemStack(toBeCrafted, amount, subItem),
					recipeFormat[0],
					recipeFormat[1],
					recipeFormat[2],
					charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
					charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
					charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]),
					charactersInRecipe[3], connectionMap.get(charactersInRecipe[3]),
					charactersInRecipe[4], connectionMap.get(charactersInRecipe[4]),
					charactersInRecipe[5], connectionMap.get(charactersInRecipe[5]));
			break;
		}
	}
	
	//9 Materials
	private static void add3x3RecipeDefault(Item toBeCrafted , int amount, int subItem, String[] recipeFormat, 
			char[] charactersInRecipe, Map<Character, ItemStack> connectionMap) {	
		
		switch(charactersInRecipe.length) {
			case 1:
				ModLoader.AddRecipe(
						new ItemStack(toBeCrafted, amount, subItem),
						recipeFormat[0],
						recipeFormat[1],
						recipeFormat[2],
						charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]));
				break;
			case 2:
				ModLoader.AddRecipe(
						new ItemStack(toBeCrafted, amount, subItem),
						recipeFormat[0],
						recipeFormat[1],
						recipeFormat[2],
						charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
						charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]));
				break;
			case 3:
				ModLoader.AddRecipe(
						new ItemStack(toBeCrafted, amount, subItem),
						recipeFormat[0],
						recipeFormat[1],
						recipeFormat[2],
						charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
						charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
						charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]));
				break;
			case 4:
				ModLoader.AddRecipe(
						new ItemStack(toBeCrafted, amount, subItem),
						recipeFormat[0],
						recipeFormat[1],
						recipeFormat[2],
						charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
						charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
						charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]),
						charactersInRecipe[3], connectionMap.get(charactersInRecipe[3]));
				break;
			case 5:
				ModLoader.AddRecipe(
						new ItemStack(toBeCrafted, amount, subItem),
						recipeFormat[0],
						recipeFormat[1],
						recipeFormat[2],
						charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
						charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
						charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]),
						charactersInRecipe[3], connectionMap.get(charactersInRecipe[3]),
						charactersInRecipe[4], connectionMap.get(charactersInRecipe[4]));
				break;
			case 6:
				ModLoader.AddRecipe(
						new ItemStack(toBeCrafted, amount, subItem),
						recipeFormat[0],
						recipeFormat[1],
						recipeFormat[2],
						charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
						charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
						charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]),
						charactersInRecipe[3], connectionMap.get(charactersInRecipe[3]),
						charactersInRecipe[4], connectionMap.get(charactersInRecipe[4]),
						charactersInRecipe[5], connectionMap.get(charactersInRecipe[5]));
				break;
			case 7:
				ModLoader.AddRecipe(
						new ItemStack(toBeCrafted, amount, subItem),
						recipeFormat[0],
						recipeFormat[1],
						recipeFormat[2],
						charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
						charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
						charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]),
						charactersInRecipe[3], connectionMap.get(charactersInRecipe[3]),
						charactersInRecipe[4], connectionMap.get(charactersInRecipe[4]),
						charactersInRecipe[5], connectionMap.get(charactersInRecipe[5]),
						charactersInRecipe[6], connectionMap.get(charactersInRecipe[6]));
				break;
			case 8:
				ModLoader.AddRecipe(
						new ItemStack(toBeCrafted, amount, subItem),
						recipeFormat[0],
						recipeFormat[1],
						recipeFormat[2],
						charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
						charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
						charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]),
						charactersInRecipe[3], connectionMap.get(charactersInRecipe[3]),
						charactersInRecipe[4], connectionMap.get(charactersInRecipe[4]),
						charactersInRecipe[5], connectionMap.get(charactersInRecipe[5]),
						charactersInRecipe[6], connectionMap.get(charactersInRecipe[6]),
						charactersInRecipe[7], connectionMap.get(charactersInRecipe[7]));
				break;
			case 9:
				ModLoader.AddRecipe(
						new ItemStack(toBeCrafted, amount, subItem),
						recipeFormat[0],
						recipeFormat[1],
						recipeFormat[2],
						charactersInRecipe[0], connectionMap.get(charactersInRecipe[0]),
						charactersInRecipe[1], connectionMap.get(charactersInRecipe[1]),
						charactersInRecipe[2], connectionMap.get(charactersInRecipe[2]),
						charactersInRecipe[3], connectionMap.get(charactersInRecipe[3]),
						charactersInRecipe[4], connectionMap.get(charactersInRecipe[4]),
						charactersInRecipe[5], connectionMap.get(charactersInRecipe[5]),
						charactersInRecipe[6], connectionMap.get(charactersInRecipe[6]),
						charactersInRecipe[7], connectionMap.get(charactersInRecipe[7]),
						charactersInRecipe[8], connectionMap.get(charactersInRecipe[8]));
				break;
		
		}
	}

	
	public static void makeConnectionMap(char character, Item item, int damage, Map<Character, ItemStack> connectionMap){
		if(!connectionMap.containsKey(character)) {
			connectionMap.put(character, new ItemStack(item, 1, damage));
		}
	}
	
	public static char[] lettersInRecipe(String[] recipeFormat) {
		ArrayList<Character> charArray = new ArrayList<>();
		
		for(String s: recipeFormat) {
			char[] c = s.toCharArray();
			for(int x = 0; x < c.length; x++) {
				if(!charArray.contains(c[x])) {
					charArray.add(c[x]);
				}
			}
		}
		
		char[] myCharArray = new char[charArray.size()];
		
		for(int i = 0; i < charArray.size(); i++) {
			myCharArray[i] = charArray.get(i);
		}
		
		//System.out.println(charArray);
		return myCharArray;
	}
	

	
}

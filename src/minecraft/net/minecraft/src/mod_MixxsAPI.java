package net.minecraft.src;

import java.io.IOException;
import java.util.Properties;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import MixxsAPI.*; 

public class mod_MixxsAPI extends BaseMod {
	public static ArrayList<String> additionalFunctionalityMods;
    public static ArrayList<Item> itemTable = new ArrayList<>();
    public static String configpath;
    EntityCustomList entityList;
    
    //APIs
    MixxsAPI_ItemAPI itemAPI;
    MixxsAPI_BlockAPI blockAPI;
    MixxsAPI_RecipeAPI recipeAPI;
    MixxsAPI_EntityAPI entityAPI;
    MixxsAPI_LootTableAPI lootTableAPI;

    public mod_MixxsAPI(){
    	//Initializing itself
    	System.out.println("> [Mixxs API]: Initializing ...");
    	
    	new Properties();
    	additionalFunctionalityMods = new ArrayList<>();  	
        try {
			configpath = Minecraft.getMinecraftDir().getCanonicalPath() + "/config/";
			configpath = configpath.replace("\\", "/");
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("> [Mixxs API]: Initializing APIs...");
    	//Initializing the other APIs;
        //Entity API
        entityList = new EntityCustomList();
        
    	itemAPI = new MixxsAPI_ItemAPI();
    	recipeAPI = new MixxsAPI_RecipeAPI();


        //Try checking if supported mods that add other functions are enabled or not is on or not.
        checkAdditionalFunctionalityMods();
        
        itemAPI.ItemAPICalls("MixxsMods/itemAPI.txt", itemTable, null);
        recipeAPI.RecipeAPICalls("MixxsMods/recipeAPI.txt");
    }

    private void checkAdditionalFunctionalityMods() {
    	//checkEspecificModsExistence("net.minecraft.src.overrideapi.OverrideAPI", "EnumToolMaterials", false);
    	checkModsExistence("overrideapi.OverrideAPI", "EnumToolMaterials");
    	checkModsExistence("ItemNBT", ModLoader.isModLoaded("mod_ItemNBT"), ModLoader.isModLoaded("net.minecraft.src.mod_ItemNBT"), "NBT tags and custom description");
    	//checkEspecificModsExistence("net.minecraft.src.DirtSwordLoader", "3D models using .obj");
    }
    
    private void checkModsExistence(String className, String functions) {
    	try {
			Class.forName(className, false, this.getClass().getClassLoader());
			additionalFunctionalityMods.add(className); 
		} catch (ClassNotFoundException e) {	
			System.err.println("> [MixxsAPI] Warning: Could not find class [" + className + "]. Some additional functions [" + functions + "] will not work");
			additionalFunctionalityMods.add("NotFound");
		}
    }
    
    private void checkModsExistence(String className, Boolean ModLoaderCheck1, Boolean ModLoaderCheck2, String functions) {
    	if(!ModLoaderCheck1 && !ModLoaderCheck2) {
    		System.err.println("> [MixxsAPI] Warning: Could not find class [" + className + "]. Some additional functions [" + functions + "] will not work");
			additionalFunctionalityMods.add("NotFound");
    	}
    	else {
    		additionalFunctionalityMods.add(className);
    	}
    }
    
    
    
    public String Version() {
		return "Mixxs API v0.1 for Minecraft B1.7.3";
	}
}
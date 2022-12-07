package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
//import net.minecraft.src.overrideapi.utils.tool.ToolMaterial;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
//import net.minecraft.src.overrideapi.utils.tool.ToolMaterial;

public class mod_MixxsAPI extends BaseMod {
	
	protected static ArrayList<String> additionalFunctionalityMods;
    public static ArrayList<Item> itemTable = new ArrayList<>();
    public static String configpath;
    private static Properties inputFormatException;
    
    //APIs
    MixxsAPI_ItemAPI itemAPI;

    public mod_MixxsAPI(){
    	//Initializing itself
    	System.out.println("> [Mixxs API]: Initializing ...");
    	
    	inputFormatException = new Properties();
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
    	itemAPI = new MixxsAPI_ItemAPI();
        
        
        //Try checking if supported mods that add other functions are enabled or not is on or not.
        checkAdditionalFunctionalityMods();
        
        itemAPI.ItemAPICalls("MixxsMods/itemAPI.txt", itemTable, null);
    }

    private void checkAdditionalFunctionalityMods() {
    	checkEspecificModsExistence("net.minecraft.src.overrideapi.OverrideAPI", "EnumToolMaterials");
    }
    
    private boolean checkEspecificModsExistence(String className, String functions) {
    	try {
			Class.forName(className, false, this.getClass().getClassLoader());
			additionalFunctionalityMods.add(className); 
		} catch (ClassNotFoundException e) {
			System.err.println("> [MixxsAPI] Warning: Could not find class [" + className + "]. Some additional functions [" + functions + "] will not work");
			additionalFunctionalityMods.add("NotFound");
		}
    	return false;
    }
    
    public String Version() {
		return "Mixxs API v0.1 for Minecraft B1.7.3";
	}
}
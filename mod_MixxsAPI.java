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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class mod_MixxsAPI extends BaseMod {
    public static ArrayList<Item> ItemTable = new ArrayList<>();
    public static String configpath;
    private static Properties inputFormatException;
    private static int defaultTexture;
    
    //APIs
    MixxsAPI_ItemAPI itemAPI;

    public mod_MixxsAPI(){
    	//Initializing the other APIs;
    	System.out.println("> Mixxs API Initializing...");
    	itemAPI = new MixxsAPI_ItemAPI();
        //inputFormatException = new Properties();
        try {
			configpath = Minecraft.getMinecraftDir().getCanonicalPath() + "/config/";
			configpath = configpath.replace("\\", "/");
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
        itemAPI.ItemAPICalls("MixxsMods/itemAPI.txt", ItemTable);
    }

    public String Version() {
		return "Mixxs API v0.1 for Minecraft B1.7.3";
	}
}
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

import javax.imageio.ImageIO;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public class mod_MixxsAPI extends BaseMod {
    public static ArrayList<Item> ItemTable = new ArrayList<>();
    public static String configpath;
    private static Properties inputFormatException;
    private static int defaultTexture;
    private int defaultIDIncrement;

    public mod_MixxsAPI(){
    	defaultIDIncrement = 0;
        inputFormatException = new Properties();
        String defaultTexturePath = "/config/MixxsMods/textures/NoTexture.png";
        defaultTexture = ModLoader.addOverride("/gui/items.png", defaultTexturePath);
        try {
			configpath = Minecraft.getMinecraftDir().getCanonicalPath() + "/config/";
			configpath = configpath.replace("\\", "/");
			
			
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
       	ItemAPICalls("MixxsMods/lootAPI.txt", mod_MixxsAPI.ItemTable);
    }

    public boolean ItemAPICalls(String modConfigPath, ArrayList<Item> modItemTable){
        //First get the ItemList
        String[] split;
        try{
            File file = new File(configpath + modConfigPath);
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
            String idItemAux = "idItem" + s;
            String itemTextureAux = "Texture" + s;  
            String itemTypeAux = "Type" + s;
            String itemNameAux = "SetName" + s;

            String type;
            String texture = "";
            String name;
            int id = 5000; 

            id = TryGettingValues(idItemAux, String.valueOf(id));
            texture = "/config/" + inputFormatException.getProperty(itemTextureAux);
            type = inputFormatException.getProperty(itemTypeAux, "Item");
            name = inputFormatException.getProperty(itemNameAux, s);

            Item auxItem = buildItem(s, id, texture, type);

            modItemTable.add(auxItem);      
            ModLoader.AddName(auxItem, name);
            if(id + defaultIDIncrement == 1000 + defaultIDIncrement) {
            	defaultIDIncrement++;
            }
        }

        return true;
    }

    private int TryGettingValues(String grabINT, String defaultINT){
        int aux = 1000;
        try{
            aux = Integer.valueOf(inputFormatException.getProperty(grabINT, defaultINT)).intValue();
        }
        catch(Exception numberFormatException5){
            System.err.println("Invalid value for: " + grabINT + ", Using default value.");
        }    
        return aux;
    }

    private Item buildItem(String name, int id, String texture, String type){
        Item aux = null;
        String itemStackSizeAux = "SetMaxStack" + name;

        if(type.equals("Item")){
            int stackSize = TryGettingValues(itemStackSizeAux, "64");
            aux = (new Item(id)).setItemName(name).setMaxStackSize(stackSize);   
            if(texture.equals("/config/null")) {
            	aux.setIconIndex(defaultTexture);
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

    public String Version() {
		return "Mixxs API v0.1 for Minecraft B1.7.3";
	}

}
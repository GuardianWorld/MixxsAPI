package MixxsAPI;

import java.util.ArrayList;

import net.minecraft.src.Block;
import net.minecraft.src.Item;

public class Block_Searcher {

    public static ArrayList<Integer> TryGettingBlockIDs(String itemName, String[] blockNames, int defaultItem) {
    	ArrayList<Integer> blockIDs = new ArrayList<>();
    	ArrayList<String> auxNames = new ArrayList<>();
    	
    	if(blockNames != null) {
    		for(String blockName : blockNames) {
        		int bID = GetBlockID(blockName, defaultItem, true);
        		if(bID != 0) blockIDs.add(bID);  		
        	}
    	}
	
    	if(blockIDs.size() == 0) {
    		ArrayList<Integer> defaultBlockIDs = new ArrayList<>();
        	defaultBlockIDs.add(defaultItem);
        	MLogger.print("Item API", "No valid block IDs set for "+ itemName +". Using default value ["+ defaultItem +"].", MLogger.ErrorType.ERROR);
        	return defaultBlockIDs;
    	}
    	
    	return blockIDs;
    }
    
    public static int GetBlockID(String blockName, int defaultBlock, boolean ShouldError) {
    	if(!blockName.contains("tile.")) {
    		blockName = "tile." + blockName;
    	}
    	if(blockName.contains("item.")) {
    		return defaultBlock;
    	}
    	
    	//Convert to internal nomeclature
    	blockName = convertNomeclature(blockName);
    	
    	for(Block b : Block.blocksList) {	
    		if(b != null && b.getBlockName() != null && b.getBlockName().equals(blockName)) {
    			return b.blockID;
    		}
    	}

    	if(ShouldError) {
    		MLogger.print("[Block API]", "Could not find block + " + blockName + " using default block: [" + defaultBlock + "].", MLogger.ErrorType.ERROR);
    	}
    	return defaultBlock;
    }
    
    public static String convertNomeclature(String name) {
    	if(name.equals("tile.cobblestone")) {
    		return "tile.stonebrick";
    	}
    	
    	return name;
    }
    
}

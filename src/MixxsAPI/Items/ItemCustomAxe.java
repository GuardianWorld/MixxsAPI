package net.minecraft.src.MixxsAPI.Items;

import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;

public class ItemCustomAxe extends CustomItemTool{
	private static Block[] blocksEffectiveAgainst = new Block[]{
			Block.planks, 
			Block.bookShelf, 
			Block.wood, 
			Block.chest,
			Block.pumpkin,
			Block.pumpkinLantern,
			};

	public ItemCustomAxe(int id, EnumToolMaterial axeMaterial){
		super(id, 3, axeMaterial, blocksEffectiveAgainst);
		//Add extra blocks that the thing can be effective later
	}
}

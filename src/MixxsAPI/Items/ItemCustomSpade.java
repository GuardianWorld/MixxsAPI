package net.minecraft.src.MixxsAPI.Items;

import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;

public class ItemCustomSpade extends CustomItemTool{
	private static Block[] blocksEffectiveAgainst = new Block[]{
			Block.grass, 
			Block.dirt, 
			Block.sand, 
			Block.gravel, 
			Block.snow, 
			Block.blockSnow, 
			Block.blockClay, 
			Block.tilledField,
			Block.slowSand
			};

	public ItemCustomSpade(int id, EnumToolMaterial toolMaterial) {
		super(id, 1, toolMaterial, blocksEffectiveAgainst);
	}

	public boolean canHarvestBlock(Block block1) {
		return block1 == Block.snow ? true : block1 == Block.blockSnow;
	}
}

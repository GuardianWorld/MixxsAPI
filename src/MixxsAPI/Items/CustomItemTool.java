package net.minecraft.src.MixxsAPI.Items;

import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemTool;

public class CustomItemTool extends ItemTool{

	protected CustomItemTool(int id, int damage, EnumToolMaterial toolMaterial, Block[] effectiveBlockList) {
		super(id, damage, toolMaterial, effectiveBlockList);
	}

}

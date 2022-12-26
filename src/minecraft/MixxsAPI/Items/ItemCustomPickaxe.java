package MixxsAPI.Items;

import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Material;

public class ItemCustomPickaxe extends CustomItemTool{
	private static Block[] blocksEffectiveAgainst = new Block[]{
			Block.cobblestone, 
			Block.stairDouble,
			Block.stairSingle, 
			Block.stone, 
			Block.sandStone, 
			Block.cobblestoneMossy, 
			Block.oreIron, 
			Block.blockSteel, 
			Block.oreCoal, 
			Block.blockGold, 
			Block.oreGold, 
			Block.oreDiamond, 
			Block.blockDiamond,
			Block.ice, 
			Block.netherrack, 
			Block.oreLapis, 
			Block.blockLapis,
			Block.oreRedstone,
			Block.oreRedstoneGlowing,
			Block.rail,
			Block.railDetector,
			Block.railPowered
			};

	public ItemCustomPickaxe(int id, EnumToolMaterial toolMaterial) {
		super(id, 2, toolMaterial, blocksEffectiveAgainst);
	}

	public boolean canHarvestBlock(Block block1) {
		return block1 == Block.obsidian ? this.toolMaterial.getHarvestLevel() == 3 : (block1 != Block.blockDiamond && block1 != Block.oreDiamond ? (block1 != Block.blockGold && block1 != Block.oreGold ? (block1 != Block.blockSteel && block1 != Block.oreIron ? (block1 != Block.blockLapis && block1 != Block.oreLapis ? (block1 != Block.oreRedstone && block1 != Block.oreRedstoneGlowing ? (block1.blockMaterial == Material.rock ? true : block1.blockMaterial == Material.iron) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2);
	}
}

package MixxsAPI.Items;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemBlockPlacer extends Item {

	private int setBlockID;
	private String soundEffect;
	
	public ItemBlockPlacer(int ID, int durability, int setBlockID, String soundEffect) {
		super(ID);
		this.maxStackSize = 1;
		this.setMaxDamage(durability);
		this.setBlockID = setBlockID;
		this.soundEffect = soundEffect;
	}

	
	public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int x, int y, int z, int blockFace) {
		
		if(blockFace == 0) { --y; }
		if(blockFace == 1) { ++y; }
		if(blockFace == 2) { --z; }
		if(blockFace == 3) { ++z; }
		if(blockFace == 4) { --x; }
		if(blockFace == 5) { ++x; }

		if(world3.getBlockId(x, y, z) == 0) {
			Block block = Block.blocksList[setBlockID];
			if(soundEffect != "fire.ignite") {
				world3.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block.stepSound.func_1145_d(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
			}
			else {
				world3.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, soundEffect, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
			}
			world3.setBlockWithNotify(x, y, z, setBlockID);
		}

		itemStack1.damageItem(1, entityPlayer2);
		return true;
	}

}

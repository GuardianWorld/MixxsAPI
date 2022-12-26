package MixxsAPI.Items;

import java.util.ArrayList;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemBlockReplacer extends Item{

	private int setBlockID;
	
	private int[] frl = {0,
			Block.bedrock.blockID,
			//Block.oreCoal.blockID,
			//Block.oreDiamond.blockID,
			//Block.oreGold.blockID,
			//Block.oreIron.blockID,
			//Block.oreLapis.blockID,
			//Block.oreRedstone.blockID,
			//Block.oreRedstoneGlowing.blockID,
			Block.lavaStill.blockID,
			Block.lavaMoving.blockID,
			Block.waterStill.blockID,
			Block.waterMoving.blockID};
	
	private ArrayList<Integer> replacerListForbidden = new ArrayList<>();
	
	public ItemBlockReplacer(int ID, int durability, int setBlockID, ArrayList<Integer> replacerListForbidden) {
		super(ID);
		this.maxStackSize = 1;
		this.setMaxDamage(durability);
		this.setBlockID = setBlockID;
		this.replacerListForbidden = replacerListForbidden;
		
		for(int i: frl) {
			replacerListForbidden.add(i);
		}
		replacerListForbidden.add(setBlockID);
	}

	
	public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int x, int y, int z, int blockFace) {
		
		boolean isValid = true;
		
		for(int i: replacerListForbidden) {
			if(world3.getBlockId(x, y, z) == i) {
				isValid = false;
				break;
			}
		}
		
		if(isValid) {
			Block block = Block.blocksList[setBlockID];
			world3.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F),
					block.stepSound.func_1145_d(),
					(block.stepSound.getVolume() + 1.0F) / 2.0F,
					block.stepSound.getPitch() * 0.8F);
			world3.setBlockWithNotify(x, y, z, setBlockID);
			itemStack1.damageItem(1, entityPlayer2);
		}

		return true;
	}
	
}

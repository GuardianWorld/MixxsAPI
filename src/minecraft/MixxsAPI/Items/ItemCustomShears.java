package MixxsAPI.Items;

import java.util.ArrayList;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemCustomShears extends Item {
	private ArrayList<Integer> destroyableBlocksIDList;
	public ItemCustomShears(int i1, ArrayList<Integer> destroyableBlocksIDList, int durability) {
		super(i1);
		this.setMaxStackSize(1);
		this.destroyableBlocksIDList = destroyableBlocksIDList;
		this.setMaxDamage(durability);
		//System.err.println(destroyableBlocksIDList);
	}

	public boolean onBlockDestroyed(ItemStack itemStack1, int blockID, int i3, int i4, int i5, EntityLiving Player) {
		int tempID = 0;
		for(int ID : destroyableBlocksIDList) {
			if(blockID == ID) {	tempID = ID; break;}
		}
		
		if(tempID != 0) {
			itemStack1.damageItem(1, Player);
			Player.dropItemWithOffset(tempID,1, -1F); //To delete the Duplicated version, checking the world, trying to find entities nearby and deleting them if they match.
		}
		else {	itemStack1.damageItem(2, Player); }
		
		return super.onBlockDestroyed(itemStack1, blockID, i3, i4, i5, Player);
	}

	public boolean canHarvestBlock(Block block1) {
		for(int ID : destroyableBlocksIDList) {
			if(block1.blockID == ID) {
				return true;
			}
		}
		return false;
	}

	public float getStrVsBlock(ItemStack itemStack1, Block block2) {
		for(int ID : destroyableBlocksIDList) {
			if(block2.blockID == ID) {
				return 15F;
			}
		}
		return 1F;
	}
}

package net.minecraft.src.MixxsAPI.Items;

import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemCustomBow extends Item{
	private Item consumeItem;
	public ItemCustomBow(int id, Item consumeItem, int durability) {
		super(id);
		this.maxStackSize = 1;
		this.consumeItem = consumeItem;
		if(durability >= 0) {
			this.setMaxDamage(durability);
		}
	}

	public ItemStack onItemRightClick(ItemStack itemStack1, World world2, EntityPlayer entityPlayer3) {
		if(entityPlayer3.inventory.consumeInventoryItem(consumeItem.shiftedIndex)) {
			world2.playSoundAtEntity(entityPlayer3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if(!world2.multiplayerWorld) {
				world2.entityJoinedWorld(new EntityArrow(world2, entityPlayer3)); //Need to rework this one for better damage later.
			}
			itemStack1.damageItem(1, entityPlayer3);
		}

		return itemStack1;
	}
}

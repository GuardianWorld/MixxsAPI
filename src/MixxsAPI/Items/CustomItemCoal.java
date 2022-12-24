package net.minecraft.src.MixxsAPI.Items;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class CustomItemCoal extends Item{
	
	public CustomItemCoal(int id) {
		super(id);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	public String getItemNameIS(ItemStack itemStack1) {
		return itemStack1.getItemDamage() == 1 ? "item.charcoal" : "item.coal";
	}
}

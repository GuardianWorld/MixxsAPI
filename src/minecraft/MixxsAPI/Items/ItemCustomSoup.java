package MixxsAPI.Items;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemCustomSoup extends ItemFood {
	
	private Item returnItem;
	
	public ItemCustomSoup(int id, int healAmount, boolean wolfFavoriteFood, Item returnItem) {
		super(id, healAmount, wolfFavoriteFood);
		if(returnItem == null) { returnItem = Item.bowlEmpty; }
		this.returnItem = returnItem;
	}

	public ItemStack onItemRightClick(ItemStack itemStack1, World world2, EntityPlayer entityPlayer3) {
		super.onItemRightClick(itemStack1, world2, entityPlayer3);
		return new ItemStack(returnItem);
	}
}


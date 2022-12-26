package net.minecraft.src.MixxsAPI.Items;

import net.minecraft.src.Item;
import net.minecraft.src.ItemArmor;

public class ItemCustomArmor extends ItemArmor {

	public static final int HELMET = 0;
	public static final int CHESTPLATE = 1;
	public static final int LEGGINGS = 2;
	public static final int BOOTS = 3;
	
	public static final int[] DefaultDamageReduceAmountArray = new int[]{3, 8, 6, 3};
	public static final int[] DefaultMaxDamageArray = new int[]{11, 16, 15, 13};	
	
	
	public final int damageReduceAmount;
	
	public ItemCustomArmor(int id, int armorLevel, int renderIndex, int armorType, int damageReduceAmount, int durability) {
		super(id, armorLevel, renderIndex, armorType);
		this.maxStackSize = 1;
		if(damageReduceAmount < 0) { this.damageReduceAmount = DefaultDamageReduceAmountArray[armorType]; }
		else 					   { this.damageReduceAmount = damageReduceAmount;}
		if(durability < 0) { this.setMaxDamage( DefaultMaxDamageArray[armorType] * 3 << armorLevel); }
		else 			   { this.setMaxDamage(durability);	}
	}

}

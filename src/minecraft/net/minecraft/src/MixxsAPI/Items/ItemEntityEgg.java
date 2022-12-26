package net.minecraft.src.MixxsAPI.Items;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.MixxsAPI.EntityCustomList;

public class ItemEntityEgg extends Item{
	
	public ItemEntityEgg(int id)
    {
        super(id);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }
	
	public String getItemNameIS(ItemStack itemStack1) {
		return "item." + EntityCustomList.getStringFromID(itemStack1.getItemDamage()) + ".egg";
	}
	
	public int getIconFromDamage(int damage) {
		return this.iconIndex + damage % 8 * 16 + damage / 8;
	}
	
	protected static Entity spawnCreature(World world, int entityID, double x, double y, double z)
    {
		System.out.println("Entity ID: " + entityID);
        if (!EntityCustomList.entityEggs.containsKey(entityID))
        {
            return null;
        }
        else
        {
            Entity entity = null;

            for (int j = 0; j < 1; ++j)
            {
                entity = EntityCustomList.createEntityByID(entityID, world);

                if (entity != null && entity instanceof Entity)
                {
                    EntityLiving entityliving = (EntityLiving)entity;
                    entity.setLocationAndAngles(x, y, z, entityliving.rotationYaw, 0.0F);
					world.entityJoinedWorld(entityliving);
                    entityliving.playLivingSound();
                }
            }

            return entity;
        }
    }
	
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World gameWorld, int posX, int posY, int posZ, int i7)
    {

    	int blockID = gameWorld.getBlockId(posX, posY, posZ);
    	//System.out.println("ID:" + blockID);
        double d0 = 0.0D;
        if (Block.blocksList[blockID] != null)
        {
            d0 = 1D;
        }
        
        Entity entity = spawnCreature(gameWorld, itemStack.getItemDamage(), (double)posX + 0.5D, (double)posY + d0, (double)posZ + 0.5D);
        
        if (entity != null)
        {  
            --itemStack.stackSize;
        }

        return true;
    }
    
}

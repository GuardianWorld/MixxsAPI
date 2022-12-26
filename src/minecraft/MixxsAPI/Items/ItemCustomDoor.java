package MixxsAPI.Items;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class ItemCustomDoor extends Item {
	private Material doorMaterial;
	private Block doorBlock;

	public ItemCustomDoor(int id, Material doorMaterial, Block doorBlock) {
		super(id);
		this.doorMaterial = doorMaterial;
		if(doorBlock == null) {
			doorBlock = Block.stone;
		}
		this.doorBlock = doorBlock;
		this.maxStackSize = 1;
	}

	public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int x, int y, int z, int face) {
		if(face != 1) {
			return false;
		} else {
			++y;
			Block block8;
			if(this.doorMaterial == Material.wood) {
				block8 = Block.doorWood;
			} else {
				block8 = Block.doorSteel;
			}

			if(!block8.canPlaceBlockAt(world3, x, y, z)) {
				return false;
			} else {
				int i9 = MathHelper.floor_double((double)((entityPlayer2.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
				byte b10 = 0;
				byte b11 = 0;
				if(i9 == 0) {
					b11 = 1;
				}

				if(i9 == 1) {
					b10 = -1;
				}

				if(i9 == 2) {
					b11 = -1;
				}

				if(i9 == 3) {
					b10 = 1;
				}

				int i12 = (world3.isBlockNormalCube(x - b10, y, z - b11) ? 1 : 0) + (world3.isBlockNormalCube(x - b10, y + 1, z - b11) ? 1 : 0);
				int i13 = (world3.isBlockNormalCube(x + b10, y, z + b11) ? 1 : 0) + (world3.isBlockNormalCube(x + b10, y + 1, z + b11) ? 1 : 0);
				boolean z14 = world3.getBlockId(x - b10, y, z - b11) == block8.blockID || world3.getBlockId(x - b10, y + 1, z - b11) == block8.blockID;
				boolean z15 = world3.getBlockId(x + b10, y, z + b11) == block8.blockID || world3.getBlockId(x + b10, y + 1, z + b11) == block8.blockID;
				boolean z16 = false;
				if(z14 && !z15) {
					z16 = true;
				} else if(i13 > i12) {
					z16 = true;
				}

				if(z16) {
					i9 = i9 - 1 & 3;
					i9 += 4;
				}

				world3.editingBlocks = true;
				world3.setBlockAndMetadataWithNotify(x, y, z, block8.blockID, i9);
				world3.setBlockAndMetadataWithNotify(x, y + 1, z, block8.blockID, i9 + 8);
				world3.editingBlocks = false;
				world3.notifyBlocksOfNeighborChange(x, y, z, block8.blockID);
				world3.notifyBlocksOfNeighborChange(x, y + 1, z, block8.blockID);
				--itemStack1.stackSize;
				return true;
			}
		}
	}
}

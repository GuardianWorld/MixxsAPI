package MixxsAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityGiantZombie;
import net.minecraft.src.EntityPig;
import net.minecraft.src.EntityPigZombie;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_MixxsAPI;
import MixxsAPI.Items.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EntityCustomList{
	
	public static Map IDtoClassMapping = new HashMap();
	public static Map classToStringMapping = new HashMap();
	private static Map<String, Integer> stringToIDMapping = new HashMap<String, Integer>();
    public static HashMap<Integer, EntityEggInfo> entityEggs = new LinkedHashMap<Integer, EntityEggInfo>();
    public static ArrayList<ItemStack> eggStack = new ArrayList<>();
    
    public EntityCustomList() {
    	
    }

    public static void addMapping(Class entityClass, String EntityName, int id) {
    	stringToIDMapping.put(EntityName, id);
    	classToStringMapping.put(entityClass, EntityName);
		IDtoClassMapping.put(id, entityClass);
    }
    
	public static void addMapping(Class entityClass, String EntityName, int id, int primaryColor, int secondaryColor)
    {
		addMapping(entityClass, EntityName, id);
        entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
        //new ItemStack(MixxsAPI_ItemAPI.monsterEgg, 1, id);
        ModLoader.AddLocalization("item." + EntityName + ".egg.name", EntityName + " Egg");
    }
	
	public static Entity createEntityByID(int id, World gameWorld)
    {
        Entity entity = null;

        try
        {
            Class oclass = getClassFromID(id);

            if (oclass != null)
            {
                entity = (Entity)oclass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {gameWorld});
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        if (entity == null)
        {
        	MLogger.print("[Entity API]", "Skipping Entity with ID: " + id, MLogger.WARNING);
        	//gameWorld.getWorldLogAgent().logWarning("Skipping Entity with id " + id);
        }

        return entity;
    }

	 public static String getStringFromID(int entityID)
	 {
	        Class entityClass = getClassFromID(entityID);
	        if(entityClass != null) {
	        	return (String) classToStringMapping.get(entityClass);
	        }
	        return null;
	 }
	
	public static Class getClassFromID(int id)
    {
        return (Class)IDtoClassMapping.get(id);
    }
	static {
		addMapping(EntityCreeper.class, "Creeper", 50, 894731, 0);
        addMapping(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
        addMapping(EntitySpider.class, "Spider", 52, 3419431, 11013646);
        addMapping(EntityGiantZombie.class, "Giant", 53);
        addMapping(EntityZombie.class, "Zombie", 54, 44975, 7969893);
        addMapping(EntitySlime.class, "Slime", 55, 5349438, 8306542);
        addMapping(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
        addMapping(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
		
        addMapping(EntityPig.class, "Pig", 90, 15771042, 14377823);
        addMapping(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
        addMapping(EntityCow.class, "Cow", 92, 4470310, 10592673);
        addMapping(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
        addMapping(EntitySquid.class, "Squid", 94, 2243405, 7375001);
		addMapping(EntityWolf.class, "Wolf", 95, 14144467, 13545366);

		
	}

	
}

package MixxsAPI;

public class EntityEggInfo {
    /** The entityID of the spawned mob */
    public int spawnedID;

    /** Base color of the egg */
    public int primaryColor;

    /** Color of the egg spots */
    public int secondaryColor;

    public EntityEggInfo(int id, int primaryColor, int secondaryColor) {
        this.spawnedID = id;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }
}
package abused_master.abusedlib.api;

public interface IEssence {

    void fillWater(int amount);
    void fillFire(int amount);
    void fillEarth(int amount);
    void fillAir(int amount);

    void consumeWater(int amount);
    void consumeFire(int amount);
    void consumeEarth(int amount);
    void consumeAir(int amount);

    void setAmountWater(int amount);
    void setAmountFire(int amount);
    void setAmountEarth(int amount);
    void setAmountAir(int amount);

    void setCapacityWater(int amount);
    void setCapacityFire(int amount);
    void setCapacityEarth(int amount);
    void setCapacityAir(int amount);

    int getEssenceTotal();
    int getCapacityTotal();

    int getEssenceWater();
    int getEssenceFire();
    int getEssenceEarth();
    int getEssenceAir();
}

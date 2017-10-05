package abused_master.abusedlib.api;

/**
 * Default IEssence implementation
 */
public class Essence implements IEssence {

    private int essenceStoredWater = 0;
    private int essenceStoredFire = 0;
    private int essenceStoredEarth = 0;
    private int essenceStoredAir = 0;

    private int capacityWater;
    private int capacityFire;
    private int capacityEarth;
    private int capacityAir;

    public Essence() {
    }

    public Essence(int waterCap, int fireCap, int earthCap, int airCap) {
        this.capacityWater = waterCap;
        this.capacityFire = fireCap;
        this.capacityEarth = earthCap;
        this.capacityAir = airCap;

    }

    /**
     * Fill the object's internal essence stored
     * @param amount
     */
    @Override
    public void fillWater(int amount) {
        int availableStorage = capacityWater - essenceStoredWater;
        if(availableStorage >= amount) {
            essenceStoredWater += amount;
        }else {
            essenceStoredWater = capacityWater;
        }

        if (this.essenceStoredWater > capacityWater) {
            this.essenceStoredWater = capacityWater;
        } else if (this.essenceStoredWater < 0) {
            this.essenceStoredWater = 0;
        }
    }

    @Override
    public void fillFire(int amount) {
        int availableStorage = capacityFire - essenceStoredFire;
        if(availableStorage >= amount) {
            essenceStoredFire += amount;
        }else {
            essenceStoredFire = capacityFire;
        }

        if (this.essenceStoredFire > capacityFire) {
            this.essenceStoredFire = capacityFire;
        } else if (this.essenceStoredFire < 0) {
            this.essenceStoredFire = 0;
        }
    }

    @Override
    public void fillEarth(int amount) {
        int availableStorage = capacityEarth - essenceStoredEarth;
        if(availableStorage >= amount) {
            essenceStoredEarth += amount;
        }else {
            essenceStoredEarth = capacityEarth;
        }

        if (this.essenceStoredEarth > capacityEarth) {
            this.essenceStoredEarth = capacityEarth;
        } else if (this.essenceStoredEarth < 0) {
            this.essenceStoredEarth = 0;
        }
    }

    @Override
    public void fillAir(int amount) {
        int availableStorage = capacityEarth - essenceStoredEarth;
        if(availableStorage >= amount) {
            essenceStoredEarth += amount;
        }else {
            essenceStoredEarth = capacityEarth;
        }

        if (this.essenceStoredEarth > capacityEarth) {
            this.essenceStoredEarth = capacityEarth;
        } else if (this.essenceStoredEarth < 0) {
            this.essenceStoredEarth = 0;
        }
    }

    /**
     * Consume the object's essence stored
     * @param amount
     */
    @Override
    public void consumeWater(int amount) {
        if(essenceStoredWater >= amount) {
            this.essenceStoredWater -= amount;
        }

        if (this.essenceStoredWater < 0) {
            this.essenceStoredWater = 0;
        }
    }

    @Override
    public void consumeFire(int amount) {
        if(essenceStoredFire >= amount) {
            this.essenceStoredFire -= amount;
        }

        if (this.essenceStoredFire < 0) {
            this.essenceStoredFire = 0;
        }
    }

    @Override
    public void consumeEarth(int amount) {
        if(essenceStoredEarth >= amount) {
            this.essenceStoredEarth -= amount;
        }

        if (this.essenceStoredEarth < 0) {
            this.essenceStoredEarth = 0;
        }
    }

    @Override
    public void consumeAir(int amount) {
        if(essenceStoredAir >= amount) {
            this.essenceStoredAir -= amount;
        }

        if (this.essenceStoredAir < 0) {
            this.essenceStoredAir = 0;
        }
    }

    /**
     * Set the object's essence stored
     * @param amount
     */
    @Override
    public void setAmountWater(int amount) {
        this.essenceStoredWater = amount;

        if (essenceStoredWater > capacityWater) {
            essenceStoredWater = capacityWater;
        }else if(this.essenceStoredWater < 0) {
            this.essenceStoredWater = 0;
        }
    }

    @Override
    public void setAmountFire(int amount) {
        this.essenceStoredFire = amount;

        if (essenceStoredFire > capacityFire) {
            essenceStoredFire = capacityFire;
        }else if(this.essenceStoredFire < 0) {
            this.essenceStoredFire = 0;
        }
    }

    @Override
    public void setAmountEarth(int amount) {
        this.essenceStoredEarth = amount;

        if (essenceStoredEarth > capacityEarth) {
            essenceStoredEarth = capacityEarth;
        }else if(this.essenceStoredEarth < 0) {
            this.essenceStoredEarth = 0;
        }
    }

    @Override
    public void setAmountAir(int amount) {
        this.essenceStoredAir = amount;

        if (essenceStoredAir > capacityAir) {
            essenceStoredAir = capacityAir;
        }else if(this.essenceStoredAir < 0) {
            this.essenceStoredAir = 0;
        }
    }

    /**
     * Set the object's capacity for essence
     * @param amount
     */
    @Override
    public void setCapacityWater(int amount) {
        this.capacityWater = amount;
    }

    @Override
    public void setCapacityFire(int amount) {
        this.capacityFire = amount;
    }

    @Override
    public void setCapacityEarth(int amount) {
        this.capacityEarth = amount;
    }

    @Override
    public void setCapacityAir(int amount) {
        this.capacityAir = amount;
    }

    /**
     * Grab information about the essence stored in an object
     */
    @Override
    public int getEssenceTotal() {
        return essenceStoredWater + essenceStoredFire + essenceStoredEarth + essenceStoredAir;
    }

    @Override
    public int getCapacityTotal() {
        return capacityWater + capacityFire + capacityEarth + capacityAir;
    }

    @Override
    public int getEssenceWater() {
        return essenceStoredWater;
    }

    @Override
    public int getEssenceFire() {
        return essenceStoredFire;
    }

    @Override
    public int getEssenceEarth() {
        return essenceStoredEarth;
    }

    @Override
    public int getEssenceAir() {
        return essenceStoredAir;
    }
}

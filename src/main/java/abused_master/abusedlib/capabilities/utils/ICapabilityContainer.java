package abused_master.abusedlib.capabilities.utils;

import abused_master.abusedlib.capabilities.Capability;
import net.minecraft.util.math.Direction;

/**
 * Implemented on TileEntityBase to check if the any extending Tiles have a capability and then grab it
 */
public interface ICapabilityContainer {

    /**
     * Checks weather or not the container has a capability
     * @param capability - The capability to check for
     * @param direction - The direction it will ask for - null if none
     * @return - True if it has a capability, false if not
     */
    boolean hasCapability(Capability<?> capability, Direction direction);

    /**
     * Grabs the capability from the container
     * @param capability - The capability to grab
     * @param direction - The direction to ask for - null if none
     * @param <T>
     * @return - The capability implementation
     */
    <T> T getCapability(Capability<T> capability, Direction direction);
}

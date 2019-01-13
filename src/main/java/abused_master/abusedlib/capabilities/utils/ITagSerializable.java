package abused_master.abusedlib.capabilities.utils;

import net.minecraft.nbt.Tag;

/**
 * Used to serialize and deserialize data to Tag
 * @param <T>
 */
public interface ITagSerializable<T extends Tag> {

    /**
     * Write data to tag
     * @return - return the Tag that the data has been stored to
     */
    T serializeTag();

    /**
     * Read data from the tag
     * @param nbt - The tag it will read and set the data from
     */
    void deserializeTag(T nbt);
}

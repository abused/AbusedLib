package abused_master.abusedlib.utils;

import abused_master.abusedlib.blocks.multipart.IMultipart;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MultipartHelper {

    public static CompoundTag serialize(IMultipart multipart, CompoundTag tag) {
        multipart.getMultipartEntity().toTag(tag);
        tag.putString("multipartIdentifier", Registry.BLOCK_ENTITY.getId(multipart.getMultipartEntity().getType()).toString());

        return tag;
    }

    public static IMultipart deserialize(CompoundTag tag) {
        BlockEntity multipartEntity = Registry.BLOCK_ENTITY.get(new Identifier(tag.getString("multipartIdentifier"))).instantiate();

        if(multipartEntity != null) {
            multipartEntity.fromTag(tag);
        }

        return (IMultipart) multipartEntity;
    }
}

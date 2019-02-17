package abused_master.abusedlib.tiles;

import abused_master.abusedlib.AbusedLib;
import abused_master.abusedlib.blocks.multipart.IMultipart;
import abused_master.abusedlib.blocks.multipart.IMultipartHost;
import abused_master.abusedlib.blocks.multipart.MultipartHelper;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;
import java.util.EnumMap;

/**
 * Template multipart block entity class, extend this or create your own.
 */
public class BlockEntityMultipart extends BlockEntityBase implements IMultipartHost {

    public EnumMap<Direction, IMultipart> multiparts = Maps.newEnumMap(Direction.class);

    public BlockEntityMultipart() {
        super(AbusedLib.MULTIPART);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);

        if(tag.containsKey("multipartList")) {
            this.multiparts.clear();
            ListTag listTag = tag.getList("multipartList", NbtType.COMPOUND);

            for (int i = 0; i < listTag.size(); i++) {
                CompoundTag dataTag = listTag.getCompoundTag(i);
                Direction direction = Direction.byId(dataTag.getInt("direction"));
                IMultipart multipart = MultipartHelper.deserialize(dataTag);
                this.multiparts.put(direction, multipart);
            }
        }


    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        if(!multiparts.isEmpty()) {
            ListTag listTag = new ListTag();

            for (Direction direction : multiparts.keySet()) {
                CompoundTag dataTag = new CompoundTag();
                dataTag.putInt("direction", direction.ordinal());
                MultipartHelper.serialize(multiparts.get(direction), dataTag);
                listTag.add(dataTag);
            }

            tag.put("multipartList", listTag);
        }

        return tag;
    }

    @Override
    public void tick() {
        super.tick();
        for (IMultipart multipart : multiparts.values()) {
            if(multipart.getMultipartEntity() instanceof Tickable) {
                ((Tickable) multipart.getMultipartEntity()).tick();
            }
        }
    }

    @Override
    public boolean tryAddMultipart(Direction direction, IMultipart multipart) {
        if(!multiparts.containsKey(direction)) {
            this.multiparts.put(direction, multipart);
            this.updateEntity();
            return true;
        }

        return false;
    }

    @Override
    public boolean tryRemoveMultipart(Direction direction, IMultipart multipart) {
        if(multiparts.containsKey(direction)) {
            this.multiparts.remove(direction);
            this.updateEntity();
            return true;
        }

        return false;
    }

    @Override
    public boolean hasMultipart(Direction direction) {
        return multiparts.containsKey(direction);
    }

    @Nullable
    @Override
    public IMultipart getMultipart(Direction direction) {
        return multiparts.get(direction);
    }

    @Override
    public EnumMap<Direction, IMultipart> getMultiparts() {
        return multiparts;
    }
}

package abused_master.abusedlib.blocks.multipart;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public interface IMultipart {

    BlockEntity getMultipartEntity();

    default void onMultipartActivated(BlockPos pos, Direction direction, PlayerEntity playerEntity, Hand hand, ItemStack stack) {
    }
}

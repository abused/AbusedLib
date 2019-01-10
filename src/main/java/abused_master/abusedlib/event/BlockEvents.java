package abused_master.abusedlib.event;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEvents extends Event {

    private World world;
    private BlockPos pos;
    private BlockState state;

    public BlockEvents(World world, BlockPos pos, BlockState state) {
        this.world = world;
        this.pos = pos;
        this.state = state;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getState() {
        return state;
    }

    public static class BlockBreakEvent extends BlockEvents {

        private PlayerEntity player;

        public BlockBreakEvent(World world, BlockPos pos, BlockState state, PlayerEntity player) {
            super(world, pos, state);
            this.player = player;
        }

        public PlayerEntity getPlayer() {
            return player;
        }
    }

    public static class BlockDropsEvent extends BlockEvents {

        public BlockDropsEvent(World world, BlockPos pos, BlockState state) {
            super(world, pos, state);
        }
    }

    public static class BlockPlaceEvent extends BlockEvents {

        public BlockPlaceEvent(World world, BlockPos pos, BlockState state) {
            super(world, pos, state);
        }
    }
}

package abused_master.abusedlib.blocks.multiblock;

import com.google.common.collect.Maps;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class MultiBlock {

    private BlockPos centralPoint = null;
    private Object centralPointBlock = null;
    private Map<BlockPos, Object> multiblockComponents;
    private BlockPos size = BlockPos.ORIGIN;

    public MultiBlock() {
        this.multiblockComponents = Maps.newHashMap();
    }

    public MultiBlock(BlockPos centralPoint, Object centralPointBlock) {
        this.centralPoint = centralPoint;
        this.centralPointBlock = centralPointBlock;
        this.multiblockComponents = Maps.newHashMap();
    }

    public void addComponent(BlockPos pos, Object block) {
        this.multiblockComponents.put(pos, block);
    }

    public void removeComponent(BlockPos pos) {
        if(this.multiblockComponents.containsKey(pos)) {
            this.multiblockComponents.remove(pos);
        }
    }

    public void setCentralPoint(BlockPos pos, Object block) {
        this.centralPoint = pos;
        this.centralPointBlock = block;
    }

    public BlockPos getCentralPoint() {
        return centralPoint;
    }

    public Object getCentralPointBlock() {
        return this.centralPointBlock;
    }

    public Map<BlockPos, Object> getMultiblockComponents() {
        return this.multiblockComponents;
    }

    public Object getComponent(BlockPos pos) {
        return this.multiblockComponents.get(pos);
    }

    public void setSize(BlockPos size) {
        this.size = size;
    }

    public BlockPos getSize() {
        return size;
    }
}

package abused_master.abusedlib.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TileEntityBase extends TileEntity implements ITickable {

    public TileEntityBase() {
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
    }

    /**
     * old update methods
     @Nullable
     @Override
     public SPacketUpdateTileEntity getUpdatePacket() {
     NBTTagCompound data = new NBTTagCompound();
     writeToNBT(data);
     return new SPacketUpdateTileEntity(this.pos, 1, data);
     }

     @Override
     @SideOnly(Side.CLIENT)
     public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity s35PacketUpdateTileEntity) {
     readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
     world.markBlockRangeForRenderUpdate(this.pos, this.pos);
     if (this.world != null) {
     this.world.notifyBlockUpdate(this.pos, world.getBlockState(this.pos), world.getBlockState(this.pos), 3);
     }
     }

     @Override
     public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
     return oldState.getBlock() != newState.getBlock();
     }

     @Override
     public NBTTagCompound getUpdateTag()
     {
     NBTTagCompound nbtTagCompound = new NBTTagCompound();
     writeToNBT(nbtTagCompound);
     return nbtTagCompound;
     }

     @Override
     public void handleUpdateTag(NBTTagCompound tag)
     {
     this.readFromNBT(tag);
     }
     */
    //////////////////////////////////
    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
    }

    /**
     * call when data changes to update
     */
    public void sendUpdates() {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, getState(), getState(), 3);
        world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
        markDirty();
    }

    public IBlockState getState() {
        return world.getBlockState(pos);
    }
}

package abused_master.abusedlib.fluid;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

public interface IFluidTank {

    boolean extractFluid(int amount);

    boolean fillFluid(FluidStack stack);

    int getFluidAmount();

    int getFluidCapacity();

    FluidStack getFluidStack();

    void setCapacity(int capacity);

    void setFluidStack(FluidStack stack);

    BlockEntity getBlockEntity();

    void setBlockEntity(BlockEntity blockEntity);

    FluidTank readFromNBT(CompoundTag nbt);

    CompoundTag writeToNBT(CompoundTag nbt);
}

package abused_master.abusedlib.fluid;

import abused_master.abusedlib.utils.CacheMapHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class FluidHelper {

    public static boolean fillFluidHandler(ItemStack stack, IFluidHandler fluidHandler, PlayerEntity player) {
        if (stack.isEmpty() || fluidHandler == null || player == null || !(stack.getItem() instanceof BucketItem)) {
            return false;
        }

        BucketItem bucketItem = (BucketItem) stack.getItem();
        Fluid fluid = CacheMapHolder.INSTANCE.getFluidForBucket(bucketItem);

        if (fluid != null && fluid != Fluids.EMPTY) {
            if (fluidHandler.getFluidTank().getFluidStack() == null) {
                fluidHandler.getFluidTank().setFluidStack(new FluidStack(fluid, 1000));
                if (!player.isCreative()) {
                    player.getMainHandStack().subtractAmount(1);
                    player.giveItemStack(new ItemStack(Items.BUCKET));
                }
                return true;
            } else if (fluidHandler.getFluidTank().getFluidStack().getFluid() == fluid) {
                if ((fluidHandler.getFluidTank().getFluidAmount() + 1000) <= fluidHandler.getFluidTank().getFluidCapacity()) {
                    fluidHandler.getFluidTank().fillFluid(new FluidStack(fluid, 1000));
                    if (!player.isCreative()) {
                        player.getMainHandStack().subtractAmount(1);
                        player.giveItemStack(new ItemStack(Items.BUCKET));
                    }
                    return true;
                }
            }
        } else if (fluid == Fluids.EMPTY || fluid == null) {
            if (fluidHandler.getFluidTank().getFluidStack() != null && fluidHandler.getFluidTank().getFluidAmount() >= 1000) {
                if (!player.isCreative()) {
                    player.giveItemStack(new ItemStack(fluidHandler.getFluidTank().getFluidStack().getFluid().getBucketItem()));
                    player.getMainHandStack().subtractAmount(1);
                }
                fluidHandler.getFluidTank().extractFluid(1000);
                return true;
            }
        }

        return false;
    }
}

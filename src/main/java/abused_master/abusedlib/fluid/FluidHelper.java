package abused_master.abusedlib.fluid;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;

public class FluidHelper {

    public static boolean fillFluidHandler(ItemStack stack, IFluidHandler fluidHandler, PlayerEntity player) {
        Fluid fluid;
        if (stack.isEmpty() || fluidHandler == null || player == null || !(stack.getItem() instanceof BucketItem)) {
            return false;
        }

        BucketItem bucketItem = (BucketItem) stack.getItem();
        try {
            Field fluidField = BucketItem.class.getDeclaredField("fluid");
            fluidField.setAccessible(true);
            fluid = (Fluid) fluidField.get(bucketItem);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }

        if(fluid != null) {
            if(fluidHandler.getFluidTank().getFluidStack() == null) {
                fluidHandler.getFluidTank().setFluidStack(new FluidStack(fluid, 1000));
                return true;
            }else if(fluidHandler.getFluidTank().getFluidStack().getFluid() == fluid) {
                if((fluidHandler.getFluidTank().getFluidAmount() + 1000) <= fluidHandler.getFluidTank().getFluidCapacity()) {
                    fluidHandler.getFluidTank().fillFluid(new FluidStack(fluid, 1000));
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }

        return false;
    }
}

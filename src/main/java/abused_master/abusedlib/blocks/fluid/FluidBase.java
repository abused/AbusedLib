package abused_master.abusedlib.blocks.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidBase extends Fluid {

    public FluidBase(String fluidName, String modid) {
        super(fluidName, new ResourceLocation(modid, "blocks/fluids/" + fluidName + "_still"), new ResourceLocation(modid, "blocks/fluids/" + fluidName + "_flowing"));
    }

    public void register() {
        FluidRegistry.registerFluid(this);
        FluidRegistry.addBucketForFluid(this);
    }
}

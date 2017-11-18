package abused_master.abusedlib.blocks.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidBlock extends BlockFluidClassic {

    public FluidBlock(Fluid fluid, Material material) {
        super(fluid, material);
        this.setRegistryName(fluid.getName());
        this.setUnlocalizedName(fluidName);
    }

    @SideOnly(Side.CLIENT)
    public void regFluid() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
    }
}

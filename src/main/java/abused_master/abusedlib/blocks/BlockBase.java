package abused_master.abusedlib.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockBase extends BlockContainer {

    public BlockBase(Material material, String name, CreativeTabs tab) {
        super(material);
        this.setCreativeTab(tab);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setHardness(1.0f);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }

}


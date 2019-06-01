package abused_master.abusedlib.client.render.obj;

import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelItemPropertyOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ExtendedBlockView;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public class OBJModel implements BakedModel, FabricBakedModel {

    private Mesh mesh;
    private Sprite sprite;
    private VertexFormat format;

    public OBJModel(Mesh mesh) {
        this.mesh = mesh;
        this.format = VertexFormats.POSITION_COLOR_UV_NORMAL;
        //TODO later for now just get the model rendering
        this.sprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, Random random) {
        List<BakedQuad>[] bakedQuads =  ModelHelper.toQuadLists(mesh);
        return bakedQuads[direction == null ? 6 : direction.getId()];
    }

    @Override
    public void emitBlockQuads(ExtendedBlockView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        if(mesh != null) {
            context.meshConsumer().accept(mesh);
        }
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        if(mesh != null) {
            context.meshConsumer().accept(mesh);
        }
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean hasDepthInGui() {
        return true;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public ModelTransformation getTransformation() {
        return ModelTransformation.NONE;
    }

    @Override
    public ModelItemPropertyOverrideList getItemPropertyOverrides() {
        return ModelItemPropertyOverrideList.EMPTY;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }
}

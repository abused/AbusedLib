package abused_master.abusedlib.client.render;

import abused_master.abusedlib.fluid.FluidStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class RenderHelper {

    public static final int MAX_LIGHT_X = 0xF000F0;
    public static final int MAX_LIGHT_Y = 0xF000F0;

    public static void renderLaser(double firstX, double firstY, double firstZ, double secondX, double secondY, double secondZ, double rotationTime, float alpha, double beamWidth, float[] color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder render = tessellator.getBufferBuilder();
        World world = MinecraftClient.getInstance().world;

        float r = color[0];
        float g = color[1];
        float b = color[2];

        Vec3d vec1 = new Vec3d(firstX, firstY, firstZ);
        Vec3d vec2 = new Vec3d(secondX, secondY, secondZ);
        Vec3d combinedVec = vec2.subtract(vec1);

        double rot = rotationTime > 0 ? (360D * ((world.getTime() % rotationTime) / rotationTime)) : 0;
        double pitch = Math.atan2(combinedVec.y, Math.sqrt(combinedVec.x * combinedVec.x + combinedVec.z * combinedVec.z));
        double yaw = Math.atan2(-combinedVec.z, combinedVec.x);

        double length = combinedVec.length();

        GlStateManager.pushMatrix();

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        int func = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
        float ref = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
        GlStateManager.alphaFunc(GL11.GL_ALWAYS, 0);
        GlStateManager.translated(firstX - BlockEntityRenderDispatcher.renderOffsetX, firstY - BlockEntityRenderDispatcher.renderOffsetY, firstZ - BlockEntityRenderDispatcher.renderOffsetZ);
        GlStateManager.rotatef((float) (180 * yaw / Math.PI), 0, 1, 0);
        GlStateManager.rotatef((float) (180 * pitch / Math.PI), 0, 0, 1);
        GlStateManager.rotatef((float) rot, 1, 0, 0);

        GlStateManager.disableTexture();
        render.begin(GL11.GL_QUADS, VertexFormats.POSITION_UV_LMAP_COLOR);
        for (double i = 0; i < 4; i++) {
            double width = beamWidth * (i / 4.0);
            render.vertex(length, width, width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();
            render.vertex(0, width, width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();
            render.vertex(0, -width, width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();
            render.vertex(length, -width, width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();

            render.vertex(length, -width, -width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();
            render.vertex(0, -width, -width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();
            render.vertex(0, width, -width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();
            render.vertex(length, width, -width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();

            render.vertex(length, width, -width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();
            render.vertex(0, width, -width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();
            render.vertex(0, width, width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();
            render.vertex(length, width, width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();

            render.vertex(length, -width, width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();
            render.vertex(0, -width, width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();
            render.vertex(0, -width, -width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();
            render.vertex(length, -width, -width).texture(0, 0).texture(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).next();
        }
        tessellator.draw();

        GlStateManager.enableTexture();

        GlStateManager.alphaFunc(func, ref);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    public static void renderFluid(FluidStack fluid, BlockPos pos, double x, double y, double z, double x1, double y1, double z1, double x2, double y2, double z2) {
        renderFluid(fluid, pos, x, y, z, x1, y1, z1, x2, y2, z2, 0xFFFFFFFF);
    }

    public static void renderFluid(FluidStack fluid, BlockPos pos, double x, double y, double z, double x1, double y1, double z1, double x2, double y2, double z2, int color) {
        MinecraftClient mc = MinecraftClient.getInstance();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        int brightness = mc.world.getLightLevel(LightType.BLOCK, pos);

        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR_UV_LMAP);
        Identifier spriteIdentifier = mc.getBakedModelManager().getBlockStateMaps().getModel(fluid.getFluid().getDefaultState().getBlockState()).getSprite().getId();
        Sprite sprite = mc.getSpriteAtlas().getSprite(spriteIdentifier);
        mc.getTextureManager().bindTexture(new Identifier(spriteIdentifier.getNamespace(), "textures/" + spriteIdentifier.getPath() + ".png"));

        setupRenderState();
        GlStateManager.translated(x, y, z);
        addTexturedQuad(buffer, sprite, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, Direction.DOWN, color, brightness);
        addTexturedQuad(buffer, sprite, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, Direction.NORTH, color, brightness);
        addTexturedQuad(buffer, sprite, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, Direction.EAST, color, brightness);
        addTexturedQuad(buffer, sprite, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, Direction.SOUTH, color, brightness);
        addTexturedQuad(buffer, sprite, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, Direction.WEST, color, brightness);
        addTexturedQuad(buffer, sprite, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, Direction.UP, color, brightness);
        tessellator.draw();
        cleanupRenderState();
    }

    public static void addTexturedQuad(BufferBuilder buffer, Sprite sprite, double x, double y, double z, double width, double height, double length, Direction face, int color, int brightness) {
        if (sprite == null) {
            return;
        }

        final int firstLightValue = brightness >> 0x10 & 0xFFFF;
        final int secondLightValue = brightness & 0xFFFF;
        final int alpha = color >> 24 & 0xFF;
        final int red = color >> 16 & 0xFF;
        final int green = color >> 8 & 0xFF;
        final int blue = color & 0xFF;

        addTextureQuad(buffer, sprite, x, y, z, width, height, length, face, red, green, blue, alpha, firstLightValue, secondLightValue);
    }

    public static void addTextureQuad(BufferBuilder buffer, Sprite sprite, double x, double y, double z, double width, double height, double length, Direction face, int red, int green, int blue, int alpha, int light1, int light2) {
        double minU;
        double maxU;
        double minV;
        double maxV;

        final double size = 16f;

        final double x2 = x + width;
        final double y2 = y + height;
        final double z2 = z + length;

        final double u = x % 1d;
        double u1 = u + width;

        while (u1 > 1f) {
            u1 -= 1f;
        }

        final double vy = y % 1d;
        double vy1 = vy + height;

        while (vy1 > 1f) {
            vy1 -= 1f;
        }

        final double vz = z % 1d;
        double vz1 = vz + length;

        while (vz1 > 1f) {
            vz1 -= 1f;
        }

        switch (face) {

            case DOWN:

            case UP:
                minU = sprite.getU(u * size);
                maxU = sprite.getU(u1 * size);
                minV = sprite.getV(vz * size);
                maxV = sprite.getV(vz1 * size);
                break;

            case NORTH:

            case SOUTH:
                minU = sprite.getU(u1 * size);
                maxU = sprite.getU(u * size);
                minV = sprite.getV(vy * size);
                maxV = sprite.getV(vy1 * size);
                break;

            case WEST:

            case EAST:
                minU = sprite.getU(vz1 * size);
                maxU = sprite.getU(vz * size);
                minV = sprite.getV(vy * size);
                maxV = sprite.getV(vy1 * size);
                break;

            default:
                minU = sprite.getMinU();
                maxU = sprite.getMaxU();
                minV = sprite.getMinV();
                maxV = sprite.getMaxV();
        }

        switch (face) {

            case DOWN:
                buffer.vertex(x, y, z).color(red, green, blue, alpha).texture(minU, minV).texture(light1, light2).next();
                buffer.vertex(x2, y, z).color(red, green, blue, alpha).texture(maxU, minV).texture(light1, light2).next();
                buffer.vertex(x2, y, z2).color(red, green, blue, alpha).texture(maxU, maxV).texture(light1, light2).next();
                buffer.vertex(x, y, z2).color(red, green, blue, alpha).texture(minU, maxV).texture(light1, light2).next();
                break;

            case UP:
                buffer.vertex(x, y2, z).color(red, green, blue, alpha).texture(minU, minV).texture(light1, light2).next();
                buffer.vertex(x, y2, z2).color(red, green, blue, alpha).texture(minU, maxV).texture(light1, light2).next();
                buffer.vertex(x2, y2, z2).color(red, green, blue, alpha).texture(maxU, maxV).texture(light1, light2).next();
                buffer.vertex(x2, y2, z).color(red, green, blue, alpha).texture(maxU, minV).texture(light1, light2).next();
                break;

            case NORTH:
                buffer.vertex(x, y, z).color(red, green, blue, alpha).texture(minU, maxV).texture(light1, light2).next();
                buffer.vertex(x, y2, z).color(red, green, blue, alpha).texture(minU, minV).texture(light1, light2).next();
                buffer.vertex(x2, y2, z).color(red, green, blue, alpha).texture(maxU, minV).texture(light1, light2).next();
                buffer.vertex(x2, y, z).color(red, green, blue, alpha).texture(maxU, maxV).texture(light1, light2).next();
                break;

            case SOUTH:
                buffer.vertex(x, y, z2).color(red, green, blue, alpha).texture(maxU, maxV).texture(light1, light2).next();
                buffer.vertex(x2, y, z2).color(red, green, blue, alpha).texture(minU, maxV).texture(light1, light2).next();
                buffer.vertex(x2, y2, z2).color(red, green, blue, alpha).texture(minU, minV).texture(light1, light2).next();
                buffer.vertex(x, y2, z2).color(red, green, blue, alpha).texture(maxU, minV).texture(light1, light2).next();
                break;

            case WEST:
                buffer.vertex(x, y, z).color(red, green, blue, alpha).texture(maxU, maxV).texture(light1, light2).next();
                buffer.vertex(x, y, z2).color(red, green, blue, alpha).texture(minU, maxV).texture(light1, light2).next();
                buffer.vertex(x, y2, z2).color(red, green, blue, alpha).texture(minU, minV).texture(light1, light2).next();
                buffer.vertex(x, y2, z).color(red, green, blue, alpha).texture(maxU, minV).texture(light1, light2).next();
                break;

            case EAST:
                buffer.vertex(x2, y, z).color(red, green, blue, alpha).texture(minU, maxV).texture(light1, light2).next();
                buffer.vertex(x2, y2, z).color(red, green, blue, alpha).texture(minU, minV).texture(light1, light2).next();
                buffer.vertex(x2, y2, z2).color(red, green, blue, alpha).texture(maxU, minV).texture(light1, light2).next();
                buffer.vertex(x2, y, z2).color(red, green, blue, alpha).texture(maxU, maxV).texture(light1, light2).next();
                break;
        }
    }

    public static void translateAgainstPlayer(BlockPos pos, boolean offset) {
        float x = (float) (pos.getX() - BlockEntityRenderDispatcher.renderOffsetX);
        float y = (float) (pos.getY() - BlockEntityRenderDispatcher.renderOffsetY);
        float z = (float) (pos.getZ() - BlockEntityRenderDispatcher.renderOffsetZ);
        if (offset) {
            GlStateManager.translatef(x + 0.5f, y + 0.5f, z + 0.5f);
        } else {
            GlStateManager.translatef(x, y, z);
        }
    }

    public static void setupRenderState() {
        GlStateManager.pushMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        if (MinecraftClient.isAmbientOcclusionEnabled()) {
            GL11.glShadeModel(GL11.GL_SMOOTH);
        } else {
            GL11.glShadeModel(GL11.GL_FLAT);
        }
    }

    public static void cleanupRenderState() {
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();
    }

    public static void disableStandardItemLighting() {
        GlStateManager.disableLighting();
        GlStateManager.disableLight(0);
        GlStateManager.disableLight(1);
        GlStateManager.disableColorMaterial();
    }

    public static void enableStandardItemLighting() {
        GlStateManager.enableLighting();
        GlStateManager.enableLight(0);
        GlStateManager.enableLight(1);
        GlStateManager.enableColorMaterial();
        GlStateManager.colorMaterial(1032, 5634);
        GlStateManager.light(16384, 4611, setColorBuffer(LIGHT0_POS.x, LIGHT0_POS.y, LIGHT0_POS.z, 0.0D));
        float f = 0.6F;
        GlStateManager.light(16384, 4609, setColorBuffer(0.6F, 0.6F, 0.6F, 1.0F));
        GlStateManager.light(16384, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        GlStateManager.light(16384, 4610, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        GlStateManager.light(16385, 4611, setColorBuffer(LIGHT1_POS.x, LIGHT1_POS.y, LIGHT1_POS.z, 0.0D));
        GlStateManager.light(16385, 4609, setColorBuffer(0.6F, 0.6F, 0.6F, 1.0F));
        GlStateManager.light(16385, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        GlStateManager.light(16385, 4610, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        GlStateManager.shadeModel(7424);
        float f1 = 0.4F;
        GlStateManager.lightModel(2899, setColorBuffer(0.4F, 0.4F, 0.4F, 1.0F));
    }

    private static final FloatBuffer COLOR_BUFFER = createDirectFloatBuffer(4);
    private static final Vec3d LIGHT0_POS = (new Vec3d(0.20000000298023224D, 1.0D, -0.699999988079071D)).normalize();
    private static final Vec3d LIGHT1_POS = (new Vec3d(-0.20000000298023224D, 1.0D, 0.699999988079071D)).normalize();

    public static FloatBuffer setColorBuffer(double p_74521_0_, double p_74521_1_, double p_74521_2_, double p_74521_3_) {
        COLOR_BUFFER.clear();
        COLOR_BUFFER.put((float) p_74521_0_).put((float) p_74521_1_).put((float) p_74521_2_).put((float) p_74521_3_);
        COLOR_BUFFER.flip();
        return COLOR_BUFFER;
    }

    public static synchronized ByteBuffer createDirectByteBuffer(int capacity) {
        return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
    }

    public static FloatBuffer createDirectFloatBuffer(int capacity) {
        return createDirectByteBuffer(capacity << 2).asFloatBuffer();
    }
}
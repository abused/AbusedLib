package abused_master.abusedlib.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

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
        GlStateManager.blendFunc(GlStateManager.class_1033.SRC_ALPHA, GlStateManager.class_1027.ONE);
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
        GlStateManager.blendFunc(GlStateManager.class_1033.SRC_ALPHA, GlStateManager.class_1027.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
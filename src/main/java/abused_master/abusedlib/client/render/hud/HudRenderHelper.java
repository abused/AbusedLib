package abused_master.abusedlib.client.render.hud;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.util.math.Direction;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Credits to McJty for this
 */
public class HudRenderHelper {

    public static void renderHud(List<String> messages, HudPlacement hudPlacement, HudOrientation hudOrientation, Direction orientation, double x, double y, double z, float scale) {
        GlStateManager.pushMatrix();

        if (hudPlacement == HudPlacement.HUD_FRONT) {
            GlStateManager.translatef((float) x + 0.5F, (float) y + 0.75F, (float) z + 0.5F);
        } else if (hudPlacement == HudPlacement.HUD_CENTER) {
            GlStateManager.translatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        } else {
            GlStateManager.translatef((float) x + 0.5F, (float) y + 1.75F, (float) z + 0.5F);
        }

        switch (hudOrientation) {
            case HUD_SOUTH:
                GlStateManager.rotatef(-getHudAngle(orientation), 0.0F, 1.0F, 0.0F);
                break;
            case HUD_TOPLAYER_HORIZ:
                GlStateManager.rotatef(-MinecraftClient.getInstance().getEntityRenderManager().field_4679, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotatef(180, 0.0F, 1.0F, 0.0F);
                break;
            case HUD_TOPLAYER:
                GlStateManager.rotatef(-MinecraftClient.getInstance().getEntityRenderManager().field_4679, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotatef(MinecraftClient.getInstance().getEntityRenderManager().field_4677, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotatef(180, 0.0F, 1.0F, 0.0F);
                break;
        }

        if (hudPlacement == HudPlacement.HUD_FRONT || hudPlacement == HudPlacement.HUD_ABOVE_FRONT) {
            GlStateManager.translatef(0.0F, -0.2500F, (float) (-0.4375F + .9));
        } else if (hudPlacement != HudPlacement.HUD_CENTER) {
            GlStateManager.translatef(0.0F, -0.2500F, (float) (-0.4375F + .4));
        }

        GlStateManager.disableBlend();
        GlStateManager.disableLighting();

        renderText(MinecraftClient.getInstance().textRenderer, messages, 11, scale);

        GlStateManager.enableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.popMatrix();
    }

    private static float getHudAngle(Direction orientation) {
        float f3 = 0.0f;

        if (orientation != null) {
            switch (orientation) {
                case NORTH:
                    f3 = 180.0F;
                    break;
                case WEST:
                    f3 = 90.0F;
                    break;
                case EAST:
                    f3 = -90.0F;
                    break;
                default:
                    f3 = 0.0f;
            }
        }
        return f3;
    }

    private static void renderText(TextRenderer fontrenderer, List<String> messages, int lines, float scale) {
        GlStateManager.translatef(-0.5F, 0.5F, 0.07F);
        float f3 = 0.0075F;
        GlStateManager.scalef(f3 * scale, -f3 * scale, f3);
        GlStateManager.normal3f(0.0F, 0.0F, 1.0F);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        renderLog(fontrenderer, messages, lines);
    }

    private static void renderLog(TextRenderer fontrenderer, List<String> messages, int lines) {
        int currently = 7;
        int height = 10;
        int logsize = messages.size();
        int i = 0;
        for (String s : messages) {
            if (i >= logsize - lines) {
                if (currently + height <= 124) {
                    fontrenderer.draw(s, 7, currently, 0xffffff);
                    currently += height;
                }
            }
            i++;
        }
    }

    public static enum HudPlacement {
        HUD_ABOVE,
        HUD_ABOVE_FRONT,
        HUD_FRONT,
        HUD_CENTER
    }

    public static enum HudOrientation {
        HUD_SOUTH,
        HUD_TOPLAYER_HORIZ,
        HUD_TOPLAYER
    }
}
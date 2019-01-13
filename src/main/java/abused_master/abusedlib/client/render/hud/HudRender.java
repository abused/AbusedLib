package abused_master.abusedlib.client.render.hud;

import net.minecraft.util.math.Direction;

import java.util.List;

/**
 * Credits to McJty for this
 */
public class HudRender {

    public static void renderHud(IHudSupport hudSupport, double x, double y, double z) {
        renderHud(hudSupport, x, y, z, 0.0f, false);
    }

    public static void renderHud(IHudSupport support, double x, double y, double z, float scale, boolean faceVert) {
        List<String> log = support.getClientLog();
        Direction orientation = support.getBlockOrientation();
        HudRenderHelper.HudPlacement hudPlacement = support.isBlockAboveAir() ? HudRenderHelper.HudPlacement.HUD_ABOVE : HudRenderHelper.HudPlacement.HUD_ABOVE_FRONT;
        HudRenderHelper.HudOrientation hudOrientation = orientation == null ? HudRenderHelper.HudOrientation.HUD_TOPLAYER_HORIZ : HudRenderHelper.HudOrientation.HUD_SOUTH;
        HudRenderHelper.renderHud(log, hudPlacement, hudOrientation, orientation, x, y - 0.5, z, 1.0f + scale);
    }
}
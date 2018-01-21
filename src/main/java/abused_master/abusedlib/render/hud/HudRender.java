package abused_master.abusedlib.render.hud;

import net.minecraft.util.EnumFacing;

public class HudRender {

    public static void renderHud(IHudSupport hudSupport, double x, double y, double z) {
        renderHud(hudSupport, x, y, z, 0.0f, false);
    }

    public static void renderHud(IHudSupport support, double x, double y, double z, float scale, boolean faceVert) {
        String display = support.getDisplay();
        EnumFacing orientation = support.getBlockOrientation();
        HudRenderHelper.HudPlacement hudPlacement = support.isBlockAboveAir() ? HudRenderHelper.HudPlacement.HUD_ABOVE : HudRenderHelper.HudPlacement.HUD_ABOVE_FRONT;
        HudRenderHelper.HudOrientation orientation1 = HudRenderHelper.HudOrientation.HUD_TOPLAYER;
        HudRenderHelper.renderHud(display, hudPlacement, orientation1, orientation, x, y - 0.5, z, 1.3f + scale);
    }
}
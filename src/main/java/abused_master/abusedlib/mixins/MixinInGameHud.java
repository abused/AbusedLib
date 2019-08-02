package abused_master.abusedlib.mixins;

import abused_master.abusedlib.utils.events.RenderHudCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private int scaledWidth;

    @Shadow
    private int scaledHeight;

    @Inject(method = "render", at = @At("RETURN"))
    public void draw(float partialTicks, CallbackInfo ci) {
        RenderHudCallback.EVENT.invoker().renderHud(client, scaledWidth, scaledHeight, client.window.getScaleFactor(), partialTicks);
    }
}

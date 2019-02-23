package abused_master.abusedlib.mixins;

import net.minecraft.client.gui.SplashScreen;
import net.minecraft.client.texture.Texture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

//TODO REMOVE WHEN MOJANG FIXES THEIR RAM USAGE DERP
@Mixin(SplashScreen.class)
public class MixinSplashScreen {

    @Redirect(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;registerTexture(Lnet/minecraft/util/Identifier;Lnet/minecraft/client/texture/Texture;)Z"))
    private boolean fixMojangRetardation(TextureManager textureManager, Identifier identifier, Texture texture) {
        return false;
    }
}

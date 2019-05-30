package abused_master.abusedlib.mixins;

import abused_master.abusedlib.client.render.obj.OBJLoader;
import abused_master.abusedlib.client.render.obj.ObjUnbakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.InputStreamReader;
import java.io.Reader;

@Mixin(ModelLoader.class)
public abstract class MixinModelLoader {

    @Final
    @Shadow
    private ResourceManager resourceManager;

    //@Inject(method = "loadModel", at = @At(target = "Lnet/minecraft/client/render/model/ModelLoader;putModel(Lnet/minecraft/util/Identifier;Lnet/minecraft/client/render/model/UnbakedModel;)V", value = "INVOKE", shift = At.Shift.BEFORE), cancellable = true)
    @Inject(method = "loadModel", at = @At("HEAD"), cancellable = true)
    private void loadModel(Identifier identifier, CallbackInfo ci) throws Exception {
        if(OBJLoader.INSTANCE.isRegisteredDomain(identifier.getNamespace()) && identifier.getPath().endsWith(".obj")) {
            Resource resource = this.resourceManager.getResource(new Identifier(identifier.getNamespace(), "models/" + identifier.getPath()));

            try (Reader reader = new InputStreamReader(resource.getInputStream())) {
                this.putModel(identifier, new ObjUnbakedModel(OBJLoader.INSTANCE.loadModel(reader)));
            }

            ci.cancel();
        }
    }

    @Shadow
    abstract void putModel(Identifier identifier_1, UnbakedModel unbakedModel_1);
}

package abused_master.abusedlib.client.render.obj;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

public class ObjUnbakedModel implements UnbakedModel {

    private OBJModel model;

    public ObjUnbakedModel(OBJModel model) {
        this.model = model;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Collection<Identifier> getTextureDependencies(Function<Identifier, UnbakedModel> var1, Set<String> var2) {
        return ImmutableSet.of(model.getSprite().getId());
    }

    @Nullable
    @Override
    public BakedModel bake(ModelLoader var1, Function<Identifier, Sprite> var2, ModelBakeSettings var3) {
        return model;
    }
}

package abused_master.abusedlib.mixins;

import abused_master.abusedlib.utils.CacheMapHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mixin(RecipeManager.class)
public abstract class MixinRecipeManager {

    @Inject(method = "add", at = @At("RETURN"))
    public void add(Recipe<?> recipe_1, CallbackInfo ci) {
        if (recipe_1.getType() == RecipeType.SMELTING) {
            Method method = null;
            Field field = null;

            try {
                method = Ingredient.class.getDeclaredMethod("method_8096");
                field = Ingredient.class.getDeclaredField("field_9018");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                try {
                    method = Ingredient.class.getDeclaredMethod("createStackArray");
                    field = Ingredient.class.getDeclaredField("stackArray");
                } catch (NoSuchMethodException | NoSuchFieldException e1) {
                    e1.printStackTrace();
                }
            }

            try {
                method.setAccessible(true);
                field.setAccessible(true);
                method.invoke(recipe_1.getPreviewInputs().get(0));
                ItemStack input = ((ItemStack[]) field.get(recipe_1.getPreviewInputs().get(0)))[0];
                if (!CacheMapHolder.INSTANCE.furnaceRecipes.containsKey(input)) {
                    CacheMapHolder.INSTANCE.furnaceRecipes.put(input, recipe_1.getOutput());
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
package abused_master.abusedlib.api.recipe;

import net.minecraft.item.ItemStack;

public interface IAltarRecipeHandler {

    void addRecipe(ItemStack water, ItemStack fire, ItemStack earth, ItemStack air, ItemStack output);

}

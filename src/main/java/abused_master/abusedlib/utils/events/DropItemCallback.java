package abused_master.abusedlib.utils.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface DropItemCallback {

    Event<DropItemCallback> EVENT = EventFactory.createArrayBacked(DropItemCallback.class, (listeners) -> (player, stack, moveEntity, isPlayer) -> {
        for (DropItemCallback callback : listeners) {
            callback.dropItem(player, stack, moveEntity, isPlayer);
        }
    });

    void dropItem(PlayerEntity player, ItemStack stack, boolean moveEntity, boolean isPlayer);
}

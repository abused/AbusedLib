package abused_master.abusedlib.utils.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;

public interface RenderHudCallback {

    Event<RenderHudCallback> EVENT = EventFactory.createArrayBacked(RenderHudCallback.class, (listeners) -> (client, scaledWidth, scaledHeight, scaleFactor, partialTicks) -> {
        for (RenderHudCallback callback : listeners) {
            callback.renderHud(client, scaledWidth, scaledHeight, scaleFactor, partialTicks);
        }
    });

    void renderHud(MinecraftClient client, int scaledWidth, int scaledHeight, double scaleFactor, float partialTicks);
}

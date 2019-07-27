package abused_master.abusedlib;

import abused_master.abusedlib.client.shaders.ShaderHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;

public class AbusedLibClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickCallback.EVENT.register(client -> {
            if(client.currentScreen == null || !client.currentScreen.isPauseScreen()) {
                ShaderHelper.ticksInGame++;
            }
        });
    }
}
